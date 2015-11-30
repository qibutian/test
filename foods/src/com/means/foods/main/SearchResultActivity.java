package com.means.foods.main;

import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.util.UserLocation;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.means.foods.R;
import com.means.foods.api.API2;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.view.RefreshListViewAndMore;

public class SearchResultActivity extends FoodsBaseActivity {

	View headV;

	RefreshListViewAndMore listV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
	}

	@Override
	public void initView() {
		setTitle("搜索结果");
		headV = LayoutInflater.from(self).inflate(R.layout.head_search_result,
				null);

		listV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
		listV.addHeadView(headV);

		String url = API2.CWBaseurl + "activity/list?";
		// 设置空的emptyView
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

	}

}
