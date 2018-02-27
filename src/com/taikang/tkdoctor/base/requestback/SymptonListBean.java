package com.taikang.tkdoctor.base.requestback;

import java.io.Serializable;
import java.util.ArrayList;

import com.taikang.tkdoctor.bean.Response;

public class SymptonListBean extends Response implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<SymptonBean> resultlist;
	public ArrayList<SymptonBean> getResultlist() {
		return resultlist;
	}

	public void setResultlist(ArrayList<SymptonBean> resultlist) {
		this.resultlist = resultlist;
	}
	public class SymptonBean extends Response{
		private static final long serialVersionUID = 1L;
		private String symptom;
		private String symptomid;
		public String getSymptom() {
			return symptom;
		}
		public void setSymptom(String symptom) {
			this.symptom = symptom;
		}
		public String getSymptomid() {
			return symptomid;
		}
		public void setSymptomid(String symptomid) {
			this.symptomid = symptomid;
		}
		}


}
