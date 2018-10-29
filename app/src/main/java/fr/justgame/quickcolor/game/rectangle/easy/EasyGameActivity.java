package fr.justgame.quickcolor.game.rectangle.easy;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.balysv.materialripple.MaterialRippleLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.MainGameActivity;
import fr.justgame.quickcolor.common.ui.CommonButton;
import fr.justgame.quickcolor.common.ui.CommonTextView;
import fr.justgame.quickcolor.common.utils.BetterColor;

/**
 * Created by Amrane Ait Zeouay on 18-Jul-17.
 */

public class EasyGameActivity extends MainGameActivity {

    @BindView(R.id.ll_progress_bar)
    LinearLayout llProgressBar;
    @BindView(R.id.btn_left)
    CommonButton btnLeft;
    @BindView(R.id.btn_right)
    CommonButton btnRight;
    @BindView(R.id.iv_failed)
    ImageView ivFailed;
    @BindView(R.id.tv_time_is_up)
    CommonTextView tvTimeIsUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_game_activity);
        ButterKnife.bind(this);
        initAnimation();
        setUpUI();
        setupProgressView();

        POINT_INCREMENT = 2;
        TIMER_BUMP = 10;

        gameMode = GameMode.EASY;
        gameType = GameType.RECTANGLE;

        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);

        // bootstrap game
        resetGame();
        setupGameLoop();
        startGame();
    }

    private void initAnimation() {
        llProgressBar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
        //llProgressBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }

    private void setUpUI() {
        MaterialRippleLayout.on(btnLeft)
                .rippleAlpha(0.5f)
                .rippleFadeDuration(500)
                .rippleOverlay(true)
                .rippleHover(true)
                .rippleColor(Color.WHITE)
                .create();
        MaterialRippleLayout.on(btnRight)
                .rippleAlpha(0.5f)
                .rippleFadeDuration(500)
                .rippleOverlay(true)
                .rippleHover(true)
                .rippleColor(Color.WHITE)
                .create();
    }

    @Override
    public void onClick(View view) {
        if (!gameStart) return;
        calculatePoints(view);
    }

    protected void setColorsOnButtons() {
        int color = Color.parseColor(BetterColor.getColor());
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        int alpha1, alpha2;
        if (Math.random() > 0.5) {
            alpha1 = 255;
            alpha2 = 185;
        } else {
            alpha1 = 185;
            alpha2 = 255;
        }

        btnLeft.setBackgroundColor(Color.argb(alpha1, red, green, blue));
        btnRight.setBackgroundColor(Color.argb(alpha2, red, green, blue));
    }

    protected void calculatePoints(View clickedView) {
        View unclickedView = clickedView == btnLeft ? btnRight : btnLeft;
        ColorDrawable clickedColor = (ColorDrawable) clickedView.getBackground();
        ColorDrawable unClickedColor = (ColorDrawable) unclickedView.getBackground();
        int alpha1 = Color.alpha(clickedColor.getColor());
        int alpha2 = Color.alpha(unClickedColor.getColor());

        if (alpha1 < alpha2) {
            setColorsOnButtons();
            updatePoints();
        } else {
            ivFailed.setVisibility(View.VISIBLE);
            ivFailed.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
            uiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    endGame();
                }
            }, 1000);
        }
    }

    @Override
    protected void onTimeIsUp() {
        tvTimeIsUp.setVisibility(View.VISIBLE);
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                endGame();
            }
        }, 1000);
    }
}
