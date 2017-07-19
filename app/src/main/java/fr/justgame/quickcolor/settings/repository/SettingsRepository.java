package fr.justgame.quickcolor.settings.repository;

import fr.justgame.quickcolor.common.AbsInjectableFactory;
import fr.justgame.quickcolor.settings.model.SettingsModel;

/**
 * Created by aaitzeouay on 19/07/2017.
 */

public interface SettingsRepository {

    String F_SETTINGS = "settings";

    void saveSettings(SettingsModel settings);
    SettingsModel getSettings();

    class Factory extends AbsInjectableFactory<SettingsRepository> {

        public static final Factory INSTANCE = new Factory();

        private Factory(){
        }

        @Override
        protected SettingsRepository createInstance() {
            return new SettingsRepositoryImpl();
        }
    }
}
