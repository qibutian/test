package com.means.foods.my;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;

/**
 * 修改姓名/用户名
 * 
 * @author Administrator
 * 
 */
public class ReviseNameActivity extends FoodsBaseActivity implements
		OnClickListener {
	EditText newnickname;
	ImageView clear;
	Intent myIntent;
	User user;
	TextView right_text;
	View backV;
	TextView tishi;
	String start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revise_name);
	}

	@Override
	public void initView() {
		user = User.getInstance();
		myIntent = getIntent();
		tishi = (TextView) findViewById(R.id.tishi);
		start = myIntent.getStringExtra("start");
		if ("1".equals(start)) {
			setTitle("修改昵称");
			tishi.setVisibility(View.VISIBLE);
		} else {
			setTitle("修改姓名");
			tishi.setVisibility(View.GONE);
		}
		backV = findViewById(R.id.backLayout);

		newnickname = (EditText) findViewById(R.id.Newnickname);
		clear = (ImageView) findViewById(R.id.clear);
		right_text = (TextView) findViewById(R.id.right_text);
		right_text.setText("保存");
		newnickname.setText(myIntent.getStringExtra("name"));
		if (!"".equals(myIntent.getStringExtra("email"))) {
			clear.setVisibility(View.VISIBLE);
		}
		if (backV != null) {
			backV.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					finish();
				}
			});
		}
		right_text.setOnClickListener(this);
		newnickname.addTextChangedListener(new TextWatcher() {

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
		if ("2".equals(start)) {// 修改姓名
			DhNet netName = new DhNet(API.editName);
			netName.addParam("uid", User.getInstance().uid);
			netName.addParam("token", User.getInstance().token);
			netName.addParam("truename", newnickname.getText().toString());
			netName.doPostInDialog(new NetTask(self) {

				@Override
				public void doInUI(Response response, Integer transfer) {
					// TODO Auto-generated method stub
					if (response.isSuccess()) {
						Intent intent = getIntent();
						intent.putExtra("name", newnickname.getText()
								.toString());
						setResult(self.RESULT_OK, intent);
						 showToast("姓名修改成功");
						finish();
						// JSONObject jo = response.jSONFromData();
					}
				}
			});
		} else {// 修改昵称
			if (newnickname.getText().toString().length() > 4
					&& newnickname.getText().toString().length() < 13) {
				DhNet net = new DhNet(API.editNickName);
				net.addParam("uid", User.getInstance().uid);
				net.addParam("token", User.getInstance().token);
				net.addParam("nickname", newnickname.getText().toString());
				net.doPostInDialog(new NetTask(self) {

					@Override
					public void doInUI(Response response, Integer transfer) {
						// TODO Auto-generated method stub
						if (response.isSuccess()) {
							Intent intent = getIntent();
							intent.putExtra("nickname", newnickname.getText()
									.toString());
							setResult(self.RESULT_OK, intent);
							 showToast("昵称修改成功");
							finish();
							// JSONObject jo = response.jSONFromData();
						}
					}
				});
			} else {
				showToast("昵称不正确，请按提示填写~");
			}

		}

	}

	/**
	 * 禁止特殊字符
	 * 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public  boolean StringFilter(String str)  {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; // 要过滤掉的字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if(m.find()){
            return false;
        }
		return true;
	}

	/**
	 * 判断资料是否有改动
	 */
	private boolean isModify() {
		String name = newnickname.getText().toString();
		if (TextUtils.isEmpty(name)) {
			return false;
		}
		if (!newnickname.getText().toString()
				.equals(myIntent.getStringExtra("name"))) {
			return true;
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.clear:
			newnickname.setText("");
			break;
		case R.id.right_text:
			if (isModify()) {
				if (!StringFilter(newnickname.getText().toString())) {
					showToast("姓名不允许输入特殊符号！");
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

}
