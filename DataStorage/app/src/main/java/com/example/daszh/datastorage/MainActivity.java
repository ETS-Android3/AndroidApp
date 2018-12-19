package com.example.daszh.datastorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;
    Boolean mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = this.getSharedPreferences("password", MODE_PRIVATE);
        if (!sp.getString("password", "").equals("")) {
            TextView cfmpwd = (TextView) findViewById(R.id.cfmpasswd);
            cfmpwd.setVisibility(View.INVISIBLE);
            TextView pw = (TextView) findViewById(R.id.passwd);
            pw.setHint("Password");
            mode = true;
        } else {
            mode = false;
        }
    }

    public void clkok(View view) {
        TextView pwd = (TextView) findViewById(R.id.passwd);
        TextView cfmpwd = (TextView) findViewById(R.id.cfmpasswd);
        String s1 = pwd.getText().toString();
        String s2 = cfmpwd.getText().toString();
        if (mode) {
            if (s1.equals(sp.getString("password", ""))) {
                Intent i = new Intent(this, Edit.class);
                startActivity(i);
            } else {
                if (s1.equals("")) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (!(s1.equals("")) && s1.equals(s2)) {
                SharedPreferences.Editor e = sp.edit();
                e.putString("password", s1);
                e.commit();
                Intent i = new Intent(this, Edit.class);
                startActivity(i);
            } else {
                if (s1.equals("")) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void clkclr(View view) {
        TextView pwd = (TextView) findViewById(R.id.passwd);
        pwd.setText("");
        TextView cfmpwd = (TextView) findViewById(R.id.cfmpasswd);
        cfmpwd.setText("");
    }

    @Override
    public void onNewIntent(Intent i) {
        super.onBackPressed();
        super.onNewIntent(i);
        if (!sp.getString("password", "").equals("")) {
            TextView cfmpwd = (TextView) findViewById(R.id.cfmpasswd);
            cfmpwd.setVisibility(View.INVISIBLE);
            TextView pw = (TextView) findViewById(R.id.passwd);
            pw.setHint("Password");
            pw.setText("");
            mode = true;
        } else {
            mode = false;
        }
    }
}
