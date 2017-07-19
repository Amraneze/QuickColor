package fr.justgame.quickcolor.settings.model;

import io.realm.RealmObject;

/**
 * Created by aaitzeouay on 19/07/2017.
 */

public class SettingsModel extends RealmObject {

    private boolean isVolumeOn = true;
    private boolean isRated = false;
    private boolean likedUs = false;

    public SettingsModel() {
    }

    public SettingsModel(boolean isVolumeOn, boolean isRated, boolean likedUs) {
        this.isVolumeOn = isVolumeOn;
        this.isRated = isRated;
        this.likedUs = likedUs;
    }

    public SettingsModel(SettingsModel model) {
        this.isVolumeOn = model.isVolumeOn();
        this.isRated = model.isRated();
        this.likedUs = model.isLikedUs();
    }

    public boolean isVolumeOn() {
        return isVolumeOn;
    }

    public void setVolumeOn(boolean volumeOn) {
        isVolumeOn = volumeOn;
    }

    public boolean isRated() {
        return isRated;
    }

    public void setRated(boolean rated) {
        isRated = rated;
    }

    public boolean isLikedUs() {
        return likedUs;
    }

    public void setLikedUs(boolean likedUs) {
        this.likedUs = likedUs;
    }

    public static SettingsModel getDefaultConfiguration() {
        return new SettingsModel(true, false, false);
    }
}
