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

	// 餐厅列表
	public static String restaurantList = "http://www.foodies.im/wap.php?g=Wap&c=Meal_list&a=merchantListApi";

	// 美食之旅
	public static String cityList = "http://www.foodies.im/wap.php?g=Wap&c=Travel&a=cityList";

	// 餐厅详情
	public static String restaurantDetail = "http://www.foodies.im/wap.php?g=Wap&c=Food&a=shopDetailApi";

}
