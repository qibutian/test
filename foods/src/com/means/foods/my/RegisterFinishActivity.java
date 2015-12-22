package com.means.foods.my;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.LoginEB;
import com.means.foods.bean.RegisterEB;
import com.means.foods.bean.User;
import com.means.foods.main.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class RegisterFinishActivity extends FoodsBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_finish);
	}

	@Override
	public void initView() {
		setTitle("注册完成");
		findViewById(R.id.edit_info).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(self, EditinfoActivity.class);
				it.putExtra("type", "register");
				startActivity(it);
				finishWithoutAnim();
			}
		});

		findViewById(R.id.next).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				User user = User.getInstance();
				if (user.isIslogout()) {
					Intent it = new Intent(self, MainActivity.class);
					startActivity(it);
					finishWithoutAnim();
				} else {
					Intent it = new Intent(self, MainActivity.class);
					startActivity(it);
					finishWithoutAnim();
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
