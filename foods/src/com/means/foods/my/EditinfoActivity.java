package com.means.foods.my;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.view.dialog.ReviseHeadDialog;
import com.means.foods.view.dialog.ReviseSexDialog;

public class EditinfoActivity extends FoodsBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personalpenter);
	}

	@Override
	public void initView() {
		setTitle("编辑资料");
		findViewById(R.id.phone_edit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ReviseHeadDialog dialog = new ReviseHeadDialog(self);
				dialog.show();

			}
		});

		findViewById(R.id.nickname_edit).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						Intent it = new Intent(self, ReviseNameActivity.class);
						startActivity(it);

					}
				});

		findViewById(R.id.name_edit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent it = new Intent(self, ReviseNameActivity.class);
				startActivity(it);

			}
		});

		findViewById(R.id.phone_edit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent it = new Intent(self, RevisePhoneActivity.class);
				startActivity(it);

			}
		});

		findViewById(R.id.sex_edit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ReviseSexDialog dialog = new ReviseSexDialog(self);
				dialog.show();

			}
		});

		findViewById(R.id.email_edit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent it = new Intent(self, ReviseEmailActivity.class);
				startActivity(it);

			}
		});

		findViewById(R.id.password_edit).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						Intent it = new Intent(self, RevisePswdActivity.class);
						startActivity(it);

					}
				});
	}

}
