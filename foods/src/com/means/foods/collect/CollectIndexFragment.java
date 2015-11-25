package com.means.foods.collect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.means.foods.R;
import com.means.foods.base.FoodsListFragment;

public class CollectIndexFragment extends FoodsListFragment {

	static CollectIndexFragment instance;

	View mainV;

	public static CollectIndexFragment getInstance() {
		if (instance == null) {
			instance = new CollectIndexFragment();
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
