package com.means.foods.hot;

import net.duohuo.dhroid.util.DhUtil;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.loadmore.LoadMoreContainer;
import in.srain.cube.views.ptr.loadmore.LoadMoreHandler;
import in.srain.cube.views.ptr.loadmore.LoadMoreListViewContainer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.means.foods.R;
import com.means.foods.adapter.TestAdapter;
import com.means.foods.base.FoodsListFragment;
import com.means.foods.collect.CollectIndexFragment;

public class HotIndexFragment extends FoodsListFragment {

	static HotIndexFragment instance;

	View mainV;

	PtrFrameLayout mPtrFrame;

	TestAdapter adapter;

	ListView listV;

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
		listV = (ListView) mainV.findViewById(R.id.listview);
		mPtrFrame = (PtrFrameLayout) mainV.findViewById(R.id.ptr_frame);
		final LoadMoreListViewContainer loadMoreListViewContainer = (LoadMoreListViewContainer) mainV
				.findViewById(R.id.load_more_list_view_container);

		initRefreshListView(mPtrFrame, loadMoreListViewContainer);

//		loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
//			@Override
//			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
//				mPtrFrame.postDelayed(new Runnable() {
//
//					@Override
//					public void run() {
//						adapter.setData(10);
////						mPtrFrame.refreshComplete();
//
//						// load more
//						loadMoreListViewContainer.loadMoreFinish(true, true);
//					}
//				}, 2000);
//			}
//		});

		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				frame.postDelayed(new Runnable() {
					@Override
					public void run() {
						adapter.refresh();
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
		mPtrFrame.postDelayed(new Runnable() {

			@Override
			public void run() {
				mPtrFrame.autoRefresh(true);
			}
		}, 150);

		adapter = new TestAdapter(getActivity());
		listV.setAdapter(adapter);
	}

}
