package com.means.foods.cate;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.means.foods.R;
import com.means.foods.adapter.BigImageAdapter;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;
import com.means.foods.view.FoodsGallery;

/**
 * 餐厅详情
 * 
 * @author dell
 * 
 */
public class RestaurantDetailsActivity extends FoodsBaseActivity implements
		OnClickListener {

	// 立即预定按钮
	View reservedV;

	// 餐厅id
	String store_id;
	/**
	 * 	cuisine 人均下面的介绍
	 *	is_collect 是否收藏
	 *	name 名称
	 *	mean_money 人均
	 *	all_pic 轮播图
	 *	reason 介绍（地址上面）
	 *	address 地址
	 *	distance 距市中心
	 *	travel 周边
	 *	trafficroute 交通
	 *	hours 营业时间
	 *	txt_info 简介
	 *	feature 特色
	 *	chef 主厨介绍
	 *	tips 温馨提示
	 *	long/lat 经纬
	 */
	TextView mean_moneyT,cuisineT,nameT,reasonT,addressT,distanceT,travelT,trafficrouteT,hoursT,txt_infoT,featureT,chefT,tipsT;

	FoodsGallery mViewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_details);
	}

	@Override
	public void initView() {
		store_id = getIntent().getStringExtra("store_id");
		setTitle("餐厅详情");
		
		mean_moneyT = (TextView) findViewById(R.id.mean_money);
		cuisineT = (TextView) findViewById(R.id.cuisine);
		nameT = (TextView) findViewById(R.id.name);
		reasonT = (TextView) findViewById(R.id.reason);
		addressT = (TextView) findViewById(R.id.address);
		distanceT = (TextView) findViewById(R.id.distance);
		travelT = (TextView) findViewById(R.id.travel);
		trafficrouteT = (TextView) findViewById(R.id.trafficroute);
		hoursT = (TextView) findViewById(R.id.hours);
		txt_infoT = (TextView) findViewById(R.id.txt_info);
		featureT = (TextView) findViewById(R.id.feature);
		chefT = (TextView) findViewById(R.id.chef);
		tipsT = (TextView) findViewById(R.id.tips);
		
		mViewPager = (FoodsGallery) findViewById(R.id.viewer);
		reservedV = findViewById(R.id.reserved);
		
		reservedV.setOnClickListener(this);

		initData();
	}

	private void initData() {
		DhNet net = new DhNet(API.restaurantDetail);
		net.addParam("store_id", "62");
		net.addParam("uid", User.getInstance().getUid());
//		net.addParam("store_id", store_id);
//		net.addParam("uid", User.getInstance().getUid());
		net.doGet(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					JSONObject jo=response.jSONFromData();
					ViewUtil.bindView(mean_moneyT, "人均￥"+JSONUtil.getString(jo, "mean_money"));
					ViewUtil.bindView(cuisineT, JSONUtil.getString(jo, "cuisine"));
					ViewUtil.bindView(nameT, JSONUtil.getString(jo, "name"));
					ViewUtil.bindView(reasonT, JSONUtil.getString(jo, "reason"));
					ViewUtil.bindView(addressT, JSONUtil.getString(jo, "address"));
					ViewUtil.bindView(distanceT, JSONUtil.getString(jo, "distance"));
					ViewUtil.bindView(travelT, JSONUtil.getString(jo, "travel"));
					ViewUtil.bindView(trafficrouteT, JSONUtil.getString(jo, "trafficroute"));
					ViewUtil.bindView(hoursT, JSONUtil.getString(jo, "hours"));
					ViewUtil.bindView(txt_infoT, JSONUtil.getString(jo, "txt_info"));
					ViewUtil.bindView(featureT, JSONUtil.getString(jo, "feature"));
					ViewUtil.bindView(chefT, JSONUtil.getString(jo, "chef"));
					ViewUtil.bindView(tipsT, JSONUtil.getString(jo, "tips"));
					JSONArray jsc = JSONUtil.getJSONArray(jo, "all_pic");
					 if (jsc != null) {
	                        BigImageAdapter adapter = new BigImageAdapter(self, jsc);
	                        mViewPager.setAdapter(adapter);
	                    }
				}

			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		case R.id.reserved:
			it = new Intent(self, ConfirmDetailsActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}
	}
}
