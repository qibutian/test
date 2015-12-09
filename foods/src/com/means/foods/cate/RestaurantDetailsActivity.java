package com.means.foods.cate;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.means.foods.R;
import com.means.foods.adapter.BigImageAdapter;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;
import com.means.foods.utils.FoodsUtils;
import com.means.foods.utils.FoodsUtils.OnCallBack;
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
	 * cuisine 人均下面的介绍 is_collect 是否收藏 name 名称 mean_money 人均 all_pic 轮播图 reason
	 * 介绍（地址上面） address 地址 distance 距市中心 travel 周边 trafficroute 交通 hours 营业时间
	 * txt_info 简介 feature 特色 chef 主厨介绍 tips 温馨提示 long/lat 经纬
	 */
	TextView mean_moneyT, cuisineT, nameT, reasonT, addressT, distanceT,
			travelT, trafficrouteT, hoursT, txt_infoT, featureT, chefT, tipsT;
	LinearLayout like_layout;
	FoodsGallery mViewPager;
	ImageView list_img;

	// 收缩icon: 简介,特色,主厨介绍,温馨提示
	ImageView info_foldI, feature_foldI, chef_foldI, tips_foldI;

	// 是否收藏
	boolean isShowCollect;

	Fold info_fold, feature_fold, chef_fold, tips_fold;

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
		like_layout = (LinearLayout) findViewById(R.id.like_layout);
		list_img = (ImageView) findViewById(R.id.list_img);
		list_img.setImageResource(R.drawable.icon_collect);
		mViewPager = (FoodsGallery) findViewById(R.id.viewer);
		reservedV = findViewById(R.id.reserved);

		info_foldI = (ImageView) findViewById(R.id.info_fold);
		feature_foldI = (ImageView) findViewById(R.id.feature_fold);
		chef_foldI = (ImageView) findViewById(R.id.chef_fold);
		tips_foldI = (ImageView) findViewById(R.id.tips_fold);

		info_fold = new Fold(txt_infoT, info_foldI, false);
		feature_fold = new Fold(featureT, feature_foldI, false);
		chef_fold = new Fold(chefT, chef_foldI, false);
		tips_fold = new Fold(tipsT, tips_foldI, false);

		reservedV.setOnClickListener(this);
		info_foldI.setOnClickListener(this);
		feature_foldI.setOnClickListener(this);
		chef_foldI.setOnClickListener(this);
		tips_foldI.setOnClickListener(this);

		initData();
	}

	private void initData() {
		DhNet net = new DhNet(API.restaurantDetail);
		net.addParam("store_id", store_id);
		net.addParam("uid", User.getInstance().getUid());
		// net.addParam("store_id", store_id);
		// net.addParam("uid", User.getInstance().getUid());
		net.doGet(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					JSONObject jo = response.jSONFromData();
					ViewUtil.bindView(mean_moneyT,
							"人均￥" + JSONUtil.getString(jo, "mean_money"));
					ViewUtil.bindView(cuisineT,
							JSONUtil.getString(jo, "cuisine"));
					ViewUtil.bindView(nameT, JSONUtil.getString(jo, "name"));
					ViewUtil.bindView(reasonT, JSONUtil.getString(jo, "reason"));
					ViewUtil.bindView(addressT,
							JSONUtil.getString(jo, "address"));
					ViewUtil.bindView(distanceT,
							JSONUtil.getString(jo, "distance"));
					ViewUtil.bindView(travelT, JSONUtil.getString(jo, "travel"));
					ViewUtil.bindView(trafficrouteT,
							JSONUtil.getString(jo, "trafficroute"));
					ViewUtil.bindView(hoursT, JSONUtil.getString(jo, "hours"));
					ViewUtil.bindView(txt_infoT,
							JSONUtil.getString(jo, "txt_info"));
					ViewUtil.bindView(featureT,
							JSONUtil.getString(jo, "feature"));
					ViewUtil.bindView(chefT, JSONUtil.getString(jo, "chef"));
					ViewUtil.bindView(tipsT, JSONUtil.getString(jo, "tips"));
					ViewUtil.bindView(findViewById(R.id.cuisine),
							JSONUtil.getString(jo, "cuisine"));
					like_layout.setOnClickListener(new MycollOnClick(jo));
					JSONArray jsc = JSONUtil.getJSONArray(jo, "all_pic");
					isShowCollect = JSONUtil.getInt(jo, "is_collect") == 1 ? true
							: false;
					// System.out.println("收藏状态"+JSONUtil.getInt(jo,
					// "is_collect"));

					// //判断简介为三行以内则不显示
					// ViewTreeObserver vto = txt_infoT.getViewTreeObserver();
					// vto.addOnPreDrawListener(new
					// ViewTreeObserver.OnPreDrawListener() {
					// @Override
					// public boolean onPreDraw() {
					//
					// if (txt_infoT.getLineCount() < 3) {
					// info_foldI.setVisibility(View.GONE);
					// }
					// return true;
					// }
					// });

					if (jsc != null) {
						BigImageAdapter adapter = new BigImageAdapter(self, jsc);
						mViewPager.setAdapter(adapter);
					}
				}

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
			case R.id.like_layout:
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

				break;

			default:
				break;
			}

		}
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		case R.id.reserved:
			it = new Intent(self, ConfirmDetailsActivity.class);
			it.putExtra("store_id", store_id);
//			it.putExtra("times",String[]);
			startActivity(it);
			break;
		case R.id.info_fold:
			textFold(info_fold);
			break;
		case R.id.feature_fold:
			textFold(feature_fold);
			break;
		case R.id.chef_fold:
			textFold(chef_fold);
			break;
		case R.id.tips_fold:
			textFold(tips_fold);
			break;

		default:
			break;
		}
	}

	/**
	 * 活动内容展开收缩
	 */
	private void textFold(Fold fold) {
		if (null == fold.getFoldView() || null == fold.getFoldImg()) {
			return;
		}
		TextView tv = fold.getFoldView();
		ImageView img = fold.getFoldImg();
		if (fold.isFlag()) {
			tv.setEllipsize(TextUtils.TruncateAt.END);// 收缩
			tv.setMaxLines(3);
			img.setImageResource(R.drawable.icon_down);
			fold.setFlag(false);
		} else {
			tv.setEllipsize(null); // 展开
			tv.setMaxLines(100);
			img.setImageResource(R.drawable.icon_brown_top);
			fold.setFlag(true);
		}
	}

	/**
	 * 下拉箭头 进行扩展收缩类 foldId : 控件id foldImg : 点击按钮 flag : true 扩展 flase 收缩
	 * 
	 * @author Administrator
	 * 
	 */
	private class Fold {
		TextView foldView;
		boolean flag = false;
		ImageView foldImg;

		Fold(TextView foldView, ImageView foldImg, boolean flag) {
			this.foldView = foldView;
			this.flag = flag;
			this.foldImg = foldImg;
		}

		public ImageView getFoldImg() {
			return foldImg;
		}

		public void setFoldImg(ImageView foldImg) {
			this.foldImg = foldImg;
		}

		public TextView getFoldView() {
			return foldView;
		}

		public void setFoldView(TextView foldView) {
			this.foldView = foldView;
		}

		public boolean isFlag() {
			return flag;
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}

	}

}
