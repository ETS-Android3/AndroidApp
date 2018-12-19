package com.example.daszh.datastoragedb;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myDB extends SQLiteOpenHelper {
    public static final String dbname = "mydata";
    public static final String ut = "userinfo";
    Context con;
    public myDB(Context c) {
        super(c, dbname, null, 1);
        con = c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createut = "CREATE TABLE if not exists " + ut + " (id integer primary key autoincrement, username text, password text, icon BLOB)";
        String createct = "CREATE TABLE if not exists comments (id integer primary key autoincrement, icon BLOB, username text, time text, comment text, num integer, prove text)";
        String createprove = "create table if not exists prove (id integet primary key autoincrement, username text, time text)";
        db.execSQL(createprove);
        db.execSQL(createct);
        db.execSQL(createut);
    }

    public boolean searchprove(String user, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select username, time from prove where username = ? and time = ?", new String[]{user, date});
        if (cursor == null || !cursor.moveToFirst()) {
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public void disprovecomment(String user, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("prove", "username = ? and time = ?", new String[]{user, date});
    }

    public void provecomment(String user, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", user);
        cv.put("time", date);
        db.insert("prove", null, cv);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int ii) {
    }

    public void insertusr(String usr, String pwd, Bitmap b) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 50, bao);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", usr);
        cv.put("password", pwd);
        cv.put("icon", bao.toByteArray());
        db.insert(ut, null, cv);
        db.close();
    }

    public void insertcomment(String unm, String cmt) {
        String tm = "", picon = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from userinfo", null);
        if (!c.moveToFirst()) {
            db.close();
            return;
        }
        byte[] uicon = null;
        do {
            if (unm.equals(c.getString(c.getColumnIndex("username")))) {
                uicon = c.getBlob(c.getColumnIndex("icon"));
                break;
            }
        } while (c.moveToNext());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        tm = sd.format(date);
        Resources r = con.getResources();
        picon = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.drawable.white)
                + "/" + r.getResourceTypeName(R.drawable.white)
                + "/" + r.getResourceEntryName(R.drawable.white);
        db.close();
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("icon", uicon);
        cv.put("username", unm);
        cv.put("time", tm);
        cv.put("comment", cmt);
        cv.put("num", 0);
        cv.put("prove", picon);
        db.insert("comments", null, cv);
        db.close();
    }

    public boolean checkuser(String s) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select username, password from userinfo", null);
        if (!cursor.moveToFirst()) {
            db.close();
            return false;
        }
        if (s.equals(cursor.getString(cursor.getColumnIndex("username")))) {
            db.close();
            return true;
        }
        while (cursor.moveToNext()) {
            if (s.equals(cursor.getString(cursor.getColumnIndex("username")))) {
                db.close();
                return true;
            }
        }
        db.close();
        return false;

    }
    public boolean login(String u, String p) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select username, password from userinfo", null);

        if (!cursor.moveToFirst()) {
            db.close();
            return false;
        }
        if (u.equals(cursor.getString(cursor.getColumnIndex("username")))) {
            if (p.equals(cursor.getString(cursor.getColumnIndex("password")))) {
                db.close();
                return true;
            } else {
                db.close();
                return false;
            }
        }
        while (cursor.moveToNext()) {
            if (u.equals(cursor.getString(cursor.getColumnIndex("username")))) {
                if (p.equals(cursor.getString(cursor.getColumnIndex("password")))) {
                    db.close();
                    return true;
                } else {
                    db.close();
                    return false;
                }
            }
        }
        db.close();
        return false;
    }

    public List<Map<String, Object>> getcomments() {
        List<Map<String, Object>> out = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from comments", null);
        if (!cursor.moveToFirst()) {
            db.close();
            return out;
        }
        do {
            Map<String, Object> m = new HashMap<>();
            byte[] img = cursor.getBlob(cursor.getColumnIndex("icon"));
            m.put("icon", img);
            m.put("username", cursor.getString(cursor.getColumnIndex("username")));
            m.put("time", cursor.getString(cursor.getColumnIndex("time")));
            m.put("comment", cursor.getString(cursor.getColumnIndex("comment")));
            m.put("num", cursor.getString(cursor.getColumnIndex("num")));
            m.put("prove", cursor.getString(cursor.getColumnIndex("prove")));
            out.add(m);
        } while (cursor.moveToNext());
        db.close();
        return out;
    }

    public void delcomment(String d) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("comments", "time = ?", new String[]{d});
        db.close();
    }

    public void updateprove(String d, int n) {
        SQLiteDatabase db = this.getWritableDatabase();
        Resources r = con.getResources();
        String picon = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.drawable.red)
                + "/" + r.getResourceTypeName(R.drawable.red)
                + "/" + r.getResourceEntryName(R.drawable.red);
        ContentValues cv = new ContentValues();
        cv.put("num", n);
        cv.put("prove", picon);
        db.update("comments", cv, "time = ?", new String[]{d});
        db.close();
    }
}
