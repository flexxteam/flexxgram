// This is a Flexxgram source code file.
// Flexxgram is not a trademark of Telegram and Telegram X.
// Flexxgram is an open and freely distributed modification of Telegram X.
//
// Copyright (C) 2023 Flexxteam.

package me.pluxurylord.flexxgram.ui.controllers;

import android.content.Context;
import android.view.View;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.telegram.TdlibUi;
import org.thunderdog.challegram.ui.ListItem;
import org.thunderdog.challegram.ui.RecyclerViewController;
import org.thunderdog.challegram.ui.SettingsAdapter;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

import me.pluxurylord.flexxgram.FlexxSettings;

public class ChatsController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public ChatsController (Context context, Tdlib tdlib) {
	super(context, tdlib);
  }

  @Override
  public CharSequence getName() {
	return Lang.getString(R.string.ChatsController);
  }

  @Override
  public int getId() {
	return R.id.controller_chats;
  }

  @Override
  public void onClick(View v) {
	int viewId = v.getId();
	if (viewId == R.id.btn_disableCameraButton) {
	  FlexxSettings.instance().toggleChatButtons(1);
	  adapter.updateValuedSettingById(R.id.btn_disableCameraButton);
	} else if (viewId == R.id.btn_disableRecordButton) {
	  FlexxSettings.instance().toggleChatButtons(2);
	  adapter.updateValuedSettingById(R.id.btn_disableRecordButton);
	} else if (viewId == R.id.btn_disableCmdButton) {
	  FlexxSettings.instance().toggleChatButtons(3);
	  adapter.updateValuedSettingById(R.id.btn_disableCmdButton);
	} else if (viewId == R.id.btn_disableSenderButton) {
	  FlexxSettings.instance().toggleChatButtons(4);
	  adapter.updateValuedSettingById(R.id.btn_disableSenderButton);
	}
	break;
  }

  @Override
  protected void onCreateView(Context context, CustomRecyclerView recyclerView) {
	SettingsAdapter adapter = new SettingsAdapter(this) {
	  @Override
	  protected void setValuedSetting(ListItem item, SettingView view, boolean isUpdate) {
		view.setDrawModifier(item.getDrawModifier());
		int itemId = item.getId();
		if (itemId == R.id.btn_disableCameraButton) {
		  view.getToggler().setRadioEnabled(FlexxSettings.disableCameraButton, isUpdate);
		} else if (itemId == R.id.btn_disableRecordButton) {
		  view.getToggler().setRadioEnabled(FlexxSettings.disableRecordButton, isUpdate);
		} else if (itemId == R.id.btn_disableCmdButton) {
		  view.getToggler().setRadioEnabled(FlexxSettings.disableCmdButton, isUpdate);
		} else if (itemId == R.id.btn_disableSenderButton) {
		  view.getToggler().setRadioEnabled(FlexxSettings.disableSenderButton, isUpdate);
		}
	  }
	};

	ArrayList<ListItem> items = new ArrayList<>();

	items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.ChatSettings));

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableCameraButton, 0, R.string.DisableCameraButton));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableRecordButton, 0, R.string.DisableRecordButton));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableCmdButton, 0, R.string.DisableCmdButton));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableSenderButton, 0, R.string.DisableSenderButton));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

	adapter.setItems(items, true);
    recyclerView.setAdapter(adapter);

  }

}