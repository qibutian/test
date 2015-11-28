package com.means.foods.cate;

import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.util.UserLocation;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.loadmore.LoadMoreListViewContainer;

import com.means.foods.R;
import com.means.foods.R.layout;
import com.means.foods.adapter.TestAdapter;
import com.means.foods.api.API2;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.main.SearchActivity;
import com.means.foods.view.RefreshListViewAndMore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 餐厅列表
 * 
 * @author dell
 * 
 */
public class RestaurantListActivity extends FoodsBaseActivity implements
		OnClickListener {

	PtrFrameLayout mPtrFrame;

	TestAdapter adapter;

	RefreshListViewAndMore listV;

	LoadMoreListViewContainer loadMoreListV;

	View headV;

	LayoutInflater mLayoutInflater;

	View bottomSearchV;

	ListView contentListV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_list);
	}

	@Override
	public void initView() {
		setTitle("餐厅列表");
		mLayoutInflater = LayoutInflater.from(self);
		listV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
		String url = API2.CWBaseurl + "activity/list?";
		headV = mLayoutInflater.inflate(R.layout.head_hot_index, null);
		bottomSearchV = findViewById(R.id.search);
		// 添加头部
		listV.addHeadView(headV);
		// 设置空的emptyView
		listV.setEmptyView(mLayoutInflater.inflate(
				R.layout.list_nomal_emptyview, null));
		NetJSONAdapter adapter = new NetJSONAdapter(url, self,
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
				Intent it = new Intent(self, SearchActivity.class);
				startActivity(it);
			}
		});

		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent it = new Intent(self, RestaurantDetailsActivity.class);
				startActivity(it);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search:
			Intent it = new Intent(self, SearchActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}
	}
}
