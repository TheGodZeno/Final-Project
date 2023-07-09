package com.example.spaceinvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminAddActivity extends AppCompatActivity {
    EditText et_UsernameA, et_PasswordA, et_EmailA, et_PhoneA, et_Birth_YearA;
    Button b_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);
        et_UsernameA = findViewById(R.id.Edit_Text_Username_Add);
        et_EmailA = findViewById(R.id.Edit_Text_Email_Add);
        et_PasswordA = findViewById(R.id.Edit_Text_Password_Add);
        et_PhoneA = findViewById(R.id.Edit_Text_Phone_Add);
        et_Birth_YearA = findViewById(R.id.Edit_Text_Year_Add);
        b_add = findViewById(R.id.Button_Add);
        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkIfValidInfo()){
                DBHelper dbHelper = new DBHelper(AdminAddActivity.this);
                dbHelper.addUser(et_UsernameA.getText().toString().trim(),
                        et_EmailA.getText().toString().trim(),
                        et_PasswordA.getText().toString().trim(),
                        et_PhoneA.getText().toString().trim(),
                        et_Birth_YearA.getText().toString().trim());
            }else
                    Toast.makeText(AdminAddActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private boolean checkIfValidInfo() {
        String username = et_UsernameA.getText().toString();
        String email = et_EmailA.getText().toString().trim();
        String password = et_PasswordA.getText().toString();
        String phone = et_PhoneA.getText().toString();
        String by = et_Birth_YearA.getText().toString();
        String passwordVal = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        String phoneVal = "^05\\d([-]{0,1})\\d{7}$";
        if (username == null || username.equals("")) {
            et_UsernameA.setError("Fill the field");
            return false;
        }
        if (email == null || email.equals("")) {
            et_EmailA.setError("Fill the field");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_EmailA.setError("Invalid email");
            return false;
        }
        if (password == null || password.equals("")) {
            et_PasswordA.setError("Fill the field");
            return false;
        }
        if(!password.matches(passwordVal))
        {
            et_PasswordA.setError("Password too weak and must contain at least 8 characters");
            return false;
        }
        if (phone == null || phone.equals("")) {
            et_PhoneA.setError("Fill the field");
            return false;
        }
        if (!phone.matches(phoneVal)) {
            et_PhoneA.setError("Phone number must start with 05 and contain only numbers");
            return false;
        }
        if (by == null || by.equals("")) {
            et_Birth_YearA.setError("Fill the field");
            return false;
        }
        return true;
    }
}