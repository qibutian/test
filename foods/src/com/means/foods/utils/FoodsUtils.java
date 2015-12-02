package com.means.foods.utils;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.content.Context;
import android.widget.Toast;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.bean.User;

public class FoodsUtils {
	static boolean flag;
	public static boolean collect(Context context,String store_id,boolean is_collect){
		DhNet net;
		String url;
		if(!is_collect){
			url =API.Collect;
		}else{
			url = API.Unsubscribe;
		}
		net = new DhNet(url);
		net.addParam("uid",User.getInstance().uid);
		net.addParam("token", User.getInstance().token);
		net.addParam("store_id",store_id);
		net.doPostInDialog(new NetTask(context) {
			@Override
			public void doInUI(Response response, Integer transfer) {
				// TODO Auto-generated method stub
				if (response.isSuccess()) {
					flag = true;
				}else {
					flag = false;
				}
			}
		});
		return flag;
		
	}

}
