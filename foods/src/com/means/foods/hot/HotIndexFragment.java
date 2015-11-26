package com.means.foods.hot;

import net.duohuo.dhroid.util.DhUtil;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
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

	PtrFrameLayout mPtrFrame;

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
		mainV = inflater.inflate(R.layout.fragment_hot_index, null);
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {
		mPtrFrame = (PtrFrameLayout) mainV.findViewById(R.id.ptr_frame);
		final StoreHouseHeader header = new StoreHouseHeader(getActivity());
		header.setPadding(0, DhUtil.dip2px(getActivity(), 15), 0, 0);
		header.initWithString("Foods");
		header.setTextColor(getResources().getColor(R.color.text_yellow));
		mPtrFrame.addPtrUIHandler(header);
		mPtrFrame.setHeaderView(header);
		mPtrFrame.setPinContent(false);
		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				frame.postDelayed(new Runnable() {
					@Override
					public void run() {
						mPtrFrame.refreshComplete();
					}
				}, 1800);
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
						content, header);
			}
		});
	}
}
