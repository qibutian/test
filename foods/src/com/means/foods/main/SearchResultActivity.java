package com.means.foods.main;

import org.json.JSONObject;

import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.util.UserLocation;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;
import com.means.foods.view.RefreshListViewAndMore;

public class SearchResultActivity extends FoodsBaseActivity {

	View headV;

	RefreshListViewAndMore listV;
	
	NetJSONAdapter adapter;

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
		
		//从热门推荐过来的
		if (getIntent().getIntExtra("type", 0) == 0) {
			String url = API.restaurantList;
			// 设置空的emptyView
			adapter = new NetJSONAdapter(url, self,
					R.layout.item_hot_list_index);
			adapter.fromWhat("data");
			adapter.addparam("city_id ", getIntent().getStringExtra("cityId"));
			adapter.addparam("name", getIntent().getStringExtra("keyword"));
			adapter.addparam("uid", User.getInstance().getUid());
			adapter.addField("name", R.id.name);
			adapter.addField(new FieldMap("tips", R.id.des) {

				@Override
				public Object fix(View itemV, Integer position, Object o, Object jo) {
					JSONObject json = (JSONObject) jo;
					ImageView collectI = (ImageView) itemV
							.findViewById(R.id.collect);
					collectI.setImageResource(JSONUtil.getInt(json, "is_collect") == 0 ? R.drawable.unlike
							: R.drawable.like);
					return o;
				}
			});
		} else {
			String url = API.restaurantList;
			// 设置空的emptyView
			adapter = new NetJSONAdapter(url, self,
					R.layout.item_restaurant_list);
			adapter.fromWhat("data");
			adapter.addparam("city_id ", getIntent().getStringExtra("cityId"));
			adapter.addparam("name", getIntent().getStringExtra("keyword"));
			adapter.addparam("uid", User.getInstance().getUid());
			adapter.addField("name", R.id.name);
			adapter.addField(new FieldMap("tips", R.id.des) {

				@Override
				public Object fix(View itemV, Integer position, Object o, Object jo) {
					JSONObject json = (JSONObject) jo;
					ImageView collectI = (ImageView) itemV
							.findViewById(R.id.collect);
					collectI.setImageResource(JSONUtil.getInt(json, "is_collect") == 0 ? R.drawable.unlike
							: R.drawable.like);
					return o;
				}
			});
		}

		listV.setAdapter(adapter);

	}

}
