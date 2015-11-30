package com.means.foods.my;

import android.os.Bundle;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;

/**
 * 消息详情
 * 
 * @author Administrator
 * 
 */
public class MsgDetailActivity extends FoodsBaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_detail);
	}

	@Override
	public void initView() {

		setTitle("消息详情");

	}
}
