package com.pingo.messenger;

import android.content.Context;
import android.content.SharedPreferences;

public class ProfileManager {

    private final SharedPreferences prefs;

    public ProfileManager(Context context) {
        prefs = context.getSharedPreferences(
                "pingo_profile",
                Context.MODE_PRIVATE
        );
    }

    // Сабти номи корбар
    public void setName(String name) {
        if (name == null) {
            return;
        }

        prefs.edit()
                .putString("name", name.trim())
                .apply();
    }

    // Гирифтани номи корбар
    public String getName() {
        return prefs.getString("name", "Корбар");
    }

    // Сабти маълумот дар бораи худ
    public void setAbout(String about) {
        if (about == null) {
            return;
        }

        prefs.edit()
                .putString("about", about.trim())
                .apply();
    }

    // Гирифтани маълумот дар бораи худ
    public String getAbout() {
        return prefs.getString(
                "about",
                "Салом! Ман PINGO истифода мебарам."
        );
    }

    // Сабти роҳи акси профил
    public void setPhotoUri(String uri) {
        if (uri == null) {
            return;
        }

        prefs.edit()
                .putString("photo_uri", uri)
                .apply();
    }

    // Гирифтани роҳи акси профил
    public String getPhotoUri() {
        return prefs.getString("photo_uri", "");
    }

    // Пок кардани маълумоти профил
    public void clearProfile() {
        prefs.edit().clear().apply();
    }
}
