
/*
* Tigase Jabber/XMPP Server
* Copyright (C) 2004-2012 "Artur Hefczyc" <artur.hefczyc@tigase.org>
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
*
* $Rev$
* Last modified by $Author$
* $Date$
 */
package tigase.cert;

//~--- JDK imports ------------------------------------------------------------

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigInteger;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;

//~--- classes ----------------------------------------------------------------

/**
 * Created: Oct 9, 2010 9:16:55 PM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class RSAPrivateKeyDecoder {
	private InputStream is = null;

	//~--- constructors ---------------------------------------------------------

	/**
	 * Constructs ...
	 *
	 *
	 * @param bytes
	 */
	public RSAPrivateKeyDecoder(byte[] bytes) {
		this(new ByteArrayInputStream(bytes));
	}

	/**
	 * Constructs ...
	 *
	 *
	 * @param is
	 */
	public RSAPrivateKeyDecoder(InputStream is) {
		this.is = is;
	}

	//~--- get methods ----------------------------------------------------------

	/**
	 * Method description
	 *
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public RSAPrivateCrtKeySpec getKeySpec() throws IOException {

		// Skip to the beginning of the sequence:
		int tag = is.read();
		int len = readLength();

		// System.out.println("Sequence: " + tag + ", size: " + len);
		BigInteger ver = nextInt();
		BigInteger mod = nextInt();
		BigInteger pubExp = nextInt();
		BigInteger privExp = nextInt();
		BigInteger prime1 = nextInt();
		BigInteger prime2 = nextInt();
		BigInteger exp1 = nextInt();
		BigInteger exp2 = nextInt();
		BigInteger coef = nextInt();

		return new RSAPrivateCrtKeySpec(mod, pubExp, privExp, prime1, prime2, exp1, exp2, coef);
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 *
	 *
	 * @throws IOException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public PrivateKey getPrivateKey()
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		return keyFactory.generatePrivate(getKeySpec());
	}

	//~--- methods --------------------------------------------------------------

	private BigInteger nextInt() throws IOException {
		int tag = is.read();
		int len = readLength();
		byte[] val = new byte[len];
		int res = is.read(val);

		if (res < len) {
			throw new IOException("Invalid DER data: data too short.");
		}

		return new BigInteger(val);
	}

	private int readLength() throws IOException {
		int len = is.read();

		if (len == -1) {
			throw new IOException("Invalid field length in DER data.");
		}

		if ((len & ~0x7F) == 0) {
			return len;
		}

		int size = len & 0x7F;

		if ((len >= 0xFF) || (size > 4)) {
			throw new IOException("Invalid field length in DER data: too big (" + len + ")");
		}

		byte[] bytes = new byte[size];
		int res = is.read(bytes);

		if (res < size) {
			throw new IOException("Invalid DER file: data too short.");
		}

		return new BigInteger(1, bytes).intValue();
	}
}


//~ Formatted in Sun Code Convention


//~ Formatted by Jindent --- http://www.jindent.com
