package fr.justgame.quickcolor.game.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.CommonActivity;
import fr.justgame.quickcolor.common.ui.CommonTextView;
import fr.justgame.quickcolor.game.rectangle.easy.EasyGameActivity;
import fr.justgame.quickcolor.intro.SplashScreen;

/**
 * Created by aaitzeouay on 17/07/2017.
 */

public class TimerActivity extends CommonActivity {

    private final static int FIRST_SCREEN = 1;
    private final static int SECOND_SCREEN = 2;
    private final static int THIRD_SCREEN = 3;
    private final static int GO_SCREEN = 4;

    @BindView(R.id.iv_light_color)
    ImageView ivLightColor;
    @BindView(R.id.iv_dark_color)
    ImageView ivDarkColor;
    @BindView(R.id.tv_timer)
    CommonTextView tv_timer;
    @BindView(R.id.tv_start_in)
    CommonTextView tvStartIn;

    private Handler uiThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);
        ButterKnife.bind(this);
        uiThread = new Handler(Looper.getMainLooper());
        startTimer(SECOND_SCREEN);
    }

    private void startTimer(int screen) {
        switch (screen){
            case FIRST_SCREEN:
                uiThread.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //tv_timer.setAnimation(AnimationUtils.loadAnimation(TimerActivity.this, R.anim.fade_in));
                        tv_timer.setText("3");
                        tv_timer.startAnimation(AnimationUtils.loadAnimation(TimerActivity.this, R.anim.fade_in));
                        ivLightColor.setBackgroundColor(getResources().getColor(R.color.timer_3_color_dark));
                        ivDarkColor.setBackgroundColor(getResources().getColor(R.color.timer_3_color));
                    }
                }, 1000);
                break;
            case SECOND_SCREEN:
                uiThread.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_timer.setText("2");
                        //tv_timer.setAnimation(AnimationUtils.loadAnimation(TimerActivity.this, R.anim.fade_in));
                        tv_timer.startAnimation(AnimationUtils.loadAnimation(TimerActivity.this, R.anim.fade_in));
                        ivLightColor.setBackgroundColor(getResources().getColor(R.color.timer_2_color));
                        ivDarkColor.setBackgroundColor(getResources().getColor(R.color.timer_2_color_dark));
                        startTimer(THIRD_SCREEN);
                    }
                }, 1000);
                break;
            case THIRD_SCREEN:
                uiThread.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_timer.setText("1");
                        //tv_timer.setAnimation(AnimationUtils.loadAnimation(TimerActivity.this, R.anim.fade_in));
                        tv_timer.startAnimation(AnimationUtils.loadAnimation(TimerActivity.this, R.anim.fade_in));
                        ivLightColor.setBackgroundColor(getResources().getColor(R.color.timer_1_color_dark));
                        ivDarkColor.setBackgroundColor(getResources().getColor(R.color.timer_1_color));
                        startTimer(GO_SCREEN);
                    }
                }, 1000);
                break;
            case GO_SCREEN:
                uiThread.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvStartIn.setVisibility(View.INVISIBLE);
                        tv_timer.setTextSize(200);
                        tv_timer.setText("Go!");
                        tv_timer.setAnimation(AnimationUtils.loadAnimation(TimerActivity.this, R.anim.fade_in));
                        //tv_timer.setTextSize(250);
                        ivLightColor.setBackgroundColor(getResources().getColor(R.color.splash_screen_red));
                        ivDarkColor.setBackgroundColor(getResources().getColor(R.color.splash_screen_red_dark));
                        startGameActivity();
                    }
                }, 1000);
                break;
        }
    }

    private void startGameActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(EasyGameActivity.class);
                finish();
            }
        }, 1000);
    }
}
