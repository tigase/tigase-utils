/**
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

import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
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
		return toString(false);
	}

	public String toString(boolean basic) {
		StringBuilder sb = new StringBuilder(4096);

		for (Certificate cert : chain) {
			if (basic) {
				if (cert instanceof X509Certificate) {
					final X509Certificate certX509 = (X509Certificate) cert;
					sb.append("CN: ").append(CertificateUtil.getCertCName(certX509)).append('\n');
					final List<String> certAltCName = CertificateUtil.getCertAltCName(certX509);
					if (certAltCName != null && !certAltCName.isEmpty()) {
						sb.append('\t').append("alt: ").append(certAltCName).append('\n');
					}
					sb.append('\t').append("Issuer: ").append(certX509.getIssuerDN()).append('\n');
					sb.append('\t').append("Not Before: ").append(certX509.getNotBefore()).append('\n');
					sb.append('\t').append("Not After: ").append(certX509.getNotAfter()).append('\n');
					sb.append('\n');
				}
			} else {
				sb.append(cert.toString());
			}
		}

		return "Private key: " + (privateKey != null ? privateKey.toString() : "private key missing!!! \n\n\n") + '\n' +
				sb;
	}

	public Optional<Certificate> getCertificate() {
		return (chain != null) && (chain.length > 0) ? Optional.of(chain[0]) : Optional.empty();
	}
}
