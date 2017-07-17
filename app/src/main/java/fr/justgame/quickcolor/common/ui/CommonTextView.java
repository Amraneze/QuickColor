package fr.justgame.quickcolor.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import fr.justgame.quickcolor.R;

/**
 * Created by aaitzeouay on 17/07/2017.
 */

public class CommonTextView extends TextView {

    private int style;

    public CommonTextView(Context context) {
        super(context);
        style = FontManager.Style.FILBERT_BRUSH;
        init();
    }

    public CommonTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        init();
    }

    public CommonTextView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CommonTextView, 0, 0);
        style = a.getInt(R.styleable.CommonTextView_font_style, FontManager.Style.FILBERT_BRUSH);
        a.recycle();
    }
}
