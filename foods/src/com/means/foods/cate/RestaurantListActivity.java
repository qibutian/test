package com.means.foods.cate;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.loadmore.LoadMoreListViewContainer;
import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;
import com.means.foods.hot.HotIndexFragment.MycollOnClick;
import com.means.foods.main.SearchActivity;
import com.means.foods.utils.FoodsUtils;
import com.means.foods.utils.FoodsUtils.OnCallBack;
import com.means.foods.view.RefreshListViewAndMore;

/**
 * 餐厅列表
 * 
 * @author dell
 * 
 */
public class RestaurantListActivity extends FoodsBaseActivity implements
		OnClickListener {

	PtrFrameLayout mPtrFrame;

	RefreshListViewAndMore listV;

	LoadMoreListViewContainer loadMoreListV;

	View headV;

	LayoutInflater mLayoutInflater;

	View bottomSearchV;

	ListView contentListV;

	String cityId;

	NetJSONAdapter adapter;
	boolean isShowCollect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_list);
	}

	@Override
	public void initView() {
		setTitle("餐厅列表");
		cityId = getIntent().getStringExtra("cityId");
		mLayoutInflater = LayoutInflater.from(self);
		listV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
		String url = API.restaurantList;
		headV = mLayoutInflater.inflate(R.layout.head_hot_index, null);
		bottomSearchV = findViewById(R.id.search);
		// 添加头部
		listV.addHeadView(headV);
		// 设置空的emptyView
		listV.setEmptyView(mLayoutInflater.inflate(
				R.layout.list_nomal_emptyview, null));
		adapter = new NetJSONAdapter(url, self, R.layout.item_restaurant_list);
		adapter.fromWhat("data");
		// setUrl("http://cwapi.gongpingjia.com:8080/v2/activity/list?latitude=32&longitude=118&maxDistance=5000000&token="+user.getToken()+"&userId="+user.getUserId());
		adapter.addparam("city_id ", cityId);
		adapter.addparam("name", "");
		adapter.addparam("uid", User.getInstance().getUid());
		adapter.addField("name", R.id.name);
		adapter.addField(new FieldMap("tagline", R.id.des) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {
				JSONObject json = (JSONObject) jo;
				JSONArray jsa = JSONUtil.getJSONArray(json, "all_pic");
				ImageView collectI = (ImageView) itemV
						.findViewById(R.id.collect);
				collectI.setImageResource(JSONUtil.getInt(json, "is_collect") == 0 ? R.drawable.unlike
						: R.drawable.like);
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
				isShowCollect = JSONUtil.getInt(json, "is_collect") == 1 ? true
						: false;
				collectI.setOnClickListener(new MycollOnClick(json));
				return o;
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
				Intent it = new Intent(self, SearchActivity.class);
				it.putExtra("type", 1);
				it.putExtra("cityId", cityId);
				startActivity(it);
			}
		});

		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				JSONObject jo = adapter.getTItem(position - 1);
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
