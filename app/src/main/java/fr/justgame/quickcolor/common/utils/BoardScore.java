package fr.justgame.quickcolor.common.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import fr.justgame.quickcolor.R;

/**
 * Created by aaitzeouay on 29/10/2017.
 */

public class BoardScore {

    public static void getBoardScore(Context context) {
        /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/758654297675487/achievements",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e("getBoardScore", "onCompleted "+response.getRawResponse());
                        Log.e("getBoardScore", "onCompleted "+response.getJSONObject().toString());
                        /* handle the result */
                    }
                }
        ).executeAsync();
    }

    public static void publishScore(Context context, int score) {
        Bundle params = new Bundle();
        params.putString("achievement", "http://example.com/achievement/"+score);
        /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/758654297675487/achievements",
                params,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        /* handle the result */
                        Log.e("publishScore", "here "+response.getRawResponse());
                        Log.e("publishScore", "not here "+response.getJSONObject());
                    }
                }
        ).executeAsync();
    }

    public static void deleteScores(Context context) {
        Bundle params = new Bundle();
        params.putString("achievement", "http://example.com/achievement/");
        /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+context.getString(R.string.facebook_app_id)+"/achievements",
                params,
                HttpMethod.DELETE,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                    }
                }
        ).executeAsync();
    }
}
