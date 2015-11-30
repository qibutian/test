package com.means.foods.my;

import in.srain.cube.views.ptr.PtrFrameLayout;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.util.UserLocation;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.means.foods.R;
import com.means.foods.adapter.TestAdapter;
import com.means.foods.api.API2;
import com.means.foods.base.FoodsListFragment;
import com.means.foods.cate.ReservationsDetailsActivity;
import com.means.foods.cate.RestaurantListActivity;
import com.means.foods.view.RefreshListViewAndMore;

public class MyIndexFragment extends FoodsListFragment implements
		OnClickListener {

	static MyIndexFragment instance;

	View mainV;

	PtrFrameLayout mPtrFrame;

	TestAdapter adapter;

	RefreshListViewAndMore listV;

	LayoutInflater mLayoutInflater;

	ListView contentListV;

	View headV;

	// 编辑资料库按钮
	Button editB;

	// 消息中心
	ImageView msgI;

	public static MyIndexFragment getInstance() {
		if (instance == null) {
			instance = new MyIndexFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_my_index, null);
		mLayoutInflater = inflater;
		initView();
		// TODO Auto-generated method stub
		return mainV;

	}

	private void initView() {
		listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
		String url = API2.CWBaseurl + "activity/list?";
		contentListV = listV.getListView();
		headV = mLayoutInflater.inflate(R.layout.head_my_index, null);
		listV.addHeadView(headV);
		// 璁剧疆绌虹殑emptyView
		NetJSONAdapter adapter = new NetJSONAdapter(url, getActivity(),
				R.layout.item_index_listview);
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
						MyReservationsListActivity.class);
				startActivity(it);
			}
		});

		editB = (Button) headV.findViewById(R.id.edit);
		editB.setOnClickListener(this);
		msgI = (ImageView) headV.findViewById(R.id.msg);
		msgI.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		case R.id.edit:
			it = new Intent(getActivity(), EditinfoActivity.class);
			startActivity(it);
			break;

		case R.id.msg:
			it = new Intent(getActivity(), MsgListActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}
	}
}
