package fr.justgame.quickcolor.settings;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;


import andy.ayaseruri.lib.CircularRevealActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.CommonActivity;
import fr.justgame.quickcolor.common.utils.BoardScore;
import fr.justgame.quickcolor.settings.model.SettingsModel;

/**
 * Created by aaitzeouay on 19/07/2017.
 */

public class SettingsActivity extends CircularRevealActivity implements SettingsView {

    @BindView(R.id.ibtn_volume)
    ImageButton volumeButton;
    @BindView(R.id.ibtn_share)
    ImageButton shareButton;
    @BindView(R.id.ibtn_flag)
    ImageButton flagButton;
    @BindView(R.id.ibtn_like)
    ImageButton likeButton;
    @BindView(R.id.ibtn_certificate)
    ImageButton certificateButton;
    @BindView(R.id.ibtn_trophy)
    ImageButton trophyButton;

    private SettingsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        setStartPoint(size.x, 0);
        setContentView(R.layout.settings_activity);
        presenter = new SettingsPresenter(this);
        ButterKnife.bind(this);
        setListeners();
    }

    private void setListeners() {
        volumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setVolume();
            }
        });
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayStore();
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        trophyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardScore.getBoardScore(SettingsActivity.this);
            }
        });
    }


    @Override
    public void displaySettings(SettingsModel settings) {

    }

    private void openPlayStore() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }
}
