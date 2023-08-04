// This is a Flexxgram source code file.
// Flexxgram is not a trademark of Telegram and Telegram X.
// Flexxgram is an open and freely distributed modification of Telegram X.
//
// Copyright (C) 2023 Flexxteam.

package me.pluxurylord.flexxgram.ui.controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.navigation.ViewController;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.telegram.TdlibUi;
import org.thunderdog.challegram.ui.ListItem;
import org.thunderdog.challegram.ui.RecyclerViewController;
import org.thunderdog.challegram.ui.SettingsAdapter;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

import me.pluxurylord.flexxgram.FlexxSettings;


public class InterfaceController extends RecyclerViewController<Void> implements View.OnClickListener, ViewController.SettingsIntDelegate {

	private SettingsAdapter adapter;

	public ChatsController (Context context, Tdlib tdlib) {
		super(context, tdlib);
	}

	@Override
	public CharSequence getName() {
		return Lang.getString(R.string.InterfaceController);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			// Soon.
		}
	}

	@Override
	public void onApplySettings(int id, SparseIntArray result) {
		switch (id) {
			// Soon.
		}
	}

	@Override
	public int getId() {
		return R.id.controller_interface;
	}

	@Override
	protected void onCreateView(Context context, CustomRecyclerView recyclerView) {
		adapter = new SettingsAdapter(this) {
			@Override
			protected void setValuedSetting(ListItem item, SettingView view, boolean isUpdate) {
				view.setDrawModifier(item.getDrawModifier());
				switch (item.getId()) {
					// Soon.
				}
			}
		};

		ArrayList<ListItem> items = new ArrayList<>();

		// No any items right now.

		adapter.setItems(items, true);
    	recyclerView.setAdapter(adapter);
	}
}