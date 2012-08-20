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

package tigase.util;

//~--- JDK imports ------------------------------------------------------------

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

//~--- classes ----------------------------------------------------------------

/**
 * This is a warpper for java.util.zip package and Deflater/Inflater classes
 * specifically. This implementation allows for easy interaction between
 * Deflater/Inflater and java.nio API which operates on ByteBuffer data.
 * It also does some tricky stuff to flush Deflater without reseting it and allow
 * a better compression ration on the data. <p/>
 * There are a few convenience methods allowing to directly compress String to
 * ByteBuffer and other way around - from ByteBuffer to String decompression.
 * For these methods data are assumed to be UTF-8 character String.<p/>
 *
 * Created: Jul 30, 2009 11:46:55 AM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class ZLibWrapper {

	/**
	 * Variable <code>log</code> is a class logger.
	 */
	private static Logger log = Logger.getLogger(ZLibWrapper.class.getName());

	/** Field description */
	public static final int COMPRESSED_BUFF_SIZE = 512;

	/** Field description */
	public static final int DECOMPRESSED_BUFF_SIZE = 10 * COMPRESSED_BUFF_SIZE;
	private static final byte[] EMPTYBYTEARRAY = new byte[0];

	//~--- fields ---------------------------------------------------------------

	private float average_compression_rate = 0f;
	private float average_decompression_rate = 0f;
	private byte[] compress_input = null;
	private byte[] compress_output = null;
	private Deflater compresser = null;
	private byte[] decompress_input = null;
	private byte[] decompress_output = null;
	private Inflater decompresser = null;
	private CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
	private int decompressed_buff_size = DECOMPRESSED_BUFF_SIZE;
	private CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
	private int compression_level = Deflater.BEST_COMPRESSION;
	private int compressed_buff_size = COMPRESSED_BUFF_SIZE;
	private float last_compression_rate = 0f;
	private float last_decompression_rate = 0f;

	//~--- constructors ---------------------------------------------------------

	/**
	 * Constructs ...
	 *
	 */
	public ZLibWrapper() {
		this(Deflater.BEST_COMPRESSION, COMPRESSED_BUFF_SIZE);
	}

	/**
	 * Constructs ...
	 *
	 *
	 * @param level
	 */
	public ZLibWrapper(int level) {
		this(level, COMPRESSED_BUFF_SIZE);
	}

	/**
	 * Constructs ...
	 *
	 *
	 * @param level
	 * @param comp_buff_size
	 */
	public ZLibWrapper(int level, int comp_buff_size) {
		this.compression_level = level;
		this.compressed_buff_size = comp_buff_size;
		this.decompressed_buff_size = 10 * comp_buff_size;
		this.compresser = new Deflater(compression_level, false);
		this.decompresser = new Inflater(false);
		compress_output = new byte[compressed_buff_size];
		compress_input = new byte[decompressed_buff_size];
		decompress_output = new byte[decompressed_buff_size];
		decompress_input = new byte[compressed_buff_size];
	}

	//~--- methods --------------------------------------------------------------

	/**
	 * Method description
	 *
	 *
	 * @param args
	 *
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ZLibWrapper zlib = new ZLibWrapper(Deflater.BEST_COMPRESSION);
		String[] inputs = {
			"Stream compression implementation for The Tigase XMPP Server.",
			"Stream compression implementation for The Tigase XMPP Server.",
			"<message to='kobit@some.domain' from='artur@another.domain'>" + "<thread>abcd</thread>"
			+ "<subject>some subject</subject>" + "<body>This is a message body</body>" + "</message>",
			"<presence to='kobit@some.domain' from='artur@another.domain'>" + "<status>away</status>"
			+ "<show>I am away</show>" + "</presence>",
			"<message to='ala@some.domain' from='as@another.domain'>" + "<thread>abcd</thread>"
			+ "<subject>Another subject</subject>" + "<body>This is a message body sent to as</body>"
			+ "</message>",
			"<presence to='ala@some.domain' from='as@another.domain'>" + "<status>dnd</status>"
			+ "<show>I am working really hard</show>" + "</presence>",
			"<message to='santa@some.domain' from='elf@another.domain'>" + "<thread>abcd</thread>"
			+ "<subject>Christmass presents</subject>"
			+ "<body>We need to have a chat about Christmas presents.</body>" + "</message>",
			"<presence to='kobit@some.domain' from='artur@another.domain'>" + "<status>away</status>"
			+ "<show>I am away</show>" + "</presence>",
		};

		for (String input : inputs) {
			System.out.println("\nINPUT[" + input.length() + "]: \n" + input);

			ByteBuffer compressedBuffer = zlib.compress(input);

			System.out.println("  CREATED, compressedBuffer capacity: " + compressedBuffer.capacity()
					+ ", remaining: " + compressedBuffer.remaining() + ", position: "
						+ compressedBuffer.position() + ", limit: " + compressedBuffer.limit());

//    ByteBuffer decompressedBuffer = zlib.decompress(compressedBuffer);
//    System.out.println(
//        "  decompressedBuffer capacity: " + decompressedBuffer.capacity() +
//        ", remaining: " + decompressedBuffer.remaining() +
//        ", position: " + decompressedBuffer.position() +
//        ", limit: " + decompressedBuffer.limit());
//    System.out.println(
//        "  AFTER DECOMP, compressedBuffer capacity: " + compressedBuffer.
//        capacity() +
//        ", remaining: " + compressedBuffer.remaining() +
//        ", position: " + compressedBuffer.position() +
//        ", limit: " + compressedBuffer.limit());
//    compressedBuffer.rewind();
//    System.out.println(
//        "  REWIND, compressedBuffer capacity: " +
//        compressedBuffer.capacity() +
//        ", remaining: " + compressedBuffer.remaining() +
//        ", position: " + compressedBuffer.position() +
//        ", limit: " + compressedBuffer.limit());
			String output = zlib.decompressToString(compressedBuffer);

			System.out.println("OUTPUT[" + output.length() + "]: \n" + output);
			System.out.println("input.equals(output)=" + (input.equals(output)));
		}

		System.out.println("Compression rate: " + zlib.lastCompressionRate());

		String inputstr = "";

		for (String input : inputs) {
			inputstr += input;
		}

		System.out.println("INPUT[" + inputstr.length() + "]: \n" + inputstr);

		ByteBuffer compressedBuffer = zlib.compress(inputstr);

		System.out.println("  CREATED, compressedBuffer capacity: " + compressedBuffer.capacity()
				+ ", remaining: " + compressedBuffer.remaining() + ", position: "
					+ compressedBuffer.position() + ", limit: " + compressedBuffer.limit());

//  ByteBuffer decompressedBuffer = zlib.decompress(compressedBuffer);
//  System.out.println(
//      "  decompressedBuffer capacity: " + decompressedBuffer.capacity() +
//      ", remaining: " + decompressedBuffer.remaining() +
//      ", position: " + decompressedBuffer.position() +
//      ", limit: " + decompressedBuffer.limit());
//  System.out.println(
//      "  AFTER DECOMP, compressedBuffer capacity: " + compressedBuffer.
//      capacity() +
//      ", remaining: " + compressedBuffer.remaining() +
//      ", position: " + compressedBuffer.position() +
//      ", limit: " + compressedBuffer.limit());
//  compressedBuffer.rewind();
//  System.out.println(
//      "  REWIND, compressedBuffer capacity: " +
//      compressedBuffer.capacity() +
//      ", remaining: " + compressedBuffer.remaining() +
//      ", position: " + compressedBuffer.position() +
//      ", limit: " + compressedBuffer.limit());
		String output = zlib.decompressToString(compressedBuffer);

		System.out.println("OUTPUT[" + output.length() + "]: \n" + output);
		System.out.println("Compression rate: " + zlib.lastCompressionRate());
		System.out.println("inputstr.equals(output)=" + (inputstr.equals(output)) + "\n");

		char[] chars = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'R', 'S', 'T', 'U', 'W', 'X', 'Y', 'Z'
		};
		StringBuilder sb = new StringBuilder();

		for (char c : chars) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 100; j++) {
					sb.append(c);
				}

				sb.append('\n');
			}
		}

		String input = sb.toString();

		System.out.println("INPUT[" + input.length() + "]:");
		compressedBuffer = zlib.compress(input);
		System.out.println("  CREATED, compressedBuffer capacity: " + compressedBuffer.capacity()
				+ ", remaining: " + compressedBuffer.remaining() + ", position: "
					+ compressedBuffer.position() + ", limit: " + compressedBuffer.limit());

//  decompressedBuffer = zlib.decompress(compressedBuffer);
//  System.out.println(
//      "  decompressedBuffer capacity: " + decompressedBuffer.capacity() +
//      ", remaining: " + decompressedBuffer.remaining() +
//      ", position: " + decompressedBuffer.position() +
//      ", limit: " + decompressedBuffer.limit());
//  System.out.println(
//      "  AFTER DECOMP, compressedBuffer capacity: " + compressedBuffer.
//      capacity() +
//      ", remaining: " + compressedBuffer.remaining() +
//      ", position: " + compressedBuffer.position() +
//      ", limit: " + compressedBuffer.limit());
//  compressedBuffer.rewind();
//  System.out.println(
//      "  REWIND, compressedBuffer capacity: " + compressedBuffer.capacity() +
//      ", remaining: " + compressedBuffer.remaining() +
//      ", position: " + compressedBuffer.position() +
//      ", limit: " + compressedBuffer.limit());
		output = zlib.decompressToString(compressedBuffer);
		System.out.println("OUTPUT[" + output.length() + "]:");
		System.out.println("Compression rate: " + zlib.lastCompressionRate());
		System.out.println("input.equals(output)=" + (input.equals(output)) + "\n");
		System.out.println("INPUT[" + input.length() + "]:");
		compressedBuffer = zlib.compress(input);
		System.out.println("  CREATED, compressedBuffer capacity: " + compressedBuffer.capacity()
				+ ", remaining: " + compressedBuffer.remaining() + ", position: "
					+ compressedBuffer.position() + ", limit: " + compressedBuffer.limit());

//  decompressedBuffer = zlib.decompress(compressedBuffer);
//  System.out.println(
//      "  decompressedBuffer capacity: " + decompressedBuffer.capacity() +
//      ", remaining: " + decompressedBuffer.remaining() +
//      ", position: " + decompressedBuffer.position() +
//      ", limit: " + decompressedBuffer.limit());
//  System.out.println(
//      "  AFTER DECOMP, compressedBuffer capacity: " + compressedBuffer.
//      capacity() +
//      ", remaining: " + compressedBuffer.remaining() +
//      ", position: " + compressedBuffer.position() +
//      ", limit: " + compressedBuffer.limit());
//  compressedBuffer.rewind();
//  System.out.println(
//      "  REWIND, compressedBuffer capacity: " + compressedBuffer.capacity() +
//      ", remaining: " + compressedBuffer.remaining() +
//      ", position: " + compressedBuffer.position() +
//      ", limit: " + compressedBuffer.limit());
		output = zlib.decompressToString(compressedBuffer);
		System.out.println("OUTPUT[" + output.length() + "]:");
		System.out.println("Compression rate: " + zlib.lastCompressionRate());
		System.out.println("input.equals(output)=" + (input.equals(output)) + "\n");
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public float averageCompressionRate() {
		return average_compression_rate;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public float averageDecompressionRate() {
		return average_decompression_rate;
	}

	/**
	 *
	 * @param input
	 * @return
	 */
	public ByteBuffer compress(ByteBuffer input) {

		// Arrays where all compressed bytes are saved.
		byte[] result_arr = null;

		// This is saved for compression rate calculation only
		float before = input.remaining();

		// Repeat compression procedure until the input buffer is empty
		while (input.hasRemaining()) {

			// ByteBuffer doesn't like calls with requested data size bigger than
			// either remaining bytes in the buffer or input array size
			int size = Math.min(compress_input.length, input.remaining());

			input.get(compress_input, 0, size);

			// From the Deflater source code it looks like each setInput() call
			// overwrites previous data, so we have to run compression on each
			// buffer separately.
			compresser.setInput(compress_input, 0, size);

			// Finish data preparation (from the Deflater source code it looks like
			// this is really dummy call doing pretty much nothing)
			// compresser.finish();
			// While there are still data waiting for compression
			while ( !compresser.needsInput()) {
				result_arr = deflate(result_arr);
			}

			// We don't want to reset or finish the compresser to not loss the real
			// compression benefits. Deflater however does not offer flushing so this
			// is a workaround from:
			// http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4206909
			// We force Deflater to flush by switching compression level
			compresser.setInput(EMPTYBYTEARRAY, 0, 0);
			compresser.setLevel(Deflater.NO_COMPRESSION);
			result_arr = deflate(result_arr);
			compresser.setLevel(compression_level);

			// I am not really sure if this last call is needed, TODO: test it and remove it
			// Absolutely sure that it is needed. Tested and confirmed, without the call
			// the compression is broken. Why? I do not know.
			result_arr = deflate(result_arr);
		}

		// If the compressed_output array is smaller assign result to the output
		// to make sure the next time there is enough space in the output array
		// to limit memory allocation calls
		if (result_arr.length > compress_output.length) {
			compress_output = result_arr;

			if (log.isLoggable(Level.FINEST)) {
				log.log(Level.FINEST, "Increasing compress_output size to: {0}", compress_output.length);
			}
		}

		// Calculate compression rate for statistics collection
		last_compression_rate = (before - result_arr.length) / before;
		average_compression_rate = (average_compression_rate + last_compression_rate) / 2;

		// Create resulting buffer.
		ByteBuffer result = ByteBuffer.wrap(result_arr);

		return result;
	}

	/**
	 * Method description
	 *
	 *
	 * @param input
	 *
	 * @return
	 *
	 * @throws CharacterCodingException
	 */
	public ByteBuffer compress(String input) throws CharacterCodingException {
		encoder.reset();

		ByteBuffer dataBuffer = encoder.encode(CharBuffer.wrap(input));

		encoder.flush(dataBuffer);

		ByteBuffer compressedBuffer = compress(dataBuffer);

		return compressedBuffer;
	}

	/**
	 * Method description
	 *
	 *
	 * @param input
	 *
	 * @return
	 */
	public ByteBuffer decompress(ByteBuffer input) {

		// Arrays where decompressed bytes are stored
		byte[] result_arr = null;

		// this is saved for decompression rate calculation only
		// Please note since compression and decompression are independent network
		// streams the compression rate might be very different, interesting thing
		// to investigate
		float before = input.remaining();

		// Repeat until the input buffer is empty
		while (input.hasRemaining()) {

			// ByteBuffer doesn't like calls with requested data size bigger than
			// either remaining bytes in the buffer or input array size
			int size = Math.min(decompress_input.length, input.remaining());

			input.get(decompress_input, 0, size);

			// From the Inflater source code it looks like each setInput() call
			// overwrites previous data, so we have to run decompression on each
			// buffer separately.
			decompresser.setInput(decompress_input, 0, size);

			int decompressed_size = 1;

			// Normally we get much more decompressed data than input data and
			// on a few first calls we can have more decompressed data than the output
			// buffer size. In time the output buffer will adjust itself but initially we
			// can expect a few calls in this loop before we get all data
			while ( !decompresser.needsInput() || (decompressed_size > 0)) {
				try {
					decompressed_size = decompresser.inflate(decompress_output, 0, decompress_output.length);

					// We might get 0 decompressed_size in case not all data are ready yet
					// probably more data are needed from the network
					if (decompressed_size > 0) {

						// Copy decompressed data to result array
						if (result_arr == null) {

							// On the first call just copy the array - data in the source array
							// will be overwritten on the next call
							result_arr = Arrays.copyOf(decompress_output, decompressed_size);
						} else {

							// If the method is called many times for a single input buffer
							// we may need to resize the output array, in time however the
							// output array size should automaticaly adjust to the data and
							// resizing array should not be needed then
							int old_size = result_arr.length;

							result_arr = Arrays.copyOf(result_arr, old_size + decompressed_size);
							System.arraycopy(decompress_output, 0, result_arr, old_size, decompressed_size);
						}
					}
				} catch (DataFormatException ex) {
					log.log(Level.INFO, "Stream decompression error: ", ex);
					decompresser.reset();
				}
			}
		}

		ByteBuffer result = null;

		// It may happen there is not enough data to decode full buffer, we return null
		// in such a case and try next time
		if (result_arr != null) {

			// If the decompressed_output array is smaller assign result to the output
			// to make sure the next time there is enough space in the output array
			// to limit memory allocation calls
			if (result_arr.length > decompress_output.length) {
				decompress_output = result_arr;

				if (log.isLoggable(Level.FINEST)) {
					log.finest("Increasing compress_output size to: " + compress_output.length);
				}
			}

			// Calculate decompression rate for statistics collection
			last_decompression_rate = (result_arr.length - before) / result_arr.length;
			average_decompression_rate = (average_decompression_rate + last_decompression_rate) / 2;

			// Create resulting buffer.
			result = ByteBuffer.wrap(result_arr);
		}

		return result;
	}

	/**
	 * Method description
	 *
	 *
	 * @param input
	 *
	 * @return
	 *
	 * @throws CharacterCodingException
	 */
	public String decompressToString(ByteBuffer input) throws CharacterCodingException {
		ByteBuffer decompressed_buff = decompress(input);
		CharBuffer cb = decoder.decode(decompressed_buff);
		String output = new String(cb.array());

		return output;
	}

	/**
	 * Method description
	 *
	 */
	public void end() {
		this.compresser.end();
		this.decompresser.end();
		this.compress_output = null;
		this.compress_input = null;
		this.decompress_output = null;
		this.decompress_input = null;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public float lastCompressionRate() {
		return last_compression_rate;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public float lastDecompressionRate() {
		return last_decompression_rate;
	}

	private byte[] deflate(byte[] input_arr) {
		byte[] result_arr = input_arr;

		// Compress data and take number of bytes ready
		int compressed_size = compresser.deflate(compress_output, 0, compress_output.length);

		// Beware, we can get 0 compressed_size quite frequently as the zlib
		// library buffers data for a better compression ratio
		if (compressed_size > 0) {

			// Copy compressed data to result array
			if (result_arr == null) {

				// On the first call just copy the array - data in the source array
				// will be overwritten on the next call
				result_arr = Arrays.copyOf(compress_output, compressed_size);
			} else {

				// If the method is called many times for a single input buffer
				// we may need to resize the output array, in time however the
				// output array size should automaticaly adjust to the data and
				// resizing array should not be needed then
				int old_size = result_arr.length;

				result_arr = Arrays.copyOf(result_arr, old_size + compressed_size);
				System.arraycopy(compress_output, 0, result_arr, old_size, compressed_size);
			}
		}

		return result_arr;
	}
}


//~ Formatted in Sun Code Convention


//~ Formatted by Jindent --- http://www.jindent.com
