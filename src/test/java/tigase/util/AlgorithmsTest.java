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
package tigase.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class AlgorithmsTest
		extends TestCase {

	public void testBytesToHex() {
		byte[] data = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, (byte) 255};
		assertEquals("000102030405060708090aff", Algorithms.bytesToHex(data));

		data = new byte[]{(byte) 0x5e, (byte) 0x88, (byte) 0x48, (byte) 0x98, (byte) 0xda, (byte) 0x28, (byte) 0x04,
						  (byte) 0x71, (byte) 0x51, (byte) 0xd0, (byte) 0xe5, (byte) 0x6f, (byte) 0x8d, (byte) 0xc6,
						  (byte) 0x29, (byte) 0x27, (byte) 0x73, (byte) 0x60, (byte) 0x3d, (byte) 0x0d, (byte) 0x6a,
						  (byte) 0xab, (byte) 0xbd, (byte) 0xd6, (byte) 0x2a, (byte) 0x11, (byte) 0xef, (byte) 0x72,
						  (byte) 0x1d, (byte) 0x15, (byte) 0x42, (byte) 0xd8};
		assertEquals("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", Algorithms.bytesToHex(data));
	}

	public void testGenerateDialbackKey() {
		String originatingServer = "example.org";
		String receivingServer = "xmpp.example.com";
		String secret = "s3cr3tf0rd14lb4ck";
		String streamID = "D60000229F";

		String k = Algorithms.generateDialbackKey(originatingServer, receivingServer, secret, streamID);

		Assert.assertEquals("Wrong Dialback Key!", "37c69b1cf07a3f67c04a5ef5902fa5114f2c76fe4a2686482ba5b89323075643",
							k);
	}

	public void testSha256() {
		assertEquals("a7136eb1f46c9ef18c5e78c36ca257067c69b3d518285f0b18a96c33beae9acc",
					 Algorithms.sha256("s3cr3tf0rd14lb4ck"));
	}

}
