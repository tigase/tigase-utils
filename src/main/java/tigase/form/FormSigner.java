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
package tigase.form;

import tigase.xmpp.jid.JID;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FormSigner {

	private SignatureCalculator calculator;

	public FormSigner(String oauthToken, String oauthTokenSecret, String oauthConsumerKey, String oauthConsumerSecret) {
		this.calculator = new SignatureCalculator(oauthToken, oauthTokenSecret, oauthConsumerKey, oauthConsumerSecret);
	}

	/**
	 * Sign given form with current time. Signature will be added to form.
	 *
	 * @param to the full destination address, including resource, if any.
	 * @param form form form to sign.
	 *
	 * @throws FormSignerException when the key is invalid or the algorithm is not supported
	 */
	public void signForm(JID to, Form form) throws FormSignerException {
		try {
			this.calculator.sign(to, form);
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			throw new FormSignerException("Can't sign Form", e);
		}
	}

}
