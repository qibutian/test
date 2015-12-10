package com.means.foods.my;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;

public class ReviseEmailActivity extends FoodsBaseActivity implements
		OnClickListener {
	EditText newemail;
	ImageView clear;
	Intent myIntent;
	User user;
	TextView right_text;
	View backV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revise_email);
	}

	@Override
	public void initView() {
		setTitle("修改邮箱");
		user = User.getInstance();
		backV = findViewById(R.id.backLayout);
		myIntent = getIntent();
		newemail = (EditText) findViewById(R.id.newemail);
		clear = (ImageView) findViewById(R.id.clear);
		right_text = (TextView) findViewById(R.id.right_text);
		right_text.setText("保存");
		newemail.setText(myIntent.getStringExtra("email"));
		if (!"".equals(myIntent.getStringExtra("email"))) {
			clear.setVisibility(View.VISIBLE);
		}
		if (backV != null) {
			backV.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
//					if (isModify()) {
//						if (isEmail(newemail.getText().toString())) {
//							setContent();
//
//						} else {
//							if (newemail.getText().toString().isEmpty()) {
//								finish();
//							} else {
//								showToast("邮箱格式不正确");
//							}
//						}
//					} else {
						finish();
//					}
				}
			});
		}
		right_text.setOnClickListener(this);
		newemail.addTextChangedListener(new TextWatcher() {

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
		clear.setOnClickListener(this);
	}

	public void setContent() {
		DhNet net = new DhNet(
				API.editEmail);
		net.addParam("uid", User.getInstance().uid);
		net.addParam("token", User.getInstance().token);
		net.addParam("email", newemail.getText().toString());
		net.doPostInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				// TODO Auto-generated method stub
				if (response.isSuccess()) {
					Intent intent = getIntent();
                    intent.putExtra("email", newemail.getText().toString());
                    setResult(self.RESULT_OK, intent);
                    showToast("邮箱修改成功");
					finish();
					// JSONObject jo = response.jSONFromData();
				}
			}
		});
	}

	// 判断email格式是否正确
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	/**
	 * 判断资料是否有改动
	 */
	private boolean isModify() {
		String name = newemail.getText().toString();
		if (TextUtils.isEmpty(name)) {
			return false;
		}
		if (!newemail.getText().toString()
				.equals(myIntent.getStringExtra("email"))) {
			return true;
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			if (isModify()) {
//				if (isEmail(newemail.getText().toString())) {
//					setContent();
//				} else {
//					if (newemail.getText().toString().isEmpty()) {
						finish();
//					} else {
//						showToast("邮箱格式不正确");
//					}
//				}
//			} else {
//				finish();
//			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.clear:
			newemail.setText("");
			break;
		case R.id.right_text:
			if (isModify()) {
				if (isEmail(newemail.getText().toString())) {
					setContent();

				} else {
					if (newemail.getText().toString().isEmpty()) {
						finish();
					} else {
						showToast("邮箱格式不正确");
					}
				}
			} else {
				finish();
			}
			break;

		default:
			break;
		}

	}
}
