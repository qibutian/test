package com.means.foods.my;

import org.json.JSONObject;

import in.srain.cube.views.ptr.PtrFrameLayout;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.util.UserLocation;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;
import com.means.foods.cate.RestaurantListActivity;
import com.means.foods.view.RefreshListViewAndMore;

public class MsgListActivity extends FoodsBaseActivity {

	PtrFrameLayout mPtrFrame;

	RefreshListViewAndMore listV;

	LayoutInflater mLayoutInflater;

	ListView contentListV;
	NetJSONAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_list);
	}

	@Override
	public void initView() {
		setTitle("消息列表");
		listV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
		String url = API.msglist;
		contentListV = listV.getListView();

		// 设置空的emptyView
		listV.setEmptyView(LayoutInflater.from(self).inflate(
				R.layout.list_nomal_emptyview, null));
		adapter = new NetJSONAdapter(url, self, R.layout.item_msg);
		adapter.fromWhat("data");
		adapter.addparam("uid", User.getInstance().getUid());
		adapter.addparam("token", User.getInstance().getToken());
		adapter.addField("title", R.id.title);
		adapter.addField("content", R.id.content);
		adapter.addField("creattime", R.id.date, "time");
		adapter.addField("show_type", R.id.show_type);
		listV.setAdapter(adapter);

		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent it = new Intent(self, MsgDetailActivity.class);
				JSONObject data = adapter.getTItem(position);
				it.putExtra("jo", data.toString());
				startActivity(it);
			}
		});
	}

}
