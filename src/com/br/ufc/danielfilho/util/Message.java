package com.br.ufc.danielfilho.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message implements Serializable{
	public static final String KEY_COLUMNS = "KEY_COLUMNS";
	public static final String KEY_COUNT_ROWS = "KEY_COUNT_ROWS";
	public static final String KEY_VET_XPREV = "KEY_VET_XPREV";
	public static final String KEY_ROW_SUM = "KEY_ROW_SUM";
	public static final String KEY_CURRENT_ROW = "KEY_CURRENT_ROW";
	public static final String KEY_MAT_A = "KEY_MAT_A";
	
	public static final int START_CALC = 0;
	public static final int END_PROCCESS = 1;
	public static final int ROW_SUM_ENDS = 2;
	
	private int rank;
	private int type;
	private Map<String, Object> map;
	
	public Message(int type) {
		this.type = type;
		this.map = new HashMap<String, Object>();
	}
	
	public void setObject(String key, Object data){
		this.putObject(key, data);
	}
	
	public Object getObject(String key){
		return this.getData(key);
	}
	
	public Message(){ 
		super();
		this.map = new HashMap<String, Object>();
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void putObject(String key, Object data){
		this.map.put(key, data);
	}
	
	public Object getData(String key){
		return this.map.get(key);
	}
	
	
	
}
