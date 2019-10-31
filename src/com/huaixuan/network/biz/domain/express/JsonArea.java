package com.huaixuan.network.biz.domain.express;

import java.util.List;

public class JsonArea {
	private Region[] array;

	public Region[] getArray() {
		return array;
	}

	public void setArray(Region[] array) {
		this.array = array;
	}

	public JsonArea(List arraylist) {
		int length = arraylist.size();
		array = new Region[length];
		for (int i = 0; i < length; i++) {
			array[i] = (Region) arraylist.get(i);
		}
	}
	
	public JsonArea(){
		
	}

	
}
