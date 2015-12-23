package com.means.foods.utils;

import java.util.Random;

import net.duohuo.dhroid.dialog.IDialog;
import net.duohuo.dhroid.ioc.IocContainer;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.api.Constant;
import com.means.foods.bean.User;
import com.means.foods.main.MainActivity;
import com.means.foods.manage.SystemBarTintManager;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class FoodsUtils {

	OnCallBack onCallBack;

	public void collect(Context context, String store_id, boolean is_collect) {
		DhNet net;
		String url;
		if (!is_collect) {
			url = API.Collect;
		} else {
			url = API.Unsubscribe;
		}
		net = new DhNet(url);
		net.addParam("uid", User.getInstance().uid);
		net.addParam("token", User.getInstance().token);
		net.addParam("store_id", store_id);
		net.doPostInDialog(new NetTask(context) {
			@Override
			public void doInUI(Response response, Integer transfer) {
				// TODO Auto-generated method stub
				if (onCallBack != null) {
					onCallBack.callBack(response);
				}
			}
		});

	}

	public OnCallBack getOnCallBack() {
		return onCallBack;
	}

	public void setOnCallBack(OnCallBack onCallBack) {
		this.onCallBack = onCallBack;
	}

	public interface OnCallBack {
		void callBack(Response response);
	}

	public static String getPYIndexStr(String strChinese, boolean bUpCase) {

		try {

			StringBuffer buffer = new StringBuffer();

			byte b[] = strChinese.getBytes("GBK");// 把中文转化成byte数组

			for (int i = 0; i < b.length; i++) {

				if ((b[i] & 255) > 128) {

					int char1 = b[i++] & 255;

					char1 <<= 8;// 左移运算符用“<<”表示，是将运算符左边的对象，向左移动运算符右边指定的位数，并且在低位补零。其实，向左移n位，就相当于乘上2的n次方

					int chart = char1 + (b[i] & 255);

					buffer.append(getPYIndexChar((char) chart, bUpCase));

					continue;

				}

				char c = (char) b[i];

				if (!Character.isJavaIdentifierPart(c))// 确定指定字符是否可以是 Java
														// 标识符中首字符以外的部分。

					c = 'A';

				buffer.append(c);

			}

			return buffer.toString();

		} catch (Exception e) {

			System.out.println((new StringBuilder())
					.append("\u53D6\u4E2D\u6587\u62FC\u97F3\u6709\u9519")
					.append(e.getMessage()).toString());

		}

		return null;

	}

	private static char getPYIndexChar(char strChinese, boolean bUpCase) {

		int charGBK = strChinese;

		char result;

		if (charGBK >= 45217 && charGBK <= 45252)

			result = 'A';

		else

		if (charGBK >= 45253 && charGBK <= 45760)

			result = 'B';

		else

		if (charGBK >= 45761 && charGBK <= 46317)

			result = 'C';

		else

		if (charGBK >= 46318 && charGBK <= 46825)

			result = 'D';

		else

		if (charGBK >= 46826 && charGBK <= 47009)

			result = 'E';

		else

		if (charGBK >= 47010 && charGBK <= 47296)

			result = 'F';

		else

		if (charGBK >= 47297 && charGBK <= 47613)

			result = 'G';

		else

		if (charGBK >= 47614 && charGBK <= 48118)

			result = 'H';

		else

		if (charGBK >= 48119 && charGBK <= 49061)

			result = 'J';

		else

		if (charGBK >= 49062 && charGBK <= 49323)

			result = 'K';

		else

		if (charGBK >= 49324 && charGBK <= 49895)

			result = 'L';

		else

		if (charGBK >= 49896 && charGBK <= 50370)

			result = 'M';

		else

		if (charGBK >= 50371 && charGBK <= 50613)

			result = 'N';

		else

		if (charGBK >= 50614 && charGBK <= 50621)

			result = 'O';

		else

		if (charGBK >= 50622 && charGBK <= 50905)

			result = 'P';

		else

		if (charGBK >= 50906 && charGBK <= 51386)

			result = 'Q';

		else

		if (charGBK >= 51387 && charGBK <= 51445)

			result = 'R';

		else

		if (charGBK >= 51446 && charGBK <= 52217)

			result = 'S';

		else

		if (charGBK >= 52218 && charGBK <= 52697)

			result = 'T';

		else

		if (charGBK >= 52698 && charGBK <= 52979)

			result = 'W';

		else

		if (charGBK >= 52980 && charGBK <= 53688)

			result = 'X';

		else

		if (charGBK >= 53689 && charGBK <= 54480)

			result = 'Y';

		else

		if (charGBK >= 54481 && charGBK <= 55289)

			result = 'Z';

		else

			result = (char) (65 + (new Random()).nextInt(25));

		if (!bUpCase)

			result = Character.toLowerCase(result);

		return result;

	}

	/**
	 * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
	 * 
	 * 
	 * @param flag
	 *            (0:分享到微信好友，1：分享到微信朋友圈)
	 */
	public static void wechatShare(int flag, Context context, String name,
			String reason, String store_id) {
		IWXAPI api = WXAPIFactory.createWXAPI(context, Constant.WX_APP_KEY);
		if (!isWXAppInstalledAndSupported(context, api)) {
			IocContainer.getShare().get(IDialog.class)
					.showToastShort(context, "请先安装微信");
			return;
		}
		api.registerApp(Constant.WX_APP_KEY);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http://www.foodies.im/wap.php?g=Wap&c=Food&a=shop&mer_id=68&store_id="
				+ store_id;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = name;
		msg.description = reason;
		// 这里替换一张自己工程里的图片资源
		Bitmap thumb = null;

		thumb = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_launcher);
		msg.setThumbImage(thumb);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
				: SendMessageToWX.Req.WXSceneTimeline;
		api.sendReq(req);
	}

	private static boolean isWXAppInstalledAndSupported(Context context,
			IWXAPI api) {
		// LogOutput.d(TAG, "isWXAppInstalledAndSupported");
		boolean sIsWXAppInstalledAndSupported = api.isWXAppInstalled()
				&& api.isWXAppSupportAPI();
		if (!sIsWXAppInstalledAndSupported) {

		}

		return sIsWXAppInstalledAndSupported;
	}

	/*
	 * 设置状态栏背景状态
	 */
	public static void initSystemBar(Activity activity) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

			setTranslucentStatus(activity, true);

		}

		SystemBarTintManager tintManager = new SystemBarTintManager(activity);

		tintManager.setStatusBarTintEnabled(true);

		// 使用颜色资源

		tintManager.setStatusBarTintResource(R.color.white);

	}

	private static void setTranslucentStatus(Activity activity, boolean on) {

		Window win = activity.getWindow();

		WindowManager.LayoutParams winParams = win.getAttributes();

		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

		if (on) {

			winParams.flags |= bits;

		} else {

			winParams.flags &= ~bits;

		}

		win.setAttributes(winParams);

	}

}
