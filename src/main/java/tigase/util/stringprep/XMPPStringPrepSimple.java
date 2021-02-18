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
package tigase.util.stringprep;

import java.util.regex.Pattern;

/**
 * Created: Feb 4, 2010 9:31:23 AM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class XMPPStringPrepSimple
		implements XMPPStringPrepIfc {

	private static final Pattern p = Pattern.compile("[ @&()\\[\\]\t\n\r\f\\a\\e]");

	@Override
	public String nameprep(String domain) throws TigaseStringprepException {
		String result = domain.trim().toLowerCase();

		if (!checkString(result)) {
			throw new TigaseStringprepException("Illegal characters in string, domain = " + domain);
		}

		return result;
	}

	@Override
	public String nodeprep(String localpart) throws TigaseStringprepException {
		String result = localpart.trim();

		if (!checkString(result)) {
			throw new TigaseStringprepException("Illegal characters in string, localpart = " + localpart);
		}

		return result;
	}

	@Override
	public String resourceprep(String resource) throws TigaseStringprepException {
		// according to the RFC6122 it is allowed to have leading and trailing SPACE in the resource part so trimming it
		// is not a good idea and if we want to forbid some chars we should throw TigaseStringprepException instead of
		// just trimming string
		return resource;
		//return resource.trim();
	}

	private boolean checkString(String input) {
		return !p.matcher(input).find();
	}
}

