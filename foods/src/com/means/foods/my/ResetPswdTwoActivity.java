package com.means.foods.my;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.my.RegisterTwoActivity.CountTimer;

public class ResetPswdTwoActivity extends FoodsBaseActivity {

	EditText codeE;

	public static final int Edit = 1001;

	Button get_codeBtn;

	private CountTimer mCountTimer;

	private String verificationCode = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_pswd_two);
	}

	@Override
	public void initView() {
		setTitle("重置密码2/3");
		codeE = (EditText) findViewById(R.id.code);
		mCountTimer = new CountTimer(60000, 1000);
		findViewById(R.id.next).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				next();
			}
		});

		get_codeBtn = (Button) findViewById(R.id.get_code);
		get_codeBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DhNet net = new DhNet(API.resetPhone);
				net.addParam("phone", getIntent().getStringExtra("phone"));
				net.doPostInDialog(new NetTask(self) {

					@Override
					public void doInUI(Response response, Integer transfer) {
						if (response.isSuccess()) {
							mCountTimer.start();
							get_codeBtn.setEnabled(false);
							// 出参： verificationCode
							JSONObject jo = response.jSONFromData();
							verificationCode = JSONUtil.getString(jo,
									"verificationCode");
						}
					}
				});
			}
		});
	}

	private void next() {
		String code = codeE.getText().toString().trim();
		if (TextUtils.isEmpty(code)) {
			showToast("请输入验证码!");
			return;
		}
		if (!verificationCode.equals(code)) {
			showToast("验证码输入错误");
			return;
		}

		Intent it = new Intent(self, ResetPswdThreeActivity.class);
		it.putExtra("phone", getIntent().getStringExtra("phone"));
		it.putExtra("verificationCode", code);
		startActivityForResult(it, Edit);
	}

	class CountTimer extends CountDownTimer {

		public CountTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			get_codeBtn.setEnabled(false);
			get_codeBtn.setText(millisUntilFinished / 1000 + "s");
		}

		@Override
		public void onFinish() {
			get_codeBtn.setEnabled(true);
			get_codeBtn.setText("重新发送");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == Edit) {
			Intent it = getIntent();
			setResult(Activity.RESULT_OK, it);
			finish();
		}
	}
}
