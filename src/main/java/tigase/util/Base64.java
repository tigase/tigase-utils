package tigase.util;

import java.util.Arrays;

public class Base64 {

	private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

	private static final int[] ALPHABET_1 = new int[256];

	static {
		Arrays.fill(ALPHABET_1, -1);
		for (int i = 0; i < ALPHABET.length; i++)
			ALPHABET_1[ALPHABET[i]] = i;
		ALPHABET_1['='] = 0;
	}

	public static byte[] decode(String s) {
		int separatorsCounter = 0;
		final int inputLen = s.length();
		for (int i = 0; i < inputLen; i++) {
			int c = ALPHABET_1[s.charAt(i)];
			if (c < 0 && c != '=')
				separatorsCounter++;
		}

		int deltas = 0;
		for (int i = inputLen - 1; i > 1 && ALPHABET_1[s.charAt(i)] <= 0; --i) {
			if (s.charAt(i) == '=') {
				++deltas;
			}
		}

		final int outputLen = (inputLen - separatorsCounter) * 3 / 4 - deltas;

		byte[] buffer = new byte[outputLen];
		int mask = 0xFF;
		int index = 0;
		int o;
		for (o = 0; o < s.length();) {

			int c0 = ALPHABET_1[s.charAt(o++)];
			if (c0 == -1) {
				o = findNexIt(s, --o);
				c0 = ALPHABET_1[s.charAt(o++)];
				if (c0 == -1)
					break;
			}
			int c1 = ALPHABET_1[s.charAt(o++)];
			if (c1 == -1) {
				o = findNexIt(s, --o);
				c1 = ALPHABET_1[s.charAt(o++)];
				if (c1 == -1)
					break;
			}

			buffer[index++] = (byte) (((c0 << 2) | (c1 >> 4)) & mask);
			if (index >= buffer.length) {
				break;
			}
			int c2 = ALPHABET_1[s.charAt(o++)];
			if (c2 == -1) {
				o = findNexIt(s, --o);
				c2 = ALPHABET_1[s.charAt(o++)];
				if (c2 == -1)
					break;
			}
			buffer[index++] = (byte) (((c1 << 4) | (c2 >> 2)) & mask);
			if (index >= buffer.length) {
				break;
			}
			int c3 = ALPHABET_1[s.charAt(o++)];
			if (c3 == -1) {
				o = findNexIt(s, --o);
				c3 = ALPHABET_1[s.charAt(o++)];
				if (c3 == -1)
					break;
			}
			buffer[index++] = (byte) (((c2 << 6) | c3) & mask);
		}

		return buffer;
	}

	public static String encode(byte[] buf) {
		final int size = buf.length;
		int outputSize = ((size + 2) / 3) * 4;
		final char[] output = new char[outputSize];
		int a = 0;
		int i = 0;
		while (i < size) {
			byte b0 = buf[i++];
			byte b1 = (i < size) ? buf[i++] : 0;
			byte b2 = (i < size) ? buf[i++] : 0;

			int mask = 0x3F;
			output[a++] = ALPHABET[(b0 >> 2) & mask];
			output[a++] = ALPHABET[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
			output[a++] = ALPHABET[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
			output[a++] = ALPHABET[b2 & mask];
		}
		switch (size % 3) {
		case 1:
			output[--a] = '=';
		case 2:
			output[--a] = '=';
		}
		return new String(output);
	}

	private static int findNexIt(String s, int i) {
		final int sl = s.length() - 1;
		int c2;
		if (i >= sl)
			return i;
		do {
			c2 = ALPHABET_1[s.charAt(++i)];
		} while (c2 == -1 && i < sl);

		return i;
	}

}