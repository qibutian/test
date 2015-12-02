package com.means.foods.hot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.DhUtil;
import net.duohuo.dhroid.util.UserLocation;
import net.duohuo.dhroid.util.ViewUtil;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.means.foods.R;
import com.means.foods.adapter.TestAdapter;
import com.means.foods.api.API;
import com.means.foods.base.FoodsListFragment;
import com.means.foods.bean.User;
import com.means.foods.cate.ReservationsDetailsActivity;
import com.means.foods.cate.RestaurantDetailsActivity;
import com.means.foods.cate.RestaurantListActivity;
import com.means.foods.collect.CollectIndexFragment;
import com.means.foods.main.SearchActivity;
import com.means.foods.utils.FoodsUtils;
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

	ImageView collectI;

	NetJSONAdapter adapter;
	boolean isShowCollect;
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
		String url = API.restaurantList;
		headV = mLayoutInflater.inflate(R.layout.head_hot_index, null);
		bottomSearchV = mainV.findViewById(R.id.search);
		// 添加头部
		listV.addHeadView(headV);
		// 设置空的emptyView
		listV.setEmptyView(mLayoutInflater.inflate(
				R.layout.list_nomal_emptyview, null));
		adapter = new NetJSONAdapter(url, getActivity(),
				R.layout.item_hot_list_index);
		adapter.fromWhat("data");
		// setUrl("http://cwapi.gongpingjia.com:8080/v2/activity/list?latitude=32&longitude=118&maxDistance=5000000&token="+user.getToken()+"&userId="+user.getUserId());
		adapter.addparam("uid", User.getInstance().getUid());
		adapter.addField("name", R.id.name);
		adapter.addField(new FieldMap("tips", R.id.des) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {
				JSONObject json = (JSONObject) jo;
//				System.out.println("热门详情" + json);
				JSONArray jsa = JSONUtil.getJSONArray(json, "all_pic");
				collectI = (ImageView) itemV.findViewById(R.id.collect);
				collectI.setImageResource(JSONUtil.getInt(json, "is_collect") == 0 ? R.drawable.unlike
						: R.drawable.like);
				System.out.println("收藏的状态"
						+ JSONUtil.getInt(json, "is_collect"));
				if (jsa != null && jsa.length() != 0) {
					try {
						ViewUtil.bindNetImage((ImageView) itemV
								.findViewById(R.id.pic), jsa.get(0).toString(),
								"default");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				isShowCollect = JSONUtil.getInt(json, "is_collect") == 1 ? true : false;
				collectI.setOnClickListener(new MycollOnClick(json));
				String des = JSONUtil.getString(json, "city_name")
						+ "  |  人均 $" + JSONUtil.getString(json, "mean_money");
				return des;
			}
		});
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

	public class MycollOnClick implements View.OnClickListener {
		JSONObject json;

		public MycollOnClick(JSONObject json) {
			this.json = json;
		}

		@Override
		public void onClick(View arg0) {
			int collectTyp = JSONUtil.getInt(json, "is_collect");
			switch (arg0.getId()) {
			case R.id.collect:
				if (!isShowCollect) {
					boolean iscollect = FoodsUtils.collect(getActivity(),
							JSONUtil.getString(json, "store_id"), isShowCollect);
					if (iscollect) {
						
						isShowCollect = !isShowCollect;
						// adapter.notifyDataSetChanged();
						Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
						adapter.refresh();
					}

				} else if (isShowCollect) {
					boolean iscollect = FoodsUtils.collect(getActivity(),
							JSONUtil.getString(json, "store_id"), isShowCollect);
					if (iscollect) {
						isShowCollect = !isShowCollect;
						Toast.makeText(getActivity(), "取消收藏", Toast.LENGTH_SHORT).show();
						adapter.refresh();
					}
				}
				break;

			default:
				break;
			}

		}

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
