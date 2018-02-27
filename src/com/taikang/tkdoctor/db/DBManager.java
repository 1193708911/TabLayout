package com.taikang.tkdoctor.db;

import java.util.List;

import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.bean.MyHealthBean;
import com.taikang.tkdoctor.bean.UserBaseInfoBean;

public abstract class DBManager {

	private static DBManager instance;

	public static DBManager getInstance() {
		return instance;
	}

	protected DBManager() {
		instance = this;
	}

	public abstract void saveOrUpdateUser(HdBsAuthUser user);
	
	public abstract HdBsAuthUser getUserById(String uid);
	
	public abstract void saveOrUpdateBaseInfo(UserBaseInfoBean bean);
	public abstract UserBaseInfoBean getBaseInfoById(String uid);
	public abstract boolean deleteUserBaseInfo(UserBaseInfoBean user);
	//
	////	public abstract void saveOrUpdateSportDay(TbSportDay sportDay);
	//
	//	public abstract boolean saveSport(TbSport sport);
	//
	//	public abstract boolean deleteSport(TbSport sport);
	//
	//	public abstract TbSportDay getSportDayById(String id);
	//
	//	public abstract List<TbSportDay> getSportDayList(String userId, Date startTime, Date endTime);
	//
	//	public abstract List<TbSport> getSportList(String userId, Date startTime, Date endTime);
	//
	//	public abstract List<TbSport> getSportListByCommited(String userId, int commited);
	//
	//	public abstract boolean updateSport(TbSport sport);
	//
	//	public abstract boolean updateSportCommit(List<TbSport> sportList,int commited) ;
	//
	public abstract boolean deleteUser(HdBsAuthUser user);


//		public abstract List<TbSport> getAllSportList();

}
