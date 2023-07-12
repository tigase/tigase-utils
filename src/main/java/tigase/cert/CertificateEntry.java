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
package tigase.cert;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Optional;

/**
 * Created: Oct 9, 2010 5:08:30 PM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class CertificateEntry {

	private Certificate[] chain = null;
	private PrivateKey privateKey = null;
	private KeyPair keyPair = null;

	public CertificateEntry() {
	}

	public CertificateEntry(Certificate[] chain, KeyPair keyPair) {
		this.chain = chain;
		this.keyPair = keyPair;
		privateKey = keyPair.getPrivate();
	}

	public CertificateEntry(Certificate[] chain, PrivateKey privateKey) {
		this.chain = chain;
		this.privateKey = privateKey;
		this.keyPair = new KeyPair(chain[0].getPublicKey(), privateKey);
	}

	public Certificate[] getCertChain() {
		return chain;
	}

	public void setCertChain(Certificate[] chain) {
		this.chain = chain;
		if (privateKey != null) {
			keyPair = new KeyPair(chain[0].getPublicKey(), privateKey);
		}
	}

	public PrivateKey getPrivateKey() {
		return keyPair.getPrivate();
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
		if (chain != null) {
			keyPair = new KeyPair(chain[0].getPublicKey(), privateKey);
		}
	}

	@Override
	public String toString() {
		return toString(false);
	}

	public String toString(boolean basic) {
		StringBuilder sb = new StringBuilder(4096);

		for (Certificate cert : chain) {
			if (basic) {
				CertificateUtil.getCertificateBasicInfo(sb, cert);
			} else {
				sb.append(cert.toString());
			}
		}

		return "Private key: " + (privateKey != null ? "present" : "MISSING!!! \n\n\n") + '\n' +
				sb;
	}

	public Optional<KeyPair> getKeyPair() {
		return Optional.ofNullable(keyPair);
	}

	public Optional<Certificate> getCertificate() {
		return (chain != null) && (chain.length > 0) ? Optional.of(chain[0]) : Optional.empty();
	}
}
