package com.lackhite.borrowland_mobile_app.borrowland.LoansHistoryPage;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import com.lackhite.borrowland_mobile_app.borrowland.Entities.ClosedLoanItem;

/**
 * Created by kannabi on 20.02.17.
 */

public class ClosedLoanListDiffCallback extends DiffUtil.Callback {

    private final List<ClosedLoanItem> oldList;
    private final List<ClosedLoanItem> newList;

    public ClosedLoanListDiffCallback(List<ClosedLoanItem> oldList, List<ClosedLoanItem> newList) {
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
        final ClosedLoanItem oldItem = oldList.get(oldItemPosition);
        final ClosedLoanItem newItem = newList.get(newItemPosition);

        return oldItem.getDebt() == newItem.getDebt();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
