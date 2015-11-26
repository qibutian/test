package com.means.foods.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.means.foods.R;
import com.means.foods.base.FoodsListFragment;

public class MyIndexFragment extends FoodsListFragment {

	static MyIndexFragment instance;

	View mainV;

	public static MyIndexFragment getInstance() {
		if (instance == null) {
			instance = new MyIndexFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_cate_index, null);
		// TODO Auto-generated method stub
		return mainV;
	}

}
