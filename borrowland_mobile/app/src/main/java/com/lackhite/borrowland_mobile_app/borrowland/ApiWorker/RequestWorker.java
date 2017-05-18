package com.lackhite.borrowland_mobile_app.borrowland.ApiWorker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by kannabi on 27.02.17.
 */

public class RequestWorker {

    String url;

    public RequestWorker(String method, String... params){
        StringBuilder reqUrl = new StringBuilder(params[0] + method + "?");

        for (int i = 1; i < params.length - 1; ++i) {
            reqUrl.append(params[i] + "&");
        }
        reqUrl.append(params[params.length - 1]);
        url = reqUrl.toString();
    }

    public String get(){
        StringBuilder answer = new StringBuilder();

        try {
            URL request = new URL(url);
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
        return answer.toString();
    }
}
