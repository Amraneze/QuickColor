package fr.justgame.quickcolor.common;

import android.content.Context;

/**
 * Created by aaitzeouay on 19/07/2017.
 */

public class BasePresenter<T> {

    protected T view;

    public BasePresenter(Context context){
        view = (T)context;
    }

    public void onPause() {
        // override if needed
    }

    public void onResume() {
        // override if needed
    }
}
