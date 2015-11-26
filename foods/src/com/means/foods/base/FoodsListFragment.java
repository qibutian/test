package com.means.foods.base;

import net.duohuo.dhroid.util.DhUtil;

import com.means.foods.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.loadmore.LoadMoreContainer;
import in.srain.cube.views.ptr.loadmore.LoadMoreHandler;
import in.srain.cube.views.ptr.loadmore.LoadMoreListViewContainer;
import android.support.v4.app.Fragment;

public class FoodsListFragment extends Fragment {

	public void initRefreshListView(final PtrFrameLayout mPtrFrame,
			LoadMoreListViewContainer loadMoreListViewContainer) {

		final StoreHouseHeader header = new StoreHouseHeader(getActivity());
		header.setPadding(0, DhUtil.dip2px(getActivity(), 15), 0, 0);
		header.initWithString("Foods");
		header.setTextColor(getResources().getColor(R.color.text_yellow));
		mPtrFrame.addPtrUIHandler(header);
		mPtrFrame.setHeaderView(header);
		mPtrFrame.setPinContent(false);

//		loadMoreListViewContainer.useDefaultHeader();
//		loadMoreListViewContainer.setAutoLoadMore(true);
//		loadMoreListViewContainer.setShowLoadingForFirstPage(true);

	}

}
