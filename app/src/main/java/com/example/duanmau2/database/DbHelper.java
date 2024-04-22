package com.example.duanmau2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context){
        super(context, "DuAnMau", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qNguoiDung = "CREATE TABLE NGUOIDUNG(tendangnhap TEXT PRIMARY KEY, matkhau TEXT, email TEXT)";
        db.execSQL(qNguoiDung);
        String qSanPham = "CREATE TABLE SANPHAM(masp INTEGER PRIMARY KEY AUTOINCREMENT, tensp TEXT, giaban INTEGER, soluong INTEGER, hinhanh TEXT)";
        db.execSQL(qSanPham);

        String dNguoiDung = "INSERT INTO NGUOIDUNG VALUES('phat', '123', 'brianpham15502@gmail.com')";
        db.execSQL(dNguoiDung);
        String dSanPham = "INSERT INTO SANPHAM VALUES('1', 'Nhẫn', '100000', '10', '')";
        db.execSQL(dSanPham);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            db.execSQL("DROP TABLE IF EXISTS SANPHAM");
            onCreate(db);
        }
    }
}
