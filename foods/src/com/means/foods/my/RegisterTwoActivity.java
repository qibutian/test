package com.means.foods.my;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.RegisterEB;

import de.greenrobot.event.EventBus;

public class RegisterTwoActivity extends FoodsBaseActivity {
	private String phone;
	private String verificationCode = "";

	private EditText codeED;
	private Button get_codeBtn;

	private CountTimer mCountTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_two);
		EventBus.getDefault().register(this);
	}

	@Override
	public void initView() {
		phone = getIntent().getStringExtra("phone");
		verificationCode = getIntent().getStringExtra("verificationCode");
		setTitle("注册2/3");
		codeED = (EditText) findViewById(R.id.code);
		get_codeBtn = (Button) findViewById(R.id.get_code);
		mCountTimer = new CountTimer(60000, 1000);
		mCountTimer.start();
		get_codeBtn.setEnabled(false);
		get_codeBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DhNet net = new DhNet(API.register_Captcha);
				net.addParam("phone", phone);
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

		findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				next();
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	private void next() {
		String code = codeED.getText().toString().trim();
		if (TextUtils.isEmpty(code)) {
			showToast("请输入验证码!");
			return;
		}
		if (!verificationCode.equals(code)) {
			showToast("验证码输入错误");
			return;
		}

		Intent it = new Intent(self, RegisterThreeActivity.class);
		it.putExtra("phone", phone);
		it.putExtra("verificationCode", code);
		startActivity(it);
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

	public void onEventMainThread(RegisterEB registerEb) {
		finish();
	}
}
