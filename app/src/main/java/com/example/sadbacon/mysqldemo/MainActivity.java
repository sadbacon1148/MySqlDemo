package com.example.sadbacon.mysqldemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.support.v4.os.LocaleListCompat.create;


public class MainActivity extends AppCompatActivity {

    EditText UsernameEt, PasswordEt;

    BgWorkerCallback bgWorkerCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button loginBtn;
        loginBtn = findViewById(R.id.btnLogin);
        setContentView(R.layout.activity_main);
        UsernameEt = (EditText) findViewById(R.id.etUserName);
        PasswordEt = (EditText) findViewById(R.id.etPassword);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin(view);
            }
        });

        bgWorkerCallback = new BgWorkerCallback() {
            @Override
            public void loginResult(String result) {

                Context ctx = getApplicationContext();
                AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setMessage(result);
                alertDialog.show();

                if (result.contentEquals("login success")) {
                    alertDialog.setMessage("login was OK");
                    alertDialog.show();

                    Intent i = new Intent(ctx, QrCameraActivity.class);
                    ctx.startActivity(i);


                    //context.startActivity(new Intent(context, QrCameraActivity.class));
                } else {
                    Toast toast = Toast.makeText(ctx, "Email or password is wrong", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };

    }

    public void onLogin(View view) {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";
        LoginBackgroundWorker backgroundWorker = new LoginBackgroundWorker(bgWorkerCallback);
        backgroundWorker.execute(type, username, password);

        //context.startActivity(new Intent(context, QrCameraActivity.class));

    }

}
