package com.lds.accounting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    
    private UserManger userManger;
    
    private Button submitBtn;
    private EditText usernameInput;
    private EditText passwordInput;
    
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        userManger = UserManger.getInstance(this);
        initViews();
    }
 

    private void initViews() {
        submitBtn = (Button) findViewById(R.id.submit);
        usernameInput = (EditText) findViewById(R.id.username);
        passwordInput = (EditText) findViewById(R.id.password);
        
        submitBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                if (userManger.login(username, password) != null) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "用户名/密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
