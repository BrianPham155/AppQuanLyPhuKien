package com.example.duanmau2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau2.Model.Product;
import com.example.duanmau2.database.DbHelper;

import java.util.ArrayList;

public class SanPhamDAO {
    private DbHelper dbHelper;

    public SanPhamDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    // Lấy danh sách sản phẩm
    public ArrayList<Product> getDS(){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<Product> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM sanpham", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                list.add(new Product(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),cursor.getInt(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    // Thêm sản phẩm
    public boolean themSPMoi (Product product){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensp", product.getTensp());
        contentValues.put("giaban", product.getGiaban());
        contentValues.put("soluong", product.getSoluong());

        long check = sqLiteDatabase.insert("SANPHAM", null, contentValues);
        return check != -1;
    }

    //Chỉnh sửa thông tin
    public boolean ChinhSuaSP (Product product){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("tensp", product.getTensp());
        contentValues.put("giaban", product.getGiaban());
        contentValues.put("soluong", product.getSoluong());

        int check = sqLiteDatabase.update("SANPHAM", contentValues, "masp = ?", new String[]{String.valueOf(product.getMasp())});
        if (check <= 0){
            return false;
        }
        return true;
    }

    public boolean XoaSP(int masp){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int check = sqLiteDatabase.delete("SANPHAM", "masp = ?", new String[]{String.valueOf(masp)});
        if (check <= 0){
            return false;
        } return true;
    }
}
