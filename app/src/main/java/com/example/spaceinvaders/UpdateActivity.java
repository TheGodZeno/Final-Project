package com.example.spaceinvaders;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText et_username_update, et_email_update, et_password_update, et_phone_update, et_birth_year_update;
    Button b_update, b_delete;

    String id, username, email, password, phone, birthYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        et_username_update = findViewById(R.id.Edit_Text_Username_Update);
        et_email_update = findViewById(R.id.Edit_Text_Email_Update);
        et_password_update = findViewById(R.id.Edit_Text_Password_Update);
        et_phone_update = findViewById(R.id.Edit_Text_Phone_Update);
        et_birth_year_update = findViewById(R.id.Edit_Text_Year_Update);
        b_delete = findViewById(R.id.Button_Delete);
        b_update =findViewById(R.id.Button_Update);
        // תחילה קוראים לזה
        getAndIntentData();

        ActionBar ab =  getSupportActionBar();
        if(ab!=null){
            ab.setTitle(username);}
        b_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ואז קוראים לזה
                DBHelper dbHelper = new DBHelper(UpdateActivity.this);
                username = et_username_update.getText().toString().trim();
                email = et_email_update.getText().toString().trim();
                password = et_password_update.getText().toString().trim();
                phone = et_phone_update.getText().toString().trim();
                birthYear = et_birth_year_update.getText().toString().trim();
                dbHelper.updateData(id, username, email, password, phone, birthYear);
            }
        });
        b_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getAndIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("username") && getIntent().hasExtra("email") &&
                getIntent().hasExtra("password") && getIntent().hasExtra("phone") && getIntent().hasExtra("birthYear")){
            // מקבלים DATA מה-INTENT
            id = getIntent().getStringExtra("id");
            username = getIntent().getStringExtra("username");
            email = getIntent().getStringExtra("email");
            password = getIntent().getStringExtra("password");
            phone = getIntent().getStringExtra("phone");
            birthYear = getIntent().getStringExtra("birthYear");


            // מייצגים intent data
            et_username_update.setText(username);
            et_email_update.setText(email);
            et_password_update.setText(password);
            et_phone_update.setText(phone);
            et_birth_year_update.setText(birthYear);
        }else{
            Toast.makeText(UpdateActivity.this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            recreate();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + username + " ?");
        builder.setMessage("Are you sure you want to delete " + username +" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper dbHelper = new DBHelper(UpdateActivity.this);
                dbHelper.deleteOneRow(id);
                Intent intent = new Intent(UpdateActivity.this, AdminActivity.class);
                startActivity(intent);
                //finish();
                //recreate();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}