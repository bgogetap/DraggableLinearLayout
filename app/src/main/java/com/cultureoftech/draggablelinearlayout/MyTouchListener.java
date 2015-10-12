package com.cultureoftech.draggablelinearlayout;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by bgogetap on 10/11/15.
 */
public class MyTouchListener implements View.OnTouchListener {

    private final View itemView;

    MyTouchListener(View itemView) {
        this.itemView = itemView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            Bitmap bitmap = Bitmap.createBitmap(itemView.getWidth(), itemView.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            itemView.draw(canvas);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(itemView);
            itemView.startDrag(data, shadowBuilder, itemView, 0);
            itemView.setVisibility(View.INVISIBLE);
            return true;
        }

        return false;
    }
}
