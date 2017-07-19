package fr.justgame.quickcolor.settings;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.rey.material.widget.ImageButton;

import andy.ayaseruri.lib.CircularRevealActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.CommonActivity;
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

    }


    @Override
    public void displaySettings(SettingsModel settings) {

    }
}
