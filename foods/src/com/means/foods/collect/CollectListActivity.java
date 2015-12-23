package com.means.foods.collect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.PSAdapter;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.cate.RestaurantDetailsActivity;
import com.means.foods.cate.RestaurantListActivity.MycollOnClick;
import com.means.foods.main.SearchActivity;
import com.means.foods.utils.FoodsUtils;
import com.means.foods.utils.FoodsUtils.OnCallBack;

public class CollectListActivity extends FoodsBaseActivity implements
		OnClickListener {

	ListView listV;

	PSAdapter adapter;

	View bottomSearchV;

	View headV;

	LayoutInflater mLayoutInflater;

	JSONObject jodata;

	JSONArray jsa;

	String cityId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collect_list);
	}

	@Override
	public void initView() {
		setTitle("我的收藏");
		String joStr = getIntent().getStringExtra("jo");
		try {
			jodata = new JSONObject(joStr);
			jsa = JSONUtil.getJSONArray(jodata, "data");

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cityId = getIntent().getStringExtra("cityId");
		bottomSearchV = findViewById(R.id.search);
		mLayoutInflater = LayoutInflater.from(self);
		headV = mLayoutInflater.inflate(R.layout.head_hot_index, null);
		listV = (ListView) findViewById(R.id.listview);
		listV.addHeaderView(headV);
		adapter = new PSAdapter(self, R.layout.item_restaurant_list);
		adapter.addField("name", R.id.name);
		adapter.addField(new FieldMap("tagline", R.id.des) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {
				JSONObject json = (JSONObject) jo;
				ImageView collectI = (ImageView) itemV
						.findViewById(R.id.collect);
				collectI.setImageResource(JSONUtil.getInt(json, "is_collect") == 0 ? R.drawable.unlike
						: R.drawable.like);
				ViewUtil.bindNetImage((ImageView) itemV.findViewById(R.id.pic),
						JSONUtil.getString(json, "list_pic"), "default");
				collectI.setOnClickListener(new MycollOnClick(json));

				String des;
				if (TextUtils.isEmpty(JSONUtil.getString(json, "tagline"))) {
					des = JSONUtil.getString(jodata, "city_name");
				} else {
					des = JSONUtil.getString(jodata, "city_name") + "  |  "
							+ JSONUtil.getString(json, "tagline");
				}
				return des;
			}
		});
		listV.setAdapter(adapter);
		adapter.addAll(jsa);

		listV.setOnScrollListener(new OnScrollListener() {

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
					showHeadView();
				} else {
					removeHeadView();
					bottomSearchV.setVisibility(View.VISIBLE);
				}

			}
		});

		bottomSearchV.setOnClickListener(this);
		headV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(self, SearchActivity.class);
				it.putExtra("type", 1);
				it.putExtra("cityId", cityId);
				startActivity(it);
			}
		});

		listV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				JSONObject jo = (JSONObject) adapter.getItem(position - 1);
				Intent it = new Intent(self, RestaurantDetailsActivity.class);
				it.putExtra("store_id", JSONUtil.getString(jo, "store_id"));
				startActivity(it);
			}
		});
	}

	public class MycollOnClick implements View.OnClickListener {
		JSONObject json;

		public MycollOnClick(JSONObject json) {
			this.json = json;
		}

		@Override
		public void onClick(View arg0) {
			final boolean isShowCollect = JSONUtil.getInt(json, "is_collect") == 1 ? true
					: false;
			switch (arg0.getId()) {
			case R.id.collect:
				FoodsUtils utils = new FoodsUtils();
				utils.collect(self, JSONUtil.getString(json, "store_id"),
						isShowCollect);
				utils.setOnCallBack(new OnCallBack() {

					@Override
					public void callBack(Response response) {
						if (response.isSuccess()) {
							if (isShowCollect) {
								Toast.makeText(self, "取消收藏成功",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(self, "收藏成功", Toast.LENGTH_SHORT)
										.show();
							}
						}
					}
				});

				// adapter.notifyDataSetChanged();
				try {
					json.put("is_collect",
							JSONUtil.getInt(json, "is_collect") == 0 ? 1 : 0);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				adapter.notifyDataSetChanged();

				break;

			default:
				break;
			}

		}

	}

	public void removeHeadView() {
		if (headV != null) {
			headV.setVisibility(View.GONE);
			headV.setPadding(0, -headV.getHeight(), 0, 0);
		}
	}

	public void showHeadView() {
		if (headV != null) {
			headV.setVisibility(View.VISIBLE);
			headV.setPadding(0, 0, 0, 0);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search:
			Intent it = new Intent(self, SearchActivity.class);
			it.putExtra("type", 1);
			it.putExtra("cityId", cityId);
			startActivity(it);
			break;

		default:
			break;
		}
	}

}
