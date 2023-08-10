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

import me.pluxurylord.flexxgram.FlexxConfig;

public class FlexxController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public FlexxController (Context context, Tdlib tdlib) {
	  super(context, tdlib);
  }

  @Override
  public CharSequence getName() {
	  return Lang.getString(R.string.FlexxController);
  }

  @Override
  public void onClick(View v) {
	  int viewId = v.getId();
	  if (viewId == R.id.btn_flexxUpdates) {
	    navigateTo(new UpdatesController(context, tdlib));
	  } else if (viewId == R.id.btn_generalController) {
	    navigateTo(new GeneralController(context, tdlib));
	  } else if (viewId == R.id.btn_interfaceController) {
	    navigateTo(new InterfaceController(context, tdlib));
	  } else if (viewId == R.id.btn_chatsController) {
	    navigateTo(new ChatsController(context, tdlib));
	  } else if (viewId == R.id.btn_tgСhannel) {
	    tdlib.ui().openUrl(this, FlexxConfig.LINK_CHANNEL, new TdlibUi.UrlOpenParameters().forceInstantView());
	  } else if (viewId == R.id.btn_sources) {
	    tdlib.ui().openUrl(this, FlexxConfig.LINK_SOURCE_CODE, new TdlibUi.UrlOpenParameters().forceInstantView());
	  } else if (viewId == R.id.btn_crowdin) {
	    tdlib.ui().openUrl(this, FlexxConfig.LINK_CROWDIN, new TdlibUi.UrlOpenParameters().forceInstantView());
	  } else if (viewId == R.id.btn_donate) {
	    tdlib.ui().openUrl(this, FlexxConfig.LINK_DONATE, new TdlibUi.UrlOpenParameters().forceInstantView());
	  }
  }

  @Override
  public int getId() {
	  return R.id.controller_flexx;
  }

  @Override
  protected void onCreateView(Context context, CustomRecyclerView recyclerView) {
	  SettingsAdapter adapter = new SettingsAdapter(this) {
	    @Override
	    protected void setValuedSetting(ListItem item, SettingView view, boolean isUpdate) {
		    view.setDrawModifier(item.getDrawModifier());
		    int itemId = item.getId();
		    if (itemId == R.id.btn_flexxUpdates) {
		      view.setData(FlexxConfig.FLEXX_VERSION);
		    } else if (itemId == R.id.btn_tgСhannel) {
		      view.setData(R.string.TgChannelDesc);
		    } else if (itemId == R.id.btn_sources) {
		      view.setData(R.string.SourcesDesc);
		    } else if (itemId == R.id.btn_crowdin) {
		      view.setData(R.string.TranslateDesc);
		    } else if (itemId == R.id.btn_donate) {
		      view.setData(R.string.DonateDesc);
		    }
	    }				
	  };

	  ArrayList<ListItem> items = new ArrayList<>();

	  items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.Flexx));

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_flexxUpdates, R.drawable.baseline_system_update_24, R.string.Flexx));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.FlexxSettings));

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_generalController, R.drawable.baseline_widgets_24, R.string.GeneralController));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_interfaceController, R.drawable.baseline_palette_24, R.string.InterfaceController));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_chatsController, R.drawable.baseline_chat_bubble_24, R.string.ChatsController));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_experimentalController, R.drawable.baseline_science_24, R.string.ExperimentalController));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.FlexxLinks));

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_tgСhannel, R.drawable.baseline_help_24, R.string.TgChannel));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_crowdin, R.drawable.baseline_translate_24, R.string.Translate));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_sources, R.drawable.baseline_github_24, R.string.Sources));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_donate, R.drawable.baseline_paid_24, R.string.Donate));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

	  adapter.setItems(items, true);
    recyclerView.setAdapter(adapter);

  }

}