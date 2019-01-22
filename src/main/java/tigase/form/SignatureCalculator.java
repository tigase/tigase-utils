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
package tigase.form;

import tigase.util.Base64;
import tigase.xmpp.jid.JID;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

public class SignatureCalculator {

	public static final String SUPPORTED_TYPE = "urn:xmpp:xdata:signature:oauth1";
	private final static String ALGORITHM = "SHA1";
	private final static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private final Comparator<Field> fieldComparator = new Comparator<Field>() {

		@Override
		public int compare(Field o1, Field o2) {
			return o1.getVar().compareToIgnoreCase(o2.getVar());
		}
	};
	/**
	 * A key identifying the account doing the signing of the form. The client has to set this to identify who performs
	 * the signature.
	 */
	private String oauthConsumerKey;
	/**
	 * The signature, signing the form. The client has to set this with the signature of the form, as calculated and
	 * described below.
	 */
	private String oauthConsumerSecret;
	/**
	 * Specifies the signature method, or hash function, to use when signing the form. This can be changed by the
	 * client. Possible values are: HMAC-SHA1, RSA-SHA1 and PLAINTEXT.
	 */
	private String oauthSignatureMethod = "HMAC-SHA1";
	/**
	 * This is a token provided by the server to the client. This parameter might not be available if the server has
	 * provided the client with this token earlier during the session.
	 */
	private String oauthToken;
	/**
	 * This is a temporary secret shared between the server and client, and is related to the token. This parameter
	 * might not be available if the server has provided the client with this token earlier during the session.
	 */
	private String oauthTokenSecret;
	/**
	 * Must be 1.0. Is not changed by the client performing the signing.
	 */
	private String oauthVersion = "1.0";
	private Random random = new SecureRandom();

	protected static String escape(String s) {
		if (s == null) {
			return "";
		}
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	protected static byte[] hmac(final SecretKey key, byte[] data)
			throws NoSuchAlgorithmException, InvalidKeyException {
		Mac mac = Mac.getInstance(key.getAlgorithm());
		mac.init(key);
		return mac.doFinal(data);
	}

	public SignatureCalculator() {
	}

	public SignatureCalculator(String oauthConsumerKey, String oauthConsumerSecret) {
		this.oauthToken = randomString();
		this.oauthTokenSecret = randomString();
		this.oauthConsumerKey = oauthConsumerKey;
		this.oauthConsumerSecret = oauthConsumerSecret;
	}

	public SignatureCalculator(String oauthToken, String oauthTokenSecret, String oauthConsumerKey,
							   String oauthConsumerSecret) {
		this.oauthToken = oauthToken;
		this.oauthTokenSecret = oauthTokenSecret;
		this.oauthConsumerKey = oauthConsumerKey;
		this.oauthConsumerSecret = oauthConsumerSecret;
	}

	public void addEmptyFields(Form form) {
		form.addField(Field.fieldHidden("FORM_TYPE", SUPPORTED_TYPE));
		form.addField(Field.fieldHidden("oauth_version", oauthVersion));
		form.addField(Field.fieldHidden("oauth_signature_method", oauthSignatureMethod));
		form.addField(Field.fieldHidden("oauth_token", oauthToken));
		form.addField(Field.fieldHidden("oauth_token_secret", oauthTokenSecret));
		form.addField(Field.fieldHidden("oauth_nonce", null));
		form.addField(Field.fieldHidden("oauth_timestamp", null));
		form.addField(Field.fieldHidden("oauth_consumer_key", null));
		form.addField(Field.fieldHidden("oauth_signature", null));

	}

	/**
	 * Calculate signature of given form. Form will not be changed.
	 *
	 * @param to the full destination address, including resource, if any.
	 * @param form form to sign.
	 *
	 * @return Signature of form.
	 */
	public String calculateSignature(JID to, Form form) throws InvalidKeyException, NoSuchAlgorithmException {
		SecretKey key = key((escape(oauthConsumerSecret) + "&" + escape(oauthTokenSecret)).getBytes());

		String pStr = pStr(form.getAllFields());
		String bStr = escape(form.getType()) + "&" + escape(to == null ? "" : (to.toString())) + "&" + escape(pStr);
		String sig = escape(Base64.encode(hmac(key, bStr.getBytes())));

		return sig;
	}

	/**
	 * @return the oauthConsumerKey
	 */
	public String getOauthConsumerKey() {
		return oauthConsumerKey;
	}

	/**
	 * @param oauthConsumerKey the oauthConsumerKey to set
	 */
	public void setOauthConsumerKey(String oauthConsumerKey) {
		this.oauthConsumerKey = oauthConsumerKey;
	}

	/**
	 * @return the oauthConsumerSecret
	 */
	public String getOauthConsumerSecret() {
		return oauthConsumerSecret;
	}

	/**
	 * @param oauthConsumerSecret the oauthConsumerSecret to set
	 */
	public void setOauthConsumerSecret(String oauthConsumerSecret) {
		this.oauthConsumerSecret = oauthConsumerSecret;
	}

	/**
	 * @return the oauthToken
	 */
	public String getOauthToken() {
		return oauthToken;
	}

	/**
	 * @param oauthToken the oauthToken to set
	 */
	public void setOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
	}

	/**
	 * @return the oauthTokenSecret
	 */
	public String getOauthTokenSecret() {
		return oauthTokenSecret;
	}

	/**
	 * @param oauthTokenSecret the oauthTokenSecret to set
	 */
	public void setOauthTokenSecret(String oauthTokenSecret) {
		this.oauthTokenSecret = oauthTokenSecret;
	}

	public boolean isMethodSupported(String fOauthSignatureMethod) {
		return fOauthSignatureMethod.equals(oauthSignatureMethod);
	}

	/**
	 * Sign given form with current time. Signature will be added to form.
	 *
	 * @param to the full destination address, including resource, if any.
	 * @param form form to sign.
	 */
	public void sign(JID to, Form form) throws InvalidKeyException, NoSuchAlgorithmException {
		String nonce = randomString();
		long timestamp = System.currentTimeMillis() / 1000l;
		sign(to, nonce, timestamp, form);
	}

	/**
	 * Sign given Form. Signature will be added to form.
	 *
	 * @param to the full destination address, including resource, if any.
	 * @param nonce A nonce value that the client has to set. Can be a random alphanumerical string.
	 * @param timestamp Number of seconds since 1st of January 1970, 00:00:00 GMT. The client has to set this at the
	 * time of signature.
	 * @param form form to sign.
	 */
	public void sign(JID to, String nonce, long timestamp, Form form)
			throws InvalidKeyException, NoSuchAlgorithmException {
		form.addField(Field.fieldHidden("FORM_TYPE", SUPPORTED_TYPE));
		form.addField(Field.fieldHidden("oauth_version", oauthVersion));
		form.addField(Field.fieldHidden("oauth_signature_method", oauthSignatureMethod));
		form.addField(Field.fieldHidden("oauth_token", oauthToken));
		form.addField(Field.fieldHidden("oauth_token_secret", oauthTokenSecret));
		form.addField(Field.fieldHidden("oauth_nonce", nonce));
		form.addField(Field.fieldHidden("oauth_timestamp", String.valueOf(timestamp)));
		form.addField(Field.fieldHidden("oauth_consumer_key", oauthConsumerKey));

		String sig = calculateSignature(to, form);
		form.addField(Field.fieldHidden("oauth_signature", sig));
	}

	protected byte[] h(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
		return digest.digest(data);
	}

	protected SecretKey key(final byte[] key) {
		return new SecretKeySpec(key, "Hmac" + ALGORITHM);
	}

	protected String randomString() {
		final int length = 20;
		final int x = ALPHABET.length();
		char[] buffer = new char[length];
		for (int i = 0; i < length; i++) {
			int r = random.nextInt(x);
			buffer[i] = ALPHABET.charAt(r);
		}
		return new String(buffer);
	}

	/**
	 * Each (name, value) pair in the list of sorted parameters are first transformed into pairs of
	 * Escape(name)=Escape(value) segments, and then concatenated into one string, where each segment is delimited using
	 * an ampersand ('&') character.
	 */
	private String pStr(List<Field> fields) {
		ArrayList<Field> tmp = new ArrayList<>(fields);
		Collections.sort(tmp, fieldComparator);

		StringBuilder sb = new StringBuilder();

		Iterator<Field> it = tmp.iterator();
		while (it.hasNext()) {
			final Field f = it.next();
			if ("oauth_signature".equals(f.getVar()) || "oauth_token_secret".equals(f.getVar())) {
				continue;
			}

			sb.append(escape(f.getVar())).append("=").append(escape(f.getValue()));
			if (it.hasNext()) {
				sb.append('&');
			}

		}

		return sb.toString();
	}

}
