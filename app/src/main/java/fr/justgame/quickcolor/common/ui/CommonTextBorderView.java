package fr.justgame.quickcolor.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import fr.justgame.quickcolor.R;

/**
 * Created by aaitzeouay on 17/07/2017.
 */

public class CommonTextBorderView extends TextView {

    private int style;
    private String text;
    int res[]={R.color.black};


    public CommonTextBorderView(Context context) {
        super(context);
        style = FontManager.Style.FILBERT_BRUSH;
        init();
    }

    public CommonTextBorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        init();
    }

    public CommonTextBorderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        init();
    }

    private void init(){
        if(isInEditMode()){
            return;
        }
        setTypeface(FontManager.INSTANCE.getTypeFace(getContext(), style));
    }

    private void parseAttributes(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CommonTextBorderView, 0, 0);
        style = a.getInt(R.styleable.CommonTextBorderView_font_style_custom, FontManager.Style.FILBERT_BRUSH);
        text = a.getText(R.styleable.CommonTextBorderView_custom_text).toString();//getAttributeValue("http://schemas.android.com/apk/res/android", "textSize");
        Log.d("parseAttributes", text);
        a.recycle();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("onDraw", text);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(text, 50, 50, paint);

        // draw again in white, slightly smaller
        paint.setColor(Color.WHITE);
        paint.setTextSize(18);                // text size

        canvas.drawText(text, 50, 50, paint);
        super.draw(canvas);
    }
}

