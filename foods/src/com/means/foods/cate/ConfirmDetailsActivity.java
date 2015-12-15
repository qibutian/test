package com.means.foods.cate;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.UserManager;
import android.provider.SyncStateContract.Constants;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.api.Constant;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.PaySuccessEB;
import com.means.foods.bean.RegisterEB;
import com.means.foods.bean.User;
import com.means.foods.manage.UserInfoManage;
import com.means.foods.manage.UserInfoManage.LoginCallBack;
import com.means.foods.my.ConfirmPaymentActivity;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;

/**
 * 确认详情
 * 
 * @author dell
 * 
 */
public class ConfirmDetailsActivity extends FoodsBaseActivity implements
		OnClickListener {

	User user;

	TextView addressT, telT, nameT, markT;
	RadioGroup rad_sex;
	LinearLayout footerbarLl;

	ImageView add_YearI, sub_YearI, add_MonthI, sub_MonthI, add_DayI, sub_DayI,
			add_NumI, sub_NumI, add_TimeT, sub_TimeT;
	TextView yearT, monthT, dayT, weekT, numT, timeT;
	String store_id = "";

	int year_n;
	int month_n;
	int day_n;
	int people_n;

	Calendar calendar;

	View pre_price_layoutV;

	// 预付款
	double pre_price;

	int now_hour;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_details);
		EventBus.getDefault().register(this);
	}

	@Override
	public void initView() {
		user = User.getInstance();
		setTitle("确认详情");

		Bundle bd = getIntent().getExtras();
		if (null != bd) {
			store_id = bd.getString("store_id");
			// String[] times = bd.getStringArray("times");
		}
		addressT = (TextView) findViewById(R.id.address);
		telT = (TextView) findViewById(R.id.tel);
		nameT = (TextView) findViewById(R.id.name);
		markT = (TextView) findViewById(R.id.mark);
		numT = (TextView) findViewById(R.id.num);
		rad_sex = (RadioGroup) findViewById(R.id.rad_sex);
		footerbarLl = (LinearLayout) findViewById(R.id.footerbar);

		yearT = (TextView) findViewById(R.id.year);
		monthT = (TextView) findViewById(R.id.month);
		dayT = (TextView) findViewById(R.id.day);
		weekT = (TextView) findViewById(R.id.week);
		numT = (TextView) findViewById(R.id.num);
		timeT = (TextView) findViewById(R.id.time);

		add_YearI = (ImageView) findViewById(R.id.add_year);
		sub_YearI = (ImageView) findViewById(R.id.sub_year);
		add_MonthI = (ImageView) findViewById(R.id.add_month);
		sub_MonthI = (ImageView) findViewById(R.id.sub_month);
		add_DayI = (ImageView) findViewById(R.id.add_day);
		sub_DayI = (ImageView) findViewById(R.id.sub_day);
		add_NumI = (ImageView) findViewById(R.id.add_num);
		sub_NumI = (ImageView) findViewById(R.id.sub_num);
		add_TimeT = (ImageView) findViewById(R.id.add_time);
		sub_TimeT = (ImageView) findViewById(R.id.sub_time);

		add_YearI.setOnClickListener(this);
		sub_YearI.setOnClickListener(this);
		add_MonthI.setOnClickListener(this);
		sub_MonthI.setOnClickListener(this);
		add_DayI.setOnClickListener(this);
		sub_DayI.setOnClickListener(this);
		add_NumI.setOnClickListener(this);
		sub_NumI.setOnClickListener(this);
		add_TimeT.setOnClickListener(this);
		sub_TimeT.setOnClickListener(this);

		footerbarLl.setOnClickListener(this);
		pre_price = getIntent().getDoubleExtra("price", 0);
		pre_price_layoutV = findViewById(R.id.pre_price_layout);
		ViewUtil.bindView(findViewById(R.id.price), pre_price);

		if (pre_price != 0) {
			pre_price_layoutV.setVisibility(View.VISIBLE);
			ViewUtil.bindView(findViewById(R.id.pre_price), "￥" + pre_price);
		}

		initData();

	}

	// 初始化页面数据(年,月,日,星期,人数)
	private void initData() {
		calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		year_n = calendar.get(Calendar.YEAR);
		month_n = calendar.get(Calendar.MONTH) + 1;
		day_n = calendar.get(Calendar.DATE);
		weekT.setText(getWeekOfDate());
		setDataView();
		people_n = 1;
		numT.setText(people_n + "");
		now_hour = calendar.get(Calendar.HOUR_OF_DAY);
		int next_hour = now_hour + 1 < 24 ? now_hour + 1 : 0;
		timeT.setText(now_hour + ":00" + "-" + next_hour + ":00");
	}

	// 更新view
	private void setDataView() {
		yearT.setText(year_n + "年");
		monthT.setText(month_n + "月");
		dayT.setText(day_n + "日");
		weekT.setText(getWeekOfDate());
	}

	// 获取当前时间区域 更新text
	private void setTimes(int t) {
		now_hour = now_hour + t;
		now_hour = now_hour < 24 ? now_hour : 0;
		now_hour = now_hour >= 0 ? now_hour : 23;
		int next_hour = now_hour + 1 < 24 ? now_hour + 1 : 0;
		timeT.setText(now_hour + ":00-" + next_hour + ":00");
	}

	// 获取更改后的年月日
	private void getData() {
		year_n = calendar.get(Calendar.YEAR);
		month_n = calendar.get(Calendar.MONTH) + 1;
		day_n = calendar.get(Calendar.DATE);
	}

	// 获取星期
	public String getWeekOfDate() {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 确认
		case R.id.footerbar:
			submitOrder();
			break;
		// 年份+1
		case R.id.add_year:
			calendar.add(Calendar.YEAR, +1);
			getData();
			setDataView();
			break;
		// 年份-1
		case R.id.sub_year:
			calendar.add(Calendar.YEAR, -1);
			getData();
			setDataView();
			break;
		// 月份+1
		case R.id.add_month:
			calendar.add(Calendar.MONTH, +1);
			getData();
			setDataView();
			break;
		// 月份-1
		case R.id.sub_month:
			calendar.add(Calendar.MONTH, -1);
			getData();
			setDataView();
			break;
		// 日期+1
		case R.id.add_day:
			calendar.add(Calendar.DATE, +1);
			getData();
			setDataView();
			break;
		// 日期-1
		case R.id.sub_day:
			calendar.add(Calendar.DATE, -1);
			getData();
			setDataView();
			break;
		// 人数+1
		case R.id.add_num:
			people_n++;
			numT.setText(people_n + "");
			break;
		// 人数-1
		case R.id.sub_num:
			people_n--;
			if (people_n < 1) {
				people_n = 1;
			}
			numT.setText(people_n + "");
			break;
		// 下一组时间
		case R.id.add_time:
			setTimes(1);
			break;
		// 上一组时间
		case R.id.sub_time:
			setTimes(-1);
			break;

		default:
			break;
		}
	}

	public void onEventMainThread(PaySuccessEB registerEb) {
		finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	// 提交订单
	private void submitOrder() {
		final String tel = telT.getText().toString().trim();
		if (TextUtils.isEmpty(tel)) {
			showToast("请输入手机号码");
			return;
		}
		if (tel.length() != 11) {
			showToast("手机号格式不正确");
			return;
		}
		final String name = nameT.getText().toString().trim();
		if (TextUtils.isEmpty(tel)) {
			showToast("请输入姓名");
			return;
		}

		UserInfoManage.getInstance().checkLogin(self, new LoginCallBack() {

			@Override
			public void onisLogin() {
				String mark = markT.getText().toString().trim();

				DhNet net = new DhNet(API.addOrder);
				net.addParam("uid", user.getUid());
				net.addParam("token", user.getToken());
				net.addParam("store_id", store_id);
				net.addParam("date", year_n + "-" + month_n + "-" + day_n);
				net.addParam("hour", timeT.getText().toString());
				net.addParam("num", numT.getText().toString());
				net.addParam("sex",
						rad_sex.getCheckedRadioButtonId() == R.id.rad_man ? "1"
								: "2");
				net.addParam("tel", tel);
				net.addParam("name", name);
				net.addParam("mark", mark);
				net.doPostInDialog(new NetTask(self) {

					@Override
					public void doInUI(Response response, Integer transfer) {
						if (response.isSuccess()) {
							// order_id
							showToast("订单提交成功");
							if (pre_price != 0) {
								JSONObject json = response.jSONFromData();
								Intent it = new Intent(self,
										ConfirmPaymentActivity.class);
								it.putExtra("order_id",
										JSONUtil.getString(json, "order_id"));
								it.putExtra("name",
										getIntent().getStringExtra("name"));
								it.putExtra("price",
										JSONUtil.getDouble(json, "price"));
								startActivity(it);
							} else {
								Intent it = new Intent(self,
										ReservationsDetailsActivity.class);
								JSONObject json = response.jSONFromData();
								it.putExtra("order_id",
										JSONUtil.getString(json, "order_id"));
								startActivity(it);
								finish();
							}
						}
					}
				});
			}

			@Override
			public void onLoginFail() {
				// TODO Auto-generated method stub

			}
		});

	}

}
