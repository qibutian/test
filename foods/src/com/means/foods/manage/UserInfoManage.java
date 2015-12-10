package com.means.foods.manage;

import com.means.foods.bean.User;
import com.means.foods.main.ReadyActivity;
import com.means.foods.my.LoginActivity;

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
			IocContainer.getShare().get(IDialog.class)
					.showToastShort(context, "");
			ReadyActivity.loginCall = loginCallBack;
			Intent it = new Intent(context, ReadyActivity.class);
			context.startActivity(it);
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
