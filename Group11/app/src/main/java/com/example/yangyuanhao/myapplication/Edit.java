package com.example.yangyuanhao.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Edit extends AppCompatActivity {
    boolean selecticon;
    boolean mode;
    Bitmap icon;
    String name;
    String title;
    String pro;
    String health;
    String attack;
    String skill;
    String difficulty;
    String skl1;
    String skl2;
    String skl3;
    String skl4;
    mHeroDB mdb;
    Hero h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        selecticon = false;
        mdb = new mHeroDB(this);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            mode = b.getBoolean("mode");
            if (mode) {
                TextView t = findViewById(R.id.edittitle);
                t.setText("添加新英雄");
            } else {
                TextView t = findViewById(R.id.edittitle);
                t.setText("编辑英雄");
                h = mdb.getherobyname(b.getString("hero")).get(0);
                TextInputLayout nm = findViewById(R.id.namepanel);
                TextInputLayout tt = findViewById(R.id.titlepanel);
                Spinner pr = findViewById(R.id.prospinner);
                Spinner hp = findViewById(R.id.hpspinner);
                Spinner atk = findViewById(R.id.atkspinner);
                Spinner skl = findViewById(R.id.sklspinner);
                Spinner diff = findViewById(R.id.diffspinner);
                TextInputLayout sk1 = findViewById(R.id.skl1panel);
                TextInputLayout sk2 = findViewById(R.id.skl2panel);
                TextInputLayout sk3 = findViewById(R.id.skl3panel);
                TextInputLayout sk4 = findViewById(R.id.skl4panel);
                ImageView iv = findViewById(R.id.icon);
                selecticon = true;
                iv.setImageBitmap(h.getIcon());
                icon = h.getIcon();
                nm.getEditText().setText(h.getName());
                tt.getEditText().setText(h.getTitle());
                switch (h.getProfession()) {
                    case "坦克":
                        pr.setSelection(0);
                        break;
                    case "战士":
                        pr.setSelection(1);
                        break;
                    case "刺客":
                        pr.setSelection(2);
                        break;
                    case "法师":
                        pr.setSelection(3);
                        break;
                    case "射手":
                        pr.setSelection(4);
                        break;
                    case "辅助":
                        pr.setSelection(5);
                        break;
                }
                hp.setSelection(h.getHealth()/10 - 1);
                atk.setSelection(h.getAttack()/10 - 1);
                skl.setSelection(h.getSkill()/10 - 1);
                diff.setSelection(h.getDifficulty()/10 - 1);
                sk1.getEditText().setText(h.getSkill1());
                sk2.getEditText().setText(h.getSkill2());
                sk3.getEditText().setText(h.getSkill3());
                sk4.getEditText().setText(h.getSkill4());
            }
        }
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
            d.getData();
            try {
                icon = MediaStore.Images.Media.getBitmap(this.getContentResolver(), d.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView iv = findViewById(R.id.icon);
            iv.setImageBitmap(icon);
            selecticon = true;
        }
        super.onActivityResult(requestcode, resultcode, d);
    }

    public void eclkok(View view) {
        TextInputLayout nm = findViewById(R.id.namepanel);
        TextInputLayout tt = findViewById(R.id.titlepanel);
        Spinner pr = findViewById(R.id.prospinner);
        Spinner hp = findViewById(R.id.hpspinner);
        Spinner atk = findViewById(R.id.atkspinner);
        Spinner skl = findViewById(R.id.sklspinner);
        Spinner diff = findViewById(R.id.diffspinner);
        TextInputLayout sk1 = findViewById(R.id.skl1panel);
        TextInputLayout sk2 = findViewById(R.id.skl2panel);
        TextInputLayout sk3 = findViewById(R.id.skl3panel);
        TextInputLayout sk4 = findViewById(R.id.skl4panel);
        if (nm.getEditText().getText().toString().equals("")) {
            nm.setError("名称不能为空");
            return;
        }
        if (tt.getEditText().getText().toString().equals("")) {
            tt.setError("外号不能为空");
            return;
        }
        if (sk1.getEditText().getText().toString().equals("")) {
            sk1.setError("技能1不能为空");
            return;
        }
        if (sk2.getEditText().getText().toString().equals("")) {
            sk2.setError("技能2不能为空");
            return;
        }
        if (sk3.getEditText().getText().toString().equals("")) {
            sk3.setError("技能3不能为空");
            return;
        }
        if (sk4.getEditText().getText().toString().equals("")) {
            sk4.setError("技能4不能为空");
            return;
        }
        if (!selecticon) {
            final AlertDialog.Builder ald = new AlertDialog.Builder(Edit.this);
            ald.setTitle("没有选择头像！").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create();
            ald.show();
            return;
        }
        name = nm.getEditText().getText().toString();
        if(mode) {
            if (mdb.getherobyname(name).size() > 0) {
                final AlertDialog.Builder ald = new AlertDialog.Builder(Edit.this);
                ald.setTitle("此名称已存在！").setMessage("新人物不能使用此名称！").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
                ald.show();
                return;
            }
        } else {
            if (!name.equals(h.getName()) && mdb.getherobyname(name).size() > 0) {
                final AlertDialog.Builder ald = new AlertDialog.Builder(Edit.this);
                ald.setTitle("此名称已存在！").setMessage("不能改为此名称！").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
                ald.show();
                return;
            }
        }
        title = tt.getEditText().getText().toString();
        pro = pr.getSelectedItem().toString();
        health = hp.getSelectedItem().toString();
        attack = atk.getSelectedItem().toString();
        skill = skl.getSelectedItem().toString();
        difficulty = diff.getSelectedItem().toString();
        skl1 = sk1.getEditText().getText().toString();
        skl2 = sk2.getEditText().getText().toString();
        skl3 = sk3.getEditText().getText().toString();
        skl4 = sk4.getEditText().getText().toString();
        int thp = Integer.parseInt(health);
        int tatk = Integer.parseInt(attack);
        int tskl = Integer.parseInt(skill);
        int tdif = Integer.parseInt(difficulty);
        if (mode) {
            mdb.inserthero(new Hero(name, title, pro, thp, tatk, tskl, tdif, skl1, skl2, skl3, skl4, icon));
            Toast.makeText(this, "创建成功", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(Edit.this, MainActivity.class);
            startActivity(in);
        } else {
            mdb.deletehero(h.getName());
            mdb.inserthero(new Hero(name, title, pro, thp, tatk, tskl, tdif, skl1, skl2, skl3, skl4, icon));
            Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(Edit.this, detailActivity.class);
            in.putExtra("name", name);
            in.putExtra("title", title);
            in.putExtra("pro", pro);
            in.putExtra("health", thp);
            in.putExtra("attack", tatk);
            in.putExtra("skill", tskl);
            in.putExtra("difficulty", tdif);
            in.putExtra("skl1", skl1);
            in.putExtra("skl2", skl2);
            in.putExtra("skl3", skl3);
            in.putExtra("skl4", skl4);
            startActivity(in);
        }

    }

    public void eclkcc(View view) {
        finish();
    }
}
