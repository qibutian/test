package com.means.foods.api;

import net.duohuo.dhroid.util.DhUtil;

/**
 * Created by Administrator on 2015/10/13.
 */
public class API {

	// 娴嬭瘯鐗圓PI鍓嶇紑
	public static final String CWBaseurl = "http://cwapi.gongpingjia.com:8080/v2/";
	// public static final String CWBaseurl =
	// "http://chewanapi.gongpingjia.com/v2/";

	public static final String recommendList = CWBaseurl
			+ "/official/activity/list";

	// 鑾峰彇鍦扮偣淇℃伅
	public static String getPlaces(String id) {
		return "http://cwapi.gongpingjia.com:8080/v2/area/list?parentId=" + id;
	}

	// 鍖归厤娲诲姩
	public static String getMatchUrl(String userId, String token) {
		return String.format(
				CWBaseurl + "activity/register?userId=%s&token=%s", userId,
				token);
	}

	// 登录
	public static String login = "http://www.foodies.im/wap.php?g=Wap&c=Login&a=loginApi";

	// 餐厅列表
	public static String restaurantList = "http://www.foodies.im/wap.php?g=Wap&c=Meal_list&a=merchantListApi";

	// 美食之旅
	public static String cityList = "http://www.foodies.im/wap.php?g=Wap&c=Travel&a=cityList";

	// 餐厅详情
	public static String restaurantDetail = "http://www.foodies.im/wap.php?g=Wap&c=Food&a=shopDetailApi";

	// 编辑头像
	public static String editAvatar = "http://www.foodies.im/wap.php?g=Wap&c=My&a=uploadAvatar";

	// 编辑性别
	public static String editSex = "http://www.foodies.im/wap.php?g=Wap&c=My&a=editSex";

	// 个人信息
	public static String myInfo = "http://www.foodies.im/wap.php?g=Wap&c=My&a=myInfoApi";
	// 收藏
	public static String Collect = "http://www.foodies.im/wap.php?g=Wap&c=Collect&a=addCollectApi";
	// 取消收藏
	public static String Unsubscribe = "http://www.foodies.im/wap.php?g=Wap&c=Collect&a=cancelCollectApi";
	// 修改email
	public static String editEmail = "http://www.foodies.im/wap.php?g=Wap&c=My&a=editEmail";
	// 修改姓名
	public static String editName = "http://www.foodies.im/wap.php?g=Wap&c=My&a=editTruename";
	// 修改昵称
	public static String editNickName = "http://www.foodies.im/wap.php?g=Wap&c=My&a=editNickname";
	// 修改密码
	public static String editPwd = "http://www.foodies.im/wap.php?g=Wap&c=My&a=editPassword";
	// 我的收藏
	public static String collectList = "http://www.foodies.im/wap.php?g=Wap&c=My&a=myCollectAPI";

	// 我的订单
	public static String orderList = "http://www.foodies.im/wap.php?g=Wap&c=My&a=myOrderAPI";

	// 取消订单
	public static String ordercancle = "http://www.foodies.im/wap.php?g=Wap&c=My&a=orderRefundApi";

	// 添加订单
	public static String addOrder = "http://www.foodies.im/wap.php?g=Wap&c=My&a=saveOrderApi";
	
	// 修改订单
	public static String modifyOrder= "http://www.foodies.im/wap.php?g=Wap&c=My&a=modifyOrderApi";

	// 获取注册验证码(注册第一步)
	public static String register_Captcha = "http://www.foodies.im/wap.php?g=Wap&c=Login&a=getVerificationCode";

	// 注册
	public static String register = "http://www.foodies.im/wap.php?g=Wap&c=Login&a=registerApi";

	// 立即支付
	public static String pay = "http://www.foodies.im/wap.php?g=Wap&c=Pay&a=payApi";

	// 订单详情
	public static String orderDetail = "http://www.foodies.im/wap.php?g=Wap&c=My&a=orderDetailApi";
	
	//获取修改手机验证码
	public static String revisePhoneCode = "http://www.foodies.im/wap.php?g=Wap&c=Login&a=checkPassword";
	
	//修改手机号
	public static String revisePhone = "http://www.foodies.im/wap.php?g=Wap&c=Login&a=checkPassword";

}
