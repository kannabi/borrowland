package com.lackhite.borrowland_mobile_app.borrowland.LoanList.LoanListInTab;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lackhite.borrowland_mobile_app.borrowland.R;

/**
 * Created by kannabi on 16.03.2017.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    public TextView nameTextView;
    public TextView dateTextView;
    public TextView sumTextView;

    private ViewHolder.OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ViewHolder(final View itemView) {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView);

        nameTextView = (TextView) itemView.findViewById(R.id.loan_item_name);
        dateTextView = (TextView) itemView.findViewById(R.id.loan_item_date);
        sumTextView = (TextView) itemView.findViewById(R.id.loan_item_sum);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Triggers click upwards to the adapter on click
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView, position);
                    }
                }
            }
        });
    }
}
