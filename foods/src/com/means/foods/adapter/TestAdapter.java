package com.means.foods.adapter;

import com.means.foods.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TestAdapter extends BaseAdapter {

	Context mContext;

	LayoutInflater mLayoutInflater;

	int count = 20;

	public TestAdapter(Context context) {
		this.mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	public void setData(int mcount) {
		count = count + mcount;
		notifyDataSetChanged();
	}

	public void refresh() {
		this.count = 20;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		System.out.println("count:" + count + "");
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		if (arg1 == null) {
			arg1 = mLayoutInflater.inflate(R.layout.item_test, null);
		}

		TextView text = (TextView) arg1.findViewById(R.id.text);
		text.setText(arg0 + "");
		// TODO Auto-generated method stub
		return arg1;
	}

}
