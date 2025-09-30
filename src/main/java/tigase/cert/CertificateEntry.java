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
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
	private String domain = null;
	private Set<String> allDomains = new HashSet<>();
	private boolean selfSigned = false;

	public CertificateEntry() {
	}

	public CertificateEntry(Certificate[] chain, KeyPair keyPair) {
		this.chain = chain;
		this.keyPair = keyPair;
		privateKey = keyPair.getPrivate();
		updateChain();
	}

	public CertificateEntry(Certificate[] chain, PrivateKey privateKey) {
		this.chain = chain;
		this.privateKey = privateKey;
		updateChain();
		updateKeyPair();
	}

	public Certificate[] getCertChain() {
		return chain;
	}

	public Certificate[] getCertChain(boolean withoutRoot) {
		if (withoutRoot) {
			return CertificateUtil.removeRootCACertificate(chain);
		} else  {
			return chain;
		}
	}

	public void setCertChain(Certificate[] chain) {
		this.chain = chain;
		updateChain();
		updateKeyPair();
	}

	public PrivateKey getPrivateKey() {
		return keyPair.getPrivate();
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
		updateKeyPair();
	}

	public String getDomain() {
		return domain;
	}

	public Set<String> getAllDomains() {
		return allDomains;
	}

	public boolean isSelfSigned() {
		return selfSigned;
	}

	public boolean isValid() {
		if (chain == null || chain.length == 0) {
			return false;
		}
		if (chain[0] instanceof X509Certificate) {
			return !CertificateUtil.isExpired((X509Certificate) chain[0]);
		}
		return true;
	}

	private void updateChain() {
		allDomains.clear();
		domain = null;
		selfSigned = false;
		if (chain != null) {
			chain = CertificateUtil.sort(chain);
			if (chain[0] instanceof X509Certificate) {
				selfSigned = CertificateUtil.isSelfSigned((X509Certificate) chain[0]);
				allDomains.addAll(CertificateUtil.getCertAltCName((X509Certificate) chain[0]));
				domain = CertificateUtil.getCertCName((X509Certificate) chain[0]);
				if (domain != null) {
					allDomains.add(domain);
				}
			}
		}
	}

	private void updateKeyPair() {
		if (chain != null && chain.length > 0 && privateKey != null) {
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

		return "Alias: " + domain + ", alternative domains: " + allDomains + ", Private key: " +
				(privateKey != null ? "present" : "MISSING!!! \n\n\n") + '\n' + sb;
	}

	public Optional<KeyPair> getKeyPair() {
		return Optional.ofNullable(keyPair);
	}

	public Optional<Certificate> getCertificate() {
		return (chain != null) && (chain.length > 0) ? Optional.of(chain[0]) : Optional.empty();
	}
}
