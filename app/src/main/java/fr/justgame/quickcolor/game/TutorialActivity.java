package fr.justgame.quickcolor.game;

import android.os.Bundle;

import com.skyfishjy.library.RippleBackground;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.CommonActivity;

/**
 * Created by aaitzeouay on 17/07/2017.
 */

public class TutorialActivity extends CommonActivity {

    @BindView(R.id.rb_finger)
    RippleBackground rb_finger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_activity);
        ButterKnife.bind(this);
        rb_finger.startRippleAnimation();
    }
}
