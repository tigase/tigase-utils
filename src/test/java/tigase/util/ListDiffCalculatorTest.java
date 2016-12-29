/*
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2016 "Tigase, Inc." <office@tigase.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
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

/**
 * Created by bmalkow on 19.12.2016.
 */
public class ListDiffCalculatorTest {

	@Test
	public void testCalcDiff() throws Exception {
		final ListDiffCalculator calc = new ListDiffCalculator();

		String[] t1 = new String[]{};
		String[] t2 = new String[]{};
		Assert.assertEquals(0, calc.calcDiff(t1, t2), 0);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{"1"};
		t2 = new String[]{"1"};
		Assert.assertEquals(0, calc.calcDiff(t1, t2), 0);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{"1", "2"};
		t2 = new String[]{"1", "2"};
		Assert.assertEquals(0, calc.calcDiff(t1, t2), 0);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{"1", "2"};
		t2 = new String[]{"2", "1"};
		Assert.assertEquals(0, calc.calcDiff(t1, t2), 0);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{"1"};
		t2 = new String[]{"2"};
		Assert.assertEquals(1, calc.calcDiff(t1, t2), 0);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{};
		t2 = new String[]{"2"};
		Assert.assertEquals(1, calc.calcDiff(t1, t2), 0);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{"1", "2"};
		t2 = new String[]{"3", "4"};
		Assert.assertEquals(1, calc.calcDiff(t1, t2), 0);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{"1", "2"};
		t2 = new String[]{"3"};
		Assert.assertEquals(1, calc.calcDiff(t1, t2), 0);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{"1", "2"};
		t2 = new String[]{"1", "3"};
		Assert.assertEquals(0.5, calc.calcDiff(t1, t2), 0);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{"1", "2"};
		t2 = new String[]{"1"};
		Assert.assertEquals(0.5, calc.calcDiff(t1, t2), 0);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{"1"};
		t2 = new String[]{"1", "2"};
		Assert.assertEquals(0.5, calc.calcDiff(t1, t2), 0);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{"1"};
		t2 = new String[]{"1", "2", "3"};
		Assert.assertEquals(0.66666666, calc.calcDiff(t1, t2), 0.0000001);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{"1", "4", "5"};
		t2 = new String[]{"1", "2", "3"};
		Assert.assertEquals(0.66666666, calc.calcDiff(t1, t2), 0.0000001);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{"1", "2", "5"};
		t2 = new String[]{"1", "2", "3"};
		Assert.assertEquals(0.33333333, calc.calcDiff(t1, t2), 0.0000001);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{"1", "2", "5", "11"};
		t2 = new String[]{"3", "2", "1"};
		Assert.assertEquals(0.5, calc.calcDiff(t1, t2), 0.0000001);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{"1", "2"};
		t2 = new String[]{"1", "2", "3"};
		Assert.assertEquals(0.33333333, calc.calcDiff(t1, t2), 0.0000001);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);

		t1 = new String[]{};
		t2 = new String[]{"1", "2", "3"};
		Assert.assertEquals(1, calc.calcDiff(t1, t2), 0);
		Assert.assertEquals(calc.calcDiff(t1, t2), calc.calcDiff(t2, t1), 0);
	}

}