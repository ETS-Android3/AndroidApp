package com.example.daszh.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
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

import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {





    List<Map<String, String>> food;
    OnItemClickListener onItemClickListener;



    public MyAdapter(List<Map<String, String>> m) {
        food = m;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        MyViewHolder mh = new MyViewHolder(view);



        return mh;
    }





    @Override
    public void onBindViewHolder(@NonNull final com.example.daszh.myapplication.MyViewHolder holder, int i) {
        Map<String, String> tmp = food.get(i);
        holder.button.setText(tmp.get("foodCshort"));
        holder.textview.setText(tmp.get("foodName"));

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }




    }


    @Override
    public int getItemCount() {
        return food.size();
    }




    public void setOnItemClickListener(OnItemClickListener _onItemClickListener) {
        this.onItemClickListener = _onItemClickListener;
    }


}


class MyViewHolder extends RecyclerView.ViewHolder {

    Button button;
    TextView textview;


    public MyViewHolder(View view) {
        super(view);
        button = view.findViewById(R.id.foodCshort);
        textview = view.findViewById(R.id.foodName);
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void setTextview(TextView textview) {
        this.textview = textview;
    }

    public Button getButton() {
        return button;
    }
    public TextView getTextview() {
        return textview;
    }

}


