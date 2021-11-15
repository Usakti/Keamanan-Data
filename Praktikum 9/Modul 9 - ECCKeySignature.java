import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.*;

public class ECCKeySignature {
    public static void main(String[] args) throws Exception{
        KeyPairGenerator kpg;
        kpg = KeyPairGenerator.getInstance("EC", "SunEC");

        ECGenParameterSpec ecsp;
        ecsp = new ECGenParameterSpec("sect163k1");

        kpg.initialize(ecsp);

        KeyPair kp = kpg.genKeyPair();
        PrivateKey privKey = kp.getPrivate();
        PublicKey pubKey = kp.getPublic();
        System.out.println(privKey.toString());
        System.out.println(pubKey.toString());

        Signature ecdsa;
        ecdsa = Signature.getInstance("SHA1withECDSA", "SunEC");
        ecdsa.initSign(privKey);

        String text = "Jangan takut kalau hidupmu akan berakhir; takutlah kalau hidupmu tak pernah dimulai.";
        System.out.println("Text: "+ text);
        byte[] baText = text.getBytes(StandardCharsets.UTF_8);

        ecdsa.update(baText);
        byte[] baSignature = ecdsa.sign();
        System.out.println("Signature: 0x" + (new BigInteger(1,
                baSignature).toString(16).toUpperCase()));

        Signature signature;
        signature = Signature.getInstance("SHA1withECDSA", "SunEC");
        signature.initVerify(pubKey);
        signature.update(baText);
        boolean result = signature.verify(baSignature);
        System.out.println("Valid: " + result);

    }

}
