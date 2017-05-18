package com.lackhite.borrowland.server.controller;

import com.lackhite.borrowland.server.domain.ActiveLoan;
import com.lackhite.borrowland.server.domain.ClosedLoan;
import com.lackhite.borrowland.server.domain.LoanContainer;
import com.lackhite.borrowland.server.domain.User;

import com.lackhite.borrowland.server.repositories.ActiveLoanRepository;
import com.lackhite.borrowland.server.repositories.ClosedLoanRepository;
import com.lackhite.borrowland.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lackhite.borrowland.server.StaticDateFormat.*;


@CrossOrigin
@RestController
public class Controller {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClosedLoanRepository closedLoanRepository;
    @Autowired
    private ActiveLoanRepository activeLoanRepository;

    // TODO: обработать неправильные запросы
    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    @ResponseBody public HttpStatus addUser(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "name") String name
    ) {
        /*System.out.println(id);
        System.out.println(name);*/

      //  if (userRepository.findById(id) == null) {
            User user = new User(id, name);
            userRepository.save(user);
            return HttpStatus.OK;
      //  } else
      //      return HttpStatus.BAD_REQUEST;
    }

    // TODO: обработать неправильные запросы
    @RequestMapping(value = "/addLoan", method = RequestMethod.GET)
    public HttpStatus addLoan(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "sum") Long sum,
            @RequestParam(name = "part_id") Long partId,
            @RequestParam(name = "part_name") String partName
    ) {
        Date date = new Date();

        ActiveLoan loan = new ActiveLoan(
                partId,
                partName,
                sum,
                dateFormat.format(date),
                timeFormat.format(date));

        loan.setId(date.getTime());
        activeLoanRepository.save(loan);
        User user = userRepository.findById(id);
        user.getActiveLoans().add(loan);
        userRepository.save(user);

        return HttpStatus.OK;
    }

    // TODO: обработка ошибок
    @RequestMapping(value = "/closeLoan", method = RequestMethod.GET)
    public HttpStatus closeLoan(
            @RequestParam(name = "client_id") Long clientId,
            @RequestParam(name = "loan_id") Long loanId
    ) {
        ActiveLoan activeLoan = activeLoanRepository.findById(loanId);
        System.err.println(activeLoan.getId());
        ClosedLoan closedLoan = new ClosedLoan(activeLoan);

        activeLoanRepository.delete(activeLoan);

        closedLoanRepository.save(closedLoan);

        User user = userRepository.findById(clientId);

        List<ClosedLoan> list = user.getClosedLoans();
        list.add(closedLoan);
        user.setClosedLoans(list);

        System.err.println(user.getClosedLoans().size());

        userRepository.save(user);
        return HttpStatus.OK;
    }

    // TODO:
    @RequestMapping(value = "/getActiveLoans", method = RequestMethod.GET)
    @ResponseBody public LoanContainer<ActiveLoan> getActiveLoans(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "count", required = false, defaultValue = "-1") Long count,
            @RequestParam(name = "offset", required = false, defaultValue = "0") Long offset
    ) {
        User user = userRepository.findById(id);

        List<ActiveLoan> list;

        if (offset < user.getActiveLoans().size() && offset >= 0) {
            if ((count == -1 || count + offset > user.getActiveLoans().size()) && count >= -1)
                list = user.getActiveLoans().subList(
                        offset.intValue(),
                        user.getActiveLoans().size()
                );
            else if (count >= 0)
                list = user.getActiveLoans().subList(
                        offset.intValue(),
                        offset.intValue() + count.intValue()
                );
            else
                list = new ArrayList<>();

        } else
            list = new ArrayList<>();

        return new LoanContainer<>(
                user.getActiveLoans().size(),
                list
        );
    }

    // TODO:
    @RequestMapping(value = "/getHistoryLoans", method = RequestMethod.GET)
    public LoanContainer<ClosedLoan> getHistoryLoans(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "count", required = false, defaultValue = "-1") Long count,
            @RequestParam(name = "offset", required = false, defaultValue = "0") Long offset
    ) {
        User user = userRepository.findById(id);

        List<ClosedLoan> list;

        if (offset < user.getClosedLoans().size() && offset >= 0) {
            if ((count == -1 || count + offset > user.getClosedLoans().size()) && count >= -1)
                list = user.getClosedLoans().subList(
                        offset.intValue(),
                        user.getClosedLoans().size()
                );
            else if (count >= 0)
                list = user.getClosedLoans().subList(
                        offset.intValue(),
                        offset.intValue() + count.intValue()
                );
            else
                list = new ArrayList<>();

        } else
            list = new ArrayList<>();

        return new LoanContainer<>(
                user.getClosedLoans().size(),
                list
        );
    }


//////////////////////////////////////////////////////


    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public User getUser(
            @RequestParam(name = "id") Long clientId
    ) throws Exception {
        User user = userRepository.findById(clientId);
        if (user == null)
            throw new Exception();
        return userRepository.findById(clientId);
    }

    @RequestMapping(value = "/clear")
    public HttpStatus clear() {
        userRepository.deleteAll();
        activeLoanRepository.deleteAll();
        closedLoanRepository.deleteAll();
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/deleteUser")
    public HttpStatus deleteUser(
            @RequestParam(name = "id") Long clientId
    ) {
        User user = userRepository.findById(clientId);
        List<ActiveLoan> activeLoans = user.getActiveLoans();
        List<ClosedLoan> closedLoans = user.getClosedLoans();

        for (ActiveLoan loan : activeLoans) {
            activeLoanRepository.delete(loan);
        }

        for (ClosedLoan loan : closedLoans) {
            closedLoanRepository.delete(loan);
        }

        userRepository.delete(user);

        return HttpStatus.OK;
    }
}
