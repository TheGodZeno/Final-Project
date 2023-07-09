package com.example.spaceinvaders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList user_id, user_username, user_email, user_password, user_phone, user_birth_year;

    CustomAdapter(Activity activity, Context context, ArrayList user_id, ArrayList user_username, ArrayList user_email,
                  ArrayList user_password, ArrayList user_phone, ArrayList user_birth_year){
        this.activity = activity;
        this.context = context;
        this.user_id = user_id;
        this.user_username = user_username;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_phone = user_phone;
        this.user_birth_year = user_birth_year;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.user_id_txt.setText(String.valueOf(user_id.get(position)));
        holder.user_username_txt.setText(String.valueOf(user_username.get(position)));
        holder.user_email_txt.setText(String.valueOf(user_email.get(position)));
        holder.user_password_txt.setText(String.valueOf(user_password.get(position)));
        holder.user_phone_txt.setText(String.valueOf(user_phone.get(position)));
        holder.user_birth_year_txt.setText(String.valueOf(user_birth_year.get(position)));
        holder.mainLayout.setOnClickListener(view ->  {
                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("id", String.valueOf(user_id.get(position)));
                    intent.putExtra("username", String.valueOf(user_username.get(position)));
                    intent.putExtra("email", String.valueOf(user_email.get(position)));
                    intent.putExtra("password", String.valueOf(user_password.get(position)));
                    intent.putExtra("phone", String.valueOf(user_phone.get(position)));
                    intent.putExtra("birthYear", String.valueOf(user_birth_year.get(position)));
                    activity.startActivityForResult(intent, 1);
                }
        );
    }

    @Override
    public int getItemCount() {
        return user_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user_id_txt, user_username_txt, user_email_txt, user_password_txt,
                user_phone_txt, user_birth_year_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_id_txt = itemView.findViewById(R.id.Text_View_Id);
            user_username_txt = itemView.findViewById(R.id.Text_View_Username);
            user_email_txt = itemView.findViewById(R.id.Text_View_Email);
            user_password_txt = itemView.findViewById(R.id.Text_View_Password);
            user_phone_txt = itemView.findViewById(R.id.Text_View_Phone);
            user_birth_year_txt = itemView.findViewById(R.id.Text_View_Birth_Year);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}

