package com.lackhite.borrowland_mobile_app.borrowland.ApiWorker;

import android.os.AsyncTask;

import com.lackhite.borrowland_mobile_app.borrowland.Entities.Friend;
import com.vk.sdk.VKAccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kannabi on 26.02.17.
 */

public abstract class GetVKFriends extends AsyncTask<String, Void, List> implements CallbackReceiver {

    final private String METHOD_URL = "https://api.vk.com/method/";
    final private String METHOD_AREA = "friends.";
    final private String METHOD = "get";
    final private String ACCESS_TOKEN = "access_token=" + VKAccessToken.currentToken().accessToken;
    final private String VERSION_API = "v=5.62";

    List <String> params = new LinkedList<>();

    public abstract void receiveData(List response);

    //на вход подаются аргументы в виде "count=20" и тд
    @Override
    protected List doInBackground(String... params){
        StringBuilder answer = new StringBuilder();
        StringBuilder reqUrl = new StringBuilder(METHOD_URL + METHOD_AREA + METHOD + "?");

        for (int i = 0; i < params.length; ++i) {
            reqUrl.append(params[i] + "&");
            this.params.add(params[i]);
        }
        reqUrl.append(ACCESS_TOKEN + "&");
        reqUrl.append(VERSION_API);

        System.out.println(reqUrl.toString());

        try {
            URL request = new URL(reqUrl.toString());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(request.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                answer.append(inputLine);
            }
            in.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return getList(answer.toString());
    }

    @Override
    protected void onPostExecute(List resp) {
        receiveData(resp);
    }

    private List<Friend> getList (String jsonAnswer){
        List<Friend> friends = new LinkedList<>();

        try{
            JSONArray items = new JSONObject(jsonAnswer).getJSONObject("response").getJSONArray("items");

            for (int i = 0; i < items.length(); ++i){
                JSONObject friend = items.getJSONObject(i);
                friends.add(new Friend(friend.getString("first_name") + " " + friend.getString("last_name"),
                                        friend.getInt("id")));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        return friends;
    }
}
