package tigase.util;

import java.util.Calendar;
import java.util.TimeZone;

import junit.framework.Assert;
import junit.framework.TestCase;

public class DateTimeFormatterTest extends TestCase {

	DateTimeFormatter dt = new DateTimeFormatter();

	public void testFormat01() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.set(2009, 8, 11, 11, 12, 13);

		String actual = dt.formatDateTime(cal.getTime());
		assertEquals("2009-09-11T11:12:13Z", actual);
	}

	public void testFormat02() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
		cal.set(2009, 8, 11, 13, 12, 13);

		String actual = dt.formatDateTime(cal.getTime());
		assertEquals("2009-09-11T11:12:13Z", actual);
	}

	public void testFormat03() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.set(2009, 8, 11, 11, 12, 13);

		String actual = dt.formatDate(cal.getTime());
		assertEquals("2009-09-11", actual);
	}

	public void testFormat04() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
		cal.set(2009, 8, 11, 13, 12, 13);

		String actual = dt.formatDate(cal.getTime());
		assertEquals("2009-09-11", actual);
	}

	public void testFormat05() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.set(2009, 8, 11, 11, 12, 13);

		String actual = dt.formatTime(cal.getTime());
		assertEquals("11:12:13Z", actual);
	}

	public void testFormat06() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
		cal.set(2009, 8, 11, 13, 12, 13);

		String actual = dt.formatTime(cal.getTime());
		assertEquals("11:12:13Z", actual);
	}

	/**
	 * Test method for
	 * {@link tigase.muc.DateTimeFormatter#parseDateTime(java.lang.String)}.
	 */

	public void testParse() {

	}

	public void testParse01() throws Exception {
		Calendar actual = dt.parseDateTime("11:12:13Z");
		Calendar expected = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		expected.clear();
		expected.set(Calendar.HOUR_OF_DAY, 11);
		expected.set(Calendar.MINUTE, 12);
		expected.set(Calendar.SECOND, 13);
		assertEquals(expected, actual);
	}

	public void testParse02() throws Exception {
		Calendar actual = dt.parseDateTime("11:12:13-01:30");
		Calendar expected = Calendar.getInstance(TimeZone.getTimeZone("GMT-01:30"));
		expected.clear();
		expected.set(Calendar.HOUR_OF_DAY, 11);
		expected.set(Calendar.MINUTE, 12);
		expected.set(Calendar.SECOND, 13);

		assertEquals(expected, actual);
	}

	public void testParse03() throws Exception {
		Calendar actual = dt.parseDateTime("2009-09-11T11:12:13-01:30");
		Calendar expected = Calendar.getInstance(TimeZone.getTimeZone("GMT-01:30"));
		expected.clear();
		expected.set(2009, 8, 11, 11, 12, 13);
		assertEquals(expected, actual);
	}

	public void testParse04() throws Exception {
		try {
			dt.parseDateTime("2009-09-11T11:12:13");
			Assert.fail("Must throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	public void testParse05() throws Exception {
		Calendar actual = dt.parseDateTime("2009-09-11");
		Calendar expected = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		expected.clear();
		expected.set(2009, 8, 11);
		assertEquals(expected, actual);
	}

	public void testParse06() throws Exception {
		Calendar actual = dt.parseDateTime("2009-09-11T11:12:13Z");
		Calendar expected = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		expected.clear();
		expected.set(2009, 8, 11, 11, 12, 13);
		assertEquals(expected, actual);
	}

	public void testParse07() throws Exception {
		Calendar actual = dt.parseDateTime("11:12:13");
		Calendar expected = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		expected.clear();
		expected.set(Calendar.HOUR_OF_DAY, 11);
		expected.set(Calendar.MINUTE, 12);
		expected.set(Calendar.SECOND, 13);
		assertEquals(expected, actual);
	}
}
