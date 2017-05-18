package com.lackhite.borrowland_mobile_app.borrowland.AddingLoan.FriendChoosingList;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lackhite.borrowland_mobile_app.borrowland.Entities.Friend;
import com.lackhite.borrowland_mobile_app.borrowland.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kannabi on 25.02.17.
 */

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder>{

    private List<Friend> friendsList = new LinkedList<>();
    private Context mContext;

    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(FriendsListAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;

        public ViewHolder(final View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.friend_list_name);

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

    public FriendsListAdapter(Context context, List <Friend> friends) {
        friendsList.addAll(friends);
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }


    @Override
    public FriendsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View loanView = inflater.inflate(R.layout.item_list_friend, parent, false);
        return new FriendsListAdapter.ViewHolder(loanView);
    }

    @Override
    public void onBindViewHolder(FriendsListAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Friend loan = friendsList.get(position);

        viewHolder.nameTextView.setText(loan.getName());
    }

    public void swapItems(List<Friend> friends) {
        final FriendsListDiffCallback diffCallback = new FriendsListDiffCallback(this.friendsList, friends);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.friendsList.clear();
        this.friendsList.addAll(friends);
        diffResult.dispatchUpdatesTo(this);
    }

    public void clear() {
        friendsList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Friend> list) {
        friendsList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }
}
