package com.lackhite.borrowland_mobile_app.borrowland.AddingLoan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.lackhite.borrowland_mobile_app.borrowland.R;

/**
 * Created by kannabi on 26.02.17.
 */

public class ChooseDetailsFragment extends Fragment {
    RelativeLayout view;

    private onButtonAddPressed addListener;
    private onButtonCancelPressed cancelListener;

    public interface onButtonAddPressed{
        void onChoose(String sum);
    }

    public interface onButtonCancelPressed{
        void onChoose();
    }

    public void setOnButtonAddListener(onButtonAddPressed listener){
        this.addListener = listener;
    }

    public void setOnButtonCancelListener(onButtonCancelPressed listener){
        cancelListener = listener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = (RelativeLayout) inflater.inflate(
                R.layout.adding_loan_details_fragment, container, false);

        final EditText sumField = (EditText) view.findViewById(R.id.enter_sum_area);

        Button addButton = (Button) view.findViewById(R.id.add_loan_button);
        Button cancelButton = (Button) view.findViewById(R.id.cancel_adding_loan);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListener.onChoose(sumField.getText().toString());
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelListener.onChoose();
            }
        });

        return view;
    }
}
