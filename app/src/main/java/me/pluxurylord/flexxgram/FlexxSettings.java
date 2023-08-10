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

  	public static final String HIDE_PHONE_NUMBER = "hide_phone_number";

  	public static final String DISABLE_CAMERA_BUTTON = "disable_camera_button";
  	public static final String DISABLE_RECORD_BUTTON = "disable_record_button";
  	public static final String DISABLE_CMD_BUTTON = "disable_cmd_button";
  	public static final String DISABLE_SENDER_BUTTON = "disable_sender_button";

  	public static final String CONTACTS = "contacts";
  	public static final String SAVED_MESSAGES = "saved_messages";
  	public static final String INVITE = "invite";
  	public static final String HELP = "help";
  	public static final String NIGHT = "night";

  	public static final String PHOTO_SIZE_LIMIT_2560 = "photo_change_size_limit";

  	public static boolean hidePhoneNumber = instance().getBoolean(HIDE_PHONE_NUMBER, false);

  	public static boolean disableCameraButton = instance().getBoolean(DISABLE_CAMERA_BUTTON, false);
  	public static boolean disableRecordButton = instance().getBoolean(DISABLE_RECORD_BUTTON, false);
  	public static boolean disableCmdButton = instance().getBoolean(DISABLE_CMD_BUTTON, false);
  	public static boolean disableSenderButton = instance().getBoolean(DISABLE_SENDER_BUTON, false);

  	public static boolean contacts = instance().getBoolean(CONTACTS, true);
  	public static boolean savedMessages = instance().getBoolean(SAVED_MESSAGES, true);
  	public static boolean invite = instance().getBoolean(INVITE, false);
  	public static boolean help = instance().getBoolean(HELP, false);
  	public static boolean night = instance().getBoolean(NIGHT, false);

  	public static boolean photoSizeLimit2560 = instance().getBoolean(PHOTO_SIZE_LIMIT_2560, false);

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
  		putBoolean(HIDE_PHONE_NUMBER, hidePhoneNumber ^= true);
  	}

  	public void toggleDrawerElements(int id) {
  		switch (id) {
  			case 1:
  				putBoolean(CONTACTS, contacts ^= true);
  			case 2:
  				putBoolean(SAVED_MESSAGES, savedMessages ^= true);
  			case 3:
  				putBoolean(INVITE, invite ^= true);
  			case 4:
  				putBoolean(HELP, help ^= true);
  			case 5:
  				putBoolean(NIGHT, night ^= true);
  		}
  	}

  	public void toggleDisableCameraButton() {
  		putBoolean(DISABLE_CAMERA_BUTTON, disableCameraButton ^= true);
  	}

  	public void toggleDisableRecordButton() {
  		putBoolean(DISABLE_RECORD_BUTTON, disableRecordButton ^= true);
  	}

  	public void toggleDisableCmdButton() {
  		putBoolean(DISABLE_CMD_BUTTON, disableCmdButton ^= true);
  	}

  	public void toggleDisableSenderButton() {
  		putBoolean(DISABLE_SENDER_BUTON, disableSenderButton ^= true);
  	}

  	public void togglePhotoSizeLimit2560() {
  		putBoolean(PHOTO_SIZE_LIMIT_2560, photoSizeLimit2560 ^= true);
  	}

}