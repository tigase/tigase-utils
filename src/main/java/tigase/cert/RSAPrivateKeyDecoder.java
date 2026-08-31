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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;

import static tigase.cert.Asn1DerUtilities.*;

/**
 * Created: Oct 9, 2010 9:16:55 PM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class RSAPrivateKeyDecoder {

	private final InputStream is;
	private final RSAPrivateCrtKeySpec keySpec;

	public RSAPrivateKeyDecoder(byte[] bytes) throws IOException {
		this(new ByteArrayInputStream(bytes));
	}

	public RSAPrivateKeyDecoder(InputStream is) throws IOException {
		this.is = is;
		keySpec = getKeySpec();
		is.close();
    }

	private RSAPrivateCrtKeySpec getKeySpec() throws IOException {

		// Skip to the beginning of the sequence:
		int tag = is.read();
		int len = readLength(is);

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

	public PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		return keyFactory.generatePrivate(keySpec);
	}

	private BigInteger nextInt() throws IOException {
		return new BigInteger(nextComponentValue(is));
	}
}

