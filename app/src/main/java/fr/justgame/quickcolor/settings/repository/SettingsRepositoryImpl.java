package fr.justgame.quickcolor.settings.repository;

import fr.justgame.quickcolor.settings.model.SettingsModel;
import io.realm.Realm;

/**
 * Created by aaitzeouay on 19/07/2017.
 */

public class SettingsRepositoryImpl implements SettingsRepository {

    @Override
    public void saveSettings(SettingsModel settings) {
        Realm realm = Realm.getDefaultInstance();
        SettingsModel dbSettings = realm.where(SettingsModel.class).findFirst();
        realm.beginTransaction();
        if (dbSettings == null) {
            realm.copyToRealm(new SettingsModel(settings));
        } else {
            dbSettings.setVolumeOn(settings.isVolumeOn());
            dbSettings.setLikedUs(settings.isLikedUs());
            dbSettings.setRated(settings.isRated());
        }
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public SettingsModel getSettings() {
        Realm realm = Realm.getDefaultInstance();
        SettingsModel result = realm
                .where(SettingsModel.class)
                .findFirst();
        SettingsModel settings;
        if (result == null) {
            realm.close();
            settings = SettingsModel.getDefaultConfiguration();
            saveSettings(settings);
        } else {
            settings = new SettingsModel(result);
            realm.close();
        }
        return settings;
    }
}
