package com.taikang.tkdoctor.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import com.taikang.tkdoctor.R;

/**
 * @author:518ad-ccn date:Dec 13, 2011 describe：24节气
 *                   注：程序中使用到的计算节气公式、节气世纪常量等相关信息参照http
 *                   ://www.360doc.com/content/11
 *                   /0106/22/5281066_84591519.shtml，
 *                   程序的运行得出的节气结果绝大多数是正确的，有少数部份是有误差的
 */
public class SolarTermUtil {
	private static final double D = 0.2422;
	private final static Map<String, Integer[]> INCREASE_OFFSETMAP = new HashMap<String, Integer[]>();// +1偏移
	private final static Map<String, Integer[]> DECREASE_OFFSETMAP = new HashMap<String, Integer[]>();// -1偏移
	private static int[] solarTermTimeMusicIds = new int[]{
		R.drawable.pic_xiaohandahan,R.drawable.pic_xiaohandahan,
		R.drawable.pic_lichunyushui,R.drawable.pic_lichunyushui,
		R.drawable.pic_jingzhechunfen,R.drawable.pic_jingzhechunfen,
		R.drawable.pic_qingmingguyu,R.drawable.pic_qingmingguyu,
		R.drawable.pic_lixiaxiaoman,R.drawable.pic_lixiaxiaoman,
		R.drawable.pic_mangzhongxiazhi,R.drawable.pic_mangzhongxiazhi,
		R.drawable.pic_xiaoshudashu,R.drawable.pic_xiaoshudashu,
		R.drawable.pic_liqiuchushu,R.drawable.pic_liqiuchushu,
		R.drawable.pic_bailuqiufen,R.drawable.pic_bailuqiufen,
		R.drawable.pic_hanlushuangjiang,R.drawable.pic_hanlushuangjiang,
		R.drawable.pic_lidongxiaoxue,R.drawable.pic_lidongxiaoxue,
		R.drawable.pic_daxuedongzhi,R.drawable.pic_daxuedongzhi};
	private static String[] solarTermNames = new String[] { "小寒", "大寒", "立春", "雨水", "惊蛰",
			"春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑",
			"白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至" };
	private final static int [] solarTermId = {23,24,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22};
	/** 24节气 **/
	private static enum SolarTermsEnum {
		LICHUN, // --立春
		YUSHUI, // --雨水
		JINGZHE, // --惊蛰
		CHUNFEN, // 春分
		QINGMING, // 清明
		GUYU, // 谷雨
		LIXIA, // 立夏
		XIAOMAN, // 小满
		MANGZHONG, // 芒种
		XIAZHI, // 夏至
		XIAOSHU, // 小暑
		DASHU, // 大暑
		LIQIU, // 立秋
		CHUSHU, // 处暑
		BAILU, // 白露
		QIUFEN, // 秋分
		HANLU, // 寒露
		SHUANGJIANG, // 霜降
		LIDONG, // 立冬
		XIAOXUE, // 小雪
		DAXUE, // 大雪
		DONGZHI, // 冬至
		XIAOHAN, // 小寒
		DAHAN;// 大寒
	}

	static {
		DECREASE_OFFSETMAP.put(SolarTermsEnum.YUSHUI.name(),new Integer[] { 2026 });// 雨水
		INCREASE_OFFSETMAP.put(SolarTermsEnum.CHUNFEN.name(),new Integer[] { 2084 });// 春分
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOMAN.name(),new Integer[] { 2008 });// 小满
		INCREASE_OFFSETMAP.put(SolarTermsEnum.MANGZHONG.name(),new Integer[] { 1902 });// 芒种
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAZHI.name(),new Integer[] { 1928 });// 夏至
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOSHU.name(), new Integer[] {1925, 2016 });// 小暑
		INCREASE_OFFSETMAP.put(SolarTermsEnum.DASHU.name(),new Integer[] { 1922 });// 大暑
		INCREASE_OFFSETMAP.put(SolarTermsEnum.LIQIU.name(),new Integer[] { 2002 });// 立秋
		INCREASE_OFFSETMAP.put(SolarTermsEnum.BAILU.name(),new Integer[] { 1927 });// 白露
		INCREASE_OFFSETMAP.put(SolarTermsEnum.QIUFEN.name(),new Integer[] { 1942 });// 秋分
		INCREASE_OFFSETMAP.put(SolarTermsEnum.SHUANGJIANG.name(),new Integer[] { 2089 });// 霜降
		INCREASE_OFFSETMAP.put(SolarTermsEnum.LIDONG.name(),new Integer[] { 2089 });// 立冬
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOXUE.name(),new Integer[] { 1978 });// 小雪
		INCREASE_OFFSETMAP.put(SolarTermsEnum.DAXUE.name(),new Integer[] { 1954 });// 大雪
		DECREASE_OFFSETMAP.put(SolarTermsEnum.DONGZHI.name(), new Integer[] {1918, 2021 });// 冬至
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOHAN.name(),new Integer[] { 1982 });// 小寒
		DECREASE_OFFSETMAP.put(SolarTermsEnum.XIAOHAN.name(),new Integer[] { 2019 });// 小寒
		INCREASE_OFFSETMAP.put(SolarTermsEnum.DAHAN.name(),new Integer[] { 2082 });// 大寒
	}

	// 定义一个二维数组，第一维数组存储的是20世纪的节气C值，第二维数组存储的是21世纪的节气C值,0到23个，依次代表立春、雨水...大寒节气的C值
	private static final double[][] CENTURY_ARRAY = {
			{ 4.6295, 19.4599, 6.3826, 21.4155, 5.59, 20.888, 6.318, 21.86,
					6.5, 22.2, 7.928, 23.65, 8.35, 23.95, 8.44, 23.822, 9.098,
					24.218, 8.218, 23.08, 7.9, 22.6, 6.11, 20.84 },
			{ 3.87, 18.73, 5.63, 20.646, 4.81, 20.1, 5.52, 21.04, 5.678, 21.37,
					7.108, 22.83, 7.5, 23.13, 7.646, 23.042, 8.318, 23.438,
					7.438, 22.36, 7.18, 21.94, 5.4055, 20.12 } };
	/**
	 * 
	 * @param year
	 *            年份
	 * @param name
	 *            节气的名称
	 * @return 返回节气是相应月份的第几天
	 */
	private static int getSolarTermNum(int year, String name) {

		double centuryValue = 0;// 节气的世纪值，每个节气的每个世纪值都不同
		name = name.trim().toUpperCase();
		int ordinal = SolarTermsEnum.valueOf(name).ordinal();

		int centuryIndex = -1;
		if (year >= 1901 && year <= 2000) {// 20世纪
			centuryIndex = 0;
		} else if (year >= 2001 && year <= 2100) {// 21世纪
			centuryIndex = 1;
		} else {
			throw new RuntimeException("不支持此年份：" + year + "，目前只支持1901年到2100年的时间范围");
		}
		centuryValue = CENTURY_ARRAY[centuryIndex][ordinal];
		int dateNum = 0;
		/**
		 * 计算 num =[Y*D+C]-L这是传说中的寿星通用公式
		 * 公式解读：年数的后2位乘0.2422加C(即：centuryValue)取整数后，减闰年数
		 */
		int y = year % 100;// 步骤1:取年分的后两位数
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {// 闰年
			if (ordinal == SolarTermsEnum.XIAOHAN.ordinal()
					|| ordinal == SolarTermsEnum.DAHAN.ordinal()
					|| ordinal == SolarTermsEnum.LICHUN.ordinal()
					|| ordinal == SolarTermsEnum.YUSHUI.ordinal()) {
				// 注意：凡闰年3月1日前闰年数要减一，即：L=[(Y-1)/4],因为小寒、大寒、立春、雨水这两个节气都小于3月1日,所以
				// y = y-1
				y = y - 1;// 步骤2
			}
		}
		dateNum = (int) (y * D + centuryValue) - (int) (y / 4);// 步骤3，使用公式[Y*D+C]-L计算
		dateNum += specialYearOffset(year, name);// 步骤4，加上特殊的年分的节气偏移量
		return dateNum;
	}

	/**
	 * 特例,特殊的年分的节气偏移量,由于公式并不完善，所以算出的个别节气的第几天数并不准确，在此返回其偏移量
	 * 
	 * @param year
	 *            年份
	 * @param name
	 *            节气名称
	 * @return 返回其偏移量
	 */
	public static int specialYearOffset(int year, String name) {
		int offset = 0;
		offset += getOffset(DECREASE_OFFSETMAP, year, name, -1);
		offset += getOffset(INCREASE_OFFSETMAP, year, name, 1);

		return offset;
	}

	private static int getOffset(Map<String, Integer[]> map, int year,
			String name, int offset) {
		int off = 0;
		Integer[] years = map.get(name);
		if (null != years) {
			for (int i : years) {
				if (i == year) {
					off = offset;
					break;
				}
			}
		}
		return off;
	}
	private static Long [] solarTermTimes = null;
	public static void initSolarTermTimes(int year) {
		if(solarTermTimes == null){
			solarTermTimes = new Long[24];
			solarTermTimes[0] = getTime(year, 1, getSolarTermNum(year, SolarTermsEnum.XIAOHAN.name()));
			solarTermTimes[1] = getTime(year, 1, getSolarTermNum(year, SolarTermsEnum.DAHAN.name()));
			solarTermTimes[2] = getTime(year, 2, getSolarTermNum(year, SolarTermsEnum.LICHUN.name()));
			solarTermTimes[3] = getTime(year, 2, getSolarTermNum(year, SolarTermsEnum.YUSHUI.name()));
			solarTermTimes[4] = getTime(year, 3, getSolarTermNum(year, SolarTermsEnum.JINGZHE.name()));
			solarTermTimes[5] = getTime(year, 3, getSolarTermNum(year, SolarTermsEnum.CHUNFEN.name()));
			solarTermTimes[6] = getTime(year, 4, getSolarTermNum(year, SolarTermsEnum.QINGMING.name()));
			solarTermTimes[7] = getTime(year, 4, getSolarTermNum(year, SolarTermsEnum.GUYU.name()));
			solarTermTimes[8] = getTime(year, 5, getSolarTermNum(year, SolarTermsEnum.LIXIA.name()));
			solarTermTimes[9] = getTime(year, 5, getSolarTermNum(year, SolarTermsEnum.XIAOMAN.name()));
			solarTermTimes[10] = getTime(year, 6, getSolarTermNum(year, SolarTermsEnum.MANGZHONG.name()));
			solarTermTimes[11] = getTime(year, 6, getSolarTermNum(year, SolarTermsEnum.XIAZHI.name()));
			solarTermTimes[12] = getTime(year, 7, getSolarTermNum(year, SolarTermsEnum.XIAOSHU.name()));
			solarTermTimes[13] = getTime(year, 7, getSolarTermNum(year, SolarTermsEnum.DASHU.name()));
			solarTermTimes[14] = getTime(year, 8, getSolarTermNum(year, SolarTermsEnum.LIQIU.name()));
			solarTermTimes[15] = getTime(year, 8, getSolarTermNum(year, SolarTermsEnum.CHUSHU.name()));
			solarTermTimes[16] = getTime(year, 9, getSolarTermNum(year, SolarTermsEnum.BAILU.name()));
			solarTermTimes[17] = getTime(year, 9, getSolarTermNum(year, SolarTermsEnum.QIUFEN.name()));
			solarTermTimes[18] = getTime(year, 10, getSolarTermNum(year, SolarTermsEnum.HANLU.name()));
			solarTermTimes[19] = getTime(year, 10, getSolarTermNum(year, SolarTermsEnum.SHUANGJIANG.name()));
			solarTermTimes[20] = getTime(year, 11, getSolarTermNum(year, SolarTermsEnum.LIDONG.name()));
			solarTermTimes[21] = getTime(year, 11, getSolarTermNum(year, SolarTermsEnum.XIAOXUE.name()));
			solarTermTimes[22] = getTime(year, 12, getSolarTermNum(year, SolarTermsEnum.DAXUE.name()));
			solarTermTimes[23] = getTime(year, 12, getSolarTermNum(year, SolarTermsEnum.DONGZHI.name()));
		}
	}
	
	private static Long getTime(int year, int month, int day){
		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = fmt.parse(year+"-"+month+"-"+day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}
	
	public static int getIndex(){
		int index = -1;
		Long time = System.currentTimeMillis();
		int year = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")).get(Calendar.YEAR);
		if(solarTermTimes == null){
			initSolarTermTimes(year);
		}
		//因为1月刚开始的几天还没有到第一个节气，所以取上年的最后一个节气
		if(getSolarTermTime(0) > time){
			initSolarTermTimes(--year);
			index = 23;
		}else{
			for(int i=solarTermTimes.length-1;i>=0;i--){
				if(solarTermTimes[i]<=time){
					index = i;
					return i;
				}
			}
		}
		return index;
	}
	
	public static String getSolarTerm(int index){
		return solarTermNames[index];
	}

	public static Long getSolarTermTime(int index) {
		return solarTermTimes[index];
	}

	public static int getSolarTermMusicId(int index) {
		return solarTermTimeMusicIds[index];
	}

	public static int getSolarTermId(int index) {
		// TODO Auto-generated method stub
		return solarTermId[index];
	}
	
	
}