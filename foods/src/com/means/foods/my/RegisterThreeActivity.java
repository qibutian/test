package com.means.foods.my;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import com.means.foods.R;
import com.means.foods.R.layout;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.RegisterEB;

import de.greenrobot.event.EventBus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterThreeActivity extends FoodsBaseActivity {
	private String phone="";
	private String verificationCode="";
	
	private EditText pswdEt;
	private Button finishBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_three);
	}

	@Override
	public void initView() {
		phone = getIntent().getStringExtra("phone");
		verificationCode = getIntent().getStringExtra("verificationCode");
		setTitle("注册3/3");
		
		pswdEt = (EditText) findViewById(R.id.password);
		finishBtn = (Button) findViewById(R.id.finish);
		
		finishBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				register();
			}
		});
	}
	
	private void register(){
		String password = pswdEt.getText().toString();
		if (TextUtils.isEmpty(password)) {
			showToast("请输入您的密码");
			return;
		}
		
		DhNet net = new DhNet(API.register);
		net.addParam("phone", phone);
		net.addParam("password", password);
		net.addParam("verificationCode", verificationCode);
		net.doPostInDialog(new NetTask(self) {
			
			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					Intent it = new Intent(self, RegisterFinishActivity.class);
					startActivity(it);
					finishWithoutAnim();
					EventBus.getDefault().post(new RegisterEB());
				}
			}
		});
		
		
	}
}
