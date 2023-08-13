// This is a Flexxgram source code file.
// Flexxgram is not a trademark of Telegram and Telegram X.
// Flexxgram is an open and freely distributed modification of Telegram X.
//
// Copyright (C) 2023 Flexxteam.

package me.pluxurylord.flexxgram;

import android.content.SharedPreferences;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.drinkmore.Tracer;
import org.thunderdog.challegram.Log;
import org.thunderdog.challegram.tool.UI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;

import me.vkryl.core.reference.ReferenceList;
import me.vkryl.leveldb.LevelDB;

public class FlexxSettings {

  private static final int VERSION = 1;
  private static final AtomicBoolean hasInstance = new AtomicBoolean(false);
  private static volatile FlexxSettings instance;
	private final LevelDB config;
  private static final String KEY_VERSION = "version";

  // General
  public static boolean hidePhoneNumber = instance().getBoolean("hide_phone_number", false);

  // Appearance
  public static boolean contacts = instance().getBoolean("contacts", true);
  public static boolean savedMessages = instance().getBoolean("savedMessages", true);
  public static boolean invite = instance().getBoolean("invite", true);
  public static boolean help = instance().getBoolean("help", true);
  public static boolean night = instance().getBoolean("night", true);

  // Chats
  public static boolean disableCameraButton = instance().getBoolean("disable_camera_button", false);
  public static boolean disableRecordButton = instance().getBoolean("disable_record_button", false);
  public static boolean disableCmdButton = instance().getBoolean("disable_cmd_button", false);
  public static boolean disableSenderButton = instance().getBoolean("disable_sender_button", false);

  // Other
  public static boolean photoSizeLimit2560 = instance().getBoolean("photo_size_limit_2560", false);

	private FlexxSettings () {

    File configDir = new File(UI.getAppContext().getFilesDir(), "flexxcfg");
    if (!configDir.exists() && !configDir.mkdir()) {
      throw new IllegalStateException("Unable to create working directory");
    }

    long ms = SystemClock.uptimeMillis();

    config = new LevelDB(new File(configDir, "db").getPath(), true, new LevelDB.ErrorHandler() {

      @Override public boolean onFatalError (LevelDB levelDB, Throwable error) {
        Tracer.onDatabaseError(error);
        return true;
      }

      @Override public void onError (LevelDB levelDB, String message, @Nullable Throwable error) {
        // Cannot use custom Log, since settings are not yet loaded
        android.util.Log.e(Log.LOG_TAG, message, error);
      }

    });

    int configVersion = 0;
    try {
      configVersion = Math.max(0, config.tryGetInt(KEY_VERSION));
    } catch (FileNotFoundException ignored) {
    	// Do nothing.
    }

    if (configVersion > VERSION) {
      Log.e("Downgrading database version: %d -> %d", configVersion, VERSION);
      config.putInt(KEY_VERSION, VERSION);
    }

    for (int version = configVersion + 1; version <= VERSION; version++) {
      SharedPreferences.Editor editor = config.edit();
      upgradeConfig(config, editor, version);
      editor.putInt(KEY_VERSION, version);
      editor.apply();
    }

    Log.i("Opened database in %dms", SystemClock.uptimeMillis() - ms);

  }

  public static FlexxSettings instance () {
    if (instance == null) {
      synchronized (FlexxSettings.class) {
        if (instance == null) {
          if (hasInstance.getAndSet(true)) throw new AssertionError();
          instance = new FlexxSettings();
        }
      }
    }
    return instance;
  }

  public LevelDB edit () {
    return config.edit();
  }

  public void remove (String key) {
    config.remove(key);
  }

  public void putLong (String key, long value) {
    config.putLong(key, value);
  }

  public long getLong (String key, long defValue) {
    return config.getLong(key, defValue);
  }

  public long[] getLongArray (String key) {
    return config.getLongArray(key);
  }

  public void putLongArray (String key, long[] value) {
    config.putLongArray(key, value);
  }

  public void putInt (String key, int value) {
    config.putInt(key, value);
  }

  public int getInt (String key, int defValue) {
    return config.getInt(key, defValue);
  }

  public void putFloat (String key, float value) {
    config.putFloat(key, value);
  }

  public void getFloat (String key, float defValue) {
    config.getFloat(key, defValue);
  }

  public void putBoolean (String key, boolean value) {
    config.putBoolean(key, value);
  }

  public boolean getBoolean (String key, boolean defValue) {
    return config.getBoolean(key, defValue);
  }

  public void putString (String key, @NonNull String value) {
    config.putString(key, value);
  }

  public String getString (String key, String defValue) {
    return config.getString(key, defValue);
  }

  public boolean containsKey (String key) {
    return config.contains(key);
  }

  public LevelDB config () {
    return config;
  }

  public interface SettingsChangeListener {
    void onSettingsChanged (String key, Object newSettings, Object oldSettings);
  }

  private ReferenceList<SettingsChangeListener> newSettingsListeners;

  public void addNewSettingsListener (SettingsChangeListener listener) {
    if (newSettingsListeners == null) {
      newSettingsListeners = new ReferenceList<>();
    	newSettingsListeners.add(listener);
    }
  }

  private void notifyNewSettingsListeners (String key, Object newSettings, Object oldSettings) {
    if (newSettingsListeners != null) {
      for (SettingsChangeListener listener : newSettingsListeners) {
        listener.onSettingsChanged(key, newSettings, oldSettings);
      }
    }
  }

  public void toggleHidePhoneNumber() {
  	putBoolean("hide_phone_number", hidePhoneNumber ^= true);
  }

  public void toggleDrawerElements(int id) {
  	switch (id) {
  		case 1:
  			putBoolean("contacts", contacts ^= true);
  		case 2:
  			putBoolean("savedMessages", savedMessages ^= true);
  		case 3:
  			putBoolean("invite", invite ^= true);
  		case 4:
  			putBoolean("help", help ^= true);
  		case 5:
  			putBoolean("night", night ^= true);
  	}
  }

  public void toggleChatButtons(int id) {
    switch (id) {
      case 1:
        putBoolean("disable_camera_button", disableCameraButton ^= true);
      case 2:
        putBoolean("disable_record_button", disableRecordButton ^= true);
      case 3:
        putBoolean("disable_cmd_button", disableCmdButton ^= true);
      case 4:
        putBoolean("disable_sender_button", disableSenderButton ^= true);
    }
  }

  public void togglePhotoSizeLimit2560() {
  	putBoolean(PHOTO_SIZE_LIMIT_2560, photoSizeLimit2560 ^= true);
  }

}