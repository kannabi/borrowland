package com.lackhite.borrowland_mobile_app.borrowland.AddingLoan.FriendChoosingList;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.lackhite.borrowland_mobile_app.borrowland.Entities.Friend;

import java.util.List;

/**
 * Created by kannabi on 25.02.17.
 */

public class FriendsListDiffCallback  extends DiffUtil.Callback {

    private final List<Friend> oldList;
    private final List<Friend> newList;

    public FriendsListDiffCallback(List<Friend> oldList, List<Friend> newList) {
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
        final Friend oldItem = oldList.get(oldItemPosition);
        final Friend newItem = newList.get(newItemPosition);

        return oldItem.getName().equals(newItem.getName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

}
