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

import gnu.inet.encoding.Stringprep;
import gnu.inet.encoding.StringprepException;

/**
 * Created: Dec 28, 2009 10:04:08 PM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
class XMPPStringPrepLibIDN
		implements XMPPStringPrepIfc {

	@Override
	public String nameprep(String domain) throws TigaseStringprepException {
		try {
			return Stringprep.nameprep(domain);
		} catch (StringprepException ex) {
			throw new TigaseStringprepException("nameprep unsuccessfull: ", ex);
		}
	}

	@Override
	public String nodeprep(String localpart) throws TigaseStringprepException {
		try {
			return Stringprep.nodeprep(localpart);
		} catch (StringprepException ex) {
			throw new TigaseStringprepException("nodeprep unsuccessfull: ", ex);
		}
	}

	@Override
	public String resourceprep(String resource) throws TigaseStringprepException {
		try {
			return Stringprep.resourceprep(resource);
		} catch (StringprepException ex) {
			throw new TigaseStringprepException("resourceprep unsuccessfull: ", ex);
		}
	}
}

