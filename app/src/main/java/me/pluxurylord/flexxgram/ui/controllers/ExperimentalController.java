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

public class ExperimentalController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public ExperimentalController (Context context, Tdlib tdlib) {
	super(context, tdlib);
  }

  @Override
  public CharSequence getName() {
    return Lang.getString(R.string.ExperimentalController);
  }

  @Override
  public int getId() {
	return R.id.controller_experimental;
  }

  @Override
  public void onClick(View v) {
	int viewId = v.getId();
	if (viewId == R.id.btn_photoSizeLimit2560) {
	  FlexxSettings.instance().togglePhotoSizeLimit2560();
	  adapter.updateValuedSettingById(R.id.btn_photoSizeLimit2560);
	} else if (viewId == R.id.btn_featureToggles) {
	  navigateTo(new FeatureToggles.Controller(context, context.currentTdlib()));
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
		if (itemId == R.id.btn_photoSizeLimit2560) {
		  view.getToggler().setRadioEnabled(FlexxSettings.photoSizeLimit2560, isUpdate);
		  view.setData(R.string.PhotoSizeLimit2560Desc);
		} else if (itemId == R.id.btn_featureToggles) {
		  view.setData(R.string.FeatureTogglesDesc);
		}
	  }
	};

	ArrayList<ListItem> items = new ArrayList<>();

	items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
	items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT_WITH_TOGGLER, R.id.btn_photoSizeLimit2560, 0, R.string.PhotoSizeLimit2560));
	items,add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
	items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_featureToggles, 0, R.string.FeatureToggles));
	items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

	adapter.setItems(items, true);
    recyclerView.setAdapter(adapter);

  }

}