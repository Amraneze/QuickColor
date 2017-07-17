package fr.justgame.quickcolor.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;

/**
 * Created by aaitzeouay on 09/07/2017.
 */

public class CommonActivity extends Activity implements View.OnClickListener {

    public static final int DELAY_TUTORIAL = 500;

    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    protected Handler operationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        operationHandler = new Handler();
        //ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        //hideSystemUI();
    }

    protected void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void finishAfterDelay(long delay) {
        finishAfterDelay(delay, true);
    }

    public void finishAfterDelay(long delay, boolean removeOldCallback) {
        if (removeOldCallback) {
            operationHandler.removeCallbacksAndMessages(null);
        }
        operationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, delay);
    }

/*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }*/
}
