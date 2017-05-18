package com.lackhite.borrowland_mobile_app.borrowland.LoanList.LoanListInTab;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lackhite.borrowland_mobile_app.borrowland.Entities.LoanItem;

import java.util.List;

/**
 * Created by kannabi on 15.03.2017.
 */

public abstract class AbstractAdapter extends RecyclerView.Adapter<ViewHolder> {

    interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public abstract void setOnItemClickListener(OnItemClickListener listener);

    public abstract void swapItems(List<LoanItem> loans);
//    Context getContext();
//
//    void swapItems(List<LoanItem> loans);
//
//    void clear();
//
//    void addAll(List<LoanItem> list);
//
//    LoanItem getLoan(int position);
//
//    void setOnItemClickListener(LoansOutAdapter.OnItemClickListener onItemClickListener);
}
