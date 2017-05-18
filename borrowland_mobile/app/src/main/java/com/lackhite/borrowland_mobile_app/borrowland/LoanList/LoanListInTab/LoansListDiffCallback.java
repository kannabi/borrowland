package com.lackhite.borrowland_mobile_app.borrowland.LoanList.LoanListInTab;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import com.lackhite.borrowland_mobile_app.borrowland.Entities.LoanItem;

/**
 * Created by kannabi on 17.02.17.
 */

public class LoansListDiffCallback extends DiffUtil.Callback{
    private final List<LoanItem> oldList;
    private final List<LoanItem> newList;

    public LoansListDiffCallback(List<LoanItem> oldList, List<LoanItem> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
//        return oldList.get(oldItemPosition).getUser_id().equals(newList.get(newItemPosition).getUser_id());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final LoanItem oldItem = oldList.get(oldItemPosition);
        final LoanItem newItem = newList.get(newItemPosition);

        return oldItem.getDebt() == newItem.getDebt();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
