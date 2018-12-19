package com.example.yangyuanhao.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.PopupWindow;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

/**
 * photoView的单击、双击监听
 * Created by Dovar on 2016/10/10 0010.
 */
public class MyOnDoubleTapListener implements GestureDetector.OnDoubleTapListener {

    private PopupWindow mPopupWindow;
    private Context mContext;

    /**
     * Default constructor
     *
     * @param photoViewAttacher PhotoViewAttacher to bind to
     */
    public MyOnDoubleTapListener(PhotoViewAttacher photoViewAttacher, Context mContext, PopupWindow mPopupWindow) {
        this.mContext = mContext;
        this.mPopupWindow = mPopupWindow;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        DRAppUtil.getInstance().showSystemUI(mPopupWindow.getContentView());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException mE) {
                    mE.printStackTrace();
                }
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPopupWindow.dismiss();
                    }
                });
            }
        }).start();
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {

        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }
}
