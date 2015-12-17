package com.means.foods.my;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;

public class ResetPswdOneActivity extends FoodsBaseActivity {

	EditText phoneE;

	public static final int Edit = 1001;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_pswd_one);
	}

	@Override
	public void initView() {
		setTitle("重置密码1/3");
		phoneE = (EditText) findViewById(R.id.phone);

		findViewById(R.id.next).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(phoneE.getText().toString())) {
					showToast("请输入手机号码!");
				} else {
					Intent it = new Intent(self, ResetPswdTwoActivity.class);
					it.putExtra("phone", phoneE.getText().toString());
					startActivityForResult(it, Edit);
				}
			}
		});


	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == Edit) {
			finish();
		}
	}
}
