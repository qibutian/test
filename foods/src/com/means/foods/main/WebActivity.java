package com.means.foods.main;

import net.duohuo.dhroid.net.JSONUtil;
import android.os.Bundle;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;
import com.means.foods.view.TouchWebView;

public class WebActivity extends FoodsBaseActivity {

	TouchWebView webV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
	}

	@Override
	public void initView() {
		String order_id = getIntent().getStringExtra("order_id");
		User user = User.getInstance();
		webV = (TouchWebView) findViewById(R.id.web);
		webV.getSettings().setDefaultTextEncodingName("UTF-8");
		webV.getSettings().setJavaScriptEnabled(true);
		String url = "http://www.foodies.im/wap.php?g=Wap&c=Food&a=html2pdfApi"
				+ "&order_id=" + order_id + "&uid=" + user.getUid() + "&token="
				+ user.getToken();
		webV.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);
	}

}
