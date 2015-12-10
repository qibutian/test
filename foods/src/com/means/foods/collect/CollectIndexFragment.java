package com.means.foods.collect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.util.UserLocation;
import in.srain.cube.views.ptr.PtrFrameLayout;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsListFragment;
import com.means.foods.bean.User;
import com.means.foods.cate.RestaurantListActivity;
import com.means.foods.utils.FoodsUtils;
import com.means.foods.view.RefreshListViewAndMore;

public class CollectIndexFragment extends FoodsListFragment {

	static CollectIndexFragment instance;

	View mainV;

	PtrFrameLayout mPtrFrame;

	RefreshListViewAndMore listV;

	LayoutInflater mLayoutInflater;

	ListView contentListV;

	View emptyView;

	NetJSONAdapter adapter;

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
		String url = API.collectList;
		contentListV = listV.getListView();
		// 设置空的emptyView
		emptyView = LayoutInflater.from(getActivity()).inflate(
				R.layout.list_collect_empty, null);
		listV.setEmptyViewTop(emptyView);
		adapter = new NetJSONAdapter(url, getActivity(),
				R.layout.item_cateindex_list_old);
		adapter.fromWhat("data");
		adapter.addparam("uid", User.getInstance().getUid());
		adapter.addparam("token", User.getInstance().getToken());
		adapter.addField("city_name", R.id.name);
		adapter.addField("area_img", R.id.pic, "default");
		adapter.addField(new FieldMap("count", R.id.count) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {

				TextView desT = (TextView) itemV.findViewById(R.id.des);
				JSONObject data = (JSONObject) jo;
				JSONArray jsa = JSONUtil.getJSONArray(data, "data");
				String des = "";
				for (int i = 0; i < jsa.length(); i++) {
					try {
						JSONObject cateJo = jsa.getJSONObject(i);
						des = des + JSONUtil.getString(cateJo, "name") + "    ";
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				desT.setText(des);

				String address = JSONUtil.getString(data, "city_name");
				if (!TextUtils.isEmpty(address)) {
					String letter = FoodsUtils.getPYIndexStr(
							address.substring(0, 1), true);
					TextView letterT = (TextView) itemV
							.findViewById(R.id.letter);
					letterT.setText(letter);
				}
				return "您收藏了" + o + "加餐厅";
			}
		});
		listV.setAdapter(adapter);

		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				JSONObject jo = adapter.getTItem(position);
				try {
					JSONArray jsa = jo.getJSONArray("data");
					JSONObject dataJo = jsa.getJSONObject(0);
					Intent it = new Intent(getActivity(),
							RestaurantListActivity.class);
					it.putExtra("cityId", JSONUtil.getString(dataJo, "city_id"));
					startActivity(it);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		emptyView.findViewById(R.id.explore).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				});

	}

}
