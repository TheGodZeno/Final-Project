package com.example.spaceinvaders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;

 class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SpaceInvaders.db";
    public static  final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "app_users";
    private static final String COLUMN_ID = "_id";
    private static final String USER_USERNAME = "userName";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_PHONE = "phone";
    private static final String USER_BIRTH_YEAR = "birth" + "" + "Year";
    private Context context;

    public DBHelper(@NonNull Context context)
    {super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_USERNAME + " TEXT, " +
                USER_EMAIL + " TEXT, " +
                USER_PASSWORD + " TEXT, " +
                USER_PHONE + " TEXT, " +
                USER_BIRTH_YEAR + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

     void addUser(String username, String email, String password, String phone, String year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        if(!isValueExist(username)){
        cv.put(USER_USERNAME, username);
        if(!isValueExist1(email)) {
            cv.put(USER_EMAIL, email);
            cv.put(USER_PASSWORD, password);
            if(!isValueExist2(phone)) {
                cv.put(USER_PHONE, phone);
                cv.put(USER_BIRTH_YEAR, year);
                long result = db.insert(TABLE_NAME, null, cv);
                if (result == -1)
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
                }
            else
                Toast.makeText(context, "The phone number is used by a different user, try a different one",Toast.LENGTH_SHORT).show();
            }
        else
            Toast.makeText(context, "The email is used by a different use,r try a different one",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(context, "The username is already taken",Toast.LENGTH_SHORT).show();

    }

     private boolean isValueExist(String value){
         String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + USER_USERNAME + " = ?";
         String[] whereArgs = {value};

         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery(query, whereArgs);

         int count = cursor.getCount();

         cursor.close();

         return count >= 1;
     }

     private boolean isValueExist1(String value){
         String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + USER_EMAIL+ " = ?";
         String[] whereArgs = {value};

         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery(query, whereArgs);

         int count = cursor.getCount();

         cursor.close();

         return count >= 1;
     }

     private boolean isValueExist2(String value){
         String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + USER_PHONE + " = ?";
         String[] whereArgs = {value};

         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery(query, whereArgs);

         int count = cursor.getCount();

         cursor.close();

         return count >= 1;
     }

     public boolean isUserExists(String username, String password){

         String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + USER_EMAIL + " = ?" + " AND " + USER_PASSWORD + " = ?";
         String[] whereArgs = {username, password};
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery(query, whereArgs);
         int count = cursor.getCount();
         if(count>0){
             return true;
         }
         else{
             return false;
         }
     }

     Cursor readAllData(){
         String query = "SELECT * FROM " +TABLE_NAME;
         SQLiteDatabase db = this.getReadableDatabase();

         Cursor cursor = null;
         if(db != null){
             cursor = db.rawQuery(query, null);

         }
         return cursor;
     }

     void updateData(String row_id, String username, String email, String password, String phone, String year){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();
         cv.put(USER_USERNAME, username);
         cv.put(USER_EMAIL, email);
         cv.put(USER_PASSWORD, password);
         cv.put(USER_PHONE, phone);
         cv.put(USER_BIRTH_YEAR, year);
         long result = db.update(TABLE_NAME, cv,"_id=?",new String[]{row_id});
         if (result == -1)
             Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
         else
             Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();

     }

     void deleteOneRow(String row_id){
         SQLiteDatabase db = this.getWritableDatabase();
         long result = db.delete(TABLE_NAME,"_id=?", new String[]{row_id});
         if(result == -1){
             Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
         }else
             Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
     }

     void deleteAllData(){
         SQLiteDatabase db =this.getWritableDatabase();
         db.execSQL("DELETE FROM " + TABLE_NAME);
     }
 }
