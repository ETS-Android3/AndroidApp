package com.example.yangyuanhao.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class mHeroDB extends SQLiteOpenHelper {
    public static final String create = "CREATE TABLE IF NOT EXISTS Hero ("
            + "id integer primary key autoincrement, "
            + "name text unique, "
            + "title text, "
            + "pro text, "
            + "hp int, "
            + "atk int, "
            + "skl int, "
            + "diff int, "
            + "skl1 text, "
            + "skl2 text, "
            + "skl3 text, "
            + "skl4 text, "
            + "icon BLOB)";
    private Context myc;

    public mHeroDB(Context c) {
        super(c, "hero.db", null, 1);
        myc = c;
    }

    public void inserthero(Hero h) {
        if (getherobyname(h.getName()).size() != 0) {
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put("name", h.getName());
        cv.put("title", h.getTitle());
        cv.put("pro", h.getProfession());
        cv.put("hp", h.getHealth());
        cv.put("atk", h.getAttack());
        cv.put("skl", h.getSkill());
        cv.put("diff", h.getDifficulty());
        cv.put("skl1", h.getSkill1());
        cv.put("skl2", h.getSkill2());
        cv.put("skl3", h.getSkill3());
        cv.put("skl4", h.getSkill4());
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        Bitmap b = h.getIcon();
        b.compress(Bitmap.CompressFormat.PNG, 50, bao);
        cv.put("icon", bao.toByteArray());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Hero", null, cv);
        db.close();
    }

    public void deletehero(String nm) {
        if (getherobyname(nm).size() == 0) {
            return;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Hero", "name = ?", new String[]{nm});
        db.close();
    }

    public void updatehero(Hero h) {
        ContentValues cv = new ContentValues();
        cv.put("title", h.getTitle());
        cv.put("pro", h.getProfession());
        cv.put("hp", h.getHealth());
        cv.put("atk", h.getAttack());
        cv.put("skl", h.getSkill());
        cv.put("diff", h.getDifficulty());
        cv.put("skl1", h.getSkill1());
        cv.put("skl2", h.getSkill2());
        cv.put("skl3", h.getSkill3());
        cv.put("skl4", h.getSkill4());
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        Bitmap b = h.getIcon();
        b.compress(Bitmap.CompressFormat.PNG, 50, bao);
        cv.put("icon", bao.toByteArray());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update("Hero", cv, "name = ?", new String[]{h.getName()});
        db.close();
    }

    public List<Hero> getherobyname(String n) {
        List<Hero> out = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Hero", null);
        if (!cursor.moveToFirst()) {
            db.close();
            return out;
        }
        do {
            String nm = cursor.getString(cursor.getColumnIndex("name"));
            if (nm.equals(n)) {
                String pro = cursor.getString(cursor.getColumnIndex("pro"));
                String tt = cursor.getString(cursor.getColumnIndex("title"));
                int hp = cursor.getInt(cursor.getColumnIndex("hp"));
                int atk = cursor.getInt(cursor.getColumnIndex("atk"));
                int skl = cursor.getInt(cursor.getColumnIndex("skl"));
                int diff = cursor.getInt(cursor.getColumnIndex("diff"));
                String skl1 = cursor.getString(cursor.getColumnIndex("skl1"));
                String skl2 = cursor.getString(cursor.getColumnIndex("skl2"));
                String skl3 = cursor.getString(cursor.getColumnIndex("skl3"));
                String skl4 = cursor.getString(cursor.getColumnIndex("skl4"));
                byte[] img = cursor.getBlob(cursor.getColumnIndex("icon"));
                Bitmap icon = BitmapFactory.decodeByteArray(img, 0, img.length);
                out.add(new Hero(nm, tt, pro, hp, atk, skl, diff, skl1, skl2, skl3, skl4, icon));
            }

        } while (cursor.moveToNext());
        db.close();
        return out;
    }

    public List<Hero> getherobypro(String p) {
        List<Hero> out = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Hero", null);
        if (!cursor.moveToFirst()) {
            db.close();
            return out;
        }
        do {
            String pro = cursor.getString(cursor.getColumnIndex("pro"));
            if (pro.equals(p)) {
                String nm = cursor.getString(cursor.getColumnIndex("name"));
                String tt = cursor.getString(cursor.getColumnIndex("title"));
                int hp = cursor.getInt(cursor.getColumnIndex("hp"));
                int atk = cursor.getInt(cursor.getColumnIndex("atk"));
                int skl = cursor.getInt(cursor.getColumnIndex("skl"));
                int diff = cursor.getInt(cursor.getColumnIndex("diff"));
                String skl1 = cursor.getString(cursor.getColumnIndex("skl1"));
                String skl2 = cursor.getString(cursor.getColumnIndex("skl2"));
                String skl3 = cursor.getString(cursor.getColumnIndex("skl3"));
                String skl4 = cursor.getString(cursor.getColumnIndex("skl4"));
                byte[] img = cursor.getBlob(cursor.getColumnIndex("icon"));
                Bitmap icon = BitmapFactory.decodeByteArray(img, 0, img.length);
                out.add(new Hero(nm, tt, pro, hp, atk, skl, diff, skl1, skl2, skl3, skl4, icon));
            }

        } while (cursor.moveToNext());
        db.close();
        return out;
    }

    public List<Hero> getallhero() {
        List<Hero> out = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Hero", null);
        if (!cursor.moveToFirst()) {
            db.close();
            return out;
        }
        do {
            String nm = cursor.getString(cursor.getColumnIndex("name"));
            String tt = cursor.getString(cursor.getColumnIndex("title"));
            String pro = cursor.getString(cursor.getColumnIndex("pro"));
            int hp = cursor.getInt(cursor.getColumnIndex("hp"));
            int atk = cursor.getInt(cursor.getColumnIndex("atk"));
            int skl = cursor.getInt(cursor.getColumnIndex("skl"));
            int diff = cursor.getInt(cursor.getColumnIndex("diff"));
            String skl1 = cursor.getString(cursor.getColumnIndex("skl1"));
            String skl2 = cursor.getString(cursor.getColumnIndex("skl2"));
            String skl3 = cursor.getString(cursor.getColumnIndex("skl3"));
            String skl4 = cursor.getString(cursor.getColumnIndex("skl4"));
            byte[] img = cursor.getBlob(cursor.getColumnIndex("icon"));
            Bitmap icon = BitmapFactory.decodeByteArray(img, 0, img.length);
            out.add(new Hero(nm, tt, pro, hp, atk, skl, diff, skl1, skl2, skl3, skl4, icon));
        } while (cursor.moveToNext());
        db.close();
        return out;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create);
        Toast.makeText(myc, "Database created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int o, int n) {

    }
}
