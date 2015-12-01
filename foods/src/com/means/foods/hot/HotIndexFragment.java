package com.means.foods.hot;

import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.util.DhUtil;
import net.duohuo.dhroid.util.UserLocation;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.loadmore.LoadMoreContainer;
import in.srain.cube.views.ptr.loadmore.LoadMoreHandler;
import in.srain.cube.views.ptr.loadmore.LoadMoreListViewContainer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.means.foods.R;
import com.means.foods.adapter.TestAdapter;
import com.means.foods.api.API;
import com.means.foods.base.FoodsListFragment;
import com.means.foods.cate.ReservationsDetailsActivity;
import com.means.foods.cate.RestaurantDetailsActivity;
import com.means.foods.cate.RestaurantListActivity;
import com.means.foods.collect.CollectIndexFragment;
import com.means.foods.main.SearchActivity;
import com.means.foods.view.RefreshListViewAndMore;

public class HotIndexFragment extends FoodsListFragment implements
		OnClickListener {

	static HotIndexFragment instance;

	View mainV;

	PtrFrameLayout mPtrFrame;

	RefreshListViewAndMore listV;

	LoadMoreListViewContainer loadMoreListV;

	View headV;

	LayoutInflater mLayoutInflater;

	View bottomSearchV;

	ListView contentListV;

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
		mLayoutInflater = inflater;
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {

		listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
		String url = API.CWBaseurl + "activity/list?";
		headV = mLayoutInflater.inflate(R.layout.head_hot_index, null);
		bottomSearchV = mainV.findViewById(R.id.search);
		// 添加头部
		listV.addHeadView(headV);
		// 设置空的emptyView
		listV.setEmptyView(mLayoutInflater.inflate(
				R.layout.list_nomal_emptyview, null));
		NetJSONAdapter adapter = new NetJSONAdapter(url, getActivity(),
				R.layout.item_hot_list_index);
		UserLocation location = UserLocation.getInstance();
		adapter.fromWhat("data");
		// setUrl("http://cwapi.gongpingjia.com:8080/v2/activity/list?latitude=32&longitude=118&maxDistance=5000000&token="+user.getToken()+"&userId="+user.getUserId());
		adapter.addparam("latitude", location.getLatitude());
		adapter.addparam("longitude", location.getLongitude());
		adapter.addparam("maxDistance", "5000000");
		adapter.addparam("majorType", "");
		adapter.addparam("pay", "");
		adapter.addparam("gender", "");
		adapter.addparam("transfer", "");
		adapter.addparam("token", "");
		adapter.addparam("userId", "");
		adapter.addField("activityId", R.id.text);
		listV.setAdapter(adapter);

		loadMoreListV = listV.getLoadMoreListViewContainer();
		contentListV = listV.getListView();
		loadMoreListV.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

				int[] location1 = new int[2];
				headV.getLocationOnScreen(location1);
				int y1 = location1[1];

				if (y1 >= 0) {
					bottomSearchV.setVisibility(View.GONE);
					listV.showHeadView();
				} else {
					listV.removeHeadView();
					bottomSearchV.setVisibility(View.VISIBLE);
				}

			}
		});

		bottomSearchV.setOnClickListener(this);
		headV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(getActivity(), SearchActivity.class);
				startActivity(it);
			}
		});

		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent it = new Intent(getActivity(),
						RestaurantDetailsActivity.class);
				startActivity(it);
			}
		});
		// listV = (ListView) mainV.findViewById(R.id.listview);
		// mPtrFrame = (PtrFrameLayout) mainV.findViewById(R.id.ptr_frame);
		// final LoadMoreListViewContainer loadMoreListViewContainer =
		// (LoadMoreListViewContainer) mainV
		// .findViewById(R.id.load_more_list_view_container);

		// initRefreshListView(mPtrFrame, loadMoreListViewContainer);
		//
		// loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
		// @Override
		// public void onLoadMore(LoadMoreContainer loadMoreContainer) {
		// mPtrFrame.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// adapter.setData(10);
		// mPtrFrame.refreshComplete();
		//
		// // load more
		// loadMoreListViewContainer.loadMoreFinish(true, true);
		// }
		// }, 2000);
		// }
		// });
		//
		// mPtrFrame.setPtrHandler(new PtrHandler() {
		// @Override
		// public void onRefreshBegin(PtrFrameLayout frame) {
		// frame.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// adapter.refresh();
		// mPtrFrame.refreshComplete();
		// }
		// }, 1800);
		// }
		//
		// @Override
		// public boolean checkCanDoRefresh(PtrFrameLayout frame,
		// View content, View header) {
		// return PtrDefaultHandler.checkContentCanBePulledDown(frame,
		// listV, header);
		// }
		// });
		// mPtrFrame.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// mPtrFrame.autoRefresh(true);
		// }
		// }, 150);
		//
		// adapter = new TestAdapter(getActivity());
		// listV.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search:
			Intent it = new Intent(getActivity(), SearchActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}
	}
}
