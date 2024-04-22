package com.example.duanmau2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau2.database.DbHelper;

public class NguoiDungDAO {
    private DbHelper dbHelper;

    public NguoiDungDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    //login
    public boolean CheckLogin (String username, String password){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE tendangnhap = ? AND matkhau = ?", new String[]{username, password});
        if (cursor.getCount() > 0){
            return true;
        }
        return false;
    }

    //register
    public boolean Register (String username, String password, String email){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("tendangnhap", username);
        contentValues.put("matkhau", password);
        contentValues.put("email", email);

        long check = sqLiteDatabase.insert("NGUOIDUNG", null, contentValues);
        if (check != -1){
            return true;
        }
        return false;
    }

    //forgot
    public String ForgotPassword(String email){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT matkhau FROM NGUOIDUNG WHERE email = ?", new String[]{email});
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            return cursor.getString(0);
        }else {
            return "";
        }
    }
}
