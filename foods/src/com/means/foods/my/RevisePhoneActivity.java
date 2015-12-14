package com.means.foods.my;

import org.json.JSONObject;

import net.duohuo.dhroid.ioc.IocContainer;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
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
import com.means.foods.bean.MyIndexEB;
import com.means.foods.bean.User;
import com.means.foods.my.RegisterTwoActivity.CountTimer;
import com.means.foods.utils.FoodsPerference;

import de.greenrobot.event.EventBus;

/**
 * 修改绑定手机号
 * 
 * @author Administrator
 * 
 */
public class RevisePhoneActivity extends FoodsBaseActivity implements
		OnClickListener {

	FoodsPerference per;
	User user;
	EditText verificationEt,telEt;
	Button get_codeBtn, finishBtn;
	
	private CountTimer mCountTimer;
	String verificationCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revise_phone);
	}

	@Override
	public void initView() {
		per = IocContainer.getShare().get(FoodsPerference.class);
		per.load();
		user = User.getInstance();
		setTitle("修改手机号");
		mCountTimer = new CountTimer(60000, 1000);
		verificationEt = (EditText) findViewById(R.id.et_verification_code);
		telEt = (EditText) findViewById(R.id.tel);
		get_codeBtn = (Button) findViewById(R.id.tv_get_code);
		finishBtn = (Button) findViewById(R.id.finish);

		get_codeBtn.setOnClickListener(this);
		finishBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_get_code:
			getVerificationCode();
			break;
		case R.id.finish:
			revisePhone();
			break;

		default:
			break;
		}
	}
	
	//获取验证码
	private void getVerificationCode(){
		
		final String tel = telEt.getText().toString().trim();
		if (TextUtils.isEmpty(tel)) {
			showToast("手机号不能为空");
			return;
		}
		if (tel.length()!=11) {
			showToast("手机格式不正确");
			return;
		}
		
		DhNet net = new DhNet(API.revisePhoneCode);
		net.addParam("phone", tel);
		net.addParam("password", per.getPswd());
		net.addParam("uid", user.getUid());
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
	
	//修改手机号
	private void revisePhone(){
		String code = verificationEt.getText().toString().trim();
		if (TextUtils.isEmpty(code)) {
			showToast("验证码不能为空");
			return;
		}
		if (!verificationCode.equals(code)) {
			showToast("验证码输入错误");
			return;
		}
		final String tel = telEt.getText().toString().trim();
		if (TextUtils.isEmpty(tel)) {
			showToast("手机号不能为空");
			return;
		}
		if (tel.length()!=11) {
			showToast("手机格式不正确");
			return;
		}
		
		DhNet net = new DhNet(API.revisePhone);
		net.addParam("phone", tel);
		net.addParam("password", per.getPswd());
		net.addParam("uid", user.getUid());
		net.addParam("verificationCode", code);
		net.doPostInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					per.setPhone(tel);
					per.commit();
					Intent intent = new Intent();
					intent.putExtra("tel", tel);
					setResult(RESULT_OK, intent);
					showToast("修改成功");
					finish();
				}
			}
		});
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
}
