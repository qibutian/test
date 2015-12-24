package com.means.foods.manage;

import com.means.foods.bean.User;
import com.means.foods.main.ReadyActivity;
import com.means.foods.my.LoginActivity;
import com.means.foods.view.dialog.LoginDialog;
import com.means.foods.view.dialog.LoginDialog.OnResultListener;

import android.app.Activity;
import android.content.Intent;

import net.duohuo.dhroid.dialog.IDialog;
import net.duohuo.dhroid.ioc.IocContainer;

public class UserInfoManage {

	static UserInfoManage instance;

	public static UserInfoManage getInstance() {
		if (instance == null) {
			instance = new UserInfoManage();
		}
		return instance;
	}

	public boolean checkLogin(final Activity context,
			final LoginCallBack loginCallBack) {
		boolean islogin = User.getInstance().isLogin();
		if (!islogin) {

			LoginDialog dialog = new LoginDialog(context);
			dialog.setOnResultListener(new OnResultListener() {

				@Override
				public void onResult() {
					ReadyActivity.loginCall = loginCallBack;
					Intent it = new Intent(context, ReadyActivity.class);
					context.startActivity(it);
				}
			});

			dialog.show();

		} else {
			if (loginCallBack != null) {
				loginCallBack.onisLogin();
			}
		}
		return islogin;
	}

	public interface LoginCallBack {
		public void onisLogin();

		public void onLoginFail();
	}
}
