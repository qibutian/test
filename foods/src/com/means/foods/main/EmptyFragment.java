package com.means.foods.main;

import in.srain.cube.views.ptr.PtrFrameLayout;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.means.foods.R;
import com.means.foods.collect.CollectIndexFragment;
import com.means.foods.view.RefreshListViewAndMore;

import de.greenrobot.event.EventBus;

public class EmptyFragment extends Fragment {

	static EmptyFragment instance;

	View mainV;

	PtrFrameLayout mPtrFrame;

	RefreshListViewAndMore listV;

	LayoutInflater mLayoutInflater;

	ListView contentListV;

	View emptyView;

	NetJSONAdapter adapter;

	public static EmptyFragment getInstance() {
		if (instance == null) {
			instance = new EmptyFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mainV = inflater.inflate(R.layout.fragment_empty, null);
		// TODO Auto-generated method stub
		return mainV;
	}

}
