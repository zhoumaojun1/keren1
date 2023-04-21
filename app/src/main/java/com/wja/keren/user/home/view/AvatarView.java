package com.wja.keren.user.home.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class AvatarView extends View {


    Paint paint;
    float mRadius;
    Bitmap headBmp;

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBitmap(Bitmap bmp)
    {
        headBmp = bmp;
        paint = null;

        invalidate();
    }

    public void onDraw(Canvas canvas)
    {
        if(headBmp == null) return;

        if(paint == null)
        {
            makePaint();
        }

        if(paint == null)
            return;

        float radius = getWidth() / 2.0f;
        float cx = 0;
        float cy = 0;
        if(mRadius < radius)
        {
            cx = cy = (radius - mRadius)/2.0f;
            radius = mRadius;

        }

        canvas.drawCircle(radius+cx,radius+cy,radius,paint);
    }

    private void makePaint()
    {
        int w = getWidth();
        if(headBmp == null)
        {
            paint = null;

        }
        else
        {
            setBackground(null);
            Bitmap bmp = Bitmap.createScaledBitmap(headBmp,w,w,false);
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mRadius = bmp.getWidth() / 2.0f;
            BitmapShader shader = new BitmapShader(bmp, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);

            paint.setShader(shader);

        }
    }
}
