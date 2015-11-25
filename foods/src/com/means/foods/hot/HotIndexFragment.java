package com.means.foods.hot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.means.foods.R;
import com.means.foods.base.FoodsListFragment;
import com.means.foods.collect.CollectIndexFragment;

public class HotIndexFragment extends FoodsListFragment {

	static HotIndexFragment instance;

	View mainV;

	public static HotIndexFragment getInstance() {
		if (instance == null) {
			instance = new HotIndexFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mainV = inflater.inflate(R.layout.fragment_cate_index, null);
		// TODO Auto-generated method stub
		return mainV;
	}
}
