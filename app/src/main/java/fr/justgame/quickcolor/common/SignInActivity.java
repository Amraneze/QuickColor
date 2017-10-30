package fr.justgame.quickcolor.common;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.example.games.basegameutils.BaseGameActivity;

import butterknife.BindView;
import fr.justgame.quickcolor.R;

/**
 * Created by aaitzeouay on 29/10/2017.
 */

public class SignInActivity extends BaseGameActivity implements View.OnClickListener {

    public static final int DELAY_TUTORIAL = 500;

    protected Handler operationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        operationHandler = new Handler();
    }

    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {

    }

    @Override
    public void onClick(View v) {

    }
}
