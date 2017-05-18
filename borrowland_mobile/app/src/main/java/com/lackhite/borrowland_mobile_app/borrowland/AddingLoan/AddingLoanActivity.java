package com.lackhite.borrowland_mobile_app.borrowland.AddingLoan;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lackhite.borrowland_mobile_app.borrowland.AddingLoan.FriendChoosingList.FriendsListFragment;
import com.lackhite.borrowland_mobile_app.borrowland.ApiWorker.AddLoan;
import com.lackhite.borrowland_mobile_app.borrowland.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class AddingLoanActivity extends AppCompatActivity {

    private String part_name;
    private String part_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_loan);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FriendsListFragment friendList = new FriendsListFragment();
        fragmentManager.beginTransaction().add(R.id.adding_loan_content, friendList).commit();

        setChooseFriendListener(friendList);
    }

    private void setChooseFriendListener(FriendsListFragment fragment){
        fragment.setOnChooseListener(new FriendsListFragment.onChooseFriendListener() {
            @Override
            public void onChoose(String partName, int partId) {
                onChooseFriend(partName, Integer.toString(partId));
            }
        });
    }

    private void onChooseFriend(String name, String id){
        try {
            part_name = URLEncoder.encode(name.replace(' ', '_'), "UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        part_id = id;

        FragmentManager fragmentManager = getSupportFragmentManager();
        ChooseDetailsFragment fragment = new ChooseDetailsFragment();
        fragmentManager.beginTransaction().replace(R.id.adding_loan_content, fragment).commit();
        setDetailButtonsListeners(fragment);
    }

    private void setDetailButtonsListeners(ChooseDetailsFragment fragment){
        fragment.setOnButtonAddListener(new ChooseDetailsFragment.onButtonAddPressed() {
            @Override
            public void onChoose(String sum) {
                if (!sum.equals("")){
                    AddLoan adder = new AddLoan() {
                        @Override
                        public void receiveData(List response) {
                            finish();
                        }
                    };
                    SharedPreferences sharedPref = getSharedPreferences(getResources().getString(R.string.global_settings), Context.MODE_PRIVATE);

                    adder.execute(getResources().getString(R.string.server_address),
                            "id=" + sharedPref.getString(getResources().getString(R.string.client_id), "0"),
                            "sum=" + sum,
                            "part_id=" + part_id,
                            "part_name=" + part_name);
                    System.out.println(sum);
                }
            }
        });

        fragment.setOnButtonCancelListener(new ChooseDetailsFragment.onButtonCancelPressed() {
            @Override
            public void onChoose() {
//                System.out.println("cancel");
                finish();
            }
        });
    }
}
