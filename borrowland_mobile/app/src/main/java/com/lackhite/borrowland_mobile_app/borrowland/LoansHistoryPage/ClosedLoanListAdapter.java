package com.lackhite.borrowland_mobile_app.borrowland.LoansHistoryPage;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import com.lackhite.borrowland_mobile_app.borrowland.Entities.ClosedLoanItem;
import com.lackhite.borrowland_mobile_app.borrowland.R;

/**
 * Created by kannabi on 20.02.17.
 */

public class ClosedLoanListAdapter extends RecyclerView.Adapter<ClosedLoanListAdapter.ViewHolder> {

    private List<ClosedLoanItem> mloans = new LinkedList<>();
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView dateTextView;
        public TextView sumTextView;
        public TextView closingDateTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.closed_loan_item_name);
            dateTextView = (TextView) itemView.findViewById(R.id.closed_loan_item_date);
            sumTextView = (TextView) itemView.findViewById(R.id.closed_loan_item_sum);
            closingDateTextView = (TextView) itemView.findViewById(R.id.closed_loan_item_close_date);
        }
    }




    public ClosedLoanListAdapter(Context context, List <ClosedLoanItem> loans) {
        mloans.addAll(loans);
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }


    @Override
    public ClosedLoanListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Inflate the custom layout
        View loanView = inflater.inflate(R.layout.item_list_closed_loan, parent, false);
        // Return a new holder instance
        return new ClosedLoanListAdapter.ViewHolder(loanView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ClosedLoanListAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final ClosedLoanItem loan = mloans.get(position);

        viewHolder.nameTextView.setText(loan.getName().replace('_', ' '));
        viewHolder.dateTextView.setText(loan.getDate() + " " + loan.getTime());
        viewHolder.sumTextView.setText(Integer.toString(loan.getDebt()));
        viewHolder.closingDateTextView.setText(loan.getCloseDate() + " " + loan.getCloseTime());

        if(loan.getDebt() > 0)
            viewHolder.sumTextView.setTextColor(mContext.getResources().getColor(R.color.green));
        else
            viewHolder.sumTextView.setTextColor(mContext.getResources().getColor(R.color.red));
    }

    public void swapItems(List<ClosedLoanItem> loans) {
        final ClosedLoanListDiffCallback diffCallback = new ClosedLoanListDiffCallback(this.mloans, loans);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.mloans.clear();
        this.mloans.addAll(loans);
        diffResult.dispatchUpdatesTo(this);
    }

    public void clear() {
        mloans.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<ClosedLoanItem> list) {
        mloans.addAll(list);
        notifyDataSetChanged();
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mloans.size();
    }
}
