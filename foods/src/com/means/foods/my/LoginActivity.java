package com.means.foods.my;

import org.json.JSONObject;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.LoginEB;
import com.means.foods.bean.RegisterEB;
import com.means.foods.bean.User;
import com.means.foods.main.MainActivity;

import de.greenrobot.event.EventBus;

/**
 * 登录页
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends FoodsBaseActivity implements
		View.OnClickListener {

	Button loginBtn;

	TextView registerT, forget_passwordT;

	EditText usernameEt, pswdEt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		EventBus.getDefault().register(this);
	}

	@Override
	public void initView() {
		setTitle("登录");
		loginBtn = (Button) findViewById(R.id.login);
		registerT = (TextView) findViewById(R.id.register);
		forget_passwordT = (TextView) findViewById(R.id.forget_password);
		usernameEt = (EditText) findViewById(R.id.username);
		pswdEt = (EditText) findViewById(R.id.pswd);

		loginBtn.setOnClickListener(this);
		registerT.setOnClickListener(this);
		forget_passwordT.setOnClickListener(this);

	}

	public void onEventMainThread(RegisterEB registerEb) {
		finishWithoutAnim();
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		case R.id.login:
			login();
			break;
		case R.id.register:
			it = new Intent(self, RegisterOneActivity.class);
			startActivity(it);
			break;
		case R.id.forget_password:
			// it = new Intent(self, LoginActivity.class);
			// startActivity(it);
			break;

		default:
			break;
		}
	}

	private void login() {
		String account = usernameEt.getText().toString().trim();
		if (TextUtils.isEmpty(account)) {
			showToast("用户名不能为空!");
			return;
		}
		String pswd = pswdEt.getText().toString().trim();
		if (TextUtils.isEmpty(account)) {
			showToast("密码不能为空!");
			return;
		}
		// MD5Util.string2MD5(pswd);
		DhNet net = new DhNet(API.login);
		net.addParam("phone", account);
		net.addParam("password", pswd);
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

					showToast("登录成功");

					Intent it = new Intent(self, MainActivity.class);
					startActivity(it);
					finishWithoutAnim();
					// 登录成功后发送事件,关闭之前的页面
					EventBus.getDefault().post(new LoginEB());
				}
			}
		});
	}

}
