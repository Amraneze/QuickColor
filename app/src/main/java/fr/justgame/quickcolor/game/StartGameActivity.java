package fr.justgame.quickcolor.game;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.rey.material.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.application.CircularActivity;
import fr.justgame.quickcolor.common.Authentication;
import fr.justgame.quickcolor.common.CommonActivity;
import fr.justgame.quickcolor.common.ui.CommonButton;
import fr.justgame.quickcolor.common.ui.FontManager;
import fr.justgame.quickcolor.common.ui.SpecialButton;
import fr.justgame.quickcolor.settings.SettingsActivity;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

import static fr.justgame.quickcolor.common.ui.FontManager.Style.O_BOLD;

/**
 * Created by aaitzeouay on 09/07/2017.
 */

public class StartGameActivity extends CommonActivity {

    private static final float UNIT_TEXT_RATIO = 0.8f;

    @BindView(R.id.lay_container)
    LinearLayout lay_container;
    @BindView(R.id.ic_settings)
    ImageView btn_settings;
    @BindView(R.id.btn_play)
    SpecialButton btn_play;
    @BindView(R.id.btn_connect_fb)
    SpecialButton btn_connect_fb;
    @BindView(R.id.btn_connect_google)
    SpecialButton btn_connect_google;

    //@BindView(R.id.ll_settings_layout)
    //RelativeLayout mRevealView;

    boolean hidden = true;
    protected boolean screenSeen;
    private Authentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.start_game_activity);
        ButterKnife.bind(this);
        authentication = Authentication.INSTANCE;
        setListeners();
        //setButtonsView();
        init();
    }

    private void setButtonsView() {
        btn_connect_fb.setText(getStringText());
        btn_connect_fb.setTypeface(FontManager.INSTANCE.getTypeFace(this, O_BOLD));
        btn_connect_fb.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_qc_facebook_icon, 0);
        btn_connect_fb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        btn_connect_google.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_qc_facebook_icon, 0);
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
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTutorialIfFirstTime();
            }
        });
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CircularActivity.class);
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
