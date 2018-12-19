package com.example.daszh.myapplication;

import android.annotation.TargetApi;



import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@TargetApi(28)
public class NewMainActivity extends AppCompatActivity {
    String tmpfood = null;
    ArrayList<Food> all = new ArrayList<Food>();
    private static final String STATICACTION = "com.exanple.daszh.myapplication.MyStaticFilter";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        all = new FoodInfo().myfood;


        EventBus.getDefault().register(this);


        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_activitymain);

        updaterview();
        updatelview();

        Random r = new Random();
        Bundle bb = new Bundle();
        int t = r.nextInt(9);
        bb.putString("Foodname", all.get(t).getName());
        Intent bi = new Intent(NewMainActivity.this, staticrcvr.class);
        bi.setAction("com.exanple.daszh.myapplication.MyStaticFilter");
        bi.putExtras(bb);
        sendBroadcast(bi);

        Intent wbi = new Intent(NewMainActivity.this, NewAppWidget.class);
        wbi.setAction("com.example.daszh.myapplication.MyWStaticFilter");
        Bundle wbb = new Bundle();
        wbb.putString("FN", all.get(t).getName());
        wbi.putExtras(wbb);
        sendBroadcast(wbi);







    }

    public void updaterview() {
        final RecyclerView foodrv = (RecyclerView) findViewById(R.id.foodRV);
        final int total = all.size();
        final List<Map<String, String>> items = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            Map<String, String> tmp = new LinkedHashMap<>();
            tmp.put("foodCshort", all.get(i).getCshort());
            tmp.put("foodName", all.get(i).getName());
            items.add(tmp);
        }
        foodrv.setHasFixedSize(true);
        final MyAdapter ra = new MyAdapter(items);
        ra.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(NewMainActivity.this, DetailActivity.class);
                intent.putExtra("foodName", items.get(position).get("foodName"));
                tmpfood = items.get(position).get("foodName");
                startActivity(intent);

            }

            @Override
            public void onLongClick(int position) {
                String s1 = "删除";
                s1 += items.get(position).get("foodName");
                items.remove(position);
                ra.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_SHORT).show();

            }
        });
        RecyclerView.LayoutManager la = new LinearLayoutManager(this);
        foodrv.setLayoutManager(la);
        foodrv.setAdapter(ra);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFood(Food f) {
        if (f.getName().equals("null")) {
            FloatingActionButton fbutton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
            final RecyclerView foodrv = (RecyclerView) findViewById(R.id.foodRV);
            final ListView foodlist = (ListView) findViewById(R.id.foodList);
            foodrv.setVisibility(View.INVISIBLE);
            foodlist.setVisibility(View.VISIBLE);
            fbutton.setImageDrawable(getResources().getDrawable(R.drawable.mainpage));
            return;
        }
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getName().equals(f.getName())) {
                all.get(i).collect++;
                break;
            }
        }
        updaterview();
        updatelview();

    }


    public void updatelview() {

        final List<Map<String, String>> citems = new ArrayList<>();
        Map<String, String> temp = new LinkedHashMap<>();
        temp.put("foodCshort", "★");
        temp.put("foodName", "收藏");
        citems.add(temp);
        for (int i = 0; i < all.size(); i++) {
            for (int j = 0; j < all.get(i).collect; j++) {
                Map<String, String> tmp = new LinkedHashMap<>();
                tmp.put("foodCshort", all.get(i).getCshort());
                tmp.put("foodName", all.get(i).getName());
                citems.add(tmp);
            }

        }

        final SimpleAdapter s = new SimpleAdapter(this, citems, R.layout.item, new String[]{"foodCshort", "foodName"}, new int[]{R.id.foodCshort, R.id.foodName});
        final ListView foodlist = (ListView) findViewById(R.id.foodList);
        foodlist.setAdapter(s);
        foodlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (citems.get(position).get("foodName").equals("收藏")) return;
                Intent intent = new Intent(NewMainActivity.this, DetailActivity.class);
                intent.putExtra("foodName", citems.get(position).get("foodName"));
                tmpfood = citems.get(position).get("foodName");
                startActivity(intent);
            }
        });
        foodlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (citems.get(position).get("foodName").equals("收藏")) return true;
                String s1 = "确定删除";
                s1 += citems.get(position).get("foodName");
                s1 += "?";

                final AlertDialog.Builder ad = new AlertDialog.Builder(NewMainActivity.this);
                ad.setTitle("删除").setMessage(s1).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i < all.size(); i++) {
                            if (all.get(i).getName().equals(citems.get(position).get("foodName"))) {
                                all.get(i).collect--;
                            }
                        }

                        citems.remove(position);
                        s.notifyDataSetChanged();
                    }
                }).create();
                ad.show();
                return true;
            }
        });


    }

    public void fff(View target) {
        FloatingActionButton fbutton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        final RecyclerView foodrv = (RecyclerView) findViewById(R.id.foodRV);
        final ListView foodlist = (ListView) findViewById(R.id.foodList);
        if (foodlist.getVisibility() == View.VISIBLE) {
            foodlist.setVisibility(foodrv.getVisibility());
            foodrv.setVisibility(View.VISIBLE);
            fbutton.setImageDrawable(getResources().getDrawable(R.drawable.collect));

        } else {
            foodrv.setVisibility(foodlist.getVisibility());
            foodlist.setVisibility(View.VISIBLE);
            fbutton.setImageDrawable(getResources().getDrawable(R.drawable.mainpage));

        }
    }

/*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            FloatingActionButton fbutton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
            final RecyclerView foodrv = (RecyclerView) findViewById(R.id.foodRV);
            final ListView foodlist = (ListView) findViewById(R.id.foodList);
            foodrv.setVisibility(View.INVISIBLE);
            foodlist.setVisibility(View.VISIBLE);
            fbutton.setImageDrawable(getResources().getDrawable(R.drawable.mainpage));
        }
    }*/
    @Override
    public void onBackPressed() {
        FloatingActionButton fbutton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        final RecyclerView foodrv = (RecyclerView) findViewById(R.id.foodRV);
        final ListView foodlist = (ListView) findViewById(R.id.foodList);
        if (foodlist.getVisibility() == View.VISIBLE) {
            foodlist.setVisibility(View.INVISIBLE);
            foodrv.setVisibility(View.VISIBLE);
            fbutton.setImageDrawable(getResources().getDrawable(R.drawable.collect));
            return;
        }
        super.onBackPressed();
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Override
    public void onNewIntent(Intent i) {


        Bundle b = i.getExtras();
        if (b != null) {
            boolean f = b.getBoolean("Tocol");
            if (f) {
                FloatingActionButton fbutton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
                final RecyclerView foodrv = (RecyclerView) findViewById(R.id.foodRV);
                final ListView foodlist = (ListView) findViewById(R.id.foodList);
                foodrv.setVisibility(View.INVISIBLE);
                foodlist.setVisibility(View.VISIBLE);
                fbutton.setImageDrawable(getResources().getDrawable(R.drawable.mainpage));
            }
        }
        super.onNewIntent(i);
    }
    @Override
    public void onRestart() {
        super.onRestart();
        Random r = new Random();
        Bundle bb = new Bundle();
        int t = r.nextInt(9);
        bb.putString("Foodname", all.get(t).getName());
        Intent bi = new Intent(NewMainActivity.this, staticrcvr.class);
        bi.setAction("com.exanple.daszh.myapplication.MyStaticFilter");
        bi.putExtras(bb);
        sendBroadcast(bi);

        Intent wbi = new Intent(NewMainActivity.this, NewAppWidget.class);
        wbi.setAction("com.example.daszh.myapplication.MyWStaticFilter");
        Bundle wbb = new Bundle();
        wbb.putString("FN", all.get(t).getName());
        wbi.putExtras(wbb);
        sendBroadcast(wbi);
    }

}
