package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class MyHealthPlanListBean extends Response implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<HealthPlanBean> resultlist;
	public ArrayList<HealthPlanBean> getResultlist() {
		return resultlist;
	}
	public void setResultlist(ArrayList<HealthPlanBean> resultlist) {
		this.resultlist = resultlist;
	}
	public  class HealthPlanBean extends Response{
		private static final long serialVersionUID = 1L;
		private String warn;
		private String content;	
		private String planid;	
		private String doplanid;
		private String title;//自定义
		private String customize;
		private String status;
		private String repeat;
		private String bdate;
		private String tag;

		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDoplanid() {
			return doplanid;
		}
		public void setDoplanid(String doplanid) {
			this.doplanid = doplanid;
		}
		public String getPlanid() {
			return planid;
		}
		public void setPlanid(String planid) {
			this.planid = planid;
		}
		public String getCustomize() {
			return customize;
		}
		public void setCustomize(String customize) {
			this.customize = customize;
		}
		public String getWarn() {
			return warn;
		}
		public void setWarn(String warn) {
			this.warn = warn;
		}
		public String getRepeat() {
			return repeat;
		}
		public void setRepeat(String repeat) {
			this.repeat = repeat;
		}
		public String getBdate() {
			return bdate;
		}
		public void setBdate(String bdate) {
			this.bdate = bdate;
		}
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
	}

}
