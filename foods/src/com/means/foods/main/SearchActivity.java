package com.means.foods.main;

import java.sql.SQLException;
import java.util.List;

import net.duohuo.dhroid.adapter.PSAdapter;
import net.duohuo.dhroid.ioc.IocContainer;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.SearchHistory;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity extends FoodsBaseActivity {

	ListView listV;
	Dao<SearchHistory, Integer> searHistoryDao;

	View headV;
	EditText contentE;

	PSAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
	}

	@Override
	public void initView() {
		if (getIntent().getIntExtra("type", 0) == 0) {
			setTitle("热门搜索");
		} else {
			setTitle("餐厅搜索");
		}
		listV = (ListView) findViewById(R.id.listview);
		headV = LayoutInflater.from(self).inflate(R.layout.head_search, null);
		contentE = (EditText) headV.findViewById(R.id.content);
		headV.findViewById(R.id.search).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						String content = contentE.getText().toString();
						if (TextUtils.isEmpty(content)) {
							return;
						}
						Intent it = new Intent(self, SearchResultActivity.class);
						it.putExtra("keyword", content);
						it.putExtra("cityId",
								getIntent().getStringExtra("cityId"));
						it.putExtra("type", getIntent().getIntExtra("type", 0));
						startActivity(it);
						saveSearch(content);
					}
				});
		listV.addHeaderView(headV);
		adapter = new PSAdapter(self, R.layout.item_search);
		adapter.addField("name", R.id.name);
		listV.setAdapter(adapter);
		listV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SearchHistory history = (SearchHistory) adapter
						.getTItem(position-1);
				Intent it = new Intent(self, SearchResultActivity.class);
				it.putExtra("keyword", history.getName());
				it.putExtra("cityId", getIntent().getStringExtra("cityId"));
				it.putExtra("type", getIntent().getIntExtra("type", 0));
				startActivity(it);
			}
		});

		OrmLiteSqliteOpenHelper daoHelper = IocContainer.getShare().get(
				OrmLiteSqliteOpenHelper.class);
		try {
			searHistoryDao = daoHelper.getDao(SearchHistory.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			List<SearchHistory> searchlist = searHistoryDao.queryForAll();
			adapter.addAll(searchlist);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 保存数据
	private void saveSearch(String name) {

		SearchHistory search = new SearchHistory();
		search.setName(name);
		try {
			if (searHistoryDao.queryForEq("name", name).size() < 1) {
				searHistoryDao.create(search);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
