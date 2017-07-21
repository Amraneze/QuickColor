package fr.justgame.quickcolor.game.rectangle.easy;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.MainGameActivity;
import fr.justgame.quickcolor.common.ui.CommonButton;
import fr.justgame.quickcolor.common.utils.BetterColor;

/**
 * Created by Amrane Ait Zeouay on 18-Jul-17.
 */

public class EasyGameActivity extends MainGameActivity {

    @BindView(R.id.btn_top)
    CommonButton btn_top;
    @BindView(R.id.btn_buttom)
    CommonButton btn_buttom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_game_activity);
        ButterKnife.bind(this);
        setupProgressView();

        POINT_INCREMENT = 2;
        TIMER_BUMP = 2;

        gameMode = GameMode.EASY;
        gameType = GameType.RECTANGLE;

        btn_top.setOnClickListener(this);
        btn_buttom.setOnClickListener(this);

        // bootstrap game
        resetGame();
        setupGameLoop();
        startGame();
    }

    @Override
    public void onClick(View view) {
        if (!gameStart) return;
        calculatePoints(view);
        setColorsOnButtons();
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

        btn_top.setBackgroundColor(Color.argb(alpha1, red, green, blue));
        btn_buttom.setBackgroundColor(Color.argb(alpha2, red, green, blue));
    }

    protected void calculatePoints(View clickedView) {
        View unclickedView = clickedView == btn_top ? btn_buttom : btn_top;
        ColorDrawable clickedColor = (ColorDrawable) clickedView.getBackground();
        ColorDrawable unClickedColor = (ColorDrawable) unclickedView.getBackground();

        int alpha1 = Color.alpha(clickedColor.getColor());
        int alpha2 = Color.alpha(unClickedColor.getColor());

        // correct guess
        if (alpha1 < alpha2) {
            updatePoints();
        } else { // incorrect guess
            endGame();
        }
    }
}
