package net.duohuo.dhroid.view.swipelayout;

import net.duohuo.dhroid.R;
import net.duohuo.dhroid.adapter.INetAdapter.LoadSuccessCallBack;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.reclcleadapter.NetJSonRecycleAdapter;
import net.duohuo.dhroid.reclcleadapter.RecyclerBaseAdapter;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NetSwipeRefreshAndMoreLayout extends BaseSwipeRefreshLayout {

	Context mContext;

	// Header View
	private ProgressBar progressBar;
	private TextView textView;
	private ImageView imageView;

	// Footer View
	private ProgressBar footerProgressBar;
	private TextView footerTextView;
	private ImageView footerImageView;

	RotateAnimation animation, reverseAnimation;

	boolean isEnable = false;

	NetJSonRecycleAdapter mAdapter;

	RecyclerView mRecyclerView;

	View footerView;

	public NetSwipeRefreshAndMoreLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);// 特效animat

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(250);
		reverseAnimation.setFillAfter(true);// 特效reverseAnimation设置

		setHeaderView(createHeaderView());// add headerView
		setFooterView(createFooterView());
		setTargetScrollWithLayout(true);
		setOnPullRefreshListener(new OnPullRefreshListener() {

			@Override
			public void onRefresh() {
				textView.setText("正在刷新");
				imageView.clearAnimation();
				imageView.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);

				if (mAdapter != null) {
					mAdapter.refresh();
				}
			}

			@Override
			public void onPullDistance(int distance) {
				System.out.println("distance" + distance);
				// pull distance
			}

			@Override
			public void onPullEnable(boolean enable) {
				textView.setText(enable ? "松开刷新" : "下拉刷新");
				imageView.setVisibility(View.VISIBLE);

				if (isEnable != enable) {
					if (enable) {
						imageView.clearAnimation();
						imageView.startAnimation(animation);
					} else {
						imageView.clearAnimation();
						imageView.startAnimation(reverseAnimation);
					}
					isEnable = enable;

				}
				// imageView.setRotation(enable ? 180 : 0);
			}
		});

		setOnPushLoadMoreListener(new OnPushLoadMoreListener() {

			@Override
			public void onLoadMore() {

				if (mAdapter != null && mAdapter.hasMore()) {
					footerTextView.setText("正在加载...");
					footerImageView.setVisibility(View.GONE);
					footerProgressBar.setVisibility(View.VISIBLE);
					mAdapter.showNext();
				} else {
					if (!mLoadMore) {
						loadMoreDisable();
					}
				}
			}

			@Override
			public void onPushEnable(boolean enable) {
				if (mAdapter != null && mAdapter.hasMore()) {
					footerTextView.setText(enable ? "松开加载" : "上拉加载");
					footerImageView.setVisibility(View.VISIBLE);
					footerImageView.setRotation(enable ? 0 : 180);
				}
			}

			@Override
			public void onPushDistance(int distance) {
				// TODO Auto-generated method stub

			}

		});

	}

	@SuppressWarnings("deprecation")
	public void bind(NetJSonRecycleAdapter adapter, RecyclerView view) {
		this.mAdapter = adapter;
		mAdapter.setOnLoadSuccess(new LoadSuccessCallBack() {

			@Override
			public void callBack(Response response) {

				setRefreshing(false);
				progressBar.setVisibility(View.GONE);
				footerProgressBar.setVisibility(View.GONE);
				if (mAdapter.hasMore()) {
					footerImageView.setVisibility(View.VISIBLE);
				} else {
					footerImageView.setVisibility(View.GONE);
					footerTextView.setVisibility(View.GONE);
				}

				setLoadMore(false);
			}
		});

		this.mRecyclerView = view;

		mRecyclerView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				LayoutManager mLayoutManager = mRecyclerView.getLayoutManager();
				int count = mLayoutManager.getItemCount();
				if (mLayoutManager instanceof LinearLayoutManager) {
					int lastVisibleItem = ((LinearLayoutManager) mLayoutManager)
							.findLastVisibleItemPosition();
					// lastVisibleItem >= totalItemCount - 4
					// 表示剩下4个item自动加载，各位自由选择
					// dy>0 表示向下滑动
					if (lastVisibleItem >= count - 1 && dy > 0) {
						startloadMore();
					}
				} else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
					StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) mLayoutManager;
					int[] lastItems = new int[2];
					staggeredGridLayoutManager
							.findLastCompletelyVisibleItemPositions(lastItems);
					int lastItem = Math.max(lastItems[0], lastItems[1]);
					if (lastItem == count - 1) {
						startloadMore();
					}
				}
			}
		});
	}

	private View createHeaderView() {
		View headerView = LayoutInflater.from(mContext).inflate(
				R.layout.layout_head, null);
		progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
		textView = (TextView) headerView.findViewById(R.id.text_view);
		textView.setText("下拉刷新");
		imageView = (ImageView) headerView.findViewById(R.id.image_view);
		imageView.setVisibility(View.VISIBLE);
		imageView.setImageResource(R.drawable.down_arrow);
		progressBar.setVisibility(View.GONE);
		return headerView;
	}

	private View createFooterView() {
		View footerView = LayoutInflater.from(mContext).inflate(
				R.layout.layout_footer, null);
		footerProgressBar = (ProgressBar) footerView
				.findViewById(R.id.footer_pb_view);
		footerImageView = (ImageView) footerView
				.findViewById(R.id.footer_image_view);
		footerTextView = (TextView) footerView
				.findViewById(R.id.footer_text_view);
		footerProgressBar.setVisibility(View.GONE);
		footerImageView.setVisibility(View.VISIBLE);
		footerImageView.setImageResource(R.drawable.down_arrow);
		footerTextView.setText("上拉加载更多...");
		return footerView;
	}

	private void startloadMore() {
		if (!mLoadMore && mAdapter.hasMore()) {
			mLoadMore = true;
			pushDistance = mFooterViewHeight;
			mFooterViewContainer.setVisibility(View.VISIBLE);
			mFooterViewContainer.bringToFront();
			mFooterViewContainer.offsetTopAndBottom(-pushDistance);
			footerTextView.setText("正在加载...");
			footerTextView.setVisibility(View.VISIBLE);
			footerImageView.setVisibility(View.GONE);
			footerProgressBar.setVisibility(View.VISIBLE);
			mAdapter.showNext();
		}
	}

	public void startRefresh() {

		// isautoRefresh = true;
		// mHeadViewContainer.offsetTopAndBottom(300);
		// mCurrentTargetOffsetTop = mHeadViewContainer.getTop();
		// mHeadViewContainer.offsetTopAndBottom(42);
		// mFrom = 192;
		// mHeadViewContainer.setAnimationListener(mRefreshListener);
		// progressBar.setVisibility(View.GONE);

		if (!mRefreshing) {
			mNotify = true;
			animateOffsetToCorrectPosition(20, mRefreshListener);
			textView.setText("正在刷新");
			imageView.setVisibility(View.GONE);
			progressBar.setVisibility(View.VISIBLE);
			mHeadViewContainer.setVisibility(View.VISIBLE);
			mRefreshing = true;
		}
	}
}
