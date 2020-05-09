package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText name, password;
    private TextView info;
    private Button loginbtn;
    private int count = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        info = (TextView)findViewById(R.id.info);
        loginbtn = (Button)findViewById(R.id.button);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();
                String passwd = password.getText().toString();
                validate(username, passwd);
            }
        });
    }

    private void validate(String userName, String userPassoword) {
        if((userName.equals("sharon")) && (userPassoword.equals("admin"))) {
            Intent intent  = new Intent(this, MainApp.class);
            startActivity(intent);
        } else {
            count--;
            info.setText("Number of Attempts Remaining: " + String.valueOf(count));
            if(count == 0) {
                loginbtn.setEnabled(false);
            }
        }
    }
}
