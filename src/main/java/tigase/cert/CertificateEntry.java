/*
 * CertificateEntry.java
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

	public Certificate[] getCertChain() {
		return chain;
	}

	public void setCertChain(Certificate[] chain) {
		this.chain = chain;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(4096);

		for (Certificate cert : chain) {
			sb.append(cert.toString());
		}

		return "Private key: " + (privateKey != null ? privateKey.toString() : "private key missing!!! \n\n\n") + '\n' +
				sb;
	}
}
