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

public class UpdatesController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public UpdatesController (Context context, Tdlib tdlib) {
	super(context, tdlib);
  }

  @Override
  public CharSequence getName() {
    return Lang.getString(R.string.UpdatesController);
  }

  @Override
  public void onClick(View v) {
	int viewId = v.getId();
	/* if (viewId == R.id.something) {
      Do actions.
	} */
	break;
  }

  @Override
  public int getId() {
	return R.id.controller_updates;
  }

  @Override
  protected void onCreateView(Context context, CustomRecyclerView recyclerView) {
	SettingsAdapter adapter = new SettingsAdapter(this) {
	  @Override
	  protected void setValuedSetting(ListItem item, SettingView view, boolean isUpdate) {
		view.setDrawModifier(item.getDrawModifier());
		int itemId = item.getId();
		/* if (itemId == R.id.something) {
		  Do actions
		} */
	  }
	};

	ArrayList<ListItem> items = new ArrayList<>();

	// No any items right now.

	adapter.setItems(items, true);
    recyclerView.setAdapter(adapter);

  }

}