package com.example.spaceinvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button b_Login;
    TextView tv_Sign_Up;
    EditText et_Password, et_Email;
    DBHelper dbHelper;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_Sign_Up = findViewById(R.id.Text_View_Sign_up);
        b_Login = findViewById(R.id.Button_Login);
        et_Email = findViewById(R.id.Edit_Text_Email);
        et_Password = findViewById(R.id.Edit_Text_Password);

        // אם המשתמש נרשם הוא מייד יישלח ל-MainActivity (בכדיי להימנע מלהירשם כל פעם) אם לא אז לא ישעה כלום
        preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor = preferences.edit();
        if(preferences.contains("saved_Email"))
        {
            Intent i = new Intent(LoginActivity.this, ShplashActivity.class);
            startActivity(i);
        }
        else{
            b_Login.setOnClickListener(view -> ifExist());
        }

        dbHelper = new DBHelper(this);
        tv_Sign_Up.setOnClickListener(view -> sendToSignUpActivity());
    }

    // שולח ל-SignUpActivity
    private void sendToSignUpActivity() {
        Intent signUpActivityIntent = new Intent(this, SignUpActivity.class);
        startActivity(signUpActivityIntent);
    }


    // בודק אם משתמש קיים בטבלה אם כן יישלח ל-MainActivity אם לא יתבקש המשתמש לנסות שנית
    private void ifExist() {
        String email = et_Email.getText().toString();
        String password = et_Password.getText().toString();
        if(email.equals("admin") && password.equals("1234")) {
            Intent i = new Intent(this, AdminActivity.class);
            startActivity(i);
        }else{
            if(email.equals("") || password.equals("")){
                et_Password.setError("Fill the field");
                et_Email.setError("Fill the field");
            }
            else{
                Boolean result = dbHelper.isUserExists(email,password);
                if(result){
                    editor.putString("saved_Email", email);
                    editor.putString("saved_password", password);
                    editor.commit();
                    sendToMainActivity();
                    Toast.makeText(this, "You successfully logged in", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // שולח ל-MainActivity
    private void sendToMainActivity() {
        Intent mainActivityIntent = new Intent(this, ShplashActivity.class);
        startActivity(mainActivityIntent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}