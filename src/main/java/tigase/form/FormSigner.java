package tigase.form;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import tigase.xmpp.JID;

public class FormSigner {

	private SignatureCalculator calculator;

	public FormSigner(String oauthToken, String oauthTokenSecret, String oauthConsumerKey, String oauthConsumerSecret) {
		this.calculator = new SignatureCalculator(oauthToken, oauthTokenSecret, oauthConsumerKey, oauthConsumerSecret);
	}

	/**
	 * Sign given form with current time. Signature will be added to form.
	 * 
	 * @param to
	 *            the full destination address, including resource, if any.
	 * @param form
	 *            form form to sign.
	 * @throws FormSignerException
	 */
	public void signForm(JID to, Form form) throws FormSignerException {
		try {
			this.calculator.sign(to, form);
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			throw new FormSignerException("Can't sign Form", e);
		}
	}

}
