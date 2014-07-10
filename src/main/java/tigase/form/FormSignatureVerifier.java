package tigase.form;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import tigase.xml.Element;
import tigase.xmpp.JID;

public class FormSignatureVerifier {

	public interface SignatureVerifyHandler {

		void onFormVerify(JID to, Form form, SignatureCalculator signatureCalculator) throws FormSignerException;

	}

	private String oauthConsumerKey;

	private String oauthConsumerSecret;

	public FormSignatureVerifier(String oauthConsumerKey, String oauthConsumerSecret) {
		this.oauthConsumerKey = oauthConsumerKey;
		this.oauthConsumerSecret = oauthConsumerSecret;
	}

	protected boolean isFormSigned(Form form) {
		String tp = form.getAsString("FORM_TYPE");
		if (tp == null || !tp.equals("urn:xmpp:xdata:signature:oauth1"))
			return false;

		return form.get("oauth_timestamp") != null && form.get("oauth_signature") != null;
	}

	/**
	 * Verify signature of given form.
	 * 
	 * @param to
	 * @param form
	 * @return timestamp of signature is signature is valid. If signature is
	 *         invalid exception will be throwed.
	 * @throws FormSignerException
	 *             if signature is invalid or can't be checked.
	 */
	public long verify(JID to, Element form) throws FormSignerException {
		return verify(to, new Form(form), null);
	}

	/**
	 * Verify signature of given form.
	 * 
	 * @param to
	 *            the full destination address, including resource, if any.
	 * @param form
	 *            signed Form to verify.
	 * @param handler
	 *            handler to make additional verification (for example validate
	 *            received <code>oauth_token</code>).
	 * @return timestamp of signature is signature is valid. If signature is
	 *         invalid exception will be throwed.
	 * @throws FormSignerException
	 *             if signature is invalid or can't be checked.
	 */
	public long verify(JID to, Element form, SignatureVerifyHandler handler) throws FormSignerException {
		return verify(to, new Form(form), handler);
	}

	/**
	 * Verify signature of given form.
	 * 
	 * @param to
	 *            the full destination address, including resource, if any.
	 * @param form
	 *            signed Form to verify.
	 * @return timestamp of signature is signature is valid. If signature is
	 *         invalid exception will be throwed.
	 * @throws FormSignerException
	 *             if signature is invalid or can't be checked.
	 */
	public long verify(JID to, Form form) throws FormSignerException {
		return verify(to, form, null);
	}

	/**
	 * Verify signature of given form.
	 * 
	 * @param to
	 *            the full destination address, including resource, if any.
	 * @param form
	 *            signed Form to verify.
	 * @param handler
	 *            handler to make additional verification (for example validate
	 *            received <code>oauth_token</code>).
	 * @return timestamp of signature is signature is valid. If signature is
	 *         invalid exception will be throwed.
	 * @throws FormSignerException
	 *             if signature is invalid or can't be checked.
	 */
	public long verify(JID to, Form form, SignatureVerifyHandler handler) throws FormSignerException {
		if (!isFormSigned(form))
			throw new FormSignerException("Form isn't signed.");

		try {
			Long timestamp = form.getAsLong("oauth_timestamp");

			final String fSignature = form.getAsString("oauth_signature");
			final String fOauthSignatureMethod = form.getAsString("oauth_signature_method");
			final String fOauthToken = form.getAsString("oauth_token");
			final String fOauthTokenSecret = form.getAsString("oauth_token_secret");
			final String fOauthConsumerKey = form.getAsString("oauth_consumer_key");

			if (!fOauthConsumerKey.equals(oauthConsumerKey))
				throw new FormSignerException("Unrecognized oauth_consumer_key.");

			SignatureCalculator calculator = new SignatureCalculator(fOauthToken, fOauthTokenSecret, oauthConsumerKey,
					oauthConsumerSecret);
			if (!calculator.isMethodSupported(fOauthSignatureMethod))
				throw new FormSignerException("Signature method " + fOauthSignatureMethod + " isn't supported.");

			final String calculatedSignature = calculator.calculateSignature(to, form);

			if (!calculatedSignature.equals(fSignature)) {
				throw new FormSignerException("Invalid signature.");
			}

			if (handler != null)
				handler.onFormVerify(to, form, calculator);

			return timestamp;
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			throw new FormSignerException("Cannot validate signature of Form", e);
		}
	}
}
