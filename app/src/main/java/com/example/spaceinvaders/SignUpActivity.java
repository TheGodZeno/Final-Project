package com.example.spaceinvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    EditText et_UsernameR, et_EmailR, et_PasswordR, et_PhoneR, et_Birth_YearR;
    Button b_SignupR;
    //private final String[] userInfo = new String[5];
    DBHelper dbHelper;
    SQLiteDatabase db;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        et_UsernameR = findViewById(R.id.Edit_Text_Username_Registration);
        et_EmailR = findViewById(R.id.Edit_Text_Email_Registration);
        et_PasswordR = findViewById(R.id.Edit_Text_Password_Registration);
        et_PhoneR = findViewById(R.id.Edit_Text_Phone_Registration);
        et_Birth_YearR = findViewById(R.id.Edit_Text_Year_Registration);
        b_SignupR = findViewById(R.id.Button_Sign_Up);
        // קולט את הנתונים של המשתמש ומכניס אותם לטבלה
        b_SignupR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkIfValidInfo()){
                    dbHelper = new DBHelper(SignUpActivity.this);
                    dbHelper.addUser(et_UsernameR.getText().toString().trim(),
                            et_EmailR.getText().toString().trim(),
                            et_PasswordR.getText().toString().trim(),
                            et_PhoneR.getText().toString().trim(),
                            et_Birth_YearR.getText().toString().trim());
                    et_UsernameR.setText("");
                    et_EmailR.setText("");
                    et_PasswordR.setText("");
                    et_PhoneR.setText("");
                    et_Birth_YearR.setText("");
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // בודק אם המשתמש הכניס ערכים שהם נכונים וקיימים את תנאי ההרשמה
    private boolean checkIfValidInfo() {
        String username = et_UsernameR.getText().toString();
        String email = et_EmailR.getText().toString().trim();
        String password = et_PasswordR.getText().toString();
        String phone = et_PhoneR.getText().toString();
        String by = et_Birth_YearR.getText().toString();
        String passwordVal = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        String phoneVal = "^05\\d([-]{0,1})\\d{7}$";
        if (username == null || username.equals("")) {
            et_UsernameR.setError("Fill the field");
            return false;
        }
        if (email == null || email.equals("")) {
            et_EmailR.setError("Fill the field");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_EmailR.setError("Invalid email");
            return false;
        }
        if (password == null || password.equals("")) {
            et_PasswordR.setError("Fill the field");
            return false;
        }
        if(!password.matches(passwordVal))
        {
            et_PasswordR.setError("Password too weak and must contain at least 8 characters");
            return false;
        }
        if (phone == null || phone.equals("")) {
            et_PhoneR.setError("Fill the field");
            return false;
        }
        if (!phone.matches(phoneVal)) {
            et_PhoneR.setError("Phone number must start with 05 and contain only numbers");
            return false;
        }
        if (by == null || by.equals("")) {
            et_Birth_YearR.setError("Fill the field");
            return false;
        }
        return true;
    }



}