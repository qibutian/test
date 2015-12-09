package com.means.foods.main;

import net.duohuo.dhroid.ioc.IocContainer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.utils.FoodsPerference;

/**
 * 欢迎页
 * 
 * @author Administrator
 * 
 */
public class SplashActivity extends FoodsBaseActivity {
	FoodsPerference per;

	private final Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		per = IocContainer.getShare().get(FoodsPerference.class);
		per.load();
		if (per.isFirst == 0) {
			first();
		} else {
			notFirst();
		}
	}

	private void first() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(self, GuidanceActivity.class);
				startActivity(intent);
				per.isFirst = 1;
				per.commit();
				finishWithoutAnim();
			}
		}, 2000);
	}

	private void notFirst() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(self, ReadyActivity.class);
				startActivity(intent);
				finishWithoutAnim();
			}
		}, 2000);

	}
}
