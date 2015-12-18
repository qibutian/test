package com.means.foods.my;

import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;

/**
 * 消息详情
 * 
 * @author Administrator
 * 
 */
public class MsgDetailActivity extends FoodsBaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_detail);
	}

	@Override
	public void initView() {
		setTitle("消息详情");
		String data = getIntent().getStringExtra("jo");
		try {
			JSONObject jo = new JSONObject(data);

			ViewUtil.bindView(findViewById(R.id.title),
					JSONUtil.getString(jo, "title"));
			ViewUtil.bindView(findViewById(R.id.content),
					JSONUtil.getString(jo, "content"));
			ViewUtil.bindView(findViewById(R.id.date),
					JSONUtil.getString(jo, "creattime"), "time");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
