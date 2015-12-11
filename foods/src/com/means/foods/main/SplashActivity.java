package com.means.foods.main;

import net.duohuo.dhroid.ioc.IocContainer;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;
import com.means.foods.utils.FoodsPerference;

/**
 * 欢迎页
 * 
 * @author Administrator
 * 
 */
public class SplashActivity extends FoodsBaseActivity {
	FoodsPerference per;

	private final Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		per = IocContainer.getShare().get(FoodsPerference.class);
		per.load();
		if (per.isFirst == 0) {
			first();
		} else {

			if (!TextUtils.isEmpty(per.phone) && !TextUtils.isEmpty(per.pswd)) {
				login();
			} else {
				notFirst();
			}
		}
	}

	private void first() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(self, GuidanceActivity.class);
				startActivity(intent);
				per.isFirst = 1;
				per.commit();
				finishWithoutAnim();
			}
		}, 2000);
	}

	private void login() {

		DhNet net = new DhNet(API.login);
		net.addParam("phone", per.phone);
		net.addParam("password", per.pswd);
		// net.addParam("phone", "13852286536");
		// net.addParam("password", "123");
		net.doPostInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					JSONObject jo = response.jSONFromData();
					User user = User.getInstance();
					user.setUid(JSONUtil.getString(jo, "uid"));
					user.setToken(JSONUtil.getString(jo, "pwd"));
					user.setLogin(true);
					notFirst();

					// showToast("登录成功");

					// Intent it = new Intent(self, MainActivity.class);
					// startActivity(it);
					// finishWithoutAnim();
					// 登录成功后发送事件,关闭之前的页面
				}
			}

			@Override
			public void onErray(Response response) {
				// TODO Auto-generated method stub
				super.onErray(response);
				notFirst();
			}
		});
	}

	private void notFirst() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(self, MainActivity.class);
				startActivity(intent);
				finishWithoutAnim();
			}
		}, 1000);
	}
}
