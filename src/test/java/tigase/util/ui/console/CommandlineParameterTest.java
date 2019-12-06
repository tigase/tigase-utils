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
package tigase.util.ui.console;

import org.junit.Assert;
import org.junit.Test;

public class CommandlineParameterTest {

	@Test
	public void testEqual() {
		final CommandlineParameter l10 = new CommandlineParameter.Builder("l", "letter").build();
		final CommandlineParameter l11 = new CommandlineParameter.Builder("L", "letter").build();
		final CommandlineParameter l12 = new CommandlineParameter.Builder("L", "Letter").build();
		final CommandlineParameter l20 = new CommandlineParameter.Builder(null, "letter").build();
		final CommandlineParameter l30 = new CommandlineParameter.Builder("l", null).build();

		Assert.assertEquals(l10, l20);
		Assert.assertEquals(l10, l11);
		Assert.assertEquals(l10, l30);
		Assert.assertNotSame(l20, l30);
	}
}