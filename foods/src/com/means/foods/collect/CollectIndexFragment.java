package com.means.foods.collect;

import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.util.UserLocation;
import in.srain.cube.views.ptr.PtrFrameLayout;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.means.foods.R;
import com.means.foods.adapter.TestAdapter;
import com.means.foods.api.API;
import com.means.foods.base.FoodsListFragment;
import com.means.foods.cate.RestaurantListActivity;
import com.means.foods.view.RefreshListViewAndMore;

public class CollectIndexFragment extends FoodsListFragment {

	static CollectIndexFragment instance;

	View mainV;

	PtrFrameLayout mPtrFrame;

	TestAdapter adapter;

	RefreshListViewAndMore listV;

	LayoutInflater mLayoutInflater;

	ListView contentListV;

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
		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {
		listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
		String url = API.CWBaseurl + "activity/list?";
		contentListV = listV.getListView();

		// 设置空的emptyView
		listV.setEmptyView(LayoutInflater.from(getActivity()).inflate(
				R.layout.list_nomal_emptyview, null));
		NetJSONAdapter adapter = new NetJSONAdapter(url, getActivity(),
				R.layout.item_cateindex_list_old);
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

		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent it = new Intent(getActivity(),
						RestaurantListActivity.class);
				startActivity(it);
			}
		});
	}

}
