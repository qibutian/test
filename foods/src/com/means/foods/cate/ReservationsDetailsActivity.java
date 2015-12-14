package com.means.foods.cate;

import java.io.File;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.means.foods.FoodsValueFix;
import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;
import com.means.foods.main.WebActivity;
import com.means.foods.utils.DownLoad;
import com.means.foods.view.TouchWebView;

/**
 * 预定详情
 * 
 * @author dell
 * 
 */
public class ReservationsDetailsActivity extends FoodsBaseActivity implements
		OnClickListener {

	// 立即预定按钮
	View reservedV;

	JSONObject jo;

	Button cancleB, editB;

	TouchWebView webV;

	String order_id;

	private String saveDir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reservations_details);
		// saveDir = new File(getCacheDir(), "foods").getPath()+"/";
		// saveDir.mkdirs();
		saveDir = getExternalCacheDir().getPath() + "/foods/";
	}

	@Override
	public void initView() {
		setTitle("预定详情");
		reservedV = findViewById(R.id.reserved);
		reservedV.setOnClickListener(this);
		webV = (TouchWebView) findViewById(R.id.web);
		webV.getSettings().setDefaultTextEncodingName("UTF-8");
		webV.getSettings().setJavaScriptEnabled(true);
		order_id = getIntent().getStringExtra("order_id");
		if (TextUtils.isEmpty(order_id)) {
			String intentjo = getIntent().getStringExtra("jo");
			try {
				jo = new JSONObject(intentjo);
				order_id = JSONUtil.getString(jo, "order_id");
				bindView(jo);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			DhNet net = new DhNet(API.orderDetail);
			net.addParam("uid ", User.getInstance().getUid());
			net.addParam("token  ", User.getInstance().getToken());
			net.addParam("order_id ", order_id);
			net.doGetInDialog(new NetTask(self) {

				@Override
				public void doInUI(Response response, Integer transfer) {
					if (response.isSuccess()) {
						jo = response.jSONFromData();
						bindView(jo);
					}

				}
			});
		}

		cancleB = (Button) findViewById(R.id.cancle);
		editB = (Button) findViewById(R.id.edit);
		cancleB.setOnClickListener(this);
		editB.setOnClickListener(this);
		// ViewUtil.bindView(findViewById(R.id.name), JSONUtil.getString(jo,
		// "store_name"));

	}

	private void bindView(JSONObject jo) {
		webV.loadUrl("http://www.foodies.im/wap.php?g=Wap&c=Food&a=map&address="
				+ JSONUtil.getString(jo, "store_address"));
		ViewUtil.bindView(findViewById(R.id.name),
				JSONUtil.getString(jo, "store_name"));
		ViewUtil.bindView(findViewById(R.id.order_id),
				"订单号  : " + JSONUtil.getString(jo, "order_id"));
		String arrtime = FoodsValueFix.getStandardTime(
				Long.parseLong(JSONUtil.getString(jo, "arrive_time")),
				"yyyy年MM月dd");
		ViewUtil.bindView(findViewById(R.id.date),
				arrtime + JSONUtil.getString(jo, "hour"));
		ViewUtil.bindView(findViewById(R.id.address),
				JSONUtil.getString(jo, "store_address"));
		ViewUtil.bindView(findViewById(R.id.tel),
				JSONUtil.getString(jo, "store_phone"));
		ViewUtil.bindView(findViewById(R.id.tuijian),
				JSONUtil.getString(jo, "store_feature"));
		ViewUtil.bindView(findViewById(R.id.tips),
				JSONUtil.getString(jo, "store_tips"));
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		case R.id.reserved:
			String url = "http://www.foodies.im/wap.php?g=Wap&c=Food&a=html2pdfApi";
			DownLoad downLoad = new DownLoad(self, mhandler, url, saveDir,
					order_id);
			downLoad.start();
			// it = new Intent(self, WebActivity.class);
			// it.putExtra("order_id", order_id);
			// startActivity(it);
			break;

		case R.id.cancel:
			cancleOrder(JSONUtil.getString(jo, "order_id"));
			break;

		case R.id.edit:
			it = new Intent(self, ConfirmDetailsActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}
	}

	private void cancleOrder(String orderId) {
		User user = User.getInstance();
		DhNet net = new DhNet(API.ordercancle);
		net.addParam("orderId", orderId);
		net.addParam("token", user.getToken());
		net.addParam("uid", user.getUid());
		net.doPostInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					showToast("取消成功");
					finish();
				}
			}
		});
	}

	private Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				showToast("下载成功!");
				break;
			case 2:
				showToast("文件不存在,下载失败！");
				break;
			case 3:
				showToast("文件已存在!");
				break;

			case 4:
				showToast("下载中,请稍后！");
				break;
			default:
				break;
			}
		};

	};
}
