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
import me.pluxurylord.flexxgram.controllers.drawer.DrawerManagerController;

public class InterfaceController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public InterfaceController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName() {
	  return Lang.getString(R.string.InterfaceController);
  }

  @Override
  public void onClick(View v) {
	  int viewId = v.getId();
	  if (viewId == R.id.btn_drawerManager) {
	    navigateTo(new DrawerManagerController(context, tdlib));
	  } else if (viewId == R.id.btn_hidePhoneNumber) {
	  	FlexxSettings.instance().toggleHidePhoneNumber();
	  	adapter.updateValuedSettingById(R.id.btn_hidePhoneNumber);
	  }
  }

  @Override
  public int getId() {
	  return R.id.controller_interface;
  }

  @Override
  protected void onCreateView(Context context, CustomRecyclerView recyclerView) {
	  SettingsAdapter adapter = new SettingsAdapter(this) {
	    @Override
	    protected void setValuedSetting(ListItem item, SettingView view, boolean isUpdate) {
		    view.setDrawModifier(item.getDrawModifier());
		    int itemId = item.getId();
		    if (itemId == R.id.btn_hidePhoneNumber) {
		    	view.getToggler().setRadioEnabled(FlexxSettings.hidePhoneNumber, isUpdate);
		    }
	    }
	  };

	  ArrayList<ListItem> items = new ArrayList<>();

	  items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.DrawerOptions));

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_drawerManager, 0, R.string.DrawerManager));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_hidePhoneNumber, 0, R.string.HidePhoneNumber));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.HidePhoneNumberDesc));

	  adapter.setItems(items, true);
    recyclerView.setAdapter(adapter);

  }

}