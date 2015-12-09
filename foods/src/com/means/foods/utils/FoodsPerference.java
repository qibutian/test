package com.means.foods.utils;

import net.duohuo.dhroid.util.Perference;

public class FoodsPerference extends Perference {
	 // 第一次登陆
    public int isFirst = 0;

	public int getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
	}
    
    
}
