package salle.android.projects.registertest.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.model.User;
import salle.android.projects.registertest.model.UserRegister;
import salle.android.projects.registertest.model.UserToken;
import salle.android.projects.registertest.restapi.callback.UserCallback;
import salle.android.projects.registertest.restapi.manager.UserManager;
import salle.android.projects.registertest.utils.Session;

public class RegisterActivity extends AppCompatActivity
        implements UserCallback {

    private EditText etEmail;
    private EditText etLogin;
    private EditText etPassword;
    private Button btnRegister;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
    }

    private void initViews () {
        etEmail = (EditText) findViewById(R.id.register_email);
        etLogin = (EditText) findViewById(R.id.register_login);
        etPassword = (EditText) findViewById(R.id.register_password);

        btnRegister = (Button) findViewById(R.id.register_btn_action);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String login = etLogin.getText().toString();
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();
                Session.getInstance(getApplicationContext()).setUserRegister(new UserRegister(email, login, password));
                UserManager.getInstance(getApplicationContext()).registerAttempt(email, login, password, RegisterActivity.this);
            }
        });
    }

    private void doLogin(String username, String userpassword) {
        UserManager.getInstance(getApplicationContext())
                .loginAttempt(username, userpassword, RegisterActivity.this);
    }

    @Override
    public void onLoginSuccess(UserToken userToken) {
        Session.getInstance(getApplicationContext())
                .setUserToken(userToken);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent); }

    @Override
    public void onLoginFailure(Throwable throwable) {
        Session.getInstance(getApplicationContext())
                .setUserRegister(null); }

    @Override
    public void onRegisterSuccess() {
        UserRegister userData = Session.getInstance(getApplicationContext()).getUserRegister();
        doLogin(userData.getLogin(), userData.getPassword()); }

    @Override
    public void onRegisterFailure(Throwable throwable) {
        Session.getInstance(getApplicationContext())
                .setUserRegister(null);
        Toast.makeText(getApplicationContext(), "Register failed " + throwable.getMessage(), Toast.LENGTH_LONG).show(); }

    @Override
    public void onUserInfoReceived(User userData) {

    }


    @Override
    public void onFailure(Throwable throwable) {

    }
}
