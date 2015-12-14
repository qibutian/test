package com.means.foods.utils;

import java.io.File;

import com.means.foods.bean.User;

import net.duohuo.dhroid.dialog.IDialog;
import net.duohuo.dhroid.ioc.IocContainer;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class DownLoad extends Thread {

	String url;
	Handler mhandler;

	String saveDir;
	String UUID;
	Context context;

	public DownLoad(Context context, Handler mhandler, String url,
			String saveDir, String UUID) {
		this.mhandler = mhandler;
		this.url = url;
		this.saveDir = saveDir;
		this.UUID = UUID;
		this.context = context;
	}

	public Intent getWordFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		// intent.setClassName("cn.wps.moffice",
		// "cn.wps.moffice.documentmanager.PreStartActivity");
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}

	@Override
	public void run() {
		Looper.prepare();
		// TODO Auto-generated method stub
		/**
		 * 判断是否有文件
		 */
		File file = new File(saveDir + UUID + ".pdf");
		if (file == null || !file.exists()) {
			mhandler.sendEmptyMessage(4);
			User user = User.getInstance();
			HttpDownloader httpDownLoader = new HttpDownloader(context,
					user.getUid(), user.getToken(), UUID);
			int result = httpDownLoader.downfile(url, saveDir, UUID + ".pdf");
			if (result == 0) {

				mhandler.sendEmptyMessage(1);

				Log.i("TAG", "下载成功");
			} else if (result == -1) {
				mhandler.sendEmptyMessage(2);
				Log.i("TAG", "下载失败");
			} else if (result == -2) {
				mhandler.sendEmptyMessage(2);

				Log.i("TAG", "文件不存在,下载失败！");
			} else {
				mhandler.sendEmptyMessage(3);

				Log.i("TAG", "文件已存在");
			}
		} else {

			try {
				Intent intent = getWordFileIntent(saveDir + UUID + ".pdf");
				context.startActivity(intent);
			} catch (ActivityNotFoundException e) {
				// 检测到系统尚未安装OliveOffice的apk程序
				// 请先到www.olivephone.com/e.apk下载并安装
				IocContainer.getShare().get(IDialog.class)
						.showToastShort(context, "检测到系统尚未安装打开Word文档的程序");
				Log.i("TAG", "检测到系统尚未安装打开Word文档的程序");
			}

		}
		Looper.loop();
	}

}
