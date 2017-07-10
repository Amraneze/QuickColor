package fr.justgame.quickcolor.game;

import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.view.ViewGroup;

import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.CommonActivity;

/**
 * Created by aaitzeouay on 09/07/2017.
 */

public class StartGameActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game_activity);
    }

}
