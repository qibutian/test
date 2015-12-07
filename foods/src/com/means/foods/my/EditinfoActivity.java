package com.means.foods.my;

import java.io.File;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ImageUtil;
import net.duohuo.dhroid.util.PhotoUtil;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.api.Constant;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;
import com.means.foods.view.RoundImageView;
import com.means.foods.view.dialog.ReviseHeadDialog;
import com.means.foods.view.dialog.ReviseHeadDialog.OnHeadResultListener;
import com.means.foods.view.dialog.ReviseSexDialog;
import com.means.foods.view.dialog.ReviseSexDialog.OnSexResultListener;

public class EditinfoActivity extends FoodsBaseActivity implements
		View.OnClickListener {

	User user;
	// 图片缓存根目录
	private File mCacheDir;
	private String mPhotoPath;
	public static final int EMAIL = 3;
	public static final int NAME = 4;
	public static final int NICKNAME = 5;
	RoundImageView headI;

	RelativeLayout photoR, nicknameR, nameR, phoneR, sexR, emailR, passwordR;

	TextView sexT, mailboxT, nameT, nicknameT, phoneT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personalpenter);

		mCacheDir = new File(getExternalCacheDir(), "CarPlay");
		mCacheDir.mkdirs();
	}

	@Override
	public void initView() {
		user = User.getInstance();
		setTitle("编辑资料");
		headI = (RoundImageView) findViewById(R.id.head);
		photoR = (RelativeLayout) findViewById(R.id.photo_edit);
		nicknameR = (RelativeLayout) findViewById(R.id.nickname_edit);
		nameR = (RelativeLayout) findViewById(R.id.name_edit);
		phoneR = (RelativeLayout) findViewById(R.id.phone_edit);
		sexR = (RelativeLayout) findViewById(R.id.sex_edit);
		emailR = (RelativeLayout) findViewById(R.id.email_edit);
		passwordR = (RelativeLayout) findViewById(R.id.password_edit);

		sexT = (TextView) findViewById(R.id.sex);
		mailboxT = (TextView) findViewById(R.id.mailbox);
		nameT = (TextView) findViewById(R.id.name);
		nicknameT = (TextView) findViewById(R.id.nickname);
		phoneT = (TextView) findViewById(R.id.phone);

		photoR.setOnClickListener(this);
		nicknameR.setOnClickListener(this);
		nameR.setOnClickListener(this);
		phoneR.setOnClickListener(this);
		sexR.setOnClickListener(this);
		emailR.setOnClickListener(this);
		passwordR.setOnClickListener(this);

		getMyDetails();
	}

	private void getMyDetails() {
		DhNet verifyNet = new DhNet(API.myInfo);
		verifyNet.addParam("uid", user.getUid());
		verifyNet.addParam("token", user.getToken());
		verifyNet.doGetInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					JSONObject jo = response.jSONFromData();
					ViewUtil.bindNetImage(headI,
							JSONUtil.getString(jo, "avatar"), "head");
					ViewUtil.bindView(nicknameT,
							JSONUtil.getString(jo, "nickname"));
					ViewUtil.bindView(nameT, JSONUtil.getString(jo, "truename"));
					ViewUtil.bindView(phoneT, JSONUtil.getString(jo, "phone"));
					ViewUtil.bindView(mailboxT, JSONUtil.getString(jo, "email"));
					ViewUtil.bindView(sexT, "1".equals(JSONUtil.getString(jo,
							"sex")) ? "男" : "女");
				}
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case Constant.TAKE_PHOTO:
				String newPath = new File(mCacheDir, System.currentTimeMillis()
						+ ".jpg").getAbsolutePath();
				String path = PhotoUtil.onPhotoFromCamera(self,
						Constant.ZOOM_PIC, mPhotoPath, 1, 1, 1000, newPath);
				mPhotoPath = path;
				break;
			case Constant.PICK_PHOTO:
				PhotoUtil.onPhotoFromPick(self, Constant.ZOOM_PIC, mPhotoPath,
						data, 1, 1, 1000);
				break;
			case Constant.ZOOM_PIC:
				Bitmap bmp = PhotoUtil.getLocalImage(new File(mPhotoPath));
				headI.setImageBitmap(ImageUtil.toRoundCorner(bmp, 1000));
				uploadHead(mPhotoPath);
				break;
			case EMAIL:
				mailboxT.setText(data.getStringExtra("email"));
				break;
			case NAME:
				nameT.setText(data.getStringExtra("name"));
				break;
			case NICKNAME:
				nicknameT.setText(data.getStringExtra("nickname"));
				break;

			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void uploadHead(String path) {

		// Bitmap bmp = PhotoUtil.getLocalImage(new File(path));
		System.out.println("1111111111");
		DhNet net = new DhNet(API.editAvatar);
		net.addParam("uid", user.getUid());
		net.addParam("token", user.getToken());
		net.upload("imgFile", new File(path), new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				hidenProgressDialog();
				if (response.isSuccess()
						&& Integer.parseInt(response.getBundle("proccess")
								.toString()) == 100) {
					showToast("更换头像成功");
				} else {
					System.out.println("333333");
				}
			}
		});
	}

	@Override
	public void onClick(View v) {

		Intent it;

		switch (v.getId()) {
		case R.id.photo_edit:
			ReviseHeadDialog dialog = new ReviseHeadDialog(self);
			dialog.setOnHeadResultListenerr(new OnHeadResultListener() {

				@Override
				public void onResult(int result) {
					mPhotoPath = new File(mCacheDir, System.currentTimeMillis()
							+ ".jpg").getAbsolutePath();
					System.out.println("mPhotoPath=============" + mPhotoPath);
					final File tempFile = new File(mPhotoPath);
					// 拍照
					if (0 == result) {
						Intent getImageByCamera = new Intent(
								"android.media.action.IMAGE_CAPTURE");
						getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(tempFile));
						startActivityForResult(getImageByCamera,
								Constant.TAKE_PHOTO);
						// 相册
					} else if (1 == result) {
						Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
						getImage.addCategory(Intent.CATEGORY_OPENABLE);
						getImage.setType("image/jpeg");
						startActivityForResult(getImage, Constant.PICK_PHOTO);
					}
				}
			});
			dialog.show();

			break;
		case R.id.nickname_edit:
			it = new Intent(self, ReviseNameActivity.class);
			it.putExtra("name", nicknameT.getText().toString());
			it.putExtra("start", "1");
			startActivity(it);
			break;
		case R.id.name_edit:
			it = new Intent(self, ReviseNameActivity.class);
			it.putExtra("name", nameT.getText().toString());
			it.putExtra("start", "2");
			startActivity(it);

			break;
		case R.id.phone_edit:
			it = new Intent(self, RevisePhoneActivity.class);
			startActivity(it);
			break;
		case R.id.sex_edit:
			ReviseSexDialog sexdialog = new ReviseSexDialog(self);
			sexdialog.setOnSexResultListener(new OnSexResultListener() {

				@Override
				public void onResult(String sex) {
					if ("man".equals(sex)) {
						editSex("1");
					} else if ("woman".equals(sex)) {
						editSex("2");
					}
					// TODO Auto-generated method stub

				}
			});
			sexdialog.show();
			break;
		case R.id.email_edit:
			it = new Intent(self, ReviseEmailActivity.class);
			it.putExtra("email", mailboxT.getText().toString());
			startActivityForResult(it, EMAIL);

			break;
		case R.id.password_edit:
			it = new Intent(self, RevisePswdActivity.class);
			it.putExtra("oldPwd", "123");
			startActivity(it);
			break;

		default:
			break;
		}
		// TODO Auto-generated method stub
	}

	private void editSex(final String sex) {
		DhNet net = new DhNet(API.editSex);
		net.addParam("uid", user.getUid());
		net.addParam("token", user.getToken());
		net.addParam("sex", sex);
		net.doPostInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				hidenProgressDialog();
				if (response.isSuccess()) {
					sexT.setText("1".equals(sex) ? "男" : "女");
					showToast("更换性别成功");
				}
			}
		});
	}

}
