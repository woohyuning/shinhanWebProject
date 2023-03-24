package com.shinhan.dbutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class DateUtil {

	public static Date convertToDate(String sdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sqlDate = null;
		try {
//			sqlDate = SimpleDateFormat.format(sdate);
			java.util.Date d = sdf.parse(sdate);
			sqlDate = new Date(d.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqlDate;
	}
}
