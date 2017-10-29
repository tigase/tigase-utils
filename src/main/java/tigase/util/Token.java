/*
 * Token.java
 *
 * Tigase Jabber/XMPP Utils
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
package tigase.util;

import tigase.xmpp.jid.BareJID;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

/**
 * Created by bmalkow on 21.04.2017.
 */
public class Token {

	private static final Random RAND_GEN = new SecureRandom();
	private BareJID jid;
	private String random;
	private Date timestamp;

	static String copy(byte[] buff, int offset, int len) {
		String r = "";

		for (int i = offset; i < offset + len; i++) {
			r += (char) buff[i];
		}

		return r;
	}

	public static Token create(BareJID jid) {
		byte[] r = new byte[20];
		RAND_GEN.nextBytes(r);
		return create(jid, new Date(), Base64.encode(r));
	}

	public static Token create(BareJID jid, Date timestamp, String random) {
		Token t = new Token();
		t.jid = jid;
		t.timestamp = timestamp;
		t.random = random;
		return t;
	}

	private static Token decodeTokenV1(final byte[] buff) {
		int jidEndPos = nullPos(buff, 1);
		int timestampEndPos = nullPos(buff, jidEndPos + 1);

		String j = copy(buff, 1, jidEndPos - 1);
		String ts = copy(buff, jidEndPos + 1, timestampEndPos - jidEndPos - 1);
		String r = copy(buff, timestampEndPos + 1, buff.length - timestampEndPos - 1);

		Token result = new Token();
		result.random = r;
		result.jid = BareJID.bareJIDInstanceNS(new String(Base64.decode(j), Charset.forName("UTF-8")));
		result.timestamp = new Date(Long.parseLong(ts));
		return result;
	}

	static int nullPos(byte[] buff, int from) {
		for (int i = from; i < buff.length; i++) {
			if (buff[i] == 0) {
				return i;
			}
		}
		return -1;
	}

	public static Token parse(String encodedToken) {
		if (encodedToken == null) {
			throw new RuntimeException("Invalid token");
		}

		byte[] buff = Base64.decode(encodedToken);
		if (buff == null || buff.length == 0) {
			throw new RuntimeException("Invalid token");
		}
		byte tokenType = buff[0];
		switch (tokenType) {
			case 1:
				return decodeTokenV1(buff);
			default:
				throw new RuntimeException("Unknown token");
		}
	}

	public String getEncoded() {
		return Base64.encode(getBuff());
	}

	public String getHash() {
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			return Base64.encode(sha.digest(getBuff()));
		} catch (Exception e) {
			return null;
		}
	}

	public BareJID getJid() {
		return jid;
	}

	public String getRandom() {
		return random;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	protected byte[] getBuff() {
		String j = Base64.encode(this.jid.toString().getBytes(Charset.forName("UTF-8")));
		String ts = String.valueOf(timestamp.getTime());

		int len = 1 + j.length() + 1 + ts.length() + 1 + random.length();
		byte[] buff = new byte[len];
		buff[0] = 1; // token version/type
		byte[] tmp;
		int idx = 1;

		tmp = j.getBytes();
		System.arraycopy(tmp, 0, buff, idx, tmp.length);
		idx += tmp.length;
		buff[idx++] = 0;

		tmp = ts.getBytes();
		System.arraycopy(tmp, 0, buff, idx, tmp.length);
		idx += tmp.length;
		buff[idx++] = 0;

		tmp = random.getBytes();
		System.arraycopy(tmp, 0, buff, idx, tmp.length);
		idx += tmp.length;
//		buff[idx++] = 0;

		return buff;
	}

}
