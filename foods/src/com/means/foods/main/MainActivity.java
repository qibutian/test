package com.means.foods.main;

import net.duohuo.dhroid.activity.ActivityTack;
import net.duohuo.dhroid.dialog.IDialog;
import net.duohuo.dhroid.ioc.IocContainer;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import org.json.JSONObject;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.bean.User;
import com.means.foods.cate.CateIndexFragment;
import com.means.foods.collect.CollectIndexFragment;
import com.means.foods.hot.HotIndexFragment;
import com.means.foods.manage.SystemBarTintManager;
import com.means.foods.manage.UserInfoManage;
import com.means.foods.manage.UserInfoManage.LoginCallBack;
import com.means.foods.my.MyIndexFragment;
import com.means.foods.utils.FoodsUtils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	FragmentManager fm;

	Fragment currentFragment;

	LinearLayout tabV;

	TextView titleT;

	boolean isExit;

	Handler mHandler;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		FoodsUtils.initSystemBar(this);
		initView();
		initTab();
		setTab(0);
		updateApp();
	}

	private void initView() {
		mHandler = new Handler();
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
		if (index == 2 || index == 3) {
			if (!user.isLogin()) {
				UserInfoManage.getInstance().checkLogin(MainActivity.this,
						new LoginCallBack() {

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

	public void updateApp() {
		final String mCurrentVersion = getAppVersion();
		DhNet net = new DhNet(API.update);
		net.doGet(new NetTask(MainActivity.this) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					JSONObject jo = response.jSONFromData();
					String version = JSONUtil.getString(jo, "version");
					if (0 < version.compareTo(mCurrentVersion)) {
						showUpdateDialog(jo);
					}
				}
			}
		});
	}

	private String getAppVersion() {
		String versionName = null;
		try {
			String pkName = this.getPackageName();
			versionName = this.getPackageManager().getPackageInfo(pkName, 0).versionName;

		} catch (Exception e) {
			return null;
		}
		return versionName;
	}

	private void showUpdateDialog(final JSONObject jo) {
		Builder builder = new Builder(this);
		builder.setTitle("发现新版本 " + JSONUtil.getString(jo, "version"));
		builder.setMessage(JSONUtil.getString(jo, "des"));
		builder.setPositiveButton("立即更新",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Intent it = new Intent(Intent.ACTION_VIEW);
						Uri uri = Uri.parse(JSONUtil.getString(jo, "download"));
						it.setData(uri);
						startActivity(it);
					}

				});
		builder.setNegativeButton("以后再说",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (JSONUtil.getInt(jo, "force_update") == 1) {
							finish();
						}
					}
				});
		builder.create().show();
	}

	public class ExitRunnable implements Runnable {
		@Override
		public void run() {
			isExit = false;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!isExit) {
				isExit = true;
				IocContainer.getShare().get(IDialog.class)
						.showToastShort(MainActivity.this, "再按一次退出程序");
				mHandler.postDelayed(new ExitRunnable(), 2000);
			} else {
				ActivityTack.getInstanse().exit(MainActivity.this);
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}