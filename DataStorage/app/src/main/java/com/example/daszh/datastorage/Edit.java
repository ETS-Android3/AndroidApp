package com.example.daszh.datastorage;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Edit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void clksvbtn(View view) {
        EditText t = (EditText) findViewById(R.id.editText);
        String s = t.getText().toString();
        try (FileOutputStream f = openFileOutput("mytext", MODE_PRIVATE)) {
            f.write(s.getBytes());
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            Log.i("TAG", "Successfully saved file.");
        } catch (IOException e) {
            Log.e("TAG", "Fail to save file.");
        }


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void clkldbtn(View view) {
        EditText t = (EditText) findViewById(R.id.editText);
        try (FileInputStream f = openFileInput("mytext")) {
            byte[] s = new byte[f.available()];
            f.read(s);
            t.setText(new String(s));
            Toast.makeText(this, "读取成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "读取失败", Toast.LENGTH_SHORT).show();
            Log.e("TAG", "Fail to load file.");
        }
    }

    public void clkclrbtn(View view) {
        EditText t = (EditText) findViewById(R.id.editText);
        t.setText("");
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
