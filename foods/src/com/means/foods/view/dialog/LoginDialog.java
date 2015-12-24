package com.means.foods.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.means.foods.R;

public class LoginDialog extends BaseAlertDialog {

	Context mContext;

	OnResultListener onResultListener;

	public LoginDialog(Context context) {
		super(context, R.style.Dialog_Fullscreen);
		this.mContext = context;
		Window win = getWindow();
		win.setGravity(Gravity.BOTTOM);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_login);
		initView();
	}

	private void initView() {
		LinearLayout login = (LinearLayout) findViewById(R.id.login);
		LinearLayout cancel_layout = (LinearLayout) findViewById(R.id.cancel_layout);

		findViewById(R.id.bg).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != onResultListener) {
					onResultListener.onResult();
				}
				dismiss();
			}
		});

		cancel_layout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

	}

	public interface OnResultListener {
		void onResult();
	}

	public OnResultListener getOnResultListener() {
		return onResultListener;
	}

	public void setOnResultListener(OnResultListener onResultListener) {
		this.onResultListener = onResultListener;
	}

}
