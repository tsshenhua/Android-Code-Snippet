package com.kuyu.view;

import android.text.style.ImageSpan;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.style.ImageSpan;
import java.lang.ref.WeakReference;

/**
 * Created by lssac on 2017/3/24.
 * 图文混排使图片文字基于中线对齐
 */

public class CenterImageSpan extends ImageSpan {

    private WeakReference<Drawable> mDrawableRef;

    public CenterImageSpan(final Drawable drawable) {
        super(drawable);
    }

    @Override
    public int getSize(Paint paint, CharSequence text,
                       int start, int end,
                       Paint.FontMetricsInt fm) {
        Drawable d = getCachedDrawable();
        Rect rect = d.getBounds();

        if (fm != null) {
            Paint.FontMetricsInt pfm = paint.getFontMetricsInt();
            // keep it the same as paint's fm
            fm.ascent = pfm.ascent;
            fm.descent = pfm.descent;
            fm.top = pfm.top;
            fm.bottom = pfm.bottom;
        }

        return rect.right;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, @NonNull Paint paint) {
        Drawable b = getCachedDrawable();
        canvas.save();

        int drawableHeight = b.getIntrinsicHeight();
        int fontAscent = paint.getFontMetricsInt().ascent;
        int fontDescent = paint.getFontMetricsInt().descent;
        int transY = bottom - b.getBounds().bottom +  // align bottom to bottom
                (drawableHeight - fontDescent + fontAscent) / 2;  // align center to center

        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }

    // Redefined locally because it is a private member from DynamicDrawableSpan
    private Drawable getCachedDrawable() {
        WeakReference<Drawable> wr = mDrawableRef;
        Drawable d = null;

        if (wr != null)
            d = wr.get();

        if (d == null) {
            d = getDrawable();
            mDrawableRef = new WeakReference<>(d);
        }

        return d;
    }
}
