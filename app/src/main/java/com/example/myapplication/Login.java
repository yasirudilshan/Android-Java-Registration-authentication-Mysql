package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextView textViewRegisterNow,textStatus,t_status,t_message;
    TextInputEditText textInputEditTextEmail,textInputEditTextPassword;
    Button buttonSubmit,tst;

    String name, email, password,apikey;
    TextView textViewError;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    ArrayList<String> arrList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textViewRegisterNow=findViewById(R.id.registerNow);
        textStatus=findViewById(R.id.logStatus);
        t_message=findViewById(R.id.t_message);
        t_status=findViewById(R.id.stats);
        textInputEditTextEmail=findViewById(R.id.email);
        textInputEditTextPassword=findViewById(R.id.password);
        buttonSubmit=findViewById(R.id.submit);
        tst=findViewById(R.id.test);
        textViewError=findViewById(R.id.error);
        progressBar=findViewById(R.id.loading);
        sharedPreferences=getSharedPreferences("MyAppName",MODE_PRIVATE);
        getSupportActionBar().setTitle("Login");

        if (sharedPreferences.getString("logged","false").equals("true")){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

        tst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewError.setVisibility(view.GONE);
                progressBar.setVisibility(view.VISIBLE);
                email= String.valueOf(textInputEditTextEmail.getText()).toLowerCase();
                password= String.valueOf(textInputEditTextPassword.getText());

                System.out.println(email);
                System.out.println(password);


                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://172.31.98.165/androidloginregistration/login.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(view.GONE);


                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    String status=jsonObject.getString("status");
                                    String message=jsonObject.getString("message");

                                    arrList.add(status);
                                    System.out.println(arrList);

                                    textStatus.setText(message);
                                    t_message.setText(message);

                                    if(status.equals("failed")){

                                    }

                                    if(status.equals("success")){

                                        name=jsonObject.getString("name");
                                        email=jsonObject.getString("email");
                                        apikey=jsonObject.getString("apikey");
                                        SharedPreferences.Editor editor=sharedPreferences.edit();
                                        editor.putString("logged","true");
                                        editor.putString("name",name);
                                        editor.putString("email",email);
                                        editor.putString("apikey",apikey);
                                        editor.apply();

                                        arrList.add(name);
                                        System.out.println(arrList);

                                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else{
                                        textViewError.setText(message);
                                        textViewError.setVisibility(view.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(view.GONE);
                        textViewError.setText(error.getLocalizedMessage());
                        textViewError.setVisibility(view.VISIBLE);
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);

            }
        });

        textViewRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Registration.class);
                startActivity(intent);
                finish();
            }
        });
    }
}