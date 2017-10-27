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
