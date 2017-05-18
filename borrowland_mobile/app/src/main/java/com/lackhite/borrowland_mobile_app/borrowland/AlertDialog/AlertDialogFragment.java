package com.lackhite.borrowland_mobile_app.borrowland.AlertDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by kannabi on 20.02.17.
 */

public class AlertDialogFragment extends DialogFragment {

    public AlertDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static AlertDialogFragment newInstance(String title) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    private DeleteChoiceListener listener;

    public interface DeleteChoiceListener {
        void onDeleteItemChoice();
    }

    public void setOnDeleteListener(DeleteChoiceListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Вы уверены?");
        alertDialogBuilder.setPositiveButton("Удалить",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                if (listener != null)
                    listener.onDeleteItemChoice();
            }
        });
        alertDialogBuilder.setNegativeButton("Оставить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }
}

