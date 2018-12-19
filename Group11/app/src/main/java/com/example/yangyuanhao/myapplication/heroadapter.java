package com.example.yangyuanhao.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;


public class heroadapter extends BaseAdapter {
    Context c;
    List<Hero> hero;
    mHeroDB mdb;
    private onTopListener mOnTopListener;
    private ondellis mondellis;
    private tdlis mthdlis;
    public heroadapter(Context con, List<Hero> h) {
        c = con;
        hero = h;
        mdb = new mHeroDB(c);
    }

    @Override
    public int getCount() {return hero.size();}

    @Override
    public Hero getItem(int i) {return hero.get(i);}

    @Override
    public long getItemId(int i) {return i;}

    @Override
    public View getView(final int i, View v, ViewGroup vg) {
        viewholder vh = null;
        if (v == null) {
            vh = new viewholder();
            v = LayoutInflater.from(c).inflate(R.layout.item, null);
            vh.icon = v.findViewById(R.id.heroicon);
            vh.name = v.findViewById(R.id.heroname);
            vh.profession = v.findViewById(R.id.heropro);
            vh.title = v.findViewById(R.id.herotitle);
            vh.btn_delete = v.findViewById(R.id.btnDelete);
            vh.btn_top = v.findViewById(R.id.btnTop);
            vh.btn_top.setTag(i);
            vh.swipe = v.findViewById(R.id.swipe);
            vh.btn_delete.setTag(i);
            vh.third = v.findViewById(R.id.third);
            vh.third.setTag(i);
            v.setTag(vh);
        } else {
            vh = (viewholder) v.getTag();
        }
        vh.icon.setImageBitmap(hero.get(i).getIcon());
        vh.name.setText(hero.get(i).getName());
        vh.profession.setText("职业: " + hero.get(i).getProfession());
        vh.title.setText(hero.get(i).getTitle());
        final SwipeMenuLayout thislayout = vh.swipe;
        vh.btn_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != mOnTopListener){
                    mOnTopListener.onTop(i);
                }
            }
        });



        vh.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int pos = (int)view.getTag();
                if (mondellis != null) {
                    mondellis.onDel(i);
                }


            }
        });
        vh.third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int pos = (int)view.getTag();
                if (mthdlis != null) {
                    mthdlis.onthrd(i);
                }
            }
        });
        return v;
    }

    class viewholder {
        ImageView icon;
        TextView name;
        TextView profession;
        TextView title;
        Button btn_delete;
        Button btn_top;
        RelativeLayout third;
        SwipeMenuLayout swipe;
    }

    public interface onTopListener {
        void onTop(int pos);
    }

    public interface ondellis {
        void onDel(int pos);
    }

    public interface tdlis {
        void onthrd(int pos);
    }

    public void setOnDelListener(onTopListener mOnDelListener) {
        this.mOnTopListener = mOnDelListener;
    }

    public void setOndlis(ondellis model) {
        this.mondellis = model;
    }

    public void setOnthrdlis(tdlis thd) {
        this.mthdlis = thd;
    }
}

