package com.example.yangyuanhao.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.donkingliang.banner.CustomBanner;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import gdut.bsx.share2.FileUtil;
import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;


public class MainActivity extends AppCompatActivity   {

    private TextView mTextMessage;
    private LinearLayoutManager mLayoutManager;
    private ListView mRv;

    private CustomBanner<String> mBanner;
    ListView lst;
    mHeroDB mdb;
    List<Hero> mlist;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.share:
                try {
                    share();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        return true;
    }

    private void setBanner()
    {
        mBanner = (CustomBanner) findViewById(R.id.banner);
        ArrayList<String> images = new ArrayList<>();
        Resources r = this.getResources();
        Uri uri =  Uri.parse(this.getContentResolver().SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.drawable.bn2) + "/"
                + r.getResourceTypeName(R.drawable.bn2) + "/"
                + r.getResourceEntryName(R.drawable.bn2));
        images.add(uri.toString());
        images.add("https://ws2.sinaimg.cn/large/006tNbRwly1fxj5vbu0mgj30go0bedi4.jpg");
        uri =  Uri.parse(this.getContentResolver().SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.drawable.bn1) + "/"
                + r.getResourceTypeName(R.drawable.bn1) + "/"
                + r.getResourceEntryName(R.drawable.bn1));
        images.add(uri.toString());
        uri =  Uri.parse(this.getContentResolver().SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.drawable.bn3) + "/"
                + r.getResourceTypeName(R.drawable.bn3) + "/"
                + r.getResourceEntryName(R.drawable.bn3));
        images.add(uri.toString());
        uri =  Uri.parse(this.getContentResolver().SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.drawable.bn4) + "/"
                + r.getResourceTypeName(R.drawable.bn4) + "/"
                + r.getResourceEntryName(R.drawable.bn4));
        images.add(uri.toString());
        uri =  Uri.parse(this.getContentResolver().SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.drawable.bn5) + "/"
                + r.getResourceTypeName(R.drawable.bn5) + "/"
                + r.getResourceEntryName(R.drawable.bn5));
        images.add(uri.toString());
        images.add("https://ws2.sinaimg.cn/large/006tNbRwly1fxj5vbu0mgj30go0bedi4.jpg");
        images.add("https://ws2.sinaimg.cn/large/006tNbRwly1fxj5wwdaa8j30s30j3gpq.jpg");
        images.add("https://ws4.sinaimg.cn/large/006tNbRwly1fxj5xviuj9j30go08dmy3.jpg");
        setBean(images);
    }

    @Override
    public void onBackPressed() {
//        if (jzvdStd.backPress()) {
//            return;
//        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
       // jzvdStd.releaseAllVideos();
    }

    private void share() throws MalformedURLException, URISyntaxException {
        new Share2.Builder(this)
                .setContentType(ShareContentType.TEXT)
                .setTextContent("这是个优秀的app，汇集了很多的王者荣耀英雄介绍，快来下载看看吧")
                .setTitle("分享王者英雄app")
                .build()
                .shareBySystem();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lst = findViewById(R.id.herolist);
        setBanner();
        mdb = new mHeroDB(this);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zhaoyun);
        mdb.inserthero(new Hero("赵云", "引擎之心", "战士", 60, 60, 60, 50, "龙鸣", "惊雷之龙", "破云之龙", "天翔之龙", bmp));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.lianpo);
        mdb.inserthero(new Hero("廉颇", "正义爆轰", "坦克", 100, 30, 40, 30, "友情守护", "豪情突进", "激情迸发", "正义豪腕", bmp));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bailishouyue);
        mdb.inserthero(new Hero("百里守约", "静谧之眼", "射手", 20, 70, 40, 70, "瞄准", "静谧之眼", "狂风之息", "逃脱", bmp));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.xiaoqiao);
        mdb.inserthero(new Hero("小乔", "恋之微风", "法师", 20, 10, 80, 30, "治愈微笑", "绽放之舞", "甜蜜恋风", "星华缭乱", bmp));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zhugeliang);
        mdb.inserthero(new Hero("诸葛亮", "绝代智谋", "法师", 30, 10, 80, 60, "策谋之刻", "东风破袭", "时空穿梭", "元气弹", bmp));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zhugeliang);
        mdb.inserthero(new Hero("zgl", "绝代智谋", "法师", 30, 10, 80, 60, "策谋之刻", "东风破袭", "时空穿梭", "元气弹", bmp));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.mozi);
        mdb.inserthero(new Hero("墨子", "和平守望", "法师", 50, 40, 50, 60, "兼爱非攻", "和平漫步", "机关重炮", "墨守成规", bmp));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.daji);
        mdb.inserthero(new Hero("妲己", "魅力之狐", "法师", 20, 10, 80, 20, "失心", "灵魂冲击", "偶像魅力", "女王崇拜", bmp));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.yingzheng);
        mdb.inserthero(new Hero("嬴政", "王者独尊", "法师", 30, 40, 100, 60, "王者审判", "王者惩戒", "王者守御", "至尊王权", bmp));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sunshangxiang);
        mdb.inserthero(new Hero("孙尚香", "千金重弩", "射手", 30, 80, 50, 60, "活力迸发", "翻滚突袭", "红莲爆弹", "究极弩炮", bmp));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.lvbu);
        mdb.inserthero(new Hero("吕布", "无双之魔", "战士", 60, 60, 30, 40, "饕餮血统", "方天画斩", "贪狼之握", "魔神降世", bmp));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.baiqi);
        mdb.inserthero(new Hero("白起", "最终兵器", "坦克", 80, 30, 40, 40, "反击之镰", "血之回响", "死神之镰", "傲慢嘲讽", bmp));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.libai);
        mdb.inserthero(new Hero("李白", "青莲剑仙", "刺客", 40, 70, 60, 90, "侠客行", "将进酒", "神来之笔", "青莲剑哥", bmp));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.taiyizhenren);
        mdb.inserthero(new Hero("太乙真人", "炼金大师", "辅助", 50, 40, 60, 70, "金光闪闪", "意外事故", "第三只手", "大变活人", bmp));

        updatelistviewbypro("");

        RadioGroup rg = findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                TextView t = findViewById(R.id.editText);
                switch (checkedId) {
                    case R.id.rb1:
                        t.setText("");
                        updatelistviewbypro("");
                        break;
                    case R.id.rb2:
                        t.setText("");
                        updatelistviewbypro("坦克");
                        break;
                    case R.id.rb3:
                        t.setText("");
                        updatelistviewbypro("战士");
                        break;
                    case R.id.rb4:
                        t.setText("");
                        updatelistviewbypro("刺客");
                        break;
                    case R.id.rb5:
                        t.setText("");
                        updatelistviewbypro("法师");
                        break;
                    case R.id.rb6:
                        t.setText("");
                        updatelistviewbypro("射手");
                        break;
                    case R.id.rb7:
                        t.setText("");
                        updatelistviewbypro("辅助");
                        break;
                }
            }
        });
        rg.check(R.id.rb1);
//        setContentView(R.layout.detail);
//        setVideo();
//        share();
       // mAdsdkContext = new VideoAdContext(this, new Gson().tojson(),null);

        //setContentView(R.layout.edit);
        //setContentView(R.layout.edit2);
    }

    private void setBean(final ArrayList<String> beans) {
        mBanner.setPages(new CustomBanner.ViewCreator<String>() {
            @Override
            public View createView(Context context, int position) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, int position, String entity) {
                Glide.with(context).load(entity).into((ImageView) view);
            }
        }, beans)
//                //设置指示器为普通指示器
//                .setIndicatorStyle(CustomBanner.IndicatorStyle.ORDINARY)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setIndicatorRes(R.drawable.shape_point_select, R.drawable.shape_point_unselect)
//                //设置指示器的方向
//                .setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER)
//                //设置指示器的指示点间隔
//                .setIndicatorInterval(20)
                //设置自动翻页
                .startTurning(5000);
    }

    void updatelv() {
        final heroadapter ha = new heroadapter(this, mlist);
        ha.setOndlis(new heroadapter.ondellis() {
            @Override
            public void onDel(final int pos) {
                final AlertDialog.Builder ald = new AlertDialog.Builder(MainActivity.this);
                ald.setTitle("是否删除?").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String thename = mlist.get(pos).name;
                        mdb.deletehero(thename);
                        updatelistviewbypro("");
                        Toast.makeText(MainActivity.this, "已删除", Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
                ald.show();
            }
        });
        ha.setOnDelListener(new heroadapter.onTopListener() {
            @Override
            public void onTop(int pos) {
                if (pos > 0 && pos < mlist.size()) {
                    Hero swipeBean = mlist.get(pos);
                    mlist.remove(swipeBean);
                    ha.notifyDataSetChanged();
                    mlist.add(0, swipeBean);
                    ha.notifyDataSetChanged();
                }
            }
        });
        ha.setOnthrdlis(new heroadapter.tdlis() {
            @Override
            public void onthrd(int position) {
                Intent in = new Intent(MainActivity.this, detailActivity.class);
                in.putExtra("name", mlist.get(position).getName());
                in.putExtra("title", mlist.get(position).getTitle());
                in.putExtra("pro", mlist.get(position).getProfession());
                in.putExtra("health", mlist.get(position).getHealth());
                in.putExtra("attack", mlist.get(position).getAttack());
                in.putExtra("skill", mlist.get(position).getSkill());
                in.putExtra("difficulty", mlist.get(position).getDifficulty());
                in.putExtra("skl1", mlist.get(position).getSkill1());
                in.putExtra("skl2", mlist.get(position).getSkill2());
                in.putExtra("skl3", mlist.get(position).getSkill3());
                in.putExtra("skl4", mlist.get(position).getSkill4());
                startActivity(in);
            }
        });


        /*lst.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder ald = new AlertDialog.Builder(MainActivity.this);
                ald.setTitle("是否删除?").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mdb.deletehero(mlist.get(position).getName());
                        mlist = mdb.getallhero();
                        RadioGroup rg = findViewById(R.id.radioGroup);
                        rg.check(R.id.rb1);
                        Toast.makeText(MainActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                        updatelistviewbypro("");
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
                ald.show();
                return true;
            }
        });*/
        lst.setAdapter(ha);
    }

    void updatelistviewbypro(String pro) {
        if (!pro.equals("")) {
            mlist = mdb.getherobypro(pro);
        } else {
            mlist = mdb.getallhero();
        }
        updatelv();
    }

    public void clksrch(View view) {
        RadioGroup rg = findViewById(R.id.radioGroup);
        rg.check(R.id.rb1);
        TextView t = findViewById(R.id.editText);
        if (t.getText().toString().equals("")) {
            Toast.makeText(this, "输入为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        mlist = mdb.getherobyname(t.getText().toString());
        if (mlist.size() == 0) {
            Toast.makeText(this, "未找到此英雄", Toast.LENGTH_SHORT).show();
            return;
        }
        updatelv();
    }

    public void clkadd(View view) {
        Intent in = new Intent(MainActivity.this, Edit.class);
        in.putExtra("mode", true);
        startActivity(in);
    }

    @Override
    public void onNewIntent(Intent in) {
        updatelistviewbypro("");
    }


}
