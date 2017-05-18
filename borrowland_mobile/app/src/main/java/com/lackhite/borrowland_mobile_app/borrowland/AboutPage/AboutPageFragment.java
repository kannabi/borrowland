package com.lackhite.borrowland_mobile_app.borrowland.AboutPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lackhite.borrowland_mobile_app.borrowland.R;

/**
 * Created by kannabi on 19.02.17.
 */

public class AboutPageFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        RelativeLayout view = (RelativeLayout) inflater.inflate(
                R.layout.about_page_layout, container, false);

        TextView text = (TextView) view.findViewById(R.id.about_page_text);

        return view;
    }
}
