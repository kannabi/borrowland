package com.lackhite.borrowland_mobile_app.borrowland.ApiWorker;

import android.os.AsyncTask;

import com.lackhite.borrowland_mobile_app.borrowland.Entities.LoanItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kannabi on 27.02.17.
 */

public abstract class ActiveLoansGetter extends AsyncTask<String, Void, List> implements CallbackReceiver {

    final private String METHOD = "getActiveLoans";

    public abstract void receiveData(List response);

    @Override
    protected List doInBackground(String... params){
        return parseAnswer(new RequestWorker(METHOD, params).get());
    }

    @Override
    protected void onPostExecute(List resp) {
        receiveData(resp);
    }

    private List parseAnswer(String response){
        List<LoanItem> loans = new LinkedList<>();

        try{
            JSONArray items = new JSONObject(response).getJSONArray("loans");

            for(int i = items.length() - 1; i >= 0 ; --i){
                JSONObject loan = items.getJSONObject(i);
                loans.add(new LoanItem(loan.getString("partName"), loan.getString("date"),
                                        loan.getString("time"), loan.getInt("sum"), loan.getString("id")));
            }

        } catch (JSONException e){
            e.printStackTrace();
        }

        return loans;
    }
}
