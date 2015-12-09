package com.means.foods.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.means.foods.R;
import com.means.foods.adapter.SimplePageAdapter;
import com.means.foods.base.FoodsBaseActivity;

/**
 * 引导页
 * 
 * @author Administrator
 * 
 */
public class GuidanceActivity extends FoodsBaseActivity {
	SimplePageAdapter pagerAdapter;
	ViewPager pager;
	View firstView, secondView, thirdView;
	LayoutInflater mLayoutInflater;

	ImageView start;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guidance);
	}

	@Override
	public void initView() {
		mLayoutInflater = LayoutInflater.from(self);
		firstView = mLayoutInflater.inflate(R.layout.item_guidance_page_first,
				null);
		secondView = mLayoutInflater.inflate(
				R.layout.item_guidance_page_second, null);
		thirdView = mLayoutInflater.inflate(R.layout.item_guidance_page_third,
				null);
		pager = (ViewPager) findViewById(R.id.viewpager);
		pagerAdapter = new SimplePageAdapter(firstView, secondView, thirdView);

		pager.setAdapter(pagerAdapter);
		
		start =  (ImageView) thirdView.findViewById(R.id.start);

		start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(self, ReadyActivity.class);
				startActivity(intent);
				finishWithoutAnim();
			}
		});
	}
}
