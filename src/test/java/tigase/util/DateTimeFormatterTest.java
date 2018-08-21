/*
 * DateTimeFormatterTest.java
 *
 * Tigase Jabber/XMPP Utils
 * Copyright (C) 2004-2017 "Tigase, Inc." <office@tigase.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */

package tigase.util;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import tigase.util.datetime.DateTimeFormatter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeFormatterTest
		extends TestCase {

	DateTimeFormatter dt = new DateTimeFormatter();

	@Test
	public void testFormat01() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.set(2009, 8, 11, 11, 12, 13);

		String actual = dt.formatDateTime(cal.getTime());
		assertEquals("2009-09-11T11:12:13Z", actual);
	}

	@Test
	public void testFormat02() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
		cal.set(2009, 8, 11, 13, 12, 13);

		String actual = dt.formatDateTime(cal.getTime());
		assertEquals("2009-09-11T11:12:13Z", actual);
	}

	@Test
	public void testFormat03() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.set(2009, 8, 11, 11, 12, 13);

		String actual = dt.formatDate(cal.getTime());
		assertEquals("2009-09-11", actual);
	}

	@Test
	public void testFormat04() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
		cal.set(2009, 8, 11, 13, 12, 13);

		String actual = dt.formatDate(cal.getTime());
		assertEquals("2009-09-11", actual);
	}

	@Test
	public void testFormat05() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.set(2009, 8, 11, 11, 12, 13);

		String actual = dt.formatTime(cal.getTime());
		assertEquals("11:12:13Z", actual);
	}

	@Test
	public void testFormat06() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
		cal.set(2009, 8, 11, 13, 12, 13);

		String actual = dt.formatTime(cal.getTime());
		assertEquals("11:12:13Z", actual);
	}

	@Test
	public void testFormatInLegacyDelayedDelivery() {
		long milis = 1524844622597l;
		Date date = new Date(milis);
		String result = dt.formatInLegacyDelayedDelivery(date);
		assertEquals("20180427T15:57:02", result);
	}

	@Test
	public void testFormattingWithMs() {
		long milis = 1524844622597l;
		Date date = new Date(milis);
		String result = dt.formatWithMs(date);
		assertEquals("2018-04-27T15:57:02.597Z", result);
	}

	@Test
	public void testParse01() throws Exception {
		Calendar actual = dt.parseDateTime("11:12:13Z");
		Calendar expected = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		expected.clear();
		expected.set(Calendar.HOUR_OF_DAY, 11);
		expected.set(Calendar.MINUTE, 12);
		expected.set(Calendar.SECOND, 13);
		assertEquals(expected, actual);
	}

	@Test
	public void testParse02() throws Exception {
		Calendar actual = dt.parseDateTime("11:12:13-01:30");
		Calendar expected = Calendar.getInstance(TimeZone.getTimeZone("GMT-01:30"));
		expected.clear();
		expected.set(Calendar.HOUR_OF_DAY, 11);
		expected.set(Calendar.MINUTE, 12);
		expected.set(Calendar.SECOND, 13);

		assertEquals(expected, actual);
	}

	@Test
	public void testParse03() throws Exception {
		Calendar actual = dt.parseDateTime("2009-09-11T11:12:13-01:30");
		Calendar expected = Calendar.getInstance(TimeZone.getTimeZone("GMT-01:30"));
		expected.clear();
		expected.set(2009, 8, 11, 11, 12, 13);
		assertEquals(expected, actual);
	}

	@Test
	public void testParse04() throws Exception {
		try {
			dt.parseDateTime("2009-09-11T11:12:13");
			Assert.fail("Must throw ParseException");
		} catch (ParseException e) {
			// OK
		}
	}

	@Test
	public void testParse05() throws Exception {
		Calendar actual = dt.parseDateTime("2009-09-11");
		Calendar expected = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		expected.clear();
		expected.set(2009, 8, 11);
		assertEquals(expected, actual);
	}

	@Test
	public void testParse06() throws Exception {
		Calendar actual = dt.parseDateTime("2009-09-11T11:12:13Z");
		Calendar expected = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		expected.clear();
		expected.set(2009, 8, 11, 11, 12, 13);
		assertEquals(expected, actual);
	}

	@Test
	public void testParse07() throws Exception {
		Calendar actual = dt.parseDateTime("11:12:13");
		Calendar expected = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		expected.clear();
		expected.set(Calendar.HOUR_OF_DAY, 11);
		expected.set(Calendar.MINUTE, 12);
		expected.set(Calendar.SECOND, 13);
		assertEquals(expected, actual);
	}

	@Test
	public void testParse08() throws Exception {
		Calendar actual = dt.parseDateTime("2018-08-21T08:48:22.792921Z");
		Calendar expected = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		expected.clear();
		expected.set(2018, 7, 21, 8, 48, 22);
		expected.set(Calendar.MILLISECOND, 792921);
		assertEquals(expected, actual);
		assertEquals(new Date(1534842094921l), actual.getTime());
	}

	@Test
	public void testParseEmptyString() throws ParseException {
		assertNull(dt.parseDateTime(""));
		assertNull(dt.parseDateTime(null));
	}

	@Test
	public void testParseException() throws Exception {
		try {
			dt.parseDateTime("2018-08-21T08:48:22.79292z0Z");
			fail("Exception should be throwed");
		} catch (ParseException ignore) {
		}
	}

}
