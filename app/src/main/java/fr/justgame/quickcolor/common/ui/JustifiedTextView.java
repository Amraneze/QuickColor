package fr.justgame.quickcolor.common.ui;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by aaitzeouay on 09/07/2017.
 */

public class JustifiedTextView extends com.uncopt.android.widget.text.justify.JustifiedTextView {

    public JustifiedTextView(Context context) {
        super(context);
        init();
    }

    public JustifiedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JustifiedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        setTypeface(FontManager.INSTANCE.getTypeFace(getContext(), FontManager.Style.FILBERT_BRUSH));
    }
}
