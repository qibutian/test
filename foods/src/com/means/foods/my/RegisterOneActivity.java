package com.means.foods.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.LoginEB;
import com.means.foods.bean.RegisterEB;

import de.greenrobot.event.EventBus;

public class RegisterOneActivity extends FoodsBaseActivity {

	EditText phoneEt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_one);
		EventBus.getDefault().register(this);
	}

	@Override
	public void initView() {
		phoneEt = (EditText) findViewById(R.id.phone);
		
		setTitle("注册1/3");
		findViewById(R.id.invitation).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				VerifyPhone();
			}
		});

		findViewById(R.id.login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(self, LoginActivity.class);
				startActivity(it);
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	public void onEventMainThread(RegisterEB registerEb) {
		finish();
	}
	
	public void onEventMainThread(LoginEB loginEb) {
		finish();
	}
	
	//验证手机号
	private void VerifyPhone(){
		String phone = phoneEt.getText().toString().trim();
		if (TextUtils.isEmpty(phone)) {
            showToast("手机号不能为空");
            return;
        }
        if (phone.length() != 11) {
            showToast("手机号不合法");
            return;
        }
        
        if (!((CheckBox)findViewById(R.id.check)).isChecked()) {
        	showToast("请阅读并同意食客服务条款");
        	return;
		}
        
        Intent it = new Intent(self, RegisterTwoActivity.class);
        it.putExtra("phone", phone);
		startActivity(it);
		
	}

}
