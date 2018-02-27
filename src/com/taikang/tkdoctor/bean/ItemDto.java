package com.taikang.tkdoctor.bean;

import java.io.Serializable;

public class ItemDto implements Serializable {

	private static final long serialVersionUID = 5475064966013134014L;
	private String code;
	private String name;

	public ItemDto() {
		super();
	}

	public ItemDto(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
