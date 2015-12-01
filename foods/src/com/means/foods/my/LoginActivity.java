package com.means.foods.my;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.LoginEB;
import com.means.foods.bean.RegisterEB;
import com.means.foods.main.MainActivity;

import de.greenrobot.event.EventBus;

public class LoginActivity extends FoodsBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		EventBus.getDefault().register(this);
	}

	@Override
	public void initView() {
		setTitle("登录");
		
//		 DhNet net = new DhNet("http://www.foodies.im/wap.php?g=Wap&c=Login&a=loginApi");
//    	 net.addParam("phone", "13852286536");
//    	 net.addParam("password", "123");
//    	 net.doPostInDialog(new NetTask(self) {
//			
//			@Override
//			public void doInUI(Response response, Integer transfer) {
//				// TODO Auto-generated method stub
//				
//			}
//		});

		findViewById(R.id.login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(self, MainActivity.class);
				startActivity(it);
				finishWithoutAnim();
				// 登录成功后发送事件,关闭之前的页面
				EventBus.getDefault().post(new LoginEB());
			}
		});

		findViewById(R.id.register).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(self, RegisterOneActivity.class);
				startActivity(it);
			}
		});

		findViewById(R.id.forget_password).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// Intent it = new Intent(self, LoginActivity.class);
						// startActivity(it);
					}
				});

	}

	public void onEventMainThread(RegisterEB registerEb) {
		finishWithoutAnim();
	}

}
