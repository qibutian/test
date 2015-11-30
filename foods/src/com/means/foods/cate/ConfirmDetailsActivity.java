package com.means.foods.cate;

import com.means.foods.R;
import com.means.foods.R.layout;
import com.means.foods.base.FoodsBaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 确认详情
 * @author dell
 *
 */
public class ConfirmDetailsActivity extends FoodsBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_details);
	}

	@Override
	public void initView() {
		setTitle("确认详情");
	}
}
