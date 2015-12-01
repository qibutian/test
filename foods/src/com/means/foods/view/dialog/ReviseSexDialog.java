package com.means.foods.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.means.foods.R;

/**
 * 修改性别Created by Administrator on 2015/10/12.
 * 
 */
public class ReviseSexDialog extends BaseAlertDialog {

	Context mContext;

	OnSexResultListener mlistener;

	public ReviseSexDialog(Context context) {
		super(context, R.style.Dialog_Fullscreen);
		this.mContext = context;
		Window win = getWindow();
		win.setGravity(Gravity.BOTTOM);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_sex);
		initView();
	}

	private void initView() {
		Window win = getWindow();
		win.setGravity(Gravity.BOTTOM);
		LinearLayout man_layout = (LinearLayout) findViewById(R.id.man_layout);
		LinearLayout woman_layout = (LinearLayout) findViewById(R.id.woman_layout);

		findViewById(R.id.bg).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null!=mlistener) {
					mlistener.onResult("-1");
				}
				dismiss();
			}
		});

		man_layout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null!=mlistener) {
					mlistener.onResult("man");
				}
				
				dismiss();
			}
		});

		woman_layout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null!=mlistener) {
					mlistener.onResult("woman");
				}
				dismiss();
			}
		});

	}

	public interface OnSexResultListener {
		void onResult(String sex);
	}

	public OnSexResultListener getOnSexResultListener() {
		return mlistener;
	}

	public void setOnSexResultListener(OnSexResultListener mlistener) {
		this.mlistener = mlistener;
	}

}
