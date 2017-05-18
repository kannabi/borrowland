package com.lackhite.borrowland_mobile_app.borrowland.LoanList.LoanListInTab;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import com.lackhite.borrowland_mobile_app.borrowland.Entities.LoanItem;
import com.lackhite.borrowland_mobile_app.borrowland.R;

/**
 * Created by kannabi on 16.02.17.
 */

public class LoansListAdapter extends AbstractAdapter {

    private List<LoanItem> mloans = new LinkedList<>();
    private Context mContext;

    // Define listener member variable

    // Define the listener interface

    private static OnItemClickListener listener;

    public void setOnItemClickListener(AbstractAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    // Define the method that allows the parent activity or fragment to define the listener
//    @Override
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }

    public LoansListAdapter(Context context, List<LoanItem> loans) {
        mloans.addAll(loans);
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Inflate the custom layout
        View loanView = inflater.inflate(R.layout.item_list_loan, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(loanView);
        viewHolder.setOnItemClickListener(new ViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                listener.onItemClick(itemView, position);
            }
        });
        // Return a new holder instance
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final LoanItem loan = mloans.get(position);

        viewHolder.nameTextView.setText(loan.getName().replace('_', ' '));
        viewHolder.dateTextView.setText(loan.getDate() + " " + loan.getTime());
        viewHolder.sumTextView.setText(Integer.toString(loan.getDebt()));

        if(loan.getDebt() > 0)
            viewHolder.sumTextView.setTextColor(mContext.getResources().getColor(R.color.green));
        else
            viewHolder.sumTextView.setTextColor(mContext.getResources().getColor(R.color.red));
    }


    public void swapItems(List<LoanItem> loans) {
        final LoansListDiffCallback diffCallback = new LoansListDiffCallback(this.mloans, loans);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.mloans.clear();
        this.mloans.addAll(loans);
        diffResult.dispatchUpdatesTo(this);
    }


    public void clear() {
        mloans.clear();
        notifyDataSetChanged();
    }


    public void addAll(List<LoanItem> list) {
        mloans.addAll(list);
        notifyDataSetChanged();
    }


    public LoanItem getLoan(int position){
        return mloans.get(position);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mloans.size();
    }
}