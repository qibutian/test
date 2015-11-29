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
 * 修改性别Created by Administrator on 2015/10/12.
 * 
 */
public class ReviseSexDialog extends AlertDialog {

	Context mContext;

	OnSexResultListener mlistener;

	public ReviseSexDialog(Context context) {
		super(context);
		this.mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_sex);
		initView();
	}

	private void initView() {
		LinearLayout camera_layout = (LinearLayout) findViewById(R.id.man_layout);
		LinearLayout gallery_layout = (LinearLayout) findViewById(R.id.woman_layout);

	}

	public interface OnSexResultListener {
		void onResult(int result);
	}

	public OnSexResultListener getOnSexResultListener() {
		return mlistener;
	}

	public void OnSexResultListener(OnSexResultListener mlistener) {
		this.mlistener = mlistener;
	}

}
