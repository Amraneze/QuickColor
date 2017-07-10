package fr.justgame.quickcolor.intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.UiThread;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.CommonActivity;
import fr.justgame.quickcolor.game.StartGameActivity;

public class SplashScreen extends CommonActivity {

    private static final long START_NEXT_SCREEN_DELAY = 2000;

    /*@BindView(R.id.iv_bkg_splash)
    ImageView iv_bkg_splash;*/

    private long startTimeForConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        ButterKnife.bind(this);
        /*iv_bkg_splash.setBackgroundResource(R.drawable.bkg_splash);*/
        //setupWindowAnimations();
        startNextActivity();
    }

    private void startNextActivity() {
        startTimeForConfiguration = System.currentTimeMillis();
        long delta = System.currentTimeMillis() - startTimeForConfiguration;
        if (delta < START_NEXT_SCREEN_DELAY) {
            navigateToTheNextScreenIn(START_NEXT_SCREEN_DELAY - delta);
        } else {
            navigateToTheNextScreen();
        }
    }

    @UiThread
    private void navigateToTheNextScreenIn(long ms) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToTheNextScreen();
            }
        }, ms);
    }

    private void navigateToTheNextScreen() {
        startActivity(new Intent(this, StartGameActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finishAfterDelay(DELAY_TUTORIAL);
    }
}
