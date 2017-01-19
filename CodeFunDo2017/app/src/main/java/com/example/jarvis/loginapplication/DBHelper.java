package com.example.jarvis.loginapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by jarvis on 1/7/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final String DB_NAME= "codefundof9.db";
    public static final int DB_VERSION= 1;

    public static final String USER_TABLE= "users";
    public static final String MERCHANT_TABLE= "merchants";
    public static final String COLUMN_NAME= "name";
    public static final String COLUMN_DOB= "dob";
    public static final String COLUMN_CITY= "city";
    public static final String COLUMN_PASSWORD= "password";
    public static final String COLUMN_MOBILE= "mobile";

    public static final String COLUMN_MOBILE1= "mobile";
    public static final String COLUMN_PASSWORD1= "password";
    public static final String COLUMN_NAME1= "name";
    public static final String COLUMN_ADDRESS1= "address";
    public static final String COLUMN_ROOM1= "room";



    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_NAME + " TEXT,"
            + COLUMN_DOB + " TEXT,"
            + COLUMN_CITY+ " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_MOBILE + " TEXT);";

    public static final String CREATE_TABLE_MERCHANTS = "CREATE TABLE " + MERCHANT_TABLE + "("
            + COLUMN_MOBILE1 + " TEXT,"
            + COLUMN_PASSWORD1 + " TEXT,"
            + COLUMN_NAME1+ " TEXT,"
            + COLUMN_ADDRESS1 + " TEXT,"
            + COLUMN_ROOM1 + " INT);";

    public DBHelper(Context context) {
        super(context, "codefundof9.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS );
        db.execSQL(CREATE_TABLE_MERCHANTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ MERCHANT_TABLE);
        onCreate(db);
    }
    public void addUser(String name, String dob, String city, String password, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from users");        //first it will be deleting all the rows from the table and then a new user will be added.
        db.execSQL("delete from merchants");        //first it will be deleting all the rows from the table and then a new user will be added.

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_CITY, city);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_MOBILE, mobile);

        long c = db.insert(USER_TABLE,null,values);
        db.close();

        Log.d(TAG, "user inserted" + c);
    }

    public void addUser2(String name, String mobile, String address, String password, int room) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from users");        //first it will be deleting all the rows from the table and then a new user will be added.

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME1, name);
        values.put(COLUMN_MOBILE1, mobile);
        values.put(COLUMN_ADDRESS1, address);
        values.put(COLUMN_PASSWORD1, password);
        values.put(COLUMN_ROOM1, room);

        long c = db.insert(MERCHANT_TABLE,null,values);
        db.close();

        Log.d(TAG, "merchant inserted" + c);
    }

    public int rowCount(){
        String countQuery = "SELECT  * FROM users";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public int rowCount2(){
        String countQuery = "SELECT  * FROM merchants";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public void delRow(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from users");
        db.execSQL("delete from merchants");
    }

    public void delRow2(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from merchants");
        db.execSQL("delete from users");
    }

    public boolean getUser(String mobile, String password){
        String selectQuery = "select * from  " + USER_TABLE + " where " +
                COLUMN_MOBILE + " = " + "'"+mobile+"'" + " and " + COLUMN_PASSWORD + " = " + "'"+password+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public boolean getUser2(String mobile, String password){
        String selectQuery = "select * from  " + MERCHANT_TABLE + " where " +
                COLUMN_MOBILE1 + " = " + "'"+mobile+"'" + " and " + COLUMN_PASSWORD1 + " = " + "'"+password+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public String[] getRow(){
        String selectQuery = "select * from users";
        String[] result=new String[5];
        result[0]="";
        result[1]="";
        result[2]="";
        result[3]="";
        result[4]="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            result[0]=cursor.getString(0);//name
            result[1]=cursor.getString(1);//dob
            result[2]=cursor.getString(2);//city
            result[3]=cursor.getString(3);//password
            result[4]=cursor.getString(4);//mobile
            return result;
        }
        cursor.close();
        db.close();
        return result;
    }
    public String[] getRow2(){
        String selectQuery = "select * from merchants";
        String[] result=new String[5];
        result[0]="";
        result[1]="";
        result[2]="";
        result[3]="";
        result[4]="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            result[0]=cursor.getString(0);//mobile
            result[1]=cursor.getString(1);//password
            result[2]=cursor.getString(2);//name
            result[3]=cursor.getString(3);//address
            result[4]=cursor.getString(4);//room
            return result;
        }
        cursor.close();
        db.close();
        return result;
    }

    public void update2(String mobile, int room){
        String qry= "update merchants SET room = " + room + " WHERE mobile = " + mobile;
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(qry);
    }

}
