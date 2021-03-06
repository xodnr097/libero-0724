package com.libero.service.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Publish implements Serializable {
	
	private int prodNo;
	private String prodType;
	private String prodName;
	private String prodDetail;
	private int retailPrice;
	private int printPrice;
	private String blindCode;
	private String prodThumbnail;
	private String coverFile;
	private String discountCode;
	private String creator;
	private String author;
	private String purposeCode;
	private String manuFile;
	private int bookPage;
	private String colorType;
	private String sizeType;
	private String coverType;
	private String innerType;
	private short tempCode;
	private String factoryId;
	
	private String coverSelect;
	private String imgType;
	private String imgSelect;
	
	public Publish() {
		
	}

}
