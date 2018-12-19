package com.example.daszh.datastoragedb;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class myAdapter extends BaseAdapter {
    Context c;
    List<Map<String, Object>> mlist = new ArrayList<>();
    public myAdapter(Context context, List<Map<String, Object>> l) {
        c = context;
        mlist = l;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return  mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View v, ViewGroup vg) {
        viewholder vh = null;
        if (v == null) {
            vh = new viewholder();
            v = LayoutInflater.from(c).inflate(R.layout.item, null);
            vh.iconuri = v.findViewById(R.id.uicon);
            vh.comment = v.findViewById(R.id.comment);
            vh.num = v.findViewById(R.id.prove);
            vh.proveuri = v.findViewById(R.id.thumb);
            vh.usrname = v.findViewById(R.id.usrname);
            vh.time = v.findViewById(R.id.time);
            v.setTag(vh);
        } else {
            vh = (viewholder) v.getTag();
        }
        vh.time.setText(mlist.get(i).get("time").toString());
        vh.usrname.setText(mlist.get(i).get("username").toString());
        vh.proveuri.setImageURI(Uri.parse(mlist.get(i).get("prove").toString()));
        vh.num.setText(mlist.get(i).get("num").toString());
        vh.comment.setText(mlist.get(i).get("comment").toString());
        byte[] img = (byte[]) mlist.get(i).get("icon");
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);

        vh.iconuri.setImageBitmap(bitmap);
        //vh.iconuri.setImageURI(Uri.parse(mlist.get(i).get("icon").toString()));
        vh.proveuri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monprovelistener.onproveclick(i);
            }
        });
        return v;
    }

    public interface onprovelistener {
        void onproveclick(int i);
    }

    private onprovelistener monprovelistener;

    public void setonproveclick(onprovelistener o) {
        this.monprovelistener = o;
    }

    class viewholder {
        ImageView iconuri;
        ImageView proveuri;
        TextView usrname;
        TextView time;
        TextView comment;
        TextView num;
    }
}
