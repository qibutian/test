package com.means.foods.cate;

import in.srain.cube.views.ptr.PtrFrameLayout;
import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.JSONUtil;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsListFragment;
import com.means.foods.view.RefreshListViewAndMore;

public class CateIndexFragment extends FoodsListFragment {

	static CateIndexFragment instance;

	View mainV;

	PtrFrameLayout mPtrFrame;

	RefreshListViewAndMore listV;

	LayoutInflater mLayoutInflater;

	ListView contentListV;

	NetJSONAdapter adapter;

	public static CateIndexFragment getInstance() {
		if (instance == null) {
			instance = new CateIndexFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_cate_index, null);

		initView();
		// TODO Auto-generated method stub
		return mainV;
	}

	private void initView() {
		listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
		String url = API.cityList;
		contentListV = listV.getListView();

		// 设置空的emptyView
		listV.setEmptyView(LayoutInflater.from(getActivity()).inflate(
				R.layout.list_nomal_emptyview, null));
		adapter = new NetJSONAdapter(url, getActivity(),
				R.layout.item_cateindex_list);
		adapter.fromWhat("data");
		// setUrl("http://cwapi.gongpingjia.com:8080/v2/activity/list?latitude=32&longitude=118&maxDistance=5000000&token="+user.getToken()+"&userId="+user.getUserId());
//		adapter.addField("area_name", R.id.name);
		adapter.addField("image", R.id.pic, "default");
		adapter.addField(new FieldMap("count", R.id.count) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {
				return o + "家餐厅";
			}
		});
		listV.setAdapter(adapter);

		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				JSONObject jo = adapter.getTItem(position);
				Intent it = new Intent(getActivity(),
						RestaurantListActivity.class);
				it.putExtra("cityId", JSONUtil.getString(jo, "area_id"));
				startActivity(it);
			}
		});
	}
}
