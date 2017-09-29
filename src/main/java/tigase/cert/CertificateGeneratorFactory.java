/*
 * Tigase Jabber/XMPP Server
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

import java.security.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CertificateGeneratorFactory {

	private static final Logger log = Logger.getLogger(CertificateGeneratorFactory.class.getCanonicalName());

	private static CertificateGenerator generator;

	static {
		try{
			generator = (CertificateGenerator) Class.forName("tigase.cert.OldSelfSignedCertificateGenerator").newInstance();
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.log(Level.WARNING, "could not initialize self-signed SSL certificate generator", ex);
		}
	}

	public static CertificateGenerator getGenerator() throws NoSuchProviderException {
		if (generator == null) {
			throw new NoSuchProviderException("Self-signed certificate generator is unavailable.");
		}

		return generator;
	}

}
