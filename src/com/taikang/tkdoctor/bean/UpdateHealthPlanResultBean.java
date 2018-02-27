package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 获取完成健康计划bean
 * @author Administrator
 *
 */
public class UpdateHealthPlanResultBean extends Response implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<HealthPlanResultBean> resultlist;
	public ArrayList<HealthPlanResultBean> getResultlist() {
		return resultlist;
	}
	public void setResultlist(ArrayList<HealthPlanResultBean> resultlist) {
		this.resultlist = resultlist;
	}
	public  class HealthPlanResultBean extends Response{
		private static final long serialVersionUID = 1L;
		private String status;
		private String doplanid;
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

	}
	

}
