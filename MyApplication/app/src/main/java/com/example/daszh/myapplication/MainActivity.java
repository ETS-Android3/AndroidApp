package com.example.daszh.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup grp = (RadioGroup) findViewById(R.id.rdgrp);
        grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String out = "";
                if (checkedId == R.id.rbtn1) {
                    out += "图片";
                } else if (checkedId == R.id.rbtn2) {
                    out += "视频";
                } else if (checkedId == R.id.rbtn3) {
                    out += "问答";
                } else {
                    out += "资讯";
                }
                out += "被选中";
                Toast.makeText(getApplicationContext(), out, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ClickSearchButton(View target) {
        EditText searchcontent = (EditText)findViewById(R.id.editText2);
        String search = searchcontent.getText().toString();
        if (search.equals("")) {
            Toast.makeText(getApplicationContext(), "搜索内容不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton r1 = (RadioButton)findViewById(R.id.rbtn1);
        RadioButton r2 = (RadioButton)findViewById(R.id.rbtn2);
        RadioButton r3 = (RadioButton)findViewById(R.id.rbtn3);
        RadioButton r4 = (RadioButton)findViewById(R.id.rbtn4);
        String output = "";
        if (search.equals("Health")) {
            String categary = "";
            if (r1.isChecked()) {
                categary = "图片";
            } else if (r2.isChecked()) {
                categary = "视频";
            } else if (r3.isChecked()) {
                categary = "问答";
            } else if (r4.isChecked()) {
                categary = "资讯";
            }
            output = categary + "搜索成功！";


        } else {
            output = "搜索失败！";
        }
        final AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("提示").setMessage(output).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "你点击了确认", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "你点击了取消", Toast.LENGTH_SHORT).show();
            }
        }).create();
        alertdialog.show();

    }

}
