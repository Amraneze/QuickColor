package fr.justgame.quickcolor.common.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by aaitzeouay on 09/07/2017.
 */

public class FontManager {

    public static final FontManager INSTANCE = new FontManager();
    private static final int FONT_ARRAY_SIZE = 22;

    private Typeface[] cache;

    private FontManager(){
        cache = new Typeface[FONT_ARRAY_SIZE];
    }

    public Typeface getTypeFace(Context context, @Style int type){
        switch (type){
            case Style.FUTURA_MEDIUM:
                return getTypefaceAndCache(type,context,"fonts/Futura Medium.ttf");
            case Style.FUTURA_HEAVY:
                return getTypefaceAndCache(type,context,"fonts/Futura Heavy.ttf");
            case Style.LIGHT:
                return getTypefaceAndCache(type,context,"fonts/AlrightSans-Light.otf");
            case Style.BLACK:
                return getTypefaceAndCache(type,context,"fonts/AlrightSans-Black.otf");
            case Style.BOLD:
                return getTypefaceAndCache(type,context,"fonts/AlrightSans-Bold.otf");
            case Style.BOLD_ITALIC:
                return getTypefaceAndCache(type,context,"fonts/AlrightSans-BoldItalic.otf");
            case Style.LIGHT_ITALIC:
                return getTypefaceAndCache(type,context,"fonts/AlrightSans-LightItalic.otf");
            case Style.MEDIUM:
                return getTypefaceAndCache(type,context,"fonts/AlrightSans-Medium.otf");
            case Style.MEDIUM_ITALIC:
                return getTypefaceAndCache(type,context,"fonts/AlrightSans-MediumItalic.otf");
            case Style.REGULAR:
                return getTypefaceAndCache(type,context,"fonts/AlrightSans-Regular.otf");
            case Style.REGULAR_ITALIC:
                return getTypefaceAndCache(type,context,"fonts/AlrightSans-RegularItalic.otf");
            case Style.THIN:
                return getTypefaceAndCache(type,context,"fonts/AlrightSans-Thin.otf");
            case Style.O_BOLD:
                return getTypefaceAndCache(type,context,"fonts/OpenSans-Bold.ttf");
            case Style.O_BOLD_ITALIC:
                return getTypefaceAndCache(type,context,"fonts/OpenSans-BoldItalic.ttf");
            case Style.O_EXTRA_BOLD:
                return getTypefaceAndCache(type,context,"fonts/OpenSans-ExtraBold.ttf");
            case Style.O_EXTRA_BOLD_ITALIC:
                return getTypefaceAndCache(type,context,"fonts/OpenSans-ExtraBoldItalic.ttf");
            case Style.O_ITALIC:
                return getTypefaceAndCache(type,context,"fonts/OpenSans-Italic.ttf");
            case Style.O_LIGHT:
                return getTypefaceAndCache(type,context,"fonts/OpenSans-Light.ttf");
            case Style.O_LIGHT_ITALIC:
                return getTypefaceAndCache(type,context,"fonts/OpenSans-LightItalic.ttf");
            case Style.O_REGULAR:
                return getTypefaceAndCache(type,context,"fonts/OpenSans-Regular.ttf");
            case Style.O_SEMI_BOLD:
                return getTypefaceAndCache(type,context,"fonts/OpenSans-Semibold.ttf");
            case Style.O_SEMI_BOLD_ITALIC:
                return getTypefaceAndCache(type,context,"fonts/OpenSans-SemiboldItalic.ttf");
            default:
                throw new IllegalArgumentException("type provided by argument = "+type+" is invalid");
        }
    }

    private Typeface getTypefaceAndCache(int type,Context context, String fontPath){
        if(cache[type] == null){
            cache[type] = Typeface
                    .createFromAsset(context.getAssets(), fontPath);
        }
        return cache[type];
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Style.FUTURA_MEDIUM, Style.FUTURA_HEAVY,Style.LIGHT, Style.BLACK,Style.BOLD,
            Style.BOLD_ITALIC, Style.LIGHT_ITALIC,Style.MEDIUM,Style.MEDIUM_ITALIC,Style.REGULAR,
            Style.REGULAR_ITALIC,Style.THIN,Style.O_BOLD,Style.O_BOLD_ITALIC,Style.O_EXTRA_BOLD,
            Style.O_EXTRA_BOLD_ITALIC, Style.O_ITALIC, Style.O_LIGHT, Style.O_LIGHT_ITALIC,
            Style.O_REGULAR, Style.O_SEMI_BOLD, Style.O_SEMI_BOLD_ITALIC,Style.NONE})
    public @interface Style {
        int NONE = -1;
        int FUTURA_MEDIUM = 0;
        int FUTURA_HEAVY = 1;
        int LIGHT = 2;
        int BLACK = 3;
        int BOLD = 4;
        int BOLD_ITALIC = 5;
        int LIGHT_ITALIC = 6;
        int MEDIUM = 7;
        int MEDIUM_ITALIC = 8;
        int REGULAR =9;
        int REGULAR_ITALIC = 10;
        int THIN = 11;
        int O_BOLD = 12;
        int O_BOLD_ITALIC = 13;
        int O_EXTRA_BOLD = 14;
        int O_EXTRA_BOLD_ITALIC = 15;
        int O_ITALIC = 16;
        int O_LIGHT = 17;
        int O_LIGHT_ITALIC = 18;
        int O_REGULAR = 19;
        int O_SEMI_BOLD = 20;
        int O_SEMI_BOLD_ITALIC = 21;

    }


}