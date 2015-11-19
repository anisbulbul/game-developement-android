package com.anisbulbul.race.danger.dates;

import java.util.Calendar;

public class DatesComponents {
	
	
	public static int getDateRemain(){

		int dateTemp = -1;
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONDAY)+1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		dateTemp = (30-day)+(11-month)*30+(2014-year)*365;

		
		return dateTemp;
	}
	
}
