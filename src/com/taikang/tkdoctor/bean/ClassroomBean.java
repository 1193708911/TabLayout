package com.taikang.tkdoctor.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ClassroomBean {
	/**
	 * 课堂类型
	 */
	private String type;
	/**
	 * 课堂内容
	 */
	private ArrayList<ClassContent> lists = new ArrayList<ClassContent>();

	/**
	 * 头条
	 */
	private ArrayList<ClassContent> topLists = new ArrayList<ClassContent>();

	/**
	 * 分类标题
	 */
	private String title;

	private String status;

	public ArrayList<ClassContent> getTopLists() {
		return topLists;
	}

	public void setTopLists(ArrayList<ClassContent> topLists) {
		this.topLists = topLists;
	}

	/**
	 * 总条数
	 */
	private String totalNum;

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<ClassContent> getLists() {
		return lists;
	}

	public void setLists(ArrayList<ClassContent> lists) {
		this.lists = lists;
	}

	public void parJson(String json) throws Exception {
		JSONObject obj = new JSONObject(json);
		if (!obj.isNull("status")) {
			this.status = obj.getString("status");
		}
		if (!obj.isNull("total_data_number")) {
			this.totalNum = obj.getString("total_data_number");
		}
		if (!obj.isNull("headline")) {
			ClassContent mclass = new ClassContent();
			mclass.parjson(obj.getJSONObject("headline"));
			topLists.add(mclass);
		}
		JSONArray content = obj.getJSONArray("content");
		for (int i = 0; i < content.length(); i++) {
			ClassContent mclass = new ClassContent();
			mclass.parjson(content.getJSONObject(i));
			mclass.setJsonData(content.optString(i, ""));
			lists.add(mclass);
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("\ninfo:{").append("status:").append(status)
				.append("\ntotalNum:").append(totalNum);
		for (int i = 0; i < lists.size(); i++) {
			sb.append(lists.get(i).toString());
		}
		sb.append("\n}");
		return sb.toString();
	}
}
