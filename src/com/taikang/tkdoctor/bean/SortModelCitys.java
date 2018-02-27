package com.taikang.tkdoctor.bean;

import java.util.ArrayList;

public class SortModelCitys {

	private String sortCitysLetter;//城市分类的首字母
	
	private ArrayList<SortModel> listSortModels;//同一首字母城市的集合

	public String getSortCitysLetter() {
		return sortCitysLetter;
	}

	public void setSortCitysLetter(String sortCitysLetter) {
		this.sortCitysLetter = sortCitysLetter;
	}

	public ArrayList<SortModel> getListSortModels() {
		return listSortModels;
	}

	public void setListSortModels(ArrayList<SortModel> listSortModels) {
		this.listSortModels = listSortModels;
	}
	
}
