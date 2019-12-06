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

public class Main {

	public static void main(String[] args) throws FormSignerException {
		Form form = new Form("form", "Create account", null);
		form.addField(Field.fieldTextSingle("UserName", "bmalkow", "User Name"));
		form.addField(Field.fieldTextSingle("Password", "dupa.8", "Password"));

		FormSigner signer = new FormSigner("111", "1111.8", "aplikacja", "aplikacja.8");
		signer.signForm(JID.jidInstanceNS("a@b.c"), form);

		form.addField(Field.fieldTextSingle("Password", "dupa.8", "Password"));
		// form.removeField("Password");

		System.out.println(form.getElement());

		FormSignatureVerifier verifier = new FormSignatureVerifier("aplikacja", "aplikacja.8");
		System.out.println(verifier.verify(JID.jidInstanceNS("a@b.c"), form.getElement()));

	}

}
