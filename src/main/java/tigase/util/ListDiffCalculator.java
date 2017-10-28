/*
 * ListDiffCalculator.java
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by bmalkow on 19.12.2016.
 */
public class ListDiffCalculator {

	private static <T> int commons(Collection<T> toCheck, Collection<T> inList) {
		int r = 0;
		for (T t : toCheck) {
			if (inList.contains(t)) {
				++r;
			}
		}
		return r;
	}

	public <T> float calcDiff(T[] t1, T[] t2) {
		List<T> l1 = Arrays.asList(t1);
		List<T> l2 = Arrays.asList(t2);
		return calcDiff(l1, l2);
	}

	public <T> float calcDiff(Collection<T> l1, Collection<T> l2) {
		if (l1.size() == 0 && l2.size() == 0) {
			return 0;
		} else if (Collections.disjoint(l1, l2)) {
			return 1;
		} else if (l1.equals(l2)) {
			return 0;
		}

		float s = Math.max(l1.size(), l2.size());
		float comm = commons(l1, l2);

		float r = 1f - (comm / s);

		return r;
	}

}
