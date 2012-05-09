package com.polsl.edziennik.desktopclient.controller.utils;

import java.util.Calendar;
import java.util.Date;

public class DateConverter {

	String date;
	String hours;
	String minutes;

	public DateConverter(Long time) {
		if (time != null) {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(time);

			date = new Integer(c.get(Calendar.DAY_OF_MONTH)).toString();
			date += "/";
			date += new Integer(c.get(Calendar.MONTH) + 1).toString();
			date += "/";
			date += new Integer(c.get(Calendar.YEAR)).toString();

			hours = new Integer(c.get(Calendar.HOUR_OF_DAY)).toString();

			minutes = new Integer(c.get(Calendar.MINUTE)).toString();
		}
	}

	public DateConverter() {
	}

	public Long getDate(Long date, String hours, String minutes) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date);
		c.set(Calendar.HOUR, new Integer(hours));
		c.set(Calendar.MINUTE, new Integer(minutes));

		return c.getTimeInMillis();
	}

	public Long getDate(Long date, String hours, String minutes, String hoursLen, String minutesLen) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date);
		c.set(Calendar.HOUR_OF_DAY, new Integer(hours));
		c.set(Calendar.MINUTE, new Integer(minutes));

		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.HOUR_OF_DAY, new Integer(hoursLen));
		c2.set(Calendar.MINUTE, new Integer(minutesLen));

		return c.getTimeInMillis() + c2.getTimeInMillis();
	}

	public void setDateConverter(Long time) {

		if (time == null) return;

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);

		date = new Integer(c.get(Calendar.DAY_OF_MONTH)).toString();
		date += "/";
		date += new Integer(c.get(Calendar.MONTH) + 1).toString();
		date += "/";
		date += new Integer(c.get(Calendar.YEAR)).toString();

		hours = new Integer(c.get(Calendar.HOUR_OF_DAY)).toString();

		minutes = new Integer(c.get(Calendar.MINUTE)).toString();

	}

	public String getDate() {
		return date;
	}

	public String getHours() {
		if (hours.length() < 2) hours = "0" + hours;
		return hours;
	}

	public String getMinutes() {
		if (minutes.length() < 2) minutes = "0" + minutes;
		return minutes;
	}

	public String getTime() {
		if (minutes.length() < 2) minutes = "0" + minutes;
		if (hours.length() < 2) hours = "0" + hours;
		return date + "  godz " + hours + ":" + minutes;
	}

	public String DateToHours(int tmp) {

		String str = "";

		if (tmp < 10)
			str = "0" + tmp;
		else
			str = "" + tmp;

		return str;
	}

	public String DateToMinutes(int tmp) {

		String str = "";

		if (tmp < 10)
			str = "0" + tmp;
		else
			str = "" + tmp;

		return str;

	}

	public Date LongToDate(Long date) {

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date);

		return c.getTime();
	}

	public String getDuration(Long date) {
		if (date == null) return "";
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date);

		return DateToHours(c.get(Calendar.HOUR_OF_DAY)) + ":" + DateToMinutes(c.get(Calendar.MINUTE));
	}
}
