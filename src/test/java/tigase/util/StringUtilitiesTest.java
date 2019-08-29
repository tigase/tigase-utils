package tigase.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tigase.util.StringUtilities.JUSTIFY;

public class StringUtilitiesTest {

	@Test
	public void testPadToColumnString() {
		final StringBuilder sb = new StringBuilder();
		assertEquals("tigase    ", StringUtilities.padStringToColumn(sb,"tigase",JUSTIFY.LEFT,10,' ',null, null).toString());
		assertEquals("tigase    tigase    ", StringUtilities.padStringToColumn(sb,"tigase",JUSTIFY.LEFT,20,' ',null, null).toString());
		assertEquals("tigase    tigase        tigase", StringUtilities.padStringToColumn(sb,"tigase",JUSTIFY.RIGHT,30,' ',null, null).toString());


	}
	@Test
	public void testPadString() {

		assertEquals("tigase    ", StringUtilities.padString(new StringBuilder(), "tigase", 10).toString());

		assertEquals("[    tigase]",
					 StringUtilities.padString(new StringBuilder(), "tigase", JUSTIFY.RIGHT, 10, ' ', "[", "]")
							 .toString());
		assertEquals("[  tigase  ]",
					 StringUtilities.padString(new StringBuilder(), "tigase", JUSTIFY.CENTRE, 10, ' ', "[", "]")
							 .toString());
		assertEquals("[tigase    ]",
					 StringUtilities.padString(new StringBuilder(), "tigase", JUSTIFY.LEFT, 10, ' ', "[", "]")
							 .toString());

		assertEquals("[tigase]",
					 StringUtilities.padString(new StringBuilder(), "tigase", JUSTIFY.RIGHT, 1, ' ', "[", "]")
							 .toString());
		assertEquals("[tigase]",
					 StringUtilities.padString(new StringBuilder(), "tigase", JUSTIFY.CENTRE, 1, ' ', "[", "]")
							 .toString());
		assertEquals("[tigase]",
					 StringUtilities.padString(new StringBuilder(), "tigase", JUSTIFY.LEFT, 1, ' ', "[", "]")
							 .toString());
	}
}