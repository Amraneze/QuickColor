package fr.justgame.quickcolor.game;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.Authentication;
import fr.justgame.quickcolor.common.CommonActivity;
import fr.justgame.quickcolor.common.ui.CommonButton;
import fr.justgame.quickcolor.common.ui.FontManager;
import fr.justgame.quickcolor.common.ui.SpecialButton;
import fr.justgame.quickcolor.common.ui.TextViewOutline;
import fr.justgame.quickcolor.common.utils.BoardScore;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

import static fr.justgame.quickcolor.common.ui.FontManager.Style.O_BOLD;

/**
 * Created by aaitzeouay on 09/07/2017.
 */

public class StartGameActivity extends BaseGameActivity implements View.OnClickListener, StartGameView {

    private static final float UNIT_TEXT_RATIO = 0.8f;

    @BindView(R.id.lay_container)
    LinearLayout lay_container;
    @BindView(R.id.btn_play)
    SpecialButton btn_play;
    @BindView(R.id.btn_connect_fb)
    SpecialButton btn_connect_fb;
    @BindView(R.id.btn_connect_google)
    SpecialButton btnConnectGoogle;

    @BindView(R.id.ll_high_score)
    LinearLayout llHighScore;
    @BindView(R.id.iv_trophy)
    ImageView ivTrophy;

    @BindView(R.id.tv_score)
    TextViewOutline tv_score;

    @BindView(R.id.login_button)
    LoginButton btnLoginFacebook;

    @BindView(R.id.sign_in_button)
    SignInButton btnLoginGoogle;

    /* Facebook */
    private CallbackManager callbackManager;
    private static Profile facebookProfile;

    //@BindView(R.id.ll_settings_layout)
    //RelativeLayout mRevealView;

    boolean hidden = true;
    protected boolean screenSeen;
    private Authentication authentication;
    private StartGamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.start_game_activity);
        ButterKnife.bind(this);
        authentication = Authentication.INSTANCE;
        callbackManager = CallbackManager.Factory.create();
        facebookProfile = Profile.getCurrentProfile().getCurrentProfile();
        presenter = new StartGamePresenter(this);

        setListeners();
        initUI();
        //setButtonsView();
        handleIntent(getIntent());
        init();
        test();
    }

    private void test() {
        BoardScore.publishScore(this, 1574);
    }

    private void initUI() {
        if (facebookProfile != null) {
            setButtonsView();
        }
    }

    private void handleIntent(Intent intent) {
        String gameMode = intent.getStringExtra("gameMode");
        String gameType = intent.getStringExtra("gameType");
        boolean newScore = intent.getBooleanExtra("newScore", false);
        int best = intent.getIntExtra("best", 0);
        int level = intent.getIntExtra("level", 0);
        int points = intent.getIntExtra("points", 0);
        if (gameMode == null) {
            Log.e("HandleIntent", "null");
            return;
        }
        tv_score.setText(String.valueOf(points));
        Log.e("HandleIntent", gameMode + " best "+best);
        initShareButtons(points);
        presenter.publishScore(points);
        Games.Leaderboards.submitScore(getApiClient(),
                getString(R.string.leaderboard_best_player),
                points);
    }

    private void initShareButtons(int score) {
        final ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://visiofuture.me"))
                .setQuote("Try to beat my score "+score+" on QuickColor.")
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#QuickColor")
                        .build())
                .build();
        btn_connect_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDialog.show(StartGameActivity.this, shareLinkContent);
            }
        });
        btn_connect_fb.setText("Share");
        btn_connect_fb.setTypeface(FontManager.INSTANCE.getTypeFace(this, O_BOLD));
        btn_connect_fb.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_qc_facebook_icon, 0);
        btn_connect_fb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }

    private void setButtonsView() {
        btn_connect_fb.setText("Logout");
        btn_connect_fb.setTypeface(FontManager.INSTANCE.getTypeFace(this, O_BOLD));
        btn_connect_fb.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_qc_facebook_icon, 0);
        btn_connect_fb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        btnConnectGoogle.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_qc_facebook_icon, 0);
    }

    private Spannable getStringText() {
        String text = "Connect with";
        Spannable spannable = new SpannableString(text);
        spannable.setSpan(new RelativeSizeSpan(UNIT_TEXT_RATIO),
                text.length(), text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    private void init() {
        //mRevealView.setVisibility(View.GONE);
    }

    private void setListeners() {
        ivTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO start achievement in presneter
                //startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()), 1);
                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                        getApiClient(), getString(R.string.leaderboard_best_player)),
                        2);
            }
        });
        btnConnectGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoginGoogle.performClick();
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTutorialIfFirstTime();
            }
        });
        btn_connect_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoginFacebook.performClick();
            }
        });
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(StartGameActivity.this, "Done", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(StartGameActivity.this, "Canceled", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(StartGameActivity.this, "There was an issue", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /*@OnClick(R.id.btn_play)
    public void onPlayButtonClicked(){
        showTutorialIfFirstTime();
    }*/

    protected void showTutorialIfFirstTime() {
        startActivity(new Intent(this, TutorialActivity.class));
        /*screenSeen = authentication.getSharedPreferences(this);
        if (!screenSeen) {
            authentication.setSharedPreferences(true, this);
            startActivity(TutorialActivity.class);
        } else {
            startActivity(TimerActivity.class);
        }*/
    }


    @Override
    public void initHighScore(int highScore) {
        if (highScore == 0) {
            llHighScore.setVisibility(View.GONE);
            tv_score.setVisibility(View.GONE);
        } else {
            tv_score.setVisibility(View.VISIBLE);
            tv_score.setText(String.valueOf(highScore));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button) {
            if (presenter.isGoogleSignedIn()) {
                beginUserInitiatedSignIn();
            } else {
                signOut();
                onSignInFailed();
            }
        }
    }

    @Override
    public void onSignInFailed() {
        btnConnectGoogle.setText("Login");
        presenter.saveGoogleSignIn(false);
    }

    @Override
    public void onSignInSucceeded() {
        btnConnectGoogle.setText("Logout");
        presenter.saveGoogleSignIn(true);
    }
}
