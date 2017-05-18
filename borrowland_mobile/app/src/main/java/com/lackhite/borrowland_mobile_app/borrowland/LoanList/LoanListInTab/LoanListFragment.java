package com.lackhite.borrowland_mobile_app.borrowland.LoanList.LoanListInTab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.List;

import com.lackhite.borrowland_mobile_app.borrowland.AddingLoan.AddingLoanActivity;
import com.lackhite.borrowland_mobile_app.borrowland.AlertDialog.AlertDialogFragment;
import com.lackhite.borrowland_mobile_app.borrowland.ApiWorker.ActiveLoansGetter;
import com.lackhite.borrowland_mobile_app.borrowland.ApiWorker.CloseLoan;
import com.lackhite.borrowland_mobile_app.borrowland.Entities.LoanItem;
import com.lackhite.borrowland_mobile_app.borrowland.ListUtils.EndlessRecyclerViewScrollListener;
import com.lackhite.borrowland_mobile_app.borrowland.R;

/**
 * Created by kannabi on 16.02.17.
 */

public class LoanListFragment extends Fragment {

    final private int NUM_OF_LOANS_IN_PACK = 20;
    final private int REFRESH = 0;

    private int direction = 0;

    private RelativeLayout view;
    private List<LoanItem> loansList = new LinkedList<>();
    private RecyclerView rvLoans;
    private AbstractAdapter loansAdapter;

    private SwipeRefreshLayout refreshLayout;
    private EndlessRecyclerViewScrollListener scrollListener;

    public LoanListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        direction = getArguments().getInt("direction");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = (RelativeLayout) inflater.inflate(
                R.layout.loan_list_recycler_view, container, false);

        rvLoans = (RecyclerView) view.findViewById(R.id.loans_recycler_view);


        switch (direction){
            case -1:
                loansAdapter = new LoansOutAdapter(view.getContext(), loansList);
                break;
            case 0:
                loansAdapter = new LoansListAdapter(view.getContext(), loansList);
                break;
            case 1:
                loansAdapter = new LoansInAdapter(view.getContext(), loansList);
                break;
        }

        rvLoans.setAdapter(loansAdapter);
        loansAdapter.setOnItemClickListener((view, position) -> showAlertDialog(position));
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_loans_container);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLoan();
            }
        });


        rvLoans.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvLoans.setLayoutManager(linearLayoutManager);
        rvLoans.addItemDecoration(new DividerItemDecoration(rvLoans.getContext(),
                linearLayoutManager.getOrientation()));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                uploadLoans(REFRESH, NUM_OF_LOANS_IN_PACK);
                refreshLayout.setRefreshing(false);
            }
        });

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                uploadLoans(loansList.size(), NUM_OF_LOANS_IN_PACK);
                uploadLoans(REFRESH, NUM_OF_LOANS_IN_PACK + loansList.size());
            }
        };

        rvLoans.addOnScrollListener(scrollListener);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        uploadLoans(REFRESH, NUM_OF_LOANS_IN_PACK);
    }

    public void uploadLoans(int offset, int count){
        if (offset == 0) {
            loansList.clear();
        }

        ActiveLoansGetter getter = new ActiveLoansGetter() {
            @Override
            public void receiveData(List response) {
                putLoans(response);
                loansAdapter.notifyDataSetChanged();
                loansAdapter.swapItems(loansList);
//                System.out.println(loansList.size());
            }
        };

        SharedPreferences sharedPref = getActivity().getSharedPreferences(getActivity().getResources().getString(R.string.global_settings),Context.MODE_PRIVATE);

        getter.execute(getResources().getString(R.string.server_address),
                        "id=" + sharedPref.getString(getActivity().getResources().getString(R.string.client_id), "0"),
                        "count=" + Integer.toString(count),
                        "offset=" + Integer.toString(offset));
    }

    private void showAlertDialog(final int position) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        AlertDialogFragment alertDialog = AlertDialogFragment.newInstance("Удалить займ");
        alertDialog.setOnDeleteListener(new AlertDialogFragment.DeleteChoiceListener(){
            @Override
            public void onDeleteItemChoice(){
                deleteLoan(position);
            }
        });
        alertDialog.show(fm, "fragment_alert");
    }

    private void deleteLoan(int position){
        CloseLoan close = new CloseLoan() {
            @Override
            public void receiveData(List response) {
                if(response.get(0) == Boolean.TRUE){
                    uploadLoans(REFRESH, NUM_OF_LOANS_IN_PACK);
                }
            }
        };

        System.out.println(direction + " " + position + " " + loansList.size());
//        System.out.println(position);
//        System.out.println(loansList.size());

        SharedPreferences sharedPref = getActivity().getSharedPreferences(getActivity().getResources().getString(R.string.global_settings),Context.MODE_PRIVATE);
        close.execute(getResources().getString(R.string.server_address),
                "client_id=" + sharedPref.getString(getActivity().getResources().getString(R.string.client_id), "0"),
                "loan_id=" + loansList.get(position).getId());
//                "loan_id=" + position);
    }

    private void addLoan(){
        Intent intent = new Intent(getActivity(), AddingLoanActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void setDirection(int direction){
        this.direction = direction;
    }

    private void putLoans(List<LoanItem> response){
        for (int i = 0; i < response.size(); ++i){
            if((direction * response.get(i).getDebt()) >= 0)
                loansList.add(response.get(i));
        }
    }
}
