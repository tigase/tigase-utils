/*
 * XMPPStringPrepFactory.java
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

package tigase.util.stringprep;

import tigase.xmpp.jid.BareJID;
import tigase.xmpp.jid.JID;

import java.util.concurrent.TimeUnit;

/**
 * Created: Dec 28, 2009 10:02:31 PM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public abstract class XMPPStringPrepFactory {

	public static final String STRINGPREP_PROCESSOR_PROP_KEY = "stringprep-processor";

	public static final String[] STRINGPREP_PROCESSORS = {"libidn", "simple", "empty"};

	public static String STRINGPREP_PROCESSOR = "simple";

	private static boolean doWithJID(JID jid) {
		return jid.toString() != null;
	}

	/**
	 * Factory method for creating and returning stringprep implementation used by the Tigase server. This factory
	 * allows for pluggable stringprep library usage and replacing the library used by default. The default stringprep
	 * processor uses LibIDN library. Very CPU demanding processing. Use with care in open, multi-lingual systems.
	 *
	 * @return instance of XMPP Stringprep processor.
	 */
	public static XMPPStringPrepIfc getDefaultXMPPStringPrep() {
		STRINGPREP_PROCESSOR = System.getProperty(STRINGPREP_PROCESSOR_PROP_KEY, STRINGPREP_PROCESSOR);

		return getXMPPStringPrep(STRINGPREP_PROCESSOR);
	}

	/**
	 * Factory method for creating and returning stringprep implementation used by the Tigase server. This factory
	 * allows for pluggable stringprep library usage and replacing the library used by default. The empty stringprep
	 * processor does not perform any processing at all. It simply returns the string provided to the method.
	 * Recommended only in strictly controlled systems where there is no possibility of incorrectly formated JID getting
	 * to the system and the performance is the ke factor. uses simple Java <code>String</code> processing.
	 *
	 * @return instance of XMPP Stringprep processor.
	 */
	public static XMPPStringPrepIfc getEmptyXMPPStringPrep() {
		return new XMPPStringPrepEmpty();
	}

	/**
	 * Factory method for creating and returning stringprep implementation used by the Tigase server. This factory
	 * allows for pluggable stringprep library usage and replacing the library used by default. The stringprep processor
	 * uses LibIDN library. Very CPU demanding processing. Use  in open, multi-lingual systems.
	 *
	 * @return instance of XMPP Stringprep processor.
	 */
	public static XMPPStringPrepIfc getLibIDNXMPPStringPrep() {
		return new XMPPStringPrepLibIDN();
	}

	/**
	 * Factory method for creating and returning stringprep implementation used by the Tigase server. This factory
	 * allows for pluggable stringprep library usage and replacing the library used by default. The simple stringprep
	 * processor uses simple Java <code>String</code> processing. Recommended in relatively closed, single language
	 * systems where there is very low probability for in correct JIDs. Causes very low impact on performance.
	 *
	 * @return instance of XMPP Stringprep processor.
	 */
	public static XMPPStringPrepIfc getSimpleXMPPStringPrep() {
		return new XMPPStringPrepSimple();
	}

	/**
	 * @param stringprepProcessor
	 *
	 * @return
	 */
	public static XMPPStringPrepIfc getXMPPStringPrep(String stringprepProcessor) {
		if ("simple".equals(stringprepProcessor)) {
			return getSimpleXMPPStringPrep();
		}

		if ("libidn".equals(stringprepProcessor)) {
			return getLibIDNXMPPStringPrep();
		}

		if ("empty".equals(stringprepProcessor)) {
			return getEmptyXMPPStringPrep();
		}

		try {
			return (XMPPStringPrepIfc) Class.forName(stringprepProcessor).newInstance();
		} catch (Exception ex) {
			throw new IllegalArgumentException("Incorrect stringprep class name: " + stringprepProcessor, ex);
		}
	}

	public static void main(String[] args) throws Exception {
		long testTime = 30;    // 30 seconds by default

		System.out.printf("Each test time: %1$,d seconds\n", testTime);

		for (String proc : STRINGPREP_PROCESSORS) {
			long result = runTest(TimeUnit.SECONDS.toMillis(testTime), proc);

			System.out.printf("Stringprep processor: %1$s, result: %2$,d\n", proc, result);
		}
	}

	private static long runTest(long time, String stringprep) throws TigaseStringprepException {
		BareJID.useStringprepProcessor(stringprep);

		long testEnd = System.currentTimeMillis() + time;
		long loopCount = 0;
		StringBuilder sb = new StringBuilder(4096);
		boolean boolResult = true;

		while (System.currentTimeMillis() < testEnd) {
			++loopCount;
			sb.delete(0, sb.length());
			sb.append("User_Local_Part_").append(loopCount).append("@JID_Domain_Part_");
			sb.append(loopCount).append("/Resource Part ").append(loopCount);
			boolResult &= doWithJID(JID.jidInstance(sb.toString()));
		}

		System.out.println("Test completed: " + boolResult);

		return loopCount;
	}
}

