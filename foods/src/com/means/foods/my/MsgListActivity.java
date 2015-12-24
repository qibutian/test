package com.means.foods.my;

import org.json.JSONException;
import org.json.JSONObject;

import in.srain.cube.views.ptr.PtrFrameLayout;
import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.UserLocation;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
		adapter.addField(new FieldMap("show_type", R.id.show_type) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {

				JSONObject data = (JSONObject) jo;

				itemV.findViewById(R.id.point).setVisibility(
						JSONUtil.getInt(data, "status") == 0 ? View.VISIBLE
								: View.GONE);
				// TODO Auto-generated method stub
				return o;
			}
		});
		listV.setAdapter(adapter);

		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent it = new Intent(self, MsgDetailActivity.class);
				JSONObject data = adapter.getTItem(position);
				it.putExtra("jo", data.toString());
				startActivity(it);
				resetMsg(JSONUtil.getString(data, "msg_id"));
				try {
					data.put("status", 1);
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private void resetMsg(String msgId) {
		User user = User.getInstance();
		DhNet net = new DhNet(API.resetMsg);
		net.addParam("uid ", user.getUid());
		net.addParam("token", user.getToken());
		net.addParam("msgId", msgId);
		net.doGet(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {

				}
			}
		});
	}

}
