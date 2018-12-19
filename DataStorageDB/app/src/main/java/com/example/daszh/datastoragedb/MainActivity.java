package com.example.daszh.datastoragedb;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ImageView im;
    myDB mdb;
    String iconuri;
    boolean flg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup r = findViewById(R.id.LRchoice);
        r.check(R.id.slui);
        im = findViewById(R.id.icon);
        mdb = new myDB(this);
        iconuri = null;
        flg = false;
    }

    public void switchicon(View view) {
        Intent in = new Intent();
        in.setAction(Intent.ACTION_PICK);
        in.setType("image/*");
        startActivityForResult(in, 0);
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent d) {
        if (d != null) {

            iconuri = d.getDataString();
            Uri uri = Uri.parse(iconuri);
            this.im.setImageURI(uri);
            flg = true;
        }
        super.onActivityResult(requestcode, resultcode, d);
    }

    public void clkclr(View view) {
        TextView t1 = findViewById(R.id.usr);
        TextView t2 = findViewById(R.id.passwd);
        t1.setText("");
        t2.setText("");
    }

    public void clkok(View view) {
        TextView t1 = findViewById(R.id.usr);
        TextView t2 = findViewById(R.id.passwd);
        if (t1.getText().toString().equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (t2.getText().toString().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mdb.checkuser(t1.getText().toString())) {
            String usr = t1.getText().toString();
            String pwd = t2.getText().toString();
            if (mdb.login(usr, pwd)) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                t1.setText("");
                t2.setText("");
                Intent i = new Intent(this, Comments.class);
                i.putExtra("username", usr);
                startActivity(i);


            } else {
                Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "用户名不存在", Toast.LENGTH_SHORT).show();
        }
    }

    public void Rclkok(View view) {
        TextView t1 = findViewById(R.id.Rusr);
        TextView t2 = findViewById(R.id.Rpasswd);
        TextView t3 = findViewById(R.id.Rcfmpasswd);
        if (t1.getText().toString().equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (t2.getText().toString().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String s = t1.getText().toString();
        if (mdb.checkuser(s)) {
            Toast.makeText(this, "用户已存在", Toast.LENGTH_SHORT).show();
        } else {
            if (!t2.getText().toString().equals(t3.getText().toString())) {
                Toast.makeText(this, "密码不匹配", Toast.LENGTH_SHORT).show();
            } else {


                if (iconuri == null || iconuri.equals("") || !flg) {
                    Resources r = this.getResources();
                    iconuri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                            + r.getResourcePackageName(R.drawable.me)
                            + "/" + r.getResourceTypeName(R.drawable.me)
                            + "/" + r.getResourceEntryName(R.drawable.me);
                }


                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(iconuri));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mdb.insertusr(s, t2.getText().toString(), bitmap);
                Toast.makeText(this, "创建成功", Toast.LENGTH_SHORT).show();
                t1.setText("");
                t2.setText("");
                t3.setText("");
                TextView t4 = findViewById(R.id.usr);
                t4.setText(s);
                Resources r = this.getResources();
                iconuri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + r.getResourcePackageName(R.drawable.add)
                        + "/" + r.getResourceTypeName(R.drawable.add)
                        + "/" + r.getResourceEntryName(R.drawable.add);
                ImageView img = findViewById(R.id.icon);
                img.setImageURI(Uri.parse(iconuri));
                flg = false;
            }
        }
    }

    public void Rclkclr(View view) {
        TextView t1 = findViewById(R.id.Rusr);
        TextView t2 = findViewById(R.id.Rpasswd);
        TextView t3 = findViewById(R.id.Rcfmpasswd);
        t1.setText("");
        t2.setText("");
        t3.setText("");
    }

    public void setLUI(View view) {
        RelativeLayout lui = findViewById(R.id.LoginUI);
        RelativeLayout rui = findViewById(R.id.RegisterUI);
        lui.setVisibility(View.VISIBLE);
        rui.setVisibility(View.INVISIBLE);
    }

    public void setRUI(View view) {
        RelativeLayout lui = findViewById(R.id.LoginUI);
        RelativeLayout rui = findViewById(R.id.RegisterUI);
        lui.setVisibility(View.INVISIBLE);
        rui.setVisibility(View.VISIBLE);
    }
}
