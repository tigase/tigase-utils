package tigase.cert;

import org.ietf.jgss.GSSException;
import org.ietf.jgss.Oid;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.System.Logger.Level;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.util.HexFormat;
import java.util.Map;

import static tigase.cert.Asn1DerUtilities.nextComponentValue;
import static tigase.cert.Asn1DerUtilities.readContents;
import static tigase.cert.Asn1DerUtilities.readLength;


/**
 * Class to decode EC private key from raw bytes as defined in SEC 1 ASN.1 structure
 * <a href="https://datatracker.ietf.org/doc/html/rfc5915">RFC5915</a>
 *
 * <pre>
 *    ECParameters, NamedCurve
 *      FROM PKIXAlgs-2009
 *        { iso(1) identified-organization(3) dod(6) internet(1)
 *          security(5) mechanisms(5) pkix(7) id-mod(0)
 *          id-mod-pkix1-algorithms2008-02(56) }
 *
 *    ECPrivateKey ::= SEQUENCE {
 *      version        INTEGER { ecPrivkeyVer1(1) } (ecPrivkeyVer1),
 *      privateKey     OCTET STRING,
 *      parameters [0] ECParameters {{ NamedCurve }} OPTIONAL,
 *      publicKey  [1] BIT STRING OPTIONAL
 *    }
 * </pre>
 */
public class ECPrivateKeyDecoder {

    private final static HexFormat hexFormat = HexFormat.of().withUpperCase().withDelimiter(" ");
    private final static System.Logger LOGGER = System.getLogger(ECPrivateKeyDecoder.class.getName());

    private static final Map<String, String> OID_TO_CURVE_NAME_MAPPER = Map.of(
            "1.2.840.10045.3.1.7", "secp256r1",
            "1.3.132.0.34", "secp384r1",
            "1.3.132.0.35", "secp521r1",
            "1.3.132.0.10", "secp256k1"
    );
    private BigInteger privateKeyBigInt;
    private Oid oid;

    public ECPrivateKeyDecoder(byte[] rawPrivateKeyBytes) {
        this(new ByteArrayInputStream(rawPrivateKeyBytes));
    }

    public ECPrivateKeyDecoder(InputStream is) {
        decode(is);
    }

    private void decode(InputStream is) {
        try {
            // read wrapper SEQUENCE
            var type = is.read();
            var sequenceLength = readLength(is);
            var sequenceValue = readContents(is, sequenceLength);
            LOGGER.log(Level.ALL, "Loaded sequence, type: " + type + ", sequenceLength: " + sequenceLength + ", sequenceValue: " + hexFormat.formatHex(sequenceValue));

            InputStream sequenceInputStream = new ByteArrayInputStream(sequenceValue);

            // read version value
            var versionValue = nextComponentValue(sequenceInputStream);
            LOGGER.log(Level.ALL, "versionValue: " + hexFormat.formatHex(versionValue));

            // read private key value
            var privateKeyValue = nextComponentValue(sequenceInputStream);
            this.privateKeyBigInt = new BigInteger(1, privateKeyValue);
            LOGGER.log(Level.ALL, "privateKeyValue: " + privateKeyBigInt + " :: " + hexFormat.formatHex(privateKeyValue));

            // parameters are optional
            if (sequenceInputStream.available() == 0) return;

            var parametersValue = nextComponentValue(sequenceInputStream);
            oid = new Oid(parametersValue);
            LOGGER.log(Level.ALL, "OID: " + hexFormat.formatHex(parametersValue) + " :: " + oid);

            // public key is optional
            if (sequenceInputStream.available() == 0) return;

            var publicKeyValue = nextComponentValue(sequenceInputStream);
            LOGGER.log(Level.ALL, "publicKeyValue: " + hexFormat.formatHex(publicKeyValue));

        } catch (IOException | GSSException e) {
            throw new RuntimeException(e);
        }
    }

    public PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            var parameters = AlgorithmParameters.getInstance("EC");
            var curveParameterName = OID_TO_CURVE_NAME_MAPPER.get(oid.toString());
            var parameterSpec = new ECGenParameterSpec(curveParameterName);
            parameters.init(parameterSpec);

            var keyFactory = KeyFactory.getInstance("EC");

            var ecParameterSpec = parameters.getParameterSpec(ECParameterSpec.class);

            var ecKeySpec = new ECPrivateKeySpec(privateKeyBigInt, ecParameterSpec);
            return keyFactory.generatePrivate(ecKeySpec);
        } catch (Exception e) {
            throw new InvalidKeySpecException("Invalid parameter spec", e);
        }
    }
}
