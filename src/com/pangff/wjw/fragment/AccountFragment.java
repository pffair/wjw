package com.pangff.wjw.fragment;

import java.io.File;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pangff.wjw.AccountChangeActivity;
import com.pangff.wjw.BaseActivity;
import com.pangff.wjw.ExchangeDetailActivity;
import com.pangff.wjw.ExchangeIntegrationActivity;
import com.pangff.wjw.R;
import com.pangff.wjw.RewardActivity;
import com.pangff.wjw.VipTransferActivity;
import com.pangff.wjw.VipTransferDetailActivity;
import com.pangff.wjw.WithDrawalsApplyActivity;
import com.pangff.wjw.WithDrawalsDetailActivity;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.MyAccountRequest;
import com.pangff.wjw.model.MyAccountResponse;
import com.pangff.wjw.util.DialogUtil;
import com.pangff.wjw.util.ImageUtils;
import com.pangff.wjw.util.StringUtil;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.view.ClipActivity;
import com.pangff.wjw.view.LoadingView;
import com.pangff.wjw.view.OnOneOffClickListenerInDialog;

/**
 * fragment基类
 * 
 * @author pangff
 */
public class AccountFragment extends PagerFragment {

	@AndroidView(R.id.withdrawalsApplyT)
	TextView withdrawalsApplyT;

	@AndroidView(R.id.withdrawalsDetailT)
	TextView withdrawalsDetailT;

	@AndroidView(R.id.vipTransferT)
	TextView vipTransferT;

	@AndroidView(R.id.transferDetailT)
	TextView transferDetailT;

	@AndroidView(R.id.exchangeT)
	TextView exchangeT;

	@AndroidView(R.id.exchangeDtailT)
	TextView exchangeDtailT;

	@AndroidView(R.id.awardDtailT)
	TextView awardDtailT;

	@AndroidView(R.id.myAccount)
	LinearLayout myAccount;

	@AndroidView(R.id.showIdT)
	TextView showIdT;

	@AndroidView(R.id.showNameT)
	TextView showNameT;

	@AndroidView(R.id.showLevelT)
	TextView showLevelT;

	@AndroidView(R.id.showRemainT)
	TextView showRemainT;

	@AndroidView(R.id.showIntegralT)
	TextView showIntegralT;

	@AndroidView(R.id.accountLoadingFrame)
	FrameLayout accountLoadingFrame;

	LoadingView accountLoading;
	
	@AndroidView(R.id.hostImage)
	ImageView hostImage;

	public static final String METHOD_ZHANGHU = "zhanghu";

	public static final int REQUEST_CODE_LOCAL = 3001;
	public static final int REQUEST_CODE_CAMERA = 3003;
	public static final int REQUEST_CODE_CLIP = 3005;
	private String cameraPicPath = "";
	private String imagePath;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_account, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		myAccount.setOnClickListener(onOneOffClickListener);
		withdrawalsApplyT.setOnClickListener(onOneOffClickListener);
		withdrawalsDetailT.setOnClickListener(onOneOffClickListener);
		vipTransferT.setOnClickListener(onOneOffClickListener);
		transferDetailT.setOnClickListener(onOneOffClickListener);
		exchangeT.setOnClickListener(onOneOffClickListener);
		exchangeDtailT.setOnClickListener(onOneOffClickListener);
		awardDtailT.setOnClickListener(onOneOffClickListener);
		hostImage.setOnClickListener(onOneOffClickListener);
		setHeader();
	}

	private void doRequestAccount() {
		accountLoading = new LoadingView(this.getActivity());
		accountLoading.addLoadingTo(accountLoadingFrame);
		MyAccountRequest myAccountRequest = new MyAccountRequest();
		String xml = myAccountRequest.getParams(METHOD_ZHANGHU);
		new HttpRequest<MyAccountResponse>().postDataXml(METHOD_ZHANGHU, xml,
				this, MyAccountResponse.class, false);
	}

	@Override
	public void onSuccess(String mothod, Object result) {
		super.onSuccess(mothod, result);
		if (mothod.equals(METHOD_ZHANGHU)) {
			accountLoading.removeLoadingFrom(accountLoadingFrame);
			MyAccountResponse myAccountResponse = (MyAccountResponse) result;
			if (myAccountResponse != null) {
				showIdT.setText(myAccountResponse.userid);
				showNameT.setText(myAccountResponse.body.username);
				showLevelT.setText(myAccountResponse.body.userleve);
				showRemainT.setText(myAccountResponse.body.money);
				showIntegralT.setText(myAccountResponse.body.jifen);
			}
		}
	}

	@Override
	public void onFailure(String mothod, String errorMsg) {
		super.onFailure(mothod, errorMsg);
		accountLoading.removeLoadingFrom(accountLoadingFrame);
	}

	@Override
	protected void initData() {
		super.initData();
		if (StringUtil.isEmpty(showIdT.getText().toString())) {
			doRequestAccount();
		}
	}

	protected void onMyClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.myAccount:
			AccountChangeActivity.invotoacountchange((BaseActivity) this
					.getActivity());
			break;
		case R.id.withdrawalsApplyT:
			WithDrawalsApplyActivity
					.invoteToWithDrawalsApply((BaseActivity) this.getActivity());
			break;
		case R.id.withdrawalsDetailT:
			WithDrawalsDetailActivity
					.invoteToWithDrawalsDetailApply((BaseActivity) this
							.getActivity());
			break;
		case R.id.vipTransferT:
			VipTransferActivity.invoteToVipTransfer((BaseActivity) this
					.getActivity());
			break;
		case R.id.transferDetailT:
			VipTransferDetailActivity
					.invoteToVipTransferDetail((BaseActivity) this
							.getActivity());
			break;
		case R.id.exchangeT:
			ExchangeIntegrationActivity
					.invoteToExchangeIntegration((BaseActivity) this
							.getActivity());
			break;
		case R.id.exchangeDtailT:
			ExchangeDetailActivity
					.invoteToVipTransferDetail((BaseActivity) this
							.getActivity());
			break;
		case R.id.awardDtailT:
			RewardActivity.invoteToReward((BaseActivity) this.getActivity());
			break;
		case R.id.hostImage:
			showHeadModifyDialog();
			break;
		}
	}

	/**
	 * 展示修改头像信息对话框
	 */
	private void showHeadModifyDialog() {
		int res = R.layout.set_head_dialog_logout;

		DialogUtil.showButtomPopUpDialog(this.getActivity(), res,
				new int[] { R.id.head_set_photo, R.id.head_set_file,
						R.id.popup_text_cancle },
				new OnOneOffClickListenerInDialog() {

					@Override
					public void onOneClick(Dialog dialog, View v) {
						DialogUtil.dismiss(dialog);
						switch (v.getId()) {
						case R.id.popup_text_cancle:
							break;
						case R.id.head_set_photo:
							cameraAction();
							break;
						case R.id.head_set_file:
							galleryAction();
							break;
						default:
							break;
						}
					}
				});
	}

	/**
	 * 从相机获取图片
	 */
	private void cameraAction() {
		String cameraSavePath = ImageUtils.IMAGE_SAVE_PATH;
		File savedir = new File(cameraSavePath);
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		String fileName = "headpic_temp.jpg";
		File out = new File(cameraSavePath, fileName);
		cameraPicPath = out.getAbsolutePath();
		Uri uri = Uri.fromFile(out);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(intent, REQUEST_CODE_CAMERA);
	}

	/**
	 * 调用本地图片
	 */
	private void galleryAction() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(Intent.createChooser(intent, "选择图片"),
				REQUEST_CODE_LOCAL);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CODE_CAMERA:
			case REQUEST_CODE_LOCAL:
			case REQUEST_CODE_CLIP:
				headPicReslut(requestCode, data);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 头像信息更改返回
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	private void headPicReslut(int requestCode, Intent data) {
		// 相机信息返回
		if (requestCode == REQUEST_CODE_CAMERA) {
			Intent intent = new Intent(this.getActivity(), ClipActivity.class);
			intent.putExtra(ClipActivity.EXTRA_PATH, cameraPicPath);
			startActivityForResult(intent, REQUEST_CODE_CLIP);
		}

		// 本地图片信息返回
		if (requestCode == REQUEST_CODE_LOCAL) {
			if (data == null) {
				return;
			}
			Uri thisUri = data.getData();
			if (thisUri != null) {
				Log.e("thisUri", "thisUri:" + thisUri);
				Intent intent = new Intent(this.getActivity(), ClipActivity.class);
				intent.putExtra(ClipActivity.EXTRA_PATH,
						ImageUtils.getRealPath(this.getActivity(), thisUri));
				startActivityForResult(intent, REQUEST_CODE_CLIP);
			} else {
				ToastUtil.show("加载头像失败");
			}
		}

		if (requestCode == REQUEST_CODE_CLIP && data != null) {
			Bundle extras = data.getExtras();
			if (extras != null
					&& extras.getBoolean(ClipActivity.SAVE_RESULT, false)) {
				imagePath = extras.getString(ClipActivity.SAVE_PATH);
				UserInfoUtil.getInstanse().setUserHeader(imagePath);
				setHeader();
			}
		}
	}
	
	private void setHeader(){
		String url = UserInfoUtil.getInstanse().getUserHeader();
		if(!StringUtil.isEmpty(url)){
			hostImage.setImageBitmap(BitmapFactory.decodeFile(url));
		}
	}

}