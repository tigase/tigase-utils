package tigase.cert;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public class Asn1DerUtilities {

    public static int readLength(InputStream is) throws IOException {
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

    public static byte[] readContents(InputStream is, int len) throws IOException {
        byte[] val = new byte[len];
        int res = is.read(val, 0, len);

        if (res < len) {
            throw new IOException("Invalid DER data: data too short.");
        }
        return val;
    }

    public static byte[] nextComponentValue(InputStream is) throws IOException {
        int tag = is.read();
        int len = readLength(is);
        byte[] val = new byte[len];
        int res = is.read(val);

        if (res < len) {
            throw new IOException("Invalid DER data: data too short.");
        }
        return val;
    }


}
