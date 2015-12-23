package com.means.foods.main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.UserLocation;
import net.duohuo.dhroid.util.ViewUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;
import com.means.foods.view.RefreshListViewAndMore;
import com.means.foods.view.RefreshListViewAndMore.OnLoadSuccess;

public class SearchResultActivity extends FoodsBaseActivity implements
		OnLoadSuccess {

	View headV;

	RefreshListViewAndMore listV;

	NetJSONAdapter adapter;

	boolean isRecommendedData = false;

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
		listV.setOnLoadSuccess(this);
		listV.addHeadView(headV);
		// 从热门推荐过来的
		if (getIntent().getIntExtra("type", 0) == 0) {
			String url = API.restaurantList;
			// 设置空的emptyView
			adapter = new NetJSONAdapter(url, self,
					R.layout.item_hot_list_index);
			adapter.fromWhat("data");
			adapter.addparam("name", getIntent().getStringExtra("keyword"));
			adapter.addparam("uid", User.getInstance().getUid());
			adapter.addField("name", R.id.name);
			adapter.addField(new FieldMap("tips", R.id.des) {

				@Override
				public Object fix(View itemV, Integer position, Object o,
						Object jo) {
					JSONObject json = (JSONObject) jo;
					JSONArray jsa = JSONUtil.getJSONArray(json, "all_pic");
					ImageView collectI = (ImageView) itemV
							.findViewById(R.id.collect);
					collectI.setImageResource(JSONUtil.getInt(json,
							"is_collect") == 0 ? R.drawable.unlike
							: R.drawable.like);
					if (jsa != null && jsa.length() != 0) {
						try {
							ViewUtil.bindNetImage(
									(ImageView) itemV.findViewById(R.id.pic),
									jsa.get(0).toString(), "default");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					String des = JSONUtil.getString(json, "city_name")
							+ "  |  人均 $"
							+ JSONUtil.getString(json, "mean_money");
					return des;
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
				public Object fix(View itemV, Integer position, Object o,
						Object jo) {
					JSONObject json = (JSONObject) jo;
					JSONArray jsa = JSONUtil.getJSONArray(json, "all_pic");
					ImageView collectI = (ImageView) itemV
							.findViewById(R.id.collect);
					collectI.setImageResource(JSONUtil.getInt(json,
							"is_collect") == 0 ? R.drawable.unlike
							: R.drawable.like);

					if (jsa != null && jsa.length() != 0) {
						try {
							ViewUtil.bindNetImage(
									(ImageView) itemV.findViewById(R.id.pic),
									jsa.get(0).toString(), "default");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					String des = "人均 $"
							+ JSONUtil.getString(json, "mean_money") + " | "
							+ o;
					return des;
				}
			});
		}

		listV.setAdapter(adapter);

	}

	@Override
	public void loadSuccess(Response response) {
		if (adapter.getValues().size() == 0) {
			adapter.addparam("name", "");
			adapter.addparam("city_id ", "");
			adapter.addparam("recommend", "1");
			adapter.refresh();
			headV.findViewById(R.id.head_layout).setVisibility(View.VISIBLE);
			isRecommendedData = true;
		}
	}

}
