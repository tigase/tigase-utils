/*
 * TimestampHelperTest.java
 *
 * Tigase Jabber/XMPP Utils
 * Copyright (C) 2004-2018 "Tigase, Inc." <office@tigase.com>
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

package tigase.util.datetime;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TimestampHelperTest {

	private final TimestampHelper helper = new TimestampHelper();

	@Test
	public void testformattingWithMs() {
		long milis = 1524844622597l;
		Date date = new Date(milis);
		String result = helper.formatWithMs(date);
		assertEquals("2018-04-27T15:57:02.597Z", result);
	}

	@Test
	public void testformattingWithoutMs() {
		long milis = 1524844622597l;
		Date date = new Date(milis);
		String result = helper.format(date);
		assertEquals("2018-04-27T15:57:02Z", result);
	}

	@Test
	public void testParsingWithMs() throws ParseException {
		Date result = helper.parseTimestamp("2018-04-27T15:57:02.597Z");
		long milis = 1524844622597l;
		Date date = new Date(milis);
		assertEquals(date, result);
	}

	@Test
	public void testParsingWitoutMs() throws ParseException {
		Date result = helper.parseTimestamp("2018-04-27T15:57:02Z");
		long milis = 1524844622000l;
		Date date = new Date(milis);
		assertEquals(date, result);
	}


	@Test
	public void testParsingWithMsAndZone() throws ParseException {
		Date result = helper.parseTimestamp("2018-04-27T17:57:02.597+02:00");
		long milis = 1524844622597l;
		Date date = new Date(milis);
		assertEquals(date, result);
	}
	
	@Test
	public void testParsingWitoutMsAndZone() throws ParseException {
		Date result = helper.parseTimestamp("2018-04-27T17:57:02+02:00");
		long milis = 1524844622000l;
		Date date = new Date(milis);
		assertEquals(date, result);
	}
}
