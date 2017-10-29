package fr.justgame.quickcolor.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.BasePresenter;
import fr.justgame.quickcolor.common.utils.BoardScore;

/**
 * Created by aaitzeouay on 09/07/2017.
 */

public class StartGamePresenter extends BasePresenter<StartGameView> {

    private Context context;

    StartGamePresenter(Context context) {
        super(context);
        this.context = context;
        getHighScore();
    }

    private void getHighScore() {
        SharedPreferences preferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        view.initHighScore(preferences.getInt("HIGHSCORE", 0));
    }

    public void publishScore(int score) {
        BoardScore.publishScore(context, score);
    }
}
