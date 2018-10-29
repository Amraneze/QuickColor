package fr.justgame.quickcolor.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.SignInButton;
import com.google.example.games.basegameutils.BaseGameActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.Authentication;
import fr.justgame.quickcolor.common.ui.CommonButton;
import fr.justgame.quickcolor.common.ui.CommonTextView;
import fr.justgame.quickcolor.common.ui.CustomSnackBar;
import fr.justgame.quickcolor.common.ui.FontManager;
import fr.justgame.quickcolor.common.ui.TextViewOutline;
import fr.justgame.quickcolor.common.ui.dialog.BaseDialog;
import fr.justgame.quickcolor.common.utils.NetworkUtils;
import fr.justgame.quickcolor.game.ui.TimerActivity;

import static fr.justgame.quickcolor.common.ui.FontManager.Style.FILBERT_BRUSH;
import static fr.justgame.quickcolor.common.ui.FontManager.Style.O_BOLD;

/**
 * Created by aaitzeouay on 09/07/2017.
 */

public class StartGameActivity extends BaseGameActivity implements View.OnClickListener, StartGameView {

    private static final float UNIT_TEXT_RATIO = 0.8f;

    @BindView(R.id.lay_container)
    LinearLayout lay_container;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.btn_play)
    CommonButton btn_play;
    @BindView(R.id.btn_connect_fb)
    CommonButton btnConnectFb;
    @BindView(R.id.btn_connect_google)
    CommonButton btnConnectGoogle;

    @BindView(R.id.ll_score)
    LinearLayout llScore;
    @BindView(R.id.ll_actual_score)
    LinearLayout llActualScore;
    @BindView(R.id.tv_actual_score_title)
    CommonTextView tvActualScoreTitle;
    @BindView(R.id.tv_actual_score)
    TextViewOutline tvActualScore;

    @BindView(R.id.ll_high_score)
    LinearLayout llHighScore;
    @BindView(R.id.tv_score)
    CommonTextView tv_score;

    @BindView(R.id.login_button)
    LoginButton btnLoginFacebook;

    @BindView(R.id.sign_in_button)
    SignInButton btnLoginGoogle;

    @BindView(R.id.adView)
    AdView adView;

    /* Facebook */
    private CallbackManager callbackManager;
    private static Profile facebookProfile;

    /**
     * AdMob
     */
    //final AdRequest adRequest = new AdRequest.Builder().build();
    final AdRequest adRequest = new AdRequest.Builder().addTestDevice("BFA33742EFB0960026849EAE30F9BC31").build();

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
        initAnimations();
        adView.loadAd(adRequest);

        authentication = Authentication.INSTANCE;
        callbackManager = CallbackManager.Factory.create();
        facebookProfile = Profile.getCurrentProfile();
        presenter = new StartGamePresenter(this);
        getGameHelper().setMaxAutoSignInAttempts(0);

        setListeners();
        initUI();
        //setButtonsView();
        handleIntent(getIntent());
        init();
    }

    private void initAnimations() {
        ivLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        llScore.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        btn_play.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        btnConnectFb.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        btnConnectGoogle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }

    private void initUI() {
        /*if (facebookProfile != null) {
            setButtonsView();
        }*/
        llActualScore.setVisibility(View.GONE);
        setFacebookButton(facebookProfile != null);
        setGoogleButton(presenter.isGoogleSignedIn());
        //setGoogleButton(mHelper.isSignedIn());
    }

    private void setGoogleButton(boolean signedIn) {
        btnConnectGoogle.setText(signedIn ? "Logout" : "Connect with");
        btnConnectGoogle.setTypeface(FontManager.INSTANCE.getTypeFace(this, FontManager.Style.O_BOLD));
        btnConnectGoogle.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_google_plus, 0);
        btnConnectGoogle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }

    private void setFacebookButton(boolean isFacebookConnected) {
        btnConnectFb.setText(isFacebookConnected ? "Logout" : "Connect with");
        btnConnectFb.setTypeface(FontManager.INSTANCE.getTypeFace(this, FontManager.Style.O_BOLD));
        btnConnectFb.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_qc_facebook_icon, 0);
        btnConnectFb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
        if (adView != null) {
            adView.resume();
        }
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.pause();
        }
        super.onDestroy();
    }

    private void displayNetworkDialog() {
        BaseDialog dialog = BaseDialog.newInstance(getString(R.string.network_unavailable_dialog_title),
                getString(R.string.network_unavailable_dialog_message))
                .setDialogListener(new BaseDialog.DialogListener() {
                    @Override
                    public void onButtonClicked(int which) {
                        if (which == BaseDialog.BUTTON_INDEX_1) {}
                    }
                });
        dialog.show(getSupportFragmentManager(), "network_unavailable");
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
        setScore(String.valueOf(points));
        tv_score.setText(String.valueOf(points));
        Log.e("HandleIntent", gameMode + " best "+best);
        /*presenter.publishScore(points);
        Games.Leaderboards.submitScore(getApiClient(),
                getString(R.string.leaderboard_best_player),
                points);*/
    }

    private void setScore(String score) {
        tvActualScore.setText(score);
        llActualScore.setVisibility(View.VISIBLE);
        //resizeBestScoreLayout();
        initShareButtons(score);
    }

    private void initShareButtons(String score) {
        final ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://visiofuture.me"))
                .setQuote("Try to beat my score "+score+" on QuickColor.")
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#QuickColor")
                        .build())
                .build();
        btnConnectFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDialog.show(StartGameActivity.this, shareLinkContent);
            }
        });
        btnConnectFb.setText("Share");
        btnConnectFb.setTypeface(FontManager.INSTANCE.getTypeFace(this, FILBERT_BRUSH));
        btnConnectFb.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_qc_facebook_icon, 0);
        btnConnectFb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }

    private void setButtonsView() {
        btnConnectFb.setText("Logout");
        btnConnectFb.setTypeface(FontManager.INSTANCE.getTypeFace(this, O_BOLD));
        btnConnectFb.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_qc_facebook_icon, 0);
        btnConnectFb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
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
//        ivTrophy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO start achievement in presneter
//                //startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()), 1);
//                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
//                        getApiClient(), getString(R.string.leaderboard_best_player)),
//                        2);
//            }
//        });
        btnConnectGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtils.isNetworkAvailable(StartGameActivity.this)) {
                    displayNetworkDialog();
                } else {
                    btnLoginGoogle.performClick();
                }
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTutorialIfFirstTime();
            }
        });
        btnConnectFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtils.isNetworkAvailable(StartGameActivity.this)) {
                    displayNetworkDialog();
                } else {
                    btnLoginFacebook.performClick();
                }
            }
        });
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showSnackBar(CustomSnackBar.newMessage(getString(R.string.social_network_connection_succeeded,
                                getString(R.string.general_facebook)))
                                .setDuration(CustomSnackBar.SNACKBAR_DURATION_SHORT));
                    }
                });
                //Toast.makeText(StartGameActivity.this, "Done", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String message = getString(R.string.social_network_connection_canceled, getString(R.string.general_facebook));
                        int messageTextSize = getResources().getDimensionPixelSize(R.dimen.snackbar_message);
                        Spannable spannableMessage = new SpannableString(message);
                        spannableMessage.setSpan(FontManager.Style.O_BOLD, 0, message.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        spannableMessage.setSpan(new AbsoluteSizeSpan(messageTextSize), 0, message.length(), 0);
                        showSnackBar(
                                CustomSnackBar.newError(spannableMessage)
                                .setDuration(CustomSnackBar.SNACKBAR_DURATION_SHORT));
                    }
                });
                //Toast.makeText(StartGameActivity.this, "Canceled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showSnackBar(CustomSnackBar.newError(getString(R.string.social_network_connection_error,
                                getString(R.string.general_facebook)))
                                .setDuration(CustomSnackBar.SNACKBAR_DURATION_SHORT));
                    }
                });
                //Toast.makeText(StartGameActivity.this, "There was an issue", Toast.LENGTH_LONG).show();
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
        //finish();
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
            llScore.setVisibility(View.INVISIBLE);
            /*llHighScore.setVisibility(View.GONE);
            tv_score.setVisibility(View.GONE);*/
        } else {
            llScore.setVisibility(View.VISIBLE);
            //tv_score.setVisibility(View.VISIBLE);
            tv_score.setTextSize(60);
            tv_score.setText(String.valueOf(highScore));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button) {
            Log.e("onCLick", " From here "+presenter.isGoogleSignedIn());
            if (!presenter.isGoogleSignedIn()) {
                beginUserInitiatedSignIn();
            } else {
                signOut();
                onSignInFailed();
            }
        }
    }

    @Override
    public void onSignInFailed() {
        btnConnectGoogle.setText("Connect with");
        presenter.saveGoogleSignIn(false);
    }

    @Override
    public void onSignInSucceeded() {
        btnConnectGoogle.setText("Logout");
        presenter.saveGoogleSignIn(true);
    }

    private void showSnackBar(final CustomSnackBar options) {
        Log.e("Amrane", "From here 1");
        final Snackbar snackbar = Snackbar.make(btnConnectFb, options.getMessage(), options.getDuration());
        Log.e("Amrane", "From here 2");
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, options.getBackgroundColor()));
        Log.e("Amrane", "From here 3");
        TextView txt = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        Log.e("Amrane", "From here 4");
        txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        txt.setTextColor(ContextCompat.getColor(this, options.getTextColor()));
        txt.setTypeface(FontManager.INSTANCE.getTypeFace(this, FontManager.Style.O_BOLD));

        if (!TextUtils.isEmpty(options.getAction())) {
            snackbar.setAction(options.getAction(), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (options.getActionListener() != null) {
                        options.getActionListener().onSnackBarActionPressed();
                    }
                }
            });
            snackbar.setActionTextColor(ContextCompat.getColor(this, options.getActionTextColor()));
        }
        snackbar.show();
    }

}
