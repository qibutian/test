package com.means.foods.my;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.srain.cube.views.ptr.PtrFrameLayout;
import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.adapter.PSAdapter;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.UserLocation;
import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.means.foods.FoodsValueFix;
import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.CancelOrderEB;
import com.means.foods.bean.User;
import com.means.foods.cate.ReservationsDetailsActivity;
import com.means.foods.utils.FoodsUtils;
import com.means.foods.view.RefreshListViewAndMore;
import com.means.foods.view.pop.SharePop;
import com.means.foods.view.pop.SharePop.ShareResultListener;

import de.greenrobot.event.EventBus;

public class MyReservationsListActivity extends FoodsBaseActivity {

	PtrFrameLayout mPtrFrame;

	ListView listV;

	LayoutInflater mLayoutInflater;

	ListView contentListV;

	NetJSONAdapter adapter;

	int CANCEL = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myreservations_list);
		EventBus.getDefault().register(this);
	}

	@Override
	public void initView() {
		setTitle("我的预定");
		listV = (ListView) findViewById(R.id.listview);
		// 设置空的emptyView
		// listV.setEmptyView(LayoutInflater.from(self).inflate(
		// R.layout.list_nomal_emptyview, null));

		String url = API.orderList;
		adapter = new NetJSONAdapter(url, self, R.layout.item_reservations);
		adapter.fromWhat("data");
		adapter.addparam("uid", User.getInstance().getUid());
		adapter.addparam("token", User.getInstance().getToken());
		adapter.addparam("cityName", getIntent().getStringExtra("cityName"));
		adapter.addField("dateline", R.id.date, "time");
		adapter.addField(new FieldMap("pay_money", R.id.price) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {
				// TODO Auto-generated method stub
				return "预付定金￥" + o;
			}
		});
		adapter.addField("order_status", R.id.order_status);

		adapter.addField("store_name", R.id.store_name);
		adapter.addField(new FieldMap("order_id", R.id.order_id) {

			@Override
			public Object fix(View itemV, final Integer position,
					final Object o, Object jo) {
				final JSONObject data = (JSONObject) jo;
				TextView arrarrive_timeT = (TextView) itemV
						.findViewById(R.id.arrive_time);
				String arrtime = FoodsValueFix.getStandardTime(
						Long.parseLong(JSONUtil.getString(data, "arrive_time")),
						"yyyy年MM月dd");
				arrarrive_timeT.setText(arrtime + " "
						+ JSONUtil.getString(data, "hour" + "") + " "
						+ JSONUtil.getString(data, "num") + "人");

				View cancelV = itemV.findViewById(R.id.cancel);

				cancelV.setEnabled(JSONUtil.getInt(data, "canCancel") == 1 ? true
						: false);
				cancelV.setBackgroundResource(JSONUtil
						.getInt(data, "canCancel") == 1 ? R.drawable.reservat_oval
						: R.drawable.btn_code_grey_n);

				cancelV.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent it = new Intent(self, CancelOerderActivity.class);
						it.putExtra("order_id",
								JSONUtil.getString(data, "order_id"));
						it.putExtra("price",
								JSONUtil.getString(data, "pay_money"));
						startActivity(it);
					}
				});

				itemV.findViewById(R.id.share).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								SharePop pop = new SharePop(self);
								pop.setOnShareResultListener(new ShareResultListener() {

									@Override
									public void onResult(int result) {
										FoodsUtils.wechatShare(result, self,
												JSONUtil.getString(data,
														"store_name"),
												JSONUtil.getString(data,
														"store_feature"),
												JSONUtil.getString(data,
														"store_id"));
									}
								});
								pop.show();
							}
						});

				// TODO Auto-generated method stub
				return "订单号  :  " + o;
			}
		});

		listV.setAdapter(adapter);
		listV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent it = new Intent(self, ReservationsDetailsActivity.class);
				JSONObject jo = (JSONObject) adapter.getTItem(position);
				it.putExtra("jo", jo.toString());
				startActivity(it);
			}
		});
		adapter.showNextInDialog();
	}

	public void onEventMainThread(CancelOrderEB myIndexEB) {
		finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}
