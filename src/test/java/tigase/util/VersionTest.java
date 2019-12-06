/*
 * Tigase Utils - Utilities module
 * Copyright (C) 2004 Tigase, Inc. (office@tigase.com)
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

import org.junit.Assert;
import org.junit.Test;

public class VersionTest {

	@Test(expected = IllegalArgumentException.class)
	public void incrementCommitFieldTest() {
		Version v = Version.of("tigase-server-0.0.0-RC1-b0");
		Assert.assertEquals(Version.of("tigase-server-0.0.0-RC1-b0"), v.increment(Version.FIELD.COMMIT, 1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void incrementComponentFieldTest() {
		Version v = Version.of("tigase-server-0.0.0-RC1-b0");
		Assert.assertEquals(Version.of("tigase-server-0.0.0-RC1-b0"), v.increment(Version.FIELD.COMPONENT, 1));
	}

	@Test
	public void baseVersionTest() {

		final Version v1 = Version.of("7.2.0-SNAPSHOT-b4904/12e027f7");
		final Version v2 = Version.of("7.2.0-SNAPSHOT-b1/xyz");
		Assert.assertFalse(v1.equals(v2));
		Assert.assertTrue(v1.getBaseVersion().equals(v2.getBaseVersion()));

		final Version v3 = Version.ZERO;
		final Version v4 = Version.of("0.0.0");
		Assert.assertTrue(v3.equals(v4));
		Assert.assertTrue(v3.getBaseVersion().equals(v4.getBaseVersion()));
	}

	@Test
	public void incrementTest() {
		Version v = Version.of("tigase-server-0.0.0-RC1-b0");
		Assert.assertEquals(Version.of("tigase-server-1.0.0-RC1-b0"), v.increment(Version.FIELD.MAJOR, 1));
		Assert.assertEquals(Version.of("tigase-server-0.1.0-RC1-b0"), v.increment(Version.FIELD.MINOR, 1));
		Assert.assertEquals(Version.of("tigase-server-0.0.1-RC1-b0"), v.increment(Version.FIELD.BUGFIX, 1));
		Assert.assertEquals(Version.of("tigase-server-0.0.0-RC1-b1"), v.increment(Version.FIELD.BUILD, 1));
		Assert.assertEquals(Version.of("tigase-server-0.0.0-RC2-b0"), v.increment(Version.FIELD.TYPE_NUMBER, 1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void incrementTypeFieldTest() {
		Version v = Version.of("tigase-server-0.0.0-RC1-b0");
		Assert.assertEquals(Version.of("tigase-server-0.0.0-RC1-b0"), v.increment(Version.FIELD.TYPE, 1));
	}

	@Test
	public void testBuilder() {
		Version build;
		build = new Version.Builder(5, 0, 0).build();
		final Version v5 = Version.of("5.0.0");
		Assert.assertTrue(v5.equals(build));

		build = new Version.Builder(5, 0, 0).setVersionType(Version.TYPE.SNAPSHOT).build();
		Assert.assertTrue(Version.of("5.0.0-SNAPSHOT").equals(build));

		build = new Version.Builder(5, 0, 0).setVersionType(Version.TYPE.SNAPSHOT).setBuild(1234).build();
		Assert.assertTrue(Version.of("5.0.0-SNAPSHOT-b1234").equals(build));

		build = new Version.Builder(5, 0, 0).setVersionType(Version.TYPE.SNAPSHOT).setBuild(1234).build();
		Assert.assertTrue(Version.of("5.0.0-SNAPSHOT-b1234").equals(build));

		build = new Version.Builder(5, 0, 0).setVersionType(Version.TYPE.SNAPSHOT)
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

		Assert.assertTrue(Version.of("7.2.0-RC4-b4904").compareTo(Version.of("7.2.0-RC3-b4904")) > 0);

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

		version = "5.1";
		Assert.assertEquals("5.1.0", (Version.of(version)).toString());

		version = "5.1-SNAPSHOT";
		Assert.assertEquals("5.1.0-SNAPSHOT", (Version.of(version)).toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParsingFail() {

		Version.of("xxx19.2f2f2ff2f2f2.dsdf6-bsfsdfsdfsdfsfsdf4379");
		Version.of("a.b.c");
	}

}