package fr.justgame.quickcolor.game;

import android.os.Bundle;
import android.view.View;

import com.skyfishjy.library.RippleBackground;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.CommonActivity;
import fr.justgame.quickcolor.common.ui.CommonButton;
import fr.justgame.quickcolor.common.ui.SpecialButton;
import fr.justgame.quickcolor.game.ui.TimerActivity;

/**
 * Created by aaitzeouay on 17/07/2017.
 */

public class TutorialActivity extends CommonActivity {

    @BindView(R.id.rb_finger)
    RippleBackground rb_finger;
    @BindView(R.id.btn_light_color)
    CommonButton btn_light_color;
    @BindView(R.id.btn_ok)
    SpecialButton btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_activity);
        ButterKnife.bind(this);
        setListeners();
        rb_finger.startRippleAnimation();
    }

    private void setListeners() {
        btn_light_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimerActivity();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimerActivity();
            }
        });
    }

    private void startTimerActivity() {
        startActivity(TimerActivity.class);
        finish();
    }
}
