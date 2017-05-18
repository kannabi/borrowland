package com.lackhite.borrowland_mobile_app.borrowland.ApiWorker;

import android.os.AsyncTask;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kannabi on 28.02.17.
 */

public abstract class CloseLoan extends AsyncTask<String, Void, List> implements CallbackReceiver  {
    final private String GOOD_RESPONSE = "\"OK\"";
    final private String METHOD = "closeLoan";

    public abstract void receiveData(List response);

    @Override
    protected List doInBackground(String... params) {
        String answer = new RequestWorker(METHOD, params).get();

        List<Boolean> ansList = new LinkedList<>();
        ansList.add(answer.equals(GOOD_RESPONSE) ? Boolean.TRUE : Boolean.FALSE);

        return ansList;
    }


    @Override
    protected void onPostExecute(List resp) {
        receiveData(resp);
    }
}
