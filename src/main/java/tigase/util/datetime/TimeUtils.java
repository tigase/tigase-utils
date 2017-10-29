/*
 * TimeUtils.java
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

package tigase.util.datetime;

//~--- JDK imports ------------------------------------------------------------

import java.util.Calendar;

//~--- classes ----------------------------------------------------------------

/**
 * This is too slow.
 * <p>
 * <p>
 * Created: Tue Oct 28 21:08:58 2008
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public abstract class TimeUtils {

	/**
	 * Method description
	 */
	public static int getHourNow() {
		Calendar cal = Calendar.getInstance();

		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Method description
	 */
	public static int getMinuteNow() {
		Calendar cal = Calendar.getInstance();

		return cal.get(Calendar.MINUTE);
	}
}

//~ Formatted in Sun Code Convention

//~ Formatted by Jindent --- http://www.jindent.com
