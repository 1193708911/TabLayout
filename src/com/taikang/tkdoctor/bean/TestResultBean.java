package com.taikang.tkdoctor.bean;

import java.util.ArrayList;

public class TestResultBean extends Response {

	private static final long serialVersionUID = 1L;

	public ArrayList<Test> resultlist;
	
	public ArrayList<Test> getResultlist() {
		return resultlist;
	}

	public void setResultlist(ArrayList<Test> resultlist) {
		this.resultlist = resultlist;
	}

	public class Test extends Response {

		private static final long serialVersionUID = 1L;

		private String evaOfTraId;
		private String healthRisksId;
		private String diabetesId;
		private String msId;
		private String hbpId;

		public String getEvaOfTraId() {
			return evaOfTraId;
		}

		public void setEvaOfTraId(String evaOfTraId) {
			this.evaOfTraId = evaOfTraId;
		}

		public String getHealthRisksId() {
			return healthRisksId;
		}

		public void setHealthRisksId(String healthRisksId) {
			this.healthRisksId = healthRisksId;
		}

		public String getDiabetesId() {
			return diabetesId;
		}

		public void setDiabetesId(String diabetesId) {
			this.diabetesId = diabetesId;
		}

		public String getMsId() {
			return msId;
		}

		public void setMsId(String msId) {
			this.msId = msId;
		}

		public String getHbpId() {
			return hbpId;
		}

		public void setHbpId(String hbpId) {
			this.hbpId = hbpId;
		}

		public String getIcdId() {
			return icdId;
		}

		public void setIcdId(String icdId) {
			this.icdId = icdId;
		}

		private String icdId;

	}

}
