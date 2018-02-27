package com.taikang.tkdoctor.global;

import java.util.ArrayList;

import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.bean.ClassContent;
import com.taikang.tkdoctor.bean.ClassroomBean;
import com.taikang.tkdoctor.enums.ClassroomDef;


public class ClassTestData {

	public static ArrayList<ClassroomBean> getClassData() {
		ArrayList<ClassroomBean> lists = new ArrayList<ClassroomBean>();
		lists.add(getClassTypeData(ClassroomDef.yinshi.getBgTypeDfId()));
		lists.add(getClassTypeData(ClassroomDef.yundong.getBgTypeDfId()));
		lists.add(getClassTypeData(ClassroomDef.xinli.getBgTypeDfId()));
		lists.add(getClassTypeData(ClassroomDef.liangxing.getBgTypeDfId()));
		lists.add(getClassTypeData(ClassroomDef.manbing.getBgTypeDfId()));
		return lists;
	}

	/**
	 * 根据课堂分类获取数据
	 * 
	 * @param type
	 * @return
	 */
	public static ClassroomBean getClassTypeData(String type) {
		ClassroomBean mclass = new ClassroomBean();
		ArrayList<ClassContent> contentList = new ArrayList<ClassContent>();
		ClassContent content = null;
		if (ClassroomDef.yinshi.getBgTypeDfId().equals(type)) {
			mclass.setType(ClassroomDef.yinshi.getBgTypeDfId());
			mclass.setTitle("饮食");
			for (int i = 0; i < 5; i++) {
				content = new ClassContent("饮食" + i, null, R.drawable.photo1,
						i + "生活生活生活生活生活生活生活生活生活生活生活生活生活生活生活生活生活生活", "生活");
				contentList.add(content);
			}
		} else if (ClassroomDef.yundong.getBgTypeDfId().equals(type)) {
			mclass.setType(ClassroomDef.yundong.getBgTypeDfId());
			mclass.setTitle("运动");
			for (int i = 0; i < 5; i++) {
				content = new ClassContent("饮食" + i, null, R.drawable.photo2,
						i + "饮食饮食饮食饮食饮食饮食饮食饮食饮食饮食饮食饮食饮食饮食饮食饮食饮食饮食", "运动");
				contentList.add(content);
			}
		} else if (ClassroomDef.xinli.getBgTypeDfId().equals(type)) {
			mclass.setType(ClassroomDef.xinli.getBgTypeDfId());
			mclass.setTitle("心理");
			for (int i = 0; i < 5; i++) {
				content = new ClassContent("职场" + i, null, R.drawable.photo3,
						i + "职场职场职场职场职场职场职场职场职场职场职场职场职场职场职场职场职场职场", "心理");
				contentList.add(content);
			}
		} else if (ClassroomDef.liangxing.getBgTypeDfId().equals(type)) {
			mclass.setType(ClassroomDef.liangxing.getBgTypeDfId());
			mclass.setTitle("两性");
			for (int i = 0; i < 5; i++) {
				content = new ClassContent("心理" + i, null, R.drawable.photo4,
						i + "心理心理心理心理心理心理心理心理心理心理心理心理心理心理心理心理心理心理", "心理");
				contentList.add(content);
			}
		} else if (ClassroomDef.manbing.getBgTypeDfId().equals(type)) {
			mclass.setType(ClassroomDef.manbing.getBgTypeDfId());
			mclass.setTitle("manbing");
			for (int i = 0; i < 5; i++) {
				content = new ClassContent("慢病" + i, null, R.drawable.photo5,
						i + "偏方偏方偏方偏偏方偏方偏方偏偏方偏方偏方偏偏方偏方偏方偏偏方偏方偏方偏方偏", "慢病");
				contentList.add(content);
			}
		}
		mclass.setLists(contentList);
		return mclass;
	}
}
