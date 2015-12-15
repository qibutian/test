package com.means.foods.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.duohuo.dhroid.net.HttpManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;

/**
 * �ĵ�����
 * 
 * @author Administrator
 * 
 */

public class HttpDownloader {
	private URL url = null;
	FileUtils fileUtils;

	String uid, token, order_id;

	private final static String LINEND = "\r\n";

	private final static String BOUNDARY = "---------------------------7da2137580612"; // 数据分隔线

	private final static String PREFIX = "--";

	private final static String MUTIPART_FORMDATA = "multipart/form-data";

	private final static String CHARSET = "utf-8";

	private final static String CONTENTTYPE = "application/octet-stream";

	public HttpDownloader(Context context, String uid, String token,
			String order_id) {
		fileUtils = new FileUtils(context);
		this.uid = uid;

		this.token = token;
		this.order_id = order_id;
	}

	public int downfile(String urlStr, String path, String fileName) {
		if (fileUtils.isFileExist(fileName)) {
			return 1;
		} else {
			try {
				InputStream input = null;
				input = getInputStream(urlStr);
				int resultFile = fileUtils.write2SDFromInput(path, fileName,
						input);
				if (resultFile != 1) {
					return -1;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				return -2;
			}

		}
		return 0;
	}

	//
	public InputStream getInputStream(String urlStr) throws IOException {
		InputStream is = null;
		try {
			String entryText = "&token=" + token + "&uid=" + uid + "&order_id="
					+ order_id;
			url = new URL(urlStr + entryText);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			// 实现连接
			urlConn.setRequestProperty("Charset", CHARSET);
			urlConn.setRequestProperty("Accept-Charset", "utf-8");
			urlConn.setRequestProperty("contentType", "utf-8");
			urlConn.setRequestMethod("GET");

			// DataOutputStream dos = new DataOutputStream(
			// urlConn.getOutputStream());
			// dos.write(entryText.getBytes());
			urlConn.connect();
			is = urlConn.getInputStream();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Log.e("TAG", e.getMessage());
			e.printStackTrace();
		}

		return is;
	}

	// public InputStream getInputStream(String urlStr) throws IOException {
	//
	// HttpPost httppost = new HttpPost(urlStr);
	// List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	//
	// formparams.add(new BasicNameValuePair("token", token));
	// formparams.add(new BasicNameValuePair("uid", uid));
	// formparams.add(new BasicNameValuePair("order_id", order_id.toString()));
	// UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
	// "UTF-8");
	// httppost.setEntity(entity);
	// HttpResponse response = HttpManager.execute(httppost);
	// HttpEntity rentity = response.getEntity();
	// if (rentity != null) {
	// System.out.println("rentity:" + rentity.getContentLength());
	// return rentity.getContent();
	// }
	// return null;
	// }

	private String bulidFormText(Map<String, Object> paramText) {
		if (paramText == null || paramText.isEmpty())
			return "";
		StringBuffer sb = new StringBuffer("");
		for (Entry<String, Object> entry : paramText.entrySet()) {
			sb.append(PREFIX).append(BOUNDARY).append(LINEND);
			sb.append("Content-Disposition:form-data;name=\"" + entry.getKey()
					+ "\"" + LINEND);
			// sb.append("Content-Type:text/plain;charset=" + CHARSET + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}
		return sb.toString();
	}
}