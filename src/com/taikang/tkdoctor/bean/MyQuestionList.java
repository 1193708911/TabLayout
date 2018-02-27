package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 问题集合
 * @author Administrator
 *
 */
public class MyQuestionList extends Response implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Questions> resultlist;
	public ArrayList<Questions> getResultlist() {
		return resultlist;
	}
	public void setResultlist(ArrayList<Questions> resultlist) {
		this.resultlist = resultlist;
	}
	/**
	 * 问题
	 * @author Administrator
	 *
	 */
	public class Questions extends Response implements Serializable{
		private static final long serialVersionUID = 1L;
		private String	id;
		private String	datacontent;
		private String	maleis;
		private String 	maleno;
		private String 	femaleis;
		private String	femaleno;
		private String	department;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getDatacontent() {
			return datacontent;
		}
		public void setDatacontent(String datacontent) {
			this.datacontent = datacontent;
		}
		public String getMaleis() {
			return maleis;
		}
		public void setMaleis(String maleis) {
			this.maleis = maleis;
		}
		public String getMaleno() {
			return maleno;
		}
		public void setMaleno(String maleno) {
			this.maleno = maleno;
		}
		public String getFemaleis() {
			return femaleis;
		}
		public void setFemaleis(String femaleis) {
			this.femaleis = femaleis;
		}
		public String getFemaleno() {
			return femaleno;
		}
		public void setFemaleno(String femaleno) {
			this.femaleno = femaleno;
		}
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}
	}

}
