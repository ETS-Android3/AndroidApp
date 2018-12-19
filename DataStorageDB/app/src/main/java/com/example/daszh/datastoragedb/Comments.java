package com.example.daszh.datastoragedb;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class Comments extends AppCompatActivity {
    String username;
    myDB mdb;
    ListView mlstv;
    List<Map<String, Object>> info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            username = b.getString("username");
        }
        mdb = new myDB(this);
        mlstv = findViewById(R.id.comm);
        info = mdb.getcomments();
        final myAdapter m = new myAdapter(this, info);
        m.setonproveclick(new myAdapter.onprovelistener() {
            @Override
            public void onproveclick(int i) {
                int p = Integer.parseInt(info.get(i).get("num").toString());
                p++;
                Resources r = getResources();
                String picon = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + r.getResourcePackageName(R.drawable.red)
                        + "/" + r.getResourceTypeName(R.drawable.red)
                        + "/" + r.getResourceEntryName(R.drawable.red);
                info.get(i).put("num", String.valueOf(p));
                info.get(i).put("prove", picon);
                mdb.updateprove(info.get(i).get("time").toString(), p);
                m.notifyDataSetChanged();
            }
        });
        mlstv.setAdapter(m);
        mlstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = "用户名: " + info.get(position).get("username").toString() + "\n" + "联系方式: ";
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = \"" + info.get(position).get("username").toString() + "\"", null, null);
                if (cursor != null && !cursor.moveToFirst()) {
                    msg += "not exist";
                } else {
                    do {
                        msg += cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + "         ";
                    } while (cursor.moveToNext());
                }
                final AlertDialog.Builder ald = new AlertDialog.Builder(Comments.this);
                ald.setTitle("INFO").setMessage(msg).setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
                ald.show();
            }
        });
        mlstv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (info.get(position).get("username").toString().equals(username)) {
                    final AlertDialog.Builder ald = new AlertDialog.Builder(Comments.this);
                    ald.setTitle("是否删除?").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mdb.delcomment(info.get(position).get("time").toString());
                            updatelistview();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
                    ald.show();
                } else {
                    final AlertDialog.Builder ald = new AlertDialog.Builder(Comments.this);
                    ald.setTitle("是否举报?").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Comments.this, "已举报", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
                    ald.show();
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void updatelistview() {
        info.clear();
        info = mdb.getcomments();
        final myAdapter m = new myAdapter(this, info);
        m.setonproveclick(new myAdapter.onprovelistener() {
            @Override
            public void onproveclick(int i) {
                int p = Integer.parseInt(info.get(i).get("num").toString());
                p++;
                Resources r = getResources();
                String picon = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + r.getResourcePackageName(R.drawable.red)
                        + "/" + r.getResourceTypeName(R.drawable.red)
                        + "/" + r.getResourceEntryName(R.drawable.red);
                info.get(i).put("num", String.valueOf(p));
                info.get(i).put("prove", picon);
                mdb.updateprove(info.get(i).get("time").toString(), p);
                m.notifyDataSetChanged();
            }
        });
        mlstv.setAdapter(m);
        mlstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = "用户名: " + info.get(position).get("username").toString() + "\n" + "联系方式: ";
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = \"" + info.get(position).get("username").toString() + "\"", null, null);
                if (cursor != null && !cursor.moveToFirst()) {
                    msg += "not exist";
                } else {
                    do {
                        msg += cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + "         ";
                    } while (cursor.moveToNext());
                }
                final AlertDialog.Builder ald = new AlertDialog.Builder(Comments.this);
                ald.setTitle("INFO").setMessage(msg).setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
                ald.show();
            }
        });
        mlstv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (info.get(position).get("username").toString().equals(username)) {
                    final AlertDialog.Builder ald = new AlertDialog.Builder(Comments.this);
                    ald.setTitle("是否删除?").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mdb.delcomment(info.get(position).get("time").toString());
                            updatelistview();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
                    ald.show();
                } else {
                    final AlertDialog.Builder ald = new AlertDialog.Builder(Comments.this);
                    ald.setTitle("是否举报?").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Comments.this, "已举报", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
                    ald.show();
                }
                return true;
            }
        });
    }

    public void clksd(View view) {
        TextView t = findViewById(R.id.mcomm);
        if (t.getText().equals("")) {
            Toast.makeText(this, "评论不能为空", Toast.LENGTH_SHORT).show();
        } else {
            mdb.insertcomment(username, t.getText().toString());
            updatelistview();
            t.setText("");
        }
    }
}
