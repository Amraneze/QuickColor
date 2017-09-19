package fr.justgame.quickcolor.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import fr.justgame.quickcolor.R;

/**
 * Created by Amrane Ait Zeouay on 14-Sep-17.
 */

public class TextViewOutline extends TextView {

    // constants
    private static final int DEFAULT_OUTLINE_SIZE = 0;
    private static final int DEFAULT_OUTLINE_COLOR = Color.TRANSPARENT;

    // data
    private int mOutlineSize;
    private int mOutlineColor;
    private int mTextColor;
    private float mShadowRadius;
    private float mShadowDx;
    private float mShadowDy;
    private int mShadowColor;
    private int mStyle;

    public TextViewOutline(Context context) {
        this(context, null);
        mStyle = FontManager.Style.FILBERT_BRUSH;
    }

    public TextViewOutline(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(attrs);
    }

    private void setAttributes(AttributeSet attrs){
        // set defaults
        mOutlineSize = DEFAULT_OUTLINE_SIZE;
        mOutlineColor = DEFAULT_OUTLINE_COLOR;
        // text color
        mTextColor = getCurrentTextColor();
        if(attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.TextViewOutline);
            // outline size
            mStyle = a.getInt(R.styleable.TextViewOutline_stroked_font_style, FontManager.Style.FILBERT_BRUSH);
            setTypeface(FontManager.INSTANCE.getTypeFace(getContext(), mStyle));
            if (a.hasValue(R.styleable.TextViewOutline_outlineSize)) {
                mOutlineSize = (int) a.getDimension(R.styleable.TextViewOutline_outlineSize, DEFAULT_OUTLINE_SIZE);
            }
            // outline color
            if (a.hasValue(R.styleable.TextViewOutline_outlineColor)) {
                mOutlineColor = a.getColor(R.styleable.TextViewOutline_outlineColor, DEFAULT_OUTLINE_COLOR);
            }
            // shadow (the reason we take shadow from attributes is because we use API level 15 and only from 16 we have the get methods for the shadow attributes)
            if (a.hasValue(R.styleable.TextViewOutline_android_shadowRadius)
                    || a.hasValue(R.styleable.TextViewOutline_android_shadowDx)
                    || a.hasValue(R.styleable.TextViewOutline_android_shadowDy)
                    || a.hasValue(R.styleable.TextViewOutline_android_shadowColor)) {
                mShadowRadius = a.getFloat(R.styleable.TextViewOutline_android_shadowRadius, 0);
                mShadowDx = a.getFloat(R.styleable.TextViewOutline_android_shadowDx, 0);
                mShadowDy = a.getFloat(R.styleable.TextViewOutline_android_shadowDy, 0);
                mShadowColor = a.getColor(R.styleable.TextViewOutline_android_shadowColor, Color.TRANSPARENT);
            }

            a.recycle();
        }

        Log.d("TextView", "mOutlineSize = " + mOutlineSize);
        Log.d("TextView", "mOutlineColor = " + mOutlineColor);
    }

    private void setPaintToOutline(){

        Paint paint = getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mOutlineSize + 10);
        paint.setAntiAlias(true);
        super.setTextColor(mOutlineColor);
        super.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy,  mShadowColor);
        /*Log.e("setPaint", getTextSize()+" Size");
        Log.e("setPaint", paint.getStrokeWidth()+" width");
        Log.e("setPaint", getWidth()+" widt");*/
        setWidth(getWidth() + 20);
    }

    private void setPaintToRegular() {
        Paint paint = getPaint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);
        paint.setAntiAlias(true);
        super.setTextColor(mTextColor);
        super.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
        /*Log.e("setPaintToRegular", paint.getStrokeWidth()+" width");
        Log.e("setPaintToRegular", getWidth()+" widt");*/
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setPaintToOutline();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        mTextColor = color;
    }

    @Override
    public void setShadowLayer(float radius, float dx, float dy, int color) {
        super.setShadowLayer(radius, dx, dy, color);
        mShadowRadius = radius;
        mShadowDx = dx;
        mShadowDy = dy;
        mShadowColor = color;
    }

    public void setOutlineSize(int size){
        mOutlineSize = size;
    }

    public void setOutlineColor(int color){
        mOutlineColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setPaintToOutline();
        super.onDraw(canvas);
        setPaintToRegular();
        super.onDraw(canvas);
    }

}
