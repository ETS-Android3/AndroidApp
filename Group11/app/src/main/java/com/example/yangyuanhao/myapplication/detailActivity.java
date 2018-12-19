package com.example.yangyuanhao.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import gdut.bsx.share2.Share2;

public class detailActivity extends AppCompatActivity {
    String name;
    FullPhotoView photoView ;
    JzvdStd jzvdStd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        TextView nm = findViewById(R.id.name);
        TextView tt = findViewById(R.id.title);
        TextView pro = findViewById(R.id.profession);
        TextView hp = findViewById(R.id.survival_num);
        TextView atk = findViewById(R.id.attack_num);
        TextView skl = findViewById(R.id.skill_num);
        TextView diff = findViewById(R.id.difficulty_num);
        TextView skl1 = findViewById(R.id.skl1);
        TextView skl2 = findViewById(R.id.skl2);
        TextView skl3 = findViewById(R.id.skl3);
        TextView skl4 = findViewById(R.id.skl4);
        if (b != null) {

            nm.setText(b.getString("name"));
            name = b.getString("name");
            tt.setText(b.getString("title"));
            pro.setText(b.getString("pro"));
            hp.setText(String.valueOf(b.getInt("health")));
            atk.setText(String.valueOf(b.getInt("attack")));
            skl.setText(String.valueOf(b.getInt("skill")));
            diff.setText(String.valueOf(b.getInt("difficulty")));
            skl1.setText(b.getString("skl1"));
            skl2.setText(b.getString("skl2"));
            skl3.setText(b.getString("skl3"));
            skl4.setText(b.getString("skl4"));
        }
        setVideo();
    }


    public void editbtn(View view) {
        Intent in = new Intent(detailActivity.this, Edit.class);
        in.putExtra("mode", false);
        in.putExtra("hero", name);
        startActivity(in);
    }

    public void delbtn(View view) {
        final AlertDialog.Builder ald = new AlertDialog.Builder(detailActivity.this);
        ald.setTitle("是否删除?").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mHeroDB mdb = new mHeroDB(detailActivity.this);
                mdb.deletehero(name);
                Intent in = new Intent(detailActivity.this, MainActivity.class);
                Toast.makeText(detailActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                startActivity(in);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        }).create();
        ald.show();
    }

    private void setVideo()
    {
        photoView = (FullPhotoView) findViewById(R.id.photo_view);
        photoView.setImageResource(R.drawable.zhugeliang);

        jzvdStd = findViewById(R.id.videoplayer);
        jzvdStd.setUp("http://pifaphkn6.bkt.clouddn.com/%E7%8E%8B%E8%80%85%E6%A6%AE%E8%80%80%E8%8B%B1%E9%9B%84%E4%BB%8B%E7%B4%B9%20%20%20%7B%E8%AB%B8%E8%91%9B%E4%BA%AE%7D.mp4"
                , "诸葛亮介绍" , Jzvd.SCREEN_WINDOW_NORMAL);
        Picasso.with(this)
                .load("https://ws4.sinaimg.cn/large/006tNbRwgy1fxd9ch4qilj30ze0jyhdt.jpg")
                .into(jzvdStd.thumbImageView);
    }

    @Override
    public void onNewIntent(Intent in) {
        Bundle b = in.getExtras();
        TextView nm = findViewById(R.id.name);
        TextView tt = findViewById(R.id.title);
        TextView pro = findViewById(R.id.profession);
        TextView hp = findViewById(R.id.survival_num);
        TextView atk = findViewById(R.id.attack_num);
        TextView skl = findViewById(R.id.skill_num);
        TextView diff = findViewById(R.id.difficulty_num);
        TextView skl1 = findViewById(R.id.skl1);
        TextView skl2 = findViewById(R.id.skl2);
        TextView skl3 = findViewById(R.id.skl3);
        TextView skl4 = findViewById(R.id.skl4);
        if (b != null) {

            nm.setText(b.getString("name"));
            name = b.getString("name");
            tt.setText(b.getString("title"));
            pro.setText(b.getString("pro"));
            hp.setText(String.valueOf(b.getInt("health")));
            atk.setText(String.valueOf(b.getInt("attack")));
            skl.setText(String.valueOf(b.getInt("skill")));
            diff.setText(String.valueOf(b.getInt("difficulty")));
            skl1.setText(b.getString("skl1"));
            skl2.setText(b.getString("skl2"));
            skl3.setText(b.getString("skl3"));
            skl4.setText(b.getString("skl4"));
        }
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(detailActivity.this, MainActivity.class);
        startActivity(in);
        finish();
    }
}
