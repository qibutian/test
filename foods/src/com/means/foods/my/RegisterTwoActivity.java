package com.means.foods.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.LoginEB;
import com.means.foods.bean.RegisterEB;

import de.greenrobot.event.EventBus;

public class RegisterTwoActivity extends FoodsBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_two);
	}

	@Override
	public void initView() {
		setTitle("注册2/3");
		findViewById(R.id.next).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(self, RegisterThreeActivity.class);
				startActivity(it);
				finishWithoutAnim();
				EventBus.getDefault().post(new RegisterEB());
			}
		});
	}

}
