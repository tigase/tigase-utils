
/*
* Tigase Jabber/XMPP Server
* Copyright (C) 2004-2012 "Artur Hefczyc" <artur.hefczyc@tigase.org>
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
*
* $Rev$
* Last modified by $Author$
* $Date$
 */
package tigase.cert;

//~--- JDK imports ------------------------------------------------------------

import java.security.PrivateKey;
import java.security.cert.Certificate;

//~--- classes ----------------------------------------------------------------

/**
 * Created: Oct 9, 2010 5:08:30 PM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class CertificateEntry {
	private Certificate[] chain = null;
	private PrivateKey privateKey = null;

	//~--- get methods ----------------------------------------------------------

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public Certificate[] getCertChain() {
		return chain;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	//~--- set methods ----------------------------------------------------------

	/**
	 * Method description
	 *
	 *
	 *
	 * @param chain
	 */
	public void setCertChain(Certificate[] chain) {
		this.chain = chain;
	}

	/**
	 * Method description
	 *
	 *
	 * @param privateKey
	 */
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	//~--- methods --------------------------------------------------------------

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(4096);

		for (Certificate cert : chain) {
			sb.append(cert.toString());
		}

		return "Private key: " + privateKey.toString() + '\n' + sb;
	}
}


//~ Formatted in Sun Code Convention


//~ Formatted by Jindent --- http://www.jindent.com
