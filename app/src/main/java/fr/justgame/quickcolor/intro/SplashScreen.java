package fr.justgame.quickcolor.intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.CommonActivity;
import fr.justgame.quickcolor.game.StartGameActivity;

public class SplashScreen extends CommonActivity {

    private static final long START_NEXT_SCREEN_DELAY = 2000;

    @BindView(R.id.rl_background)
    RelativeLayout rlBackground;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        ButterKnife.bind(this);
        initBackgroundAnimation();
        initLogoAnimation();
        startNextActivity();
    }

    private void initBackgroundAnimation() {
        rlBackground.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }

    private void initLogoAnimation() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ivLogo.setVisibility(View.VISIBLE);
                ivLogo.setAnimation(AnimationUtils.loadAnimation(SplashScreen.this, R.anim.fade_in));
            }
        }, 500);
    }

    private void startNextActivity() {
        long startTimeForConfiguration = System.currentTimeMillis();
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
