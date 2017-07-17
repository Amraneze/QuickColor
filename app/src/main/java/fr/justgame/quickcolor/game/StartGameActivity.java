package fr.justgame.quickcolor.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.Authentication;
import fr.justgame.quickcolor.common.CommonActivity;
import fr.justgame.quickcolor.common.ui.CommonButton;
import fr.justgame.quickcolor.game.ui.TimerActivity;

/**
 * Created by aaitzeouay on 09/07/2017.
 */

public class StartGameActivity extends CommonActivity {

    @BindView(R.id.btn_play)
    CommonButton btn_play;
    @BindView(R.id.btn_fb)
    CommonButton btn_fb;
    @BindView(R.id.btn_google)
    CommonButton btn_google;

    protected boolean screenSeen;
    private Authentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game_activity);
        ButterKnife.bind(this);
        authentication = Authentication.INSTANCE;
        setListeners();
    }

    private void setListeners() {
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTutorialIfFirstTime();
            }
        });
    }

    /*@OnClick(R.id.btn_play)
    public void onPlayButtonClicked(){
        showTutorialIfFirstTime();
    }*/

    protected void showTutorialIfFirstTime() {
        startActivity(TutorialActivity.class);
        /*screenSeen = authentication.getSharedPreferences(this);
        if (!screenSeen) {
            authentication.setSharedPreferences(true, this);
            startActivity(TutorialActivity.class);
        } else {
            startActivity(TimerActivity.class);
        }*/
    }


}
