package net.duohuo.dhroid.reclcleadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.duohuo.dhroid.adapter.FieldMap;
import net.duohuo.dhroid.adapter.FieldMapImpl;
import net.duohuo.dhroid.adapter.ValueFix;
import net.duohuo.dhroid.ioc.IocContainer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.duohuo.dhroid.reclcleadapter.RecyclerBaseAdapter.BaseViewHolder;

public abstract class RecyclerBaseAdapter<T> extends
		RecyclerView.Adapter<BaseViewHolder> {

	public List<FieldMap> fields;

	public List<T> mVaules = null;

	private final Object mLock = new Object();

	protected int mResource;

	protected int mDropDownResource;

	protected boolean mNotifyOnChange = true;

	public LayoutInflater mInflater;

	public Map<Integer, InViewClickListener> canClickItem;

	public boolean isReuse = true;

	public Context mContext;

	public ValueFix fixer;

	public Class jumpClazz;

	public String jumpKey;

	int headViewCount = 0;

	public static int headViewType = 10086;

	View headView;

	public Class getJumpClazz() {
		return jumpClazz;
	}

	public String getJumpKey() {
		return jumpKey;
	}

	public void setJumpKey(String jumpKey) {
		this.jumpKey = jumpKey;
	}

	public void setJump(Class jumpClazz, String jumpkey) {
		this.jumpClazz = jumpClazz;
	}

	public RecyclerBaseAdapter(Context context, int mResource,
			boolean isViewReuse) {
		super();
		fields = new ArrayList<FieldMap>();
		this.mResource = mResource;
		isReuse = isViewReuse;
		this.mDropDownResource = mResource;
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mVaules = new ArrayList<T>();
		fixer = IocContainer.getShare().get(ValueFix.class);
	}

	public RecyclerBaseAdapter(Context context, int mResource) {
		this(context, mResource, true);
	}

	/**
	 * 添加Field
	 * 
	 * @param key
	 * @param refid
	 * @return
	 */
	public RecyclerBaseAdapter addField(String key, Integer refid) {
		FieldMap bigMap = new FieldMapImpl(key, refid);
		fields.add(bigMap);
		return this;
	}

	/**
	 * 添加Field
	 * 
	 * @param key
	 * @param refid
	 * @param type
	 * @return
	 */
	public RecyclerBaseAdapter addField(String key, Integer refid, String type) {
		FieldMap bigMap = new FieldMapImpl(key, refid, type);
		fields.add(bigMap);
		return this;
	}

	/**
	 * 添加Field
	 * 
	 * @param fieldMap
	 * @return
	 */
	public RecyclerBaseAdapter addField(FieldMap fieldMap) {
		fields.add(fieldMap);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getValues() {
		return (List<T>) mVaules;
	}

	public void add(T one) {
		synchronized (mLock) {
			mVaules.add(one);
		}
		if (mNotifyOnChange)
			notifyDataSetChanged();
	}

	public void addAll(List<T> ones) {
		synchronized (mLock) {
			mVaules.addAll(ones);
		}
		if (mNotifyOnChange)
			notifyDataSetChanged();
	}

	public void insert(int index, T one) {
		synchronized (mLock) {
			mVaules.add(index, one);
		}
		if (mNotifyOnChange)
			notifyDataSetChanged();
	}

	public void remove(int index) {
		synchronized (mLock) {
			mVaules.remove(index);
		}
		if (mNotifyOnChange)
			notifyDataSetChanged();
	}

	public void clear() {
		synchronized (mLock) {
			mVaules.clear();
		}
		if (mNotifyOnChange)
			notifyDataSetChanged();
	}

	public void setNotifyOnChange(boolean notifyOnChange) {
		mNotifyOnChange = notifyOnChange;
	}

	public Object getItem(int position) {
		return mVaules.get(position);
	}

	@SuppressWarnings("unchecked")
	public <T> T getTItem(int position) {
		return (T) mVaules.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public String getTItemId(int position) {

		return position + "";
	}

	public void addheadView(View headView) {
		this.headView = headView;
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		headView.setLayoutParams(lp);
		headViewCount++;
	}

	public abstract void bindView(View itemV, BaseViewHolder holder,
			int position, T jo);

	/**
	 * 将值和控件绑定 可以防止图片的移位
	 * 
	 * @param position
	 * @param v
	 * @param o
	 */
	public void bindValue(final Integer position, View v, Object o,
			DisplayImageOptions options) {
		if (o == null)
			o = "";
		if (v instanceof ImageView) {
			ImageView imagev = (ImageView) v;
			if (o instanceof Drawable) {
				imagev.setImageDrawable((Drawable) o);
			} else if (o instanceof Bitmap) {
				imagev.setImageBitmap((Bitmap) o);
			} else if (o instanceof Integer) {
				imagev.setImageResource((Integer) o);
			} else if (o instanceof String) {
				ImageLoader.getInstance().displayImage((String) o,
						(ImageView) v, options);
			}
		} else if (v instanceof TextView) {
			if (o instanceof CharSequence) {
				((TextView) v).setText((CharSequence) o);
			} else {
				((TextView) v).setText(o.toString());
			}
		}
	}

	private void bindInViewListener(final BaseViewHolder itemV,
			final Integer position, final Object valuesMap) {
		if (canClickItem != null) {
			for (Integer key : canClickItem.keySet()) {
				View inView = itemV.get(key);
				final InViewClickListener inviewListener = canClickItem
						.get(key);
				if (inView != null && inviewListener != null) {
					inView.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							inviewListener.OnClickListener(itemV, v, position,
									valuesMap);
						}
					});
				}
			}
		}
	}

	public void setOnInViewClickListener(Integer key,
			InViewClickListener inViewClickListener) {
		if (canClickItem == null)
			canClickItem = new HashMap<Integer, InViewClickListener>();
		canClickItem.put(key, inViewClickListener);
	}

	public interface InViewClickListener {
		public void OnClickListener(RecyclerBaseAdapter.BaseViewHolder parentV,
				View v, Integer position, Object values);
	}

	public int getItemCount() {
		// TODO Auto-generated method stub
		return mVaules.size() + headViewCount;
	}

	public void onBindViewHolder(RecyclerBaseAdapter.BaseViewHolder viewHolder,
			int position) {
		if (position >= headViewCount) {
			int realPosition = position - headViewCount;
			bindView(viewHolder.itemView, viewHolder, realPosition,
					mVaules.get(realPosition));
			bindInViewListener(viewHolder, realPosition,
					mVaules.get(realPosition));
		} else {
			return;
		}
	}

	@Override
	public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
		BaseViewHolder holder;
		if (viewtype == headViewType) {
			holder = new HeadViewHolder(headView);
		} else {
			holder = buildNormalHolder(viewtype,parent);
		}
		return holder;
	}

	public abstract BaseViewHolder buildNormalHolder(int viewType,ViewGroup parent);

	public abstract int getMyItemViewType(int position);

	@Override
	public int getItemViewType(int position) {

		if (position < headViewCount) {

			return headViewType;
		} else {
			return getMyItemViewType(position);
		}
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 * 大家都用的viewholder
	 * 
	 */
	public class BaseViewHolder extends ViewHolder {
		Map<Integer, View> views;

		public BaseViewHolder(View view) {
			super(view);
			views = new HashMap<Integer, View>();
			for (Iterator<FieldMap> iterator = fields.iterator(); iterator
					.hasNext();) {
				FieldMap fieldMap = iterator.next();
				views.put(fieldMap.getRefId(),
						view.findViewById(fieldMap.getRefId()));
			}
		}

		public void put(Integer id, View v) {
			views.put(id, v);
		}

		public View get(Integer id) {
			return views.get(id);
		}

	}

	public class HeadViewHolder extends BaseViewHolder {

		public HeadViewHolder(View arg0) {
			super(arg0);
		}

	}

	public class FootViewHolder extends BaseViewHolder {

		public FootViewHolder(View arg0) {
			super(arg0);
			// TODO Auto-generated constructor stub
		}

	}

}
