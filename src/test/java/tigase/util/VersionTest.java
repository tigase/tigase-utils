/*
 * Tigase Jabber/XMPP Server
 *  Copyright (C) 2004-2017 "Tigase, Inc." <office@tigase.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License,
 *  or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program. Look for COPYING file in the top folder.
 *  If not, see http://www.gnu.org/licenses/.
 *
 */

package tigase.util;

import org.junit.Assert;
import org.junit.Test;

public class VersionTest {

	@Test
	public void testBuilder() {
		Version build;
		build = new Version.Builder(5, 0, 0).build();
		Assert.assertTrue(Version.of("5.0.0").equals(build));

		build = new Version.Builder(5, 0, 0).setVersionType(Version.TYPE.SNAPSHOT).build();
		Assert.assertTrue(Version.of("5.0.0-SNAPSHOT").equals(build));

		build = new Version.Builder(5, 0, 0)
				.setVersionType(Version.TYPE.SNAPSHOT)
				.setBuild(1234)
				.build();
		Assert.assertTrue(Version.of("5.0.0-SNAPSHOT-b1234").equals(build));

		build = new Version.Builder(5, 0, 0)
				.setVersionType(Version.TYPE.SNAPSHOT)
				.setBuild(1234)
				.build();
		Assert.assertTrue(Version.of("5.0.0-SNAPSHOT-b1234").equals(build));

		build = new Version.Builder(5, 0, 0)
				.setVersionType(Version.TYPE.SNAPSHOT)
				.setBuild(1234)
				.setCommit("1234abcd")
				.build();
		Assert.assertTrue(Version.of("5.0.0-SNAPSHOT-b1234/1234abcd").equals(build));

	}

	@Test
	public void testComparison() throws Exception {
		Assert.assertTrue(
				Version.of("7.2.0-SNAPSHOT-b4904/12e027f7").equals(Version.of("7.2.0-SNAPSHOT-b4904/12e027f7")));

		Assert.assertFalse(
				Version.of("6.2.0-SNAPSHOT-b4904/12e027f7").equals(Version.of("7.2.0-SNAPSHOT-b4904/12e027f7")));

		Assert.assertFalse(
				Version.of("7.1.0-SNAPSHOT-b4904/12e027f7").equals(Version.of("7.2.0-SNAPSHOT-b4904/12e027f7")));

		Assert.assertFalse(
				Version.of("7.2.1-SNAPSHOT-b4904/12e027f7").equals(Version.of("7.2.0-SNAPSHOT-b4904/12e027f7")));

		Assert.assertFalse(
				Version.of("7.2.0-SNAPSHOT-b9999/12e027f7").equals(Version.of("7.2.0-SNAPSHOT-b4904/12e027f7")));

		Assert.assertFalse(
				Version.of("7.2.0-SNAPSHOT-b4904/12345678").equals(Version.of("7.2.0-SNAPSHOT-b4904/12e027f7")));

		Assert.assertFalse(Version.of("7.2.0-b4904/12e027f7").equals(Version.of("7.2.0-SNAPSHOT-b4904/12e027f7")));

		// version comparison
		Assert.assertTrue(
				Version.of("8.2.0-b4904/12e027f7").compareTo(Version.of("7.2.0-SNAPSHOT-b4904/12e027f7")) > 0);

		Assert.assertTrue(
				Version.of("7.3.0-SNAPSHOT-b4904/12e027f7").compareTo(Version.of("7.2.0-SNAPSHOT-b4904/12e027f7")) > 0);

		Assert.assertTrue(
				Version.of("7.2.1-SNAPSHOT-b4904/12e027f7").compareTo(Version.of("7.2.0-SNAPSHOT-b4904/12e027f7")) > 0);

		Assert.assertTrue(
				Version.of("7.2.0-SNAPSHOT-b9999/12e027f7").compareTo(Version.of("7.2.0-SNAPSHOT-b4904/12e027f7")) > 0);

		Assert.assertTrue(
				Version.of("7.2.0-b4904/12e027f7").compareTo(Version.of("7.2.0-SNAPSHOT-b4904/12e027f7")) > 0);

		Assert.assertTrue(
				Version.of("7.2.0-SNAPSHOT-b4904/12345678").compareTo(Version.of("7.2.0-SNAPSHOT-b4904/12e027f7")) ==
						0);

		Assert.assertTrue(Version.of("7.2.0-SNAPSHOT-b4904").compareTo(Version.of("7.2.0-SNAPSHOT-b4904")) == 0);

		Assert.assertTrue(Version.of("7.2.0-SNAPSHOT").compareTo(Version.of("7.2.0-SNAPSHOT")) == 0);

		Assert.assertTrue(Version.of("7.2.0").compareTo(Version.of("7.2.0")) == 0);

		Assert.assertTrue(Version.of("7.2.0").equals(Version.of("7.2.0")));

		Assert.assertTrue(Version.of("7.2.0-RC3-b4904").compareTo(Version.of("7.2.0-RC3-b4904")) == 0);

		Assert.assertTrue(Version.of("7.2.0-RC3-b4904").equals(Version.of("7.2.0-RC3-b4904")));

		Assert.assertFalse(Version.of("7.2.0-RC2-b4904").equals(Version.of("7.2.0-RC3-b4904")));

		Assert.assertTrue(
				Version.of("7.2.0-RC4-b4904").compareTo(Version.of("7.2.0-RC3-b4904")) > 0);

		Assert.assertTrue(Version.of("7.2.0-RC4-b4904").compareTo(Version.of("7.2.0-BETA15-b4904")) > 0);
		Assert.assertFalse(Version.of("7.2.0-RC4-b4904").compareTo(Version.of("7.2.0-BETA15-b4904")) < 0);
		Assert.assertTrue(Version.of("7.2.0-b4904").compareTo(Version.of("7.2.0-RC15-b4904")) > 0);
		Assert.assertFalse(Version.of("7.2.0-b4904").compareTo(Version.of("7.2.0-RC15-b4904")) < 0);
		Assert.assertTrue(Version.of("7.2.0-b4904").compareTo(Version.of("7.2.0-BETA15-b4904")) > 0);
		Assert.assertFalse(Version.of("7.2.0-b4904").compareTo(Version.of("7.2.0-BETA15-b4904")) < 0);
	}

	@Test
	public void testParsing() throws Exception {
		String version;

//		version = "7.2.0-SNAPSHOT-b4904/12e027f7";
//		Assert.assertEquals(version, (Version.of(version)).toString());
//
//		version = "7.1.0-b4904/12e027f7";
//		Assert.assertEquals(version, (Version.of(version)).toString());
//
//		version = "7.1.0";
//		Assert.assertEquals(version, (Version.of(version)).toString());
//
//		version = "tigase-server-7.1.0-b4379-dist-max.tar.gz";
//		Assert.assertEquals("7.1.0-b4379", (Version.of(version)).toString());

		version = "tigase-server-5.2.0-beta3-b3269-dist-max.tar.gz";
		Assert.assertEquals("5.2.0-BETA3-b3269", (Version.of(version)).toString());

		version = "tigase-server-5.2.0-rc3-b3269-dist-max.tar.gz";
		Assert.assertEquals("5.2.0-RC3-b3269", (Version.of(version)).toString());

		version = "tigase-server-7.1.0-b4379.exe";
		Assert.assertEquals("7.1.0-b4379", (Version.of(version)).toString());

		version = "tigase-server-7.1.0-b4379/12e027f7-dist-max.tar.gz";
		Assert.assertEquals("7.1.0-b4379/12e027f7", (Version.of(version)).toString());

		version = "tigase-server-7.1.0-b4379/12e027f7.exe";
		Assert.assertEquals("7.1.0-b4379/12e027f7", (Version.of(version)).toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParsingFail() {

		Version.of("xxx19.2f2f2ff2f2f2.dsdf6-bsfsdfsdfsdfsfsdf4379");
		Version.of("a.b.c");
	}
}