package com.lackhite.borrowland_mobile_app.borrowland.LoansHistoryPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.lackhite.borrowland_mobile_app.borrowland.ApiWorker.ClosedLoansGetter;
import com.lackhite.borrowland_mobile_app.borrowland.Entities.ClosedLoanItem;
import com.lackhite.borrowland_mobile_app.borrowland.ListUtils.EndlessRecyclerViewScrollListener;
import com.lackhite.borrowland_mobile_app.borrowland.R;

/**
 * Created by kannabi on 20.02.17.
 */

public class ClosedLoansListFragment extends Fragment {

    final int NUM_OF_LOANS_IN_PACK = 50;
    final int REFRESH = 0;

    private RelativeLayout view;
    private List<ClosedLoanItem> loansList = new LinkedList<>();
    private RecyclerView rvLoans;
    private ClosedLoanListAdapter loansAdapter;

    private SwipeRefreshLayout refreshLayout;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = (RelativeLayout) inflater.inflate(
                R.layout.loan_list_recycler_view, container, false);

        rvLoans = (RecyclerView) view.findViewById(R.id.loans_recycler_view);

        loansList.addAll(getLoansList(REFRESH, NUM_OF_LOANS_IN_PACK));

        loansAdapter = new ClosedLoanListAdapter(view.getContext(), loansList);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_loans_container);

        rvLoans.setAdapter(loansAdapter);
        rvLoans.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvLoans.setLayoutManager(linearLayoutManager);
        rvLoans.addItemDecoration(new DividerItemDecoration(rvLoans.getContext(),
                linearLayoutManager.getOrientation()));


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                loansList.clear();
                loansList.addAll(getLoansList(REFRESH, loansList.size()));
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loansAdapter.notifyDataSetChanged();
                        loansAdapter.swapItems(loansList);
                    }
                }, 100);
                refreshLayout.setRefreshing(false);
            }
        });

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                System.out.println("onLoadMore");
                loansList.addAll(getLoansList(loansList.size(), NUM_OF_LOANS_IN_PACK));
                System.out.println(loansList.size());
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        loansAdapter.notifyDataSetChanged();
                        loansAdapter.swapItems(loansList);
                    }
                });
            }
        };

        rvLoans.addOnScrollListener(scrollListener);

        return view;
    }

    public List<ClosedLoanItem> getLoansList(int offset, int count){
        List<ClosedLoanItem> loans = new LinkedList<>();

        ClosedLoansGetter getter = new ClosedLoansGetter() {
            @Override
            public void receiveData(List response) {
                loansList.addAll(response);
                loansAdapter.notifyDataSetChanged();
                loansAdapter.swapItems(loansList);
                System.out.println("in upload");
            }
        };

        SharedPreferences sharedPref = getActivity().getSharedPreferences(getActivity().getResources().getString(R.string.global_settings), Context.MODE_PRIVATE);

        getter.execute(getResources().getString(R.string.server_address),
                "id=" + sharedPref.getString(getActivity().getResources().getString(R.string.client_id), "0"),
                "count=" + Integer.toString(count),
                "offset=" + Integer.toString(offset));

        return loans;
    }
}
