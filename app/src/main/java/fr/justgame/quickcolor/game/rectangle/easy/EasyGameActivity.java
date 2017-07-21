package fr.justgame.quickcolor.game.easy;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.MainGameActivity;

/**
 * Created by Amrane Ait Zeouay on 18-Jul-17.
 */

public class EasyGameActivity extends MainGameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_game_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void setColorsOnButtons() {

    }

    @Override
    protected void calculatePoints(View view) {

    }

    @Override
    public void onClick(View view) {

    }
}
