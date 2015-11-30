package com.means.foods.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.means.foods.R;

/**
 * 修改头像 Created by Administrator on 2015/10/12.
 * 
 */
public class ReviseHeadDialog extends AlertDialog {

	Context mContext;

	OnHeadResultListener mlistener;

	public ReviseHeadDialog(Context context) {
		super(context);
		this.mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_camera);
		initView();
	}

	private void initView() {
		LinearLayout camera_layout = (LinearLayout) findViewById(R.id.camera_layout);
		LinearLayout gallery_layout = (LinearLayout) findViewById(R.id.gallery_layout);
		LinearLayout cancel_layout = (LinearLayout) findViewById(R.id.cancel_layout);

	}

	public interface OnHeadResultListener {
		void onResult(int result);
	}

	public OnHeadResultListener getOnHeadResultListener() {
		return mlistener;
	}

	public void setOnHeadResultListenerr(OnHeadResultListener mlistener) {
		this.mlistener = mlistener;
	}

}
