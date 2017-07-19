package fr.justgame.quickcolor.settings;

import android.content.Context;

import fr.justgame.quickcolor.common.BasePresenter;
import fr.justgame.quickcolor.settings.repository.SettingsRepository;

/**
 * Created by aaitzeouay on 19/07/2017.
 */

public class SettingsPresenter extends BasePresenter<SettingsView> {

    private SettingsRepository settingsRepository;

    SettingsPresenter(Context context) {
        super(context);
        settingsRepository = SettingsRepository.Factory.INSTANCE.getInstance();
        view.displaySettings(settingsRepository.getSettings());
    }
}
