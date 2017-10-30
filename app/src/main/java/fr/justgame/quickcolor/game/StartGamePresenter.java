package fr.justgame.quickcolor.game;

import android.content.Context;
import android.content.SharedPreferences;

import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.BasePresenter;
import fr.justgame.quickcolor.common.utils.BoardScore;

/**
 * Created by aaitzeouay on 09/07/2017.
 */

public class StartGamePresenter extends BasePresenter<StartGameView> {

    private Context context;
    private SharedPreferences preferences;

    StartGamePresenter(Context context) {
        super(context);
        this.context = context;
        preferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        getHighScore();
    }

    private void getHighScore() {
        view.initHighScore(preferences.getInt("HIGHSCORE", 0));
    }


    public boolean isGoogleSignedIn() {
        return preferences.getBoolean("GoogleSignedInQuick", false);
    }

    public void saveGoogleSignIn(boolean isSigned) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("GoogleSignedInQuick", isSigned);
        editor.apply();
    }

    public void publishScore(int score) {
        BoardScore.publishScore(context, score);
    }
}
