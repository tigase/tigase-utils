/**
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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tigase.util.StringUtilities.*;

public class StringUtilitiesTest {

	@Test
	public void stripALLNonPrintable() {
		final String input = "🂡	🂢\n🂣";
		final String withoutWhitespace = convertNonPrintableCharactersToLiterals(input, false);
		assertEquals("\\uD83C\\uDCA1\\u0009\\uD83C\\uDCA2\\u000A\\uD83C\\uDCA3", withoutWhitespace);
		final String withWhitespace = convertNonPrintableCharactersToLiterals(input, true);
		assertEquals("\\uD83C\\uDCA1\t\\uD83C\\uDCA2\n\\uD83C\\uDCA3", withWhitespace);
	}

	@Test
	public void testPadToColumnString() {
		final StringBuilder sb = new StringBuilder();
		assertEquals("tigase    ", padStringToColumn(sb, "tigase", JUSTIFY.LEFT, 10, ' ', null, null).toString());
		assertEquals("tigase    tigase    ",
					 padStringToColumn(sb, "tigase", JUSTIFY.LEFT, 20, ' ', null, null).toString());
		assertEquals("tigase    tigase        tigase",
					 padStringToColumn(sb, "tigase", JUSTIFY.RIGHT, 30, ' ', null, null).toString());

	}

	@Test
	public void testPadString() {

		assertEquals("tigase    ", padString(new StringBuilder(), "tigase", 10).toString());

		assertEquals("[    tigase]",
					 padString(new StringBuilder(), "tigase", JUSTIFY.RIGHT, 10, ' ', "[", "]").toString());
		assertEquals("[  tigase  ]",
					 padString(new StringBuilder(), "tigase", JUSTIFY.CENTRE, 10, ' ', "[", "]").toString());
		assertEquals("[tigase    ]",
					 padString(new StringBuilder(), "tigase", JUSTIFY.LEFT, 10, ' ', "[", "]").toString());

		assertEquals("[tigase]", padString(new StringBuilder(), "tigase", JUSTIFY.RIGHT, 1, ' ', "[", "]").toString());
		assertEquals("[tigase]", padString(new StringBuilder(), "tigase", JUSTIFY.CENTRE, 1, ' ', "[", "]").toString());
		assertEquals("[tigase]", padString(new StringBuilder(), "tigase", JUSTIFY.LEFT, 1, ' ', "[", "]").toString());
	}
}