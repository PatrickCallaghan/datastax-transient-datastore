package com.datastax.global.dao;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class ObjectData {
	private String key;
	private String value;
	
	public ObjectData(){}
	
	public ObjectData(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {		
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isEmpty(){
		return key==null;
	}
	
	@Override
	public String toString() {
		return "ObjectData [key=" + key + ", value=" + value + "]";
	}
}
