package fr.justgame.quickcolor.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import fr.justgame.quickcolor.R;

/**
 * Created by aaitzeouay on 09/07/2017.
 */

public class CommonEditText extends android.support.v7.widget.AppCompatEditText {

    private static final int DEFAULT_FONT = FontManager.Style.FILBERT_BRUSH;

    private int style;
    private boolean hideHintOnTap;
    private String hint;

    public CommonEditText(Context context) {
        super(context);
        style = DEFAULT_FONT;
        init();
    }

    public CommonEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        init();
    }

    public CommonEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        init();
    }

    protected void init() {
        if (isInEditMode()) {
            return;
        }
        setTypeface(FontManager.INSTANCE.getTypeFace(getContext(), style));
        if(getHint() == null){
            return;
        }
        hint = getHint().toString();
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hideHintOnTap) {
                    return;
                }

                if (hasFocus) {
                    setHint("");
                } else if (TextUtils.isEmpty(getText().toString())) {
                    setHint(hint);
                }
            }
        });
    }

    private void parseAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CommonTextView, 0, 0);
        style = a.getInt(R.styleable.CommonTextView_font_style, DEFAULT_FONT);
        a.recycle();
    }

    public void setHideHintOnTap(boolean hideHintOnTap) {
        this.hideHintOnTap = hideHintOnTap;
    }

    public void showHint() {
        setHint(hint);
        clearFocus();
    }

}
