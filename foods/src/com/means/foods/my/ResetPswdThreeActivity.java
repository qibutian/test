package com.means.foods.my;

import net.duohuo.dhroid.ioc.IocContainer;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.LoginEB;
import com.means.foods.bean.RegisterEB;
import com.means.foods.bean.User;
import com.means.foods.utils.FoodsPerference;

import de.greenrobot.event.EventBus;

public class ResetPswdThreeActivity extends FoodsBaseActivity {

	EditText pswdE;

	FoodsPerference per;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_pswd_three);
		per = IocContainer.getShare().get(FoodsPerference.class);
		per.load();
	}

	@Override
	public void initView() {
		setTitle("重置密码3/3");
		pswdE = (EditText) findViewById(R.id.pswd);
		findViewById(R.id.ok).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edit();
			}
		});
	}

	private void edit() {
		String password = pswdE.getText().toString();
		if (TextUtils.isEmpty(password)) {
			showToast("请输入您的密码");
			return;
		}

		DhNet net = new DhNet(API.resetPhone);
		net.addParam("phone", getIntent().getStringExtra("phone"));
		net.addParam("password", password);
		net.addParam("verificationCode",
				getIntent().getStringExtra("verificationCode"));
		net.doPostInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					showToast("重置成功!");
					Intent it = getIntent();
					setResult(Activity.RESULT_OK, it);
					finish();
				}
			}
		});

	}
}
