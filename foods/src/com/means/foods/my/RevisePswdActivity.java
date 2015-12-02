package com.means.foods.my;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;
import com.means.foods.view.Utils;

/**
 * 修改密码
 * 
 * @author Administrator
 * 
 */
public class RevisePswdActivity extends FoodsBaseActivity implements
		OnClickListener {
	EditText pswd;
	ImageView clear;
	User user;
	Intent myIntent;
	TextView right_text;
	String oldPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revise_pswd);
	}

	@Override
	public void initView() {
		setTitle("修改密码");
		user = User.getInstance();
		myIntent = getIntent();
		oldPwd = myIntent.getStringExtra("oldPwd");
		pswd = (EditText) findViewById(R.id.pswd);
		clear = (ImageView) findViewById(R.id.clear);
		right_text = (TextView) findViewById(R.id.right_text);
		right_text.setText("保存");
		right_text.setOnClickListener(this);
		clear.setOnClickListener(this);
		pswd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					clear.setVisibility(View.VISIBLE);
				} else {
					clear.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				if (start > 0) {
					right_text.setVisibility(View.VISIBLE);
				}else{
					right_text.setVisibility(View.GONE);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.clear:
			pswd.setText("");
			break;
		case R.id.right_text:
			if (isModify()) {
				if (TextUtils.isEmpty(pswd.getText().toString())) {
					showToast("请输入密码");
					return;
				}
				if (oldPwd.equals(pswd.getText().toString())) {
					showToast("新密码和旧密码不能一致，请重新输入");
					return;
				}

				if (!Utils.isValidPassword(pswd.getText().toString())) {
					showToast("密码为6-15位字母和数字的组合");
					return;
				}

				if (pswd.length() < 6 || pswd.length() > 20) {
					showToast("密码长度应在6-20之间，请重新输入");
					return;
				}
				setContent();
			} else {
				finish();
			}

			break;

		default:
			break;
		}
	}

	/**
	 * 判断资料是否有改动
	 */
	private boolean isModify() {
		String name = pswd.getText().toString();
		if (TextUtils.isEmpty(name)) {
			return false;
		}
		if (!pswd.getText().toString()
				.equals(myIntent.getStringExtra("oldPwd"))) {
			return true;
		}
		return false;
	}

	public void setContent() {
		DhNet netName = new DhNet(API.editPwd);
		netName.addParam("uid", "667 ");
		netName.addParam("oldPwd", myIntent.getStringExtra("oldPwd"));
		netName.addParam("newPwd", pswd.getText().toString());
		netName.addParam("newPwd2", pswd.getText().toString());
		netName.doPostInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				// TODO Auto-generated method stub
				if (response.isSuccess()) {
					 showToast("密码修改成功");
					finish();
				}
			}
		});

	}

}
