package com.means.foods.main;

import com.means.foods.R;
import com.means.foods.bean.User;
import com.means.foods.cate.CateIndexFragment;
import com.means.foods.collect.CollectIndexFragment;
import com.means.foods.hot.HotIndexFragment;
import com.means.foods.manage.UserInfoManage;
import com.means.foods.manage.UserInfoManage.LoginCallBack;
import com.means.foods.my.MyIndexFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	FragmentManager fm;

	Fragment currentFragment;

	LinearLayout tabV;

	TextView titleT;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		initView();
		initTab();
		setTab(0);
	}

	private void initView() {
		fm = getSupportFragmentManager();
		tabV = (LinearLayout) findViewById(R.id.tab);
		titleT = (TextView) findViewById(R.id.title);
	}

	private void initTab() {
		for (int i = 0; i < tabV.getChildCount(); i++) {
			final int index = i;
			LinearLayout childV = (LinearLayout) tabV.getChildAt(i);
			childV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setTab(index);
				}
			});
		}
	}

	private void setTab(final int index) {
		User user = User.getInstance();
		if(index==1||index==2) {
			if(!user.isLogin()) {
				UserInfoManage.getInstance().checkLogin(MainActivity.this, new  LoginCallBack() {
				
				@Override
				public void onisLogin() {
					setTab(index);
					
				}
				
				@Override
				public void onLoginFail() {
					// TODO Auto-generated method stub
					
				}
			});
				return;
			}
		}
		
		
		for (int i = 0; i < tabV.getChildCount(); i++) {
			LinearLayout childV = (LinearLayout) tabV.getChildAt(i);
			RelativeLayout imgV = (RelativeLayout) childV.getChildAt(0);
			ImageView imgI = (ImageView) imgV.getChildAt(0);
			TextView textT = (TextView) childV.getChildAt(1);
			if (i == index) {
				childV.setBackgroundColor(getResources().getColor(
						R.color.tab_index_bg));
				switch (i) {
				case 0:
					switchContent(HotIndexFragment.getInstance());
					imgI.setImageResource(R.drawable.icon_renmen_f);
					textT.setTextColor(getResources().getColor(
							R.color.text_yellow));
					titleT.setText(getString(R.string.hot_title));
					break;

				case 1:
					switchContent(CateIndexFragment.getInstance());
					imgI.setImageResource(R.drawable.icon_meishi_f);
					textT.setTextColor(getResources().getColor(
							R.color.text_yellow));
					titleT.setText(getString(R.string.cate_title));
					break;

				case 2:
					switchContent(CollectIndexFragment.getInstance());
					imgI.setImageResource(R.drawable.icon_collect_f);
					textT.setTextColor(getResources().getColor(
							R.color.text_yellow));
					titleT.setText(getString(R.string.collect_title));
					break;

				case 3:
					switchContent(MyIndexFragment.getInstance());
					imgI.setImageResource(R.drawable.icon_person_f);
					textT.setTextColor(getResources().getColor(
							R.color.text_yellow));
					titleT.setText(getString(R.string.my_title));
					break;

				default:
					break;
				}
			} else {
				childV.setBackgroundColor(getResources().getColor(
						R.color.nothing));
				switch (i) {
				case 0:
					imgI.setImageResource(R.drawable.icon_remen);
					textT.setTextColor(getResources().getColor(
							R.color.text_66_black_light));
					break;

				case 1:
					imgI.setImageResource(R.drawable.icon_meishi);
					textT.setTextColor(getResources().getColor(
							R.color.text_66_black_light));
					break;

				case 2:
					imgI.setImageResource(R.drawable.icon_collect);
					textT.setTextColor(getResources().getColor(
							R.color.text_66_black_light));
					break;

				case 3:
					imgI.setImageResource(R.drawable.icon_person);
					textT.setTextColor(getResources().getColor(
							R.color.text_66_black_light));
					break;

				default:
					break;
				}
			}
		}
	}

	public void switchContent(Fragment fragment) {
		try {
			FragmentTransaction t = fm.beginTransaction();
			if (currentFragment != null) {
				t.hide(currentFragment);
			}
			if (!fragment.isAdded()) {
				t.add(R.id.main_content, fragment);
			}
			t.show(fragment);
			t.commitAllowingStateLoss();
			currentFragment = fragment;
		} catch (Exception e) {
		}
	}

}