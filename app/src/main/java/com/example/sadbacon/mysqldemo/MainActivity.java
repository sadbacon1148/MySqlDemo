package com.example.sadbacon.mysqldemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import static android.support.v4.os.LocaleListCompat.create;


public class MainActivity extends AppCompatActivity {

    EditText UsernameEt, PasswordEt;

    BgWorkerCallback bgWorkerCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsernameEt = (EditText) findViewById(R.id.etUserName);
        PasswordEt = (EditText) findViewById(R.id.etPassword);
        Button loginBtn;
        loginBtn = findViewById(R.id.btnLogin);

        ConstraintLayout constraintLayout = findViewById(R.id.layout_constraint);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HideKeyboard(view);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin(view);
            }
        });

        final Context ctx = this;
        bgWorkerCallback = new BgWorkerCallback() {

            @Override
            public void loginResult(String result) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx)
                        .setTitle("Login Status")
                        .setMessage(result);//the "login success" result from the PHP script
                alertDialog.create().show();

                if (result.contentEquals("login success")) {
//                    alertDialog.setMessage("login was OK");
                    alertDialog.show();

                    Intent i = new Intent(ctx, BarcodeCaptureActivity.class);
                    ctx.startActivity(i);
                    finish();

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

    public void HideKeyboard(View view)
        {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);

        }

}
