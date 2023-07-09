package com.example.spaceinvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.spaceinvaders.alieninvaders.view.SpaceInvadersGame;

public class MainActivity extends AppCompatActivity {
    Button b_play, b_logout, b_leader_board;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor = preferences.edit();
        b_play =  findViewById(R.id.Button_Play);
        // שולח משתמש לעמוד של המשחק
        b_play.setOnClickListener(view -> {
            Intent intent = new Intent(this, SpaceInvadersGame.class);
            startActivity(intent);
        });
        b_logout = findViewById(R.id.Button_Sign_Out);
        // מנתק משתמש
        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // יוצא מאפליקציה כדיי לא לגרום למצב שהמשתמש יחזור ל-login
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}