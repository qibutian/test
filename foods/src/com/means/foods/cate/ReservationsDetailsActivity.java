package com.means.foods.cate;

import java.io.File;

import net.duohuo.dhroid.dialog.IDialog;
import net.duohuo.dhroid.ioc.IocContainer;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.means.foods.FoodsValueFix;
import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.api.Constant;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.CancelOrderEB;
import com.means.foods.bean.User;
import com.means.foods.my.CancelOerderActivity;
import com.means.foods.utils.DownLoad;
import com.means.foods.utils.FoodsUtils;
import com.means.foods.view.TouchWebView;
import com.means.foods.view.pop.SharePop;
import com.means.foods.view.pop.SharePop.ShareResultListener;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;

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

	View shareV;

	int CANCEL = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reservations_details);
		saveDir = getExternalCacheDir().getPath() + "/foods/";
		EventBus.getDefault().register(this);
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
		if (JSONUtil.getInt(jo, "can_edit") != 1) {
			editB.setEnabled(false);
			editB.setBackgroundResource(R.drawable.btn_code_grey_n);
		}
		if (JSONUtil.getInt(jo, "can_cancel") != 1) {
			cancleB.setEnabled(false);
		}
		cancleB.setOnClickListener(this);
		editB.setOnClickListener(this);
		shareV = findViewById(R.id.share);
		shareV.setOnClickListener(this);
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

		ViewUtil.bindView(findViewById(R.id.order_status),
				JSONUtil.getString(jo, "order_status"));

		String arrtime1 = FoodsValueFix.getStandardTime(
				Long.parseLong(JSONUtil.getString(jo, "arrive_time")),
				"yyyy-MM-dd");
		ViewUtil.bindView(findViewById(R.id.order_des), arrtime1 + "  "
				+ "预付定金￥" + JSONUtil.getString(jo, "pay_money"));
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

			it = new Intent(self, CancelOerderActivity.class);
			it.putExtra("order_id", order_id);
			it.putExtra("price", JSONUtil.getString(jo, "pay_money"));
			startActivity(it);
			// cancleOrder(JSONUtil.getString(jo, "order_id"));
			break;

		case R.id.edit:
			it = new Intent(self, ConfirmDetailsActivity.class);
			it.putExtra("order_id", order_id);
			it.putExtra("jo", jo.toString());
			it.putExtra("orderType", "modifyOrder");
			startActivity(it);
			break;

		case R.id.share:
			SharePop pop = new SharePop(self);
			pop.setOnShareResultListener(new ShareResultListener() {

				@Override
				public void onResult(int result) {
					FoodsUtils.wechatShare(result, self,
							JSONUtil.getString(jo, "store_name"),
							JSONUtil.getString(jo, "store_feature"),
							JSONUtil.getString(jo, "store_id"));
				}
			});
			pop.show();
			break;

		default:
			break;
		}
	}

	private Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				showToast("下载成功!");
				try {
					Intent intent = getWordFileIntent(saveDir + order_id
							+ ".pdf");
					startActivity(intent);
				} catch (ActivityNotFoundException e) {
					// 检测到系统尚未安装OliveOffice的apk程序
					// 请先到www.olivephone.com/e.apk下载并安装
					IocContainer.getShare().get(IDialog.class)
							.showToastShort(self, "检测到系统尚未安装打开Word文档的程序");
					Log.i("TAG", "检测到系统尚未安装打开Word文档的程序");
				}

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

	public Intent getWordFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		// intent.setClassName("cn.wps.moffice",
		// "cn.wps.moffice.documentmanager.PreStartActivity");
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/pdf");
		return intent;
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
