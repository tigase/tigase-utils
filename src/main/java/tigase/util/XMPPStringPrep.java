/*
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2008 "Artur Hefczyc" <artur.hefczyc@tigase.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 * 
 * $Rev$
 * Last modified by $Author$
 * $Date$
 */

package tigase.util;

import gnu.inet.encoding.Stringprep;
import gnu.inet.encoding.StringprepException;

/**
 * Created: Dec 28, 2009 10:04:08 PM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
class XMPPStringPrep implements XMPPStringPrepIfc {

	@Override
	public String nameprep(String input) throws TigaseStringprepException {
		try {
			return Stringprep.nameprep(input);
		} catch (StringprepException ex) {
			throw new TigaseStringprepException("nameprep unsuccessfull: ", ex);
		}
	}

	@Override
	public String nodeprep(String input) throws TigaseStringprepException {
		try {
			return Stringprep.nodeprep(input);
		} catch (StringprepException ex) {
			throw new TigaseStringprepException("nodeprep unsuccessfull: ", ex);
		}
	}

	@Override
	public String resourceprep(String input) throws TigaseStringprepException {
		try {
			return Stringprep.resourceprep(input);
		} catch (StringprepException ex) {
			throw new TigaseStringprepException("resourceprep unsuccessfull: ", ex);
		}
	}

}
