package com.means.foods.view;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.loadmore.LoadMoreContainer;
import in.srain.cube.views.ptr.loadmore.LoadMoreHandler;
import in.srain.cube.views.ptr.loadmore.LoadMoreListViewContainer;
import net.duohuo.dhroid.adapter.INetAdapter.LoadSuccessCallBack;
import net.duohuo.dhroid.adapter.NetJSONAdapter;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.DhUtil;

import com.means.foods.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class RefreshListViewAndMore extends LinearLayout {

	View contentV;

	Context mContext;

	PtrFrameLayout mPtrFrame;

	ListView listV;

	NetJSONAdapter mAdapter;

	LoadMoreListViewContainer loadMoreListViewContainer;

	OnLoadSuccess onLoadSuccess;

	View mheadV, mEmptyV;

	public RefreshListViewAndMore(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initView();
	}

	private void initView() {
		LayoutInflater.from(mContext).inflate(
				R.layout.include_refresh_listview, this);
		listV = (ListView) findViewById(R.id.listview);
		mPtrFrame = (PtrFrameLayout) findViewById(R.id.ptr_frame);
		loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
		final StoreHouseHeader header = new StoreHouseHeader(mContext);
		header.setPadding(0, DhUtil.dip2px(mContext, 15), 0, 0);
		header.initWithString("Foods");
		header.setTextColor(getResources().getColor(R.color.text_yellow));
		mPtrFrame.addPtrUIHandler(header);
		mPtrFrame.setHeaderView(header);
		mPtrFrame.setPinContent(false);

		loadMoreListViewContainer.useDefaultHeader();
		loadMoreListViewContainer.setAutoLoadMore(true);

		loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				mAdapter.showNext();
			}
		});

		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				mAdapter.refresh();
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
						listV, header);
			}
		});

		mPtrFrame.postDelayed(new Runnable() {

			@Override
			public void run() {
				mPtrFrame.autoRefresh(true);
				mAdapter.refresh();
			}
		}, 150);

	}

	public void setListViewPadding(int left, int top, int right, int bottom) {
		listV.setPadding(left, top, right, bottom);
	}

	public void addHeadView(View headV) {
		mheadV = headV;
		listV.addHeaderView(headV);
	}

	public void removeHeadView() {
		if (mheadV != null) {
			mheadV.setVisibility(View.GONE);
			mheadV.setPadding(0, -mheadV.getHeight(), 0, 0);
		}
	}

	public void showHeadView() {
		if (mheadV != null) {
			mheadV.setVisibility(View.VISIBLE);
			mheadV.setPadding(0, 0, 0, 0);
		}
	}

	public void setEmptyView(View empty) {
		mEmptyV = empty;
		mPtrFrame.addView(mEmptyV);
	}

	public void setAdapter(NetJSONAdapter adapter) {
		mAdapter = adapter;
		mAdapter.setOnLoadSuccess(new LoadSuccessCallBack() {

			@Override
			public void callBack(Response response) {

				if (onLoadSuccess != null) {
					onLoadSuccess.loadSuccess(response);
				}

				if (onLoadSuccess != null && !response.isCache()
						&& mAdapter.getPageNo() == 0) {
					if (mEmptyV != null) {
						mEmptyV.setVisibility(mAdapter.getValues().size() != 0 ? View.VISIBLE
								: View.GONE);
					}
					loadMoreListViewContainer
							.setShowLoadingForFirstPage(mAdapter.getValues()
									.size() != 0 ? true : false);
				}

				mPtrFrame.refreshComplete();
				loadMoreListViewContainer.loadMoreFinish(mAdapter.getValues()
						.size() == 0 ? true : false, mAdapter.hasMore());
			}
		});
		listV.setAdapter(mAdapter);
	}

	public OnLoadSuccess getOnLoadSuccess() {
		return onLoadSuccess;
	}

	public void setOnLoadSuccess(OnLoadSuccess onLoadSuccess) {
		this.onLoadSuccess = onLoadSuccess;
	}

	public interface OnLoadSuccess {
		void loadSuccess(Response response);
	}

}
