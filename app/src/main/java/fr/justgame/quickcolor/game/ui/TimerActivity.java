package fr.justgame.quickcolor.game.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.CommonActivity;
import fr.justgame.quickcolor.common.ui.CommonTextView;
import fr.justgame.quickcolor.game.rectangle.easy.EasyGameActivity;

/**
 * Created by aaitzeouay on 17/07/2017.
 */

public class TimerActivity extends CommonActivity {

    private final static int FIRST_SCREEN = 1;
    private final static int SECOND_SCREEN = 2;
    private final static int THIRD_SCREEN = 3;
    private final static int GO_SCREEN = 4;

    @BindView(R.id.ll_timer_layout)
    LinearLayout ll_timer_layout;
    @BindView(R.id.tv_timer)
    CommonTextView tv_timer;

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
                        ll_timer_layout.setBackgroundColor(getResources().getColor(R.color.accentColor));
                        tv_timer.setText("3");
                    }
                }, 1000);
                break;
            case SECOND_SCREEN:
                uiThread.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ll_timer_layout.setBackgroundColor(getResources().getColor(R.color.color_oxy));
                        tv_timer.setText("2");
                        startTimer(THIRD_SCREEN);
                    }
                }, 1000);
                break;
            case THIRD_SCREEN:
                uiThread.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ll_timer_layout.setBackgroundColor(getResources().getColor(R.color.accentColor));
                        tv_timer.setText("1");
                        startTimer(GO_SCREEN);
                    }
                }, 1000);
                break;
            case GO_SCREEN:
                uiThread.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ll_timer_layout.setBackgroundColor(getResources().getColor(R.color.accentColorDisabled));
                        tv_timer.setText("Go");
                        tv_timer.setTextSize(250);
                        startActivity(EasyGameActivity.class);
                        finish();
                    }
                }, 1000);
                break;
        }
    }
}
