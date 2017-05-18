package com.lackhite.borrowland_mobile_app.borrowland.AddingLoan.FriendChoosingList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lackhite.borrowland_mobile_app.borrowland.ApiWorker.GetVKFriends;
import com.lackhite.borrowland_mobile_app.borrowland.Entities.Friend;
import com.lackhite.borrowland_mobile_app.borrowland.ListUtils.EndlessRecyclerViewScrollListener;
import com.lackhite.borrowland_mobile_app.borrowland.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kannabi on 25.02.17.
 */

public class FriendsListFragment extends Fragment {

    final int NUM_OF_FRIENDS_IN_PACK = 30;
    final int REFRESH = 0;

    private RelativeLayout view;
    private List<Friend> friendsList = new LinkedList<>();
    private RecyclerView rvFriends;
    private FriendsListAdapter friendsAdapter;

    private EndlessRecyclerViewScrollListener scrollListener;

    private onChooseFriendListener listener;

    public interface onChooseFriendListener{
        void onChoose(String partName, int partId);
    }

    public void setOnChooseListener(onChooseFriendListener listener){
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = (RelativeLayout) inflater.inflate(
                R.layout.friends_list_recycler_view, container, false);

        rvFriends = (RecyclerView) view.findViewById(R.id.friends_recycler_view);

        friendsList.addAll(getFriendsList(REFRESH, NUM_OF_FRIENDS_IN_PACK));

        friendsAdapter = new FriendsListAdapter(view.getContext(), friendsList);

        rvFriends.setAdapter(friendsAdapter);
        rvFriends.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvFriends.setLayoutManager(linearLayoutManager);
        rvFriends.addItemDecoration(new DividerItemDecoration(rvFriends.getContext(),
                linearLayoutManager.getOrientation()));

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                friendsList.addAll(getFriendsList(friendsList.size(), NUM_OF_FRIENDS_IN_PACK));
                System.out.println(friendsList.size());
            }
        };

        friendsAdapter.setOnItemClickListener(new FriendsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                listener.onChoose(friendsList.get(position).getName() ,friendsList.get(position).getId());
            }
        });

        rvFriends.addOnScrollListener(scrollListener);

        return view;
    }

    public List<Friend> getFriendsList(int offset, int count){
        final List<Friend> friends = new LinkedList<>();

        GetVKFriends request = new GetVKFriends() {
            @Override
            public void receiveData(List response) {
                friendsList.addAll(response);
                friendsAdapter.notifyDataSetChanged();
                friendsAdapter.swapItems(friendsList);
            }
        };

        request.execute("fields=first_name,last_name",
                        "count=" + Integer.toString(count),
                        "offset=" + Integer.toString(offset),
                        "order=hints");

        return friends;
    }
}