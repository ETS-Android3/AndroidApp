package com.example.daszh.myapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity{
    boolean star = false;
    String foodName = null;
    boolean collect = false;
    ArrayList<Food> myfood = null;
    mDnmcrcvr Dr = new mDnmcrcvr();

    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        myfood = new FoodInfo().myfood;
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        setContentView(R.layout.detail);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            foodName = b.getString("foodName");
        }
        int f = 0;
        for (int i = 0; i < myfood.size(); i++) {
            if (myfood.get(i).getName().equals(foodName)) {
                f = i;
                break;
            }
        }
        TextView nm = (TextView)findViewById(R.id.itemname);
        nm.setText(foodName);
        TextView ct = (TextView)findViewById(R.id.category);
        ct.setText(myfood.get(f).getCategory());
        TextView nu = (TextView)findViewById((R.id.nutrition));
        String tmp = "富含 ";
        tmp += myfood.get(f).getNutrition();
        nu.setText(tmp);
        LinearLayout l = (LinearLayout)findViewById(R.id.change);
        l.setBackgroundColor(Color.parseColor(myfood.get(f).getColor()));
        ImageButton bk = (ImageButton)findViewById(R.id.goingback);
        ImageButton st = (ImageButton)findViewById(R.id.star);
        nm.setBackgroundColor(Color.parseColor(myfood.get(f).getColor()));
        bk.setBackgroundColor(Color.parseColor(myfood.get(f).getColor()));
        st.setBackgroundColor(Color.parseColor(myfood.get(f).getColor()));


        ListView listView = (ListView)findViewById(R.id.option);
        String[] options = {"分享信息", "不感兴趣", "查看更多信息", "出错反馈", " "};
        ArrayAdapter<String> arra = new ArrayAdapter<>(this, R.layout.detaillist, options);
        listView.setAdapter(arra);



        IntentFilter wif = new IntentFilter();
        wif.addAction("DNMCFLTR");
        registerReceiver(Dr, wif);


    }

    public void goback(View view) {
        Intent i = new Intent(DetailActivity.this, NewMainActivity.class);
        startActivity(i);
        finish();
    }

    public void collec(View view) {
        collect = true;
        Toast.makeText(getApplicationContext(), "已收藏", Toast.LENGTH_SHORT).show();
        Bundle bb = new Bundle();
        bb.putString("Foodname", foodName);

        int pos;
        for (pos = 0; pos < myfood.size(); pos++) {
            if (myfood.get(pos).getName().equals(foodName)) break;
        }
        Food f = new Food(foodName, myfood.get(pos).getCshort(), myfood.get(pos).getCategory(), myfood.get(pos).getNutrition(), myfood.get(pos).getColor());

        EventBus.getDefault().post(f);

        Intent bi = new Intent(DetailActivity.this, staticrcvr.class);
        bi.setAction("com.exanple.daszh.myapplication.MyCollectFilter");
        bi.putExtras(bb);
        sendBroadcast(bi);
        Intent swbi = new Intent(DetailActivity.this, NewAppWidget.class);
        swbi.setAction("DNMCFLTR");
        Bundle wbb = new Bundle();
        wbb.putString("FN", foodName);
        swbi.putExtras(wbb);
        sendBroadcast(swbi);



        Intent wbi = new Intent(DetailActivity.this, mDnmcrcvr.class);
        wbi.setAction("DNMCFLTR");
        wbi.putExtra("FN", foodName);
        sendBroadcast(wbi);


    }

    public void sclick(View view) {
        ImageButton st = (ImageButton)findViewById(R.id.star);
        if (star) {
            st.setImageDrawable(getResources().getDrawable(R.drawable.empty_star));

        } else {
            st.setImageDrawable(getResources().getDrawable(R.drawable.full_star));
        }
        star = !star;
    }
    public void onBackPressed() {
        Intent i = new Intent(DetailActivity.this, NewMainActivity.class);
        startActivity(i);

        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFood(Food f) {
        if (f.getName().equals("null")) {
            Intent intent = new Intent(DetailActivity.this, NewMainActivity.class);
            startActivity(intent);
        }


    }
    @Override
    public void onDestroy() {

        unregisterReceiver(Dr);
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onNewIntent(Intent i) {
        super.onNewIntent(i);

        Bundle b = i.getExtras();
        if (b != null) {
            foodName = b.getString("foodName");
        }
        int f = 0;
        for (int j = 0; j < myfood.size(); j++) {
            if (myfood.get(j).getName().equals(foodName)) {
                f = j;
                break;
            }
        }
        TextView nm = (TextView)findViewById(R.id.itemname);
        nm.setText(foodName);
        TextView ct = (TextView)findViewById(R.id.category);
        ct.setText(myfood.get(f).getCategory());
        TextView nu = (TextView)findViewById((R.id.nutrition));
        String tmp = "富含 ";
        tmp += myfood.get(f).getNutrition();
        nu.setText(tmp);
        LinearLayout l = (LinearLayout)findViewById(R.id.change);
        l.setBackgroundColor(Color.parseColor(myfood.get(f).getColor()));
        ImageButton bk = (ImageButton)findViewById(R.id.goingback);
        ImageButton st = (ImageButton)findViewById(R.id.star);
        nm.setBackgroundColor(Color.parseColor(myfood.get(f).getColor()));
        bk.setBackgroundColor(Color.parseColor(myfood.get(f).getColor()));
        st.setBackgroundColor(Color.parseColor(myfood.get(f).getColor()));

    }

}
