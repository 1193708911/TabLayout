package com.taikang.tkdoctor.db;

import java.util.Date;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.bean.UserBaseInfoBean;

public class SQLiteDBManager extends DBManager {

	private DbUtils db;

	public SQLiteDBManager(DbUtils db) {
		this.db = db;
	}

	@Override
	public void saveOrUpdateUser(HdBsAuthUser user) {
		try {
			db.saveOrUpdate(user);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public HdBsAuthUser getUserById(String uid) {
		try {
			return db.findById(HdBsAuthUser.class, uid);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean deleteUser(HdBsAuthUser user) {
		try {
			db.delete(user);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void saveOrUpdateBaseInfo(UserBaseInfoBean bean) {
		// TODO Auto-generated method stub
		try {
			db.saveOrUpdate(bean);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserBaseInfoBean getBaseInfoById(String uid) {
		try {
			return db.findById(UserBaseInfoBean.class, uid);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteUserBaseInfo(UserBaseInfoBean bean) {
		try {
			db.delete(bean);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return true;
	}
	
//	@Override
//	public boolean saveSport(TbSport sport) {
//		SQLiteDatabase database = db.getDatabase();
//		try {
//			database.beginTransaction();
//			db.save(sport);
//			TbSportDay sportDay = new TbSportDay();
//			int step = sport.getStep();
//			String idDay = TbSportDay.buildId(sport.getEndTime());
//			TbSportDay dayDb = db.findById(TbSportDay.class, idDay);
//			if (dayDb != null)
//				step += dayDb.getStep();
//			sportDay.setId(idDay);
//			sportDay.setUserId(sport.getUserId());
//			sportDay.setStep(step);
//			db.saveOrUpdate(sportDay);
//			database.setTransactionSuccessful();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			database.endTransaction();
//		}
//		return true;
//	}
//
//	@Override
//	public boolean deleteSport(TbSport sport) {
//		SQLiteDatabase database = db.getDatabase();
//		try {
//			database.beginTransaction();
//			db.delete(sport);
//			int step = sport.getStep();
//			String idDay = TbSportDay.buildId(sport.getEndTime());
//			TbSportDay dayDb = db.findById(TbSportDay.class, idDay);
//			if (dayDb != null) {
//				int step2 = dayDb.getStep() - step;
//				if (step2 < 0)
//					step2 = 0;
//				dayDb.setStep(step2);
//				db.saveOrUpdate(dayDb);
//			}
//			StepDetector.CURRENT_STEP_DAY -= step;
//			if (StepDetector.CURRENT_STEP_DAY < 0)
//				StepDetector.CURRENT_STEP_DAY = 0;
//			database.setTransactionSuccessful();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			database.endTransaction();
//		}
//		return true;
//	}
//
//	@Override
//	public boolean updateSport(TbSport sport) {
//		try {
//			db.update(sport);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
//
//	@Override
//	public boolean updateSportCommit(List<TbSport> sportList, int commited) {
//		try {
//			if (!Util.isEmpty(sportList)) {
//				for (TbSport tbSport : sportList) {
//					tbSport.setCommited(commited);
//					db.update(tbSport);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
//
////	@Override
////	public void saveOrUpdateSportDay(TbSportDay sportDay) {
////		try {
////			db.saveOrUpdate(sportDay);
////		} catch (DbException e) {
////			e.printStackTrace();
////		}
////	}
//
//	@Override
//	public List<TbSport> getSportList(String userId, Date startTime, Date endTime) {
//		try {
//			return db.findAll(Selector.from(TbSport.class).where("USER_ID", "=", userId)
//					.and("START_TIME", ">=", startTime).and("END_TIME", "<", endTime));
//		} catch (DbException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@Override
//	public List<TbSportDay> getSportDayList(String userId, Date startTime, Date endTime) {
//		try {
//			return db.findAll(Selector.from(TbSportDay.class).where("USER_ID", "=", userId)
//					.and("ID", ">=", TbSportDay.buildId(startTime)).and("ID", "<=", TbSportDay.buildId(endTime)));
//		} catch (DbException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

//	@Override
//	public List<TbSport> getAllSportList() {
//		try {
//			return db.findAll(TbSport.class);
//		} catch (DbException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

//	@Override
//	public TbSportDay getSportDayById(String id) {
//		try {
//			return db.findById(TbSportDay.class, id);
//		} catch (DbException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@Override
//	public List<TbSport> getSportListByCommited(String userId, int commited) {
//		try {
//			return db
//					.findAll(Selector.from(TbSport.class).where("USER_ID", "=", userId).and("COMMITED", "=", commited));
//		} catch (DbException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
