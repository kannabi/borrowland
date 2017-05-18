package com.lackhite.borrowland_mobile_app.borrowland.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lackhite.borrowland_mobile_app.borrowland.ApiWorker.RegisterUser;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import com.lackhite.borrowland_mobile_app.borrowland.MainActivity;
import com.lackhite.borrowland_mobile_app.borrowland.R;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Context activityContext;
    Button loginButton;
    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);

        activityContext = this.getApplicationContext();
        loginButton = (Button) findViewById(R.id.login_button);
        errorMessage = (TextView) findViewById((R.id.login_activity_error_message));

        if (!VKSdk.wakeUpSession(this)) {
            loginButton.setVisibility(View.VISIBLE);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    authenticate(view);
                }
            });
        }
        else
            startWork();
    }



    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(final VKAccessToken res) {
                final RegisterUser registerUser = new RegisterUser() {
                    @Override
                    public void receiveData(List response) {
                        if(response.get(0) == Boolean.TRUE) {

                            System.out.println("login" + getResources().getString(R.string.global_settings));
                            System.out.println(getResources().getString(R.string.client_id));

                            SharedPreferences sharedPref = getSharedPreferences(getResources().getString(R.string.global_settings),Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(getResources().getString(R.string.client_id), res.userId);
                            editor.commit();
                            startWork();
                        }else
                            showErrorMessage();
                    }
                };

                VKRequest getName = new VKRequest("account.getProfileInfo");

                getName.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);

                        String name = new String();
                        try {
                            JSONObject user = response.json.getJSONObject("response");

                            name = URLEncoder.encode(user.getString("first_name"), "UTF-8") + "_"
                                    + URLEncoder.encode(user.getString("last_name"), "UTF-8");
//                            name = user.getString("first_name") + "_"
//                                    + user.getString("last_name");
                        } catch (JSONException e){
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e_){
                            e_.printStackTrace();
                        }

                        registerUser.execute(getResources().getString(R.string.server_address),
                                            "id=" + res.userId,
                                            "name=" + name);
                    }
                });
            }
            @Override
            public void onError(VKError error) {
                showErrorMessage();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void startWork(){
        Intent intent = new Intent(activityContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityContext.startActivity(intent);
    }

    private void authenticate(View v){
        VKSdk.login(this, VKScope.FRIENDS);
    }

    private void showErrorMessage(){
        loginButton.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }
}
