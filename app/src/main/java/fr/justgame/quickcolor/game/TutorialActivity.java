package fr.justgame.quickcolor.game;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

//import com.thunderrise.animations.PulseAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.CommonActivity;
import fr.justgame.quickcolor.common.ui.CommonButton;
import fr.justgame.quickcolor.common.ui.PulseAnimation;
import fr.justgame.quickcolor.common.utils.CommonUtils;
import fr.justgame.quickcolor.game.ui.TimerActivity;

/**
 * Created by aaitzeouay on 17/07/2017.
 */

public class TutorialActivity extends CommonActivity {

    @BindView(R.id.btn_dark_color)
    CommonButton btnDarkColor;
    @BindView(R.id.iv_pulse)
    ImageView ivPulse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_activity);
        ButterKnife.bind(this);
        setListeners();
        initAnimation();
    }

    private void initAnimation() {
        ivPulse.requestLayout();
        ivPulse.getLayoutParams().height = CommonUtils.getScreenHeight(this) / 4;
        ivPulse.getLayoutParams().width = CommonUtils.getScreenWidth(this) / 2;
        PulseAnimation.create().with(ivPulse)
                .setDuration(310)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();
    }

    private void setListeners() {
        btnDarkColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TimerActivity.class);
                finish();
            }
        });
    }
}