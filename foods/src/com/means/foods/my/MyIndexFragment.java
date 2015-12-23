package com.means.foods.my;

import java.util.Timer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.srain.cube.views.ptr.PtrFrameLayout;
import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.UserLocation;
import net.duohuo.dhroid.util.ViewUtil;
import net.duohuo.dhroid.view.BadgeView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.bean.CancelOrderEB;
import com.means.foods.bean.MyIndexEB;
import com.means.foods.bean.PaySuccessEB;
import com.means.foods.bean.User;
import com.means.foods.utils.FoodsUtils;
import com.means.foods.view.RefreshListViewAndMore;
import com.means.foods.view.RoundImageView;
import com.means.foods.view.RefreshListViewAndMore.OnLoadSuccess;

import de.greenrobot.event.EventBus;

public class MyIndexFragment extends Fragment implements OnClickListener,
		OnLoadSuccess {

	static MyIndexFragment instance;

	User user;

	View mainV;

	PtrFrameLayout mPtrFrame;

	RefreshListViewAndMore listV;

	LayoutInflater mLayoutInflater;

	ListView contentListV;

	View headV;

	// 编辑资料库按钮
	Button editB;

	// 消息中心
	ImageView msgI;

	RoundImageView headI;
	TextView nicknameT;

	NetJSONAdapter adapter;

	boolean hidden;

	BadgeView badgeT;

	View bradeV;

	public static MyIndexFragment getInstance() {
		if (instance == null) {
			instance = new MyIndexFragment();
		}

		return instance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainV = inflater.inflate(R.layout.fragment_my_index, null);
		mLayoutInflater = inflater;
		EventBus.getDefault().register(this);
		initView();
		// TODO Auto-generated method stub
		return mainV;

	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		this.hidden = hidden;
		if (!hidden) {
			getMsgCount();
		}
	}

	private void initView() {
		user = User.getInstance();

		listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
		listV.setOnLoadSuccess(this);
		String url = API.orderList;
		contentListV = listV.getListView();
		headV = mLayoutInflater.inflate(R.layout.head_my_index, null);
		listV.addHeadView(headV);
		// 璁剧疆绌虹殑emptyView
		adapter = new NetJSONAdapter(url, getActivity(),
				R.layout.item_index_listview);
		adapter.fromWhat("data");
		adapter.addparam("uid", User.getInstance().getUid());
		adapter.addparam("token", User.getInstance().getToken());
		adapter.addField(new FieldMap("count", R.id.count) {

			@Override
			public Object fix(View itemV, Integer position, Object o, Object jo) {

				// TextView desT = (TextView) itemV.findViewById(R.id.des);
				JSONObject data = (JSONObject) jo;
				// JSONArray jsa = JSONUtil.getJSONArray(data, "data");
				// String des = "";
				// for (int i = 0; i < jsa.length(); i++) {
				// try {
				// JSONObject cateJo = jsa.getJSONObject(i);
				// des = des + JSONUtil.getString(cateJo, "name") + "    ";
				// } catch (JSONException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// }
				// desT.setText(des);

				String address = JSONUtil.getString(data, "city_name");
				if (!TextUtils.isEmpty(address)) {
					String letter = FoodsUtils.getPYIndexStr(
							address.substring(0, 1), true);
					TextView letterT = (TextView) itemV
							.findViewById(R.id.letter);
					letterT.setText(letter);
				}
				return "您预定了" + o + "家餐厅";
			}
		});
		adapter.addField("city_name", R.id.name);
		listV.setAdapter(adapter);

		contentListV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent it = new Intent(getActivity(),
						MyReservationsListActivity.class);
				JSONObject jo = adapter.getTItem(position - 1);
				it.putExtra("cityName", JSONUtil.getString(jo, "city_name"));
				startActivity(it);
			}
		});
		headI = (RoundImageView) headV.findViewById(R.id.head);
		nicknameT = (TextView) headV.findViewById(R.id.nickname);

		editB = (Button) headV.findViewById(R.id.edit);
		editB.setOnClickListener(this);
		msgI = (ImageView) headV.findViewById(R.id.msg);
		msgI.setOnClickListener(this);

		bradeV = headV.findViewById(R.id.brade);
		badgeT = new BadgeView(getActivity(), bradeV);// 创建一个BadgeView对象，view为你需要显示提醒信息的控件
		badgeT.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.中间，还有其他位置属性
		badgeT.setTextColor(Color.WHITE); // 文本颜色
		badgeT.setBadgeBackgroundColor(getResources()
				.getColor(R.color.text_red)); // 背景颜色
		badgeT.setTextSize(10); // 文本大小
		getMyDetails();
		getMsgCount();
	}

	private void getMyDetails() {
		DhNet verifyNet = new DhNet(API.myInfo);
		verifyNet.addParam("uid", user.getUid());
		verifyNet.addParam("token", user.getToken());
		verifyNet.doGetInDialog(new NetTask(getActivity()) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					JSONObject jo = response.jSONFromData();
					ViewUtil.bindNetImage(headI,
							JSONUtil.getString(jo, "avatar"), "head");
					ViewUtil.bindView(nicknameT,
							JSONUtil.getString(jo, "nickname"));
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		case R.id.edit:
			it = new Intent(getActivity(), EditinfoActivity.class);
			startActivity(it);
			break;

		case R.id.msg:
			it = new Intent(getActivity(), MsgListActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void loadSuccess(Response response) {
		headV.findViewById(R.id.empty_view).setVisibility(
				adapter.getValues().size() == 0 ? View.VISIBLE : View.GONE);
	}

	// 更新个人信息
	public void onEventMainThread(MyIndexEB myIndexEB) {
		getMyDetails();
	}

	public void onEventMainThread(CancelOrderEB myIndexEB) {
		adapter.refresh();
	}

	private void getMsgCount() {
		User user = User.getInstance();
		DhNet net = new DhNet(API.msgcount);
		net.addParam("uid ", user.getUid());
		net.addParam("token", user.getToken());
		net.doGet(new NetTask(getActivity()) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					JSONObject jo = response.jSONFromData();
					// 公平价收车数
					int count = JSONUtil.getInt(jo, "count");
					if (count > 0) {

						if (count > 99) {
							badgeT.setText("N");
						} else {
							badgeT.setText(count + "");
						}
						badgeT.show();
					} else {
						badgeT.hide();
					}

				}
			}
		});
	}

}
