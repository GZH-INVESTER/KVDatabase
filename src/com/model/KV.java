package com.model;

import java.io.Serializable;

public class KV implements Serializable {

	private static final long serialVersionUID = 7965470946723147485L;

	private Integer method;

	private Integer key;

	private String value;
	
	private Integer n;

	public KV() {

	}

	public KV(Integer method, Integer key, String value,Integer n) {

		this.method = method;

		this.key = key;

		this.value = value;
		
		this.n = n;

	}

	public Integer getMethod() {

		return method;

	}

	public void setMethod(Integer method) {

		this.method = method;

	}

	public Integer getKey() {

		return key;

	}

	public void setKey(Integer key) {

		this.key = key;

	}

	public String getValue() {

		return value;

	}

	public void setValue(String value) {

		this.value = value;

	}
	public Integer getN() {
		return n;
	}
	@Override
  public String toString() {
  return "KV [method=" + method + ", key=" + key + ", value=" + value+ ",n="+n+"]";
  }
}