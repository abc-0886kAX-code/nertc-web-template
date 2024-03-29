package com.ytxd.util.secure;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Classname RSAUtils
 * @Author TY
 * @Date 2023/11/28 10:19
 * @Description TODO rsa 非对称加密
 */
public class RSAUtils {
    private static final String publicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm2k4lkxDnltuQmhdmj1Z9fu+5ms2nTGzl5VBvM1hzk+nc5RSO88hcsGjCdVPzYLUDLV0E+5PSOhWXuTVYGrGnMut7XZIdlr6Qbk1apoQCGD6mtHS8+svW0ItNEdtcXflRxqx1V/RdI6rQRjnBUFgYFn/P1rO5R+IIqcVx/CoRxpbUj3KWgbM5so7RoTxJuX0HKBVZbboCXUd0atCIHWAUQb6GzB5OL+lXTV7yKOENs4hoiWc9W3AwAeJyYaHyj7miDKoR96nn3eKca/QRlTmogBW+921eeUye9kkniIZGrXgzEvwgpgUhGSMRZTG7ff5y6qtJPQ1X0euhXMdbJCwawIDAQAB";
    private static final String privateKey ="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCbaTiWTEOeW25CaF2aPVn1+77mazadMbOXlUG8zWHOT6dzlFI7zyFywaMJ1U/NgtQMtXQT7k9I6FZe5NVgasacy63tdkh2WvpBuTVqmhAIYPqa0dLz6y9bQi00R21xd+VHGrHVX9F0jqtBGOcFQWBgWf8/Ws7lH4gipxXH8KhHGltSPcpaBszmyjtGhPEm5fQcoFVltugJdR3Rq0IgdYBRBvobMHk4v6VdNXvIo4Q2ziGiJZz1bcDAB4nJhofKPuaIMqhH3qefd4pxr9BGVOaiAFb73bV55TJ72SSeIhkateDMS/CCmBSEZIxFlMbt9/nLqq0k9DVfR66Fcx1skLBrAgMBAAECggEBAIfrJX9lYboMksjnh/2ObLBSpSnWWKRwRHZtJloecczDI1tVO/ps8/7uVU4TTkEZi6U1yThpMtQ7xtYlYmx51gxpokrNKSDB/p8egRYjeaQqVOK0Yb/MT9WLcgeH9LLJPi/Kq/9GVuSOpj4/rkSNYeHCtBbPr6T5x9EuukDsO/A6O95sGlDxpi0zP+dlotlmv6bLDAFdbqDcDlSR9D3jDbX9iq0W0+Zi/vBl7TNpMsRh1yPCf1ZmHAhKi/hhIvNfid1X/zSNOdIASpydW6ByYN5CUNUEQtRZj3/Tkf7BDWOZIDkR4JLhCuvTb7N+v0RS+IA6eiAkIAwy3yo5z5ZMnCECgYEA9BZGh8tKS7666L1426iv9qwyMjENNbbxeiOlP/V9UBc0do0P/zzrudY/9SWEY7IyJL7qCcdGKybEjdpfafd3lkAjYL6yR4gpvA8vIshKz0MpHsORIsv4xWsz/JsLN9aW0OFL8fYX+kgvjiHlTHYm/jquPOcSFdQk46rWosvqo7sCgYEAov79qDLvMWoMgO8LMIhIzoECxDVQIdHn7UPkUcAsHvH5kKSynwpVyaptYh0w0Ckd+tEnhl3fHF7DRWJgT8TJo7f3NksKG4kRGvBorJve0vAmPRGlhZOlYmN8xsb/lSbGXW5nuAe1GyNSukOVGLPzY1eNinhWmMuqqaw26gcX4xECgYEAw9sX7yCdVhgOTHC0iLVbtLhJcuLPMbnBG/t8Ps0SJ3DnRPU9z9WnZK/ZFDFtgL3c5IVYsE35LOjnsNd31kSe7a47dQavcRx+OIuKjANhm9vNJAVS8TPwQyEZTult3/lNfQA6+0U3yjmgBxGFt4vbEAYwB2h2p5NZomFT5r9vYL8CgYByqXyZkVMw6NBo90Bg4M6O7rSagIKhd/92qpb4Z37iOgj9Fs9NEEEvP8P5DF3lYbdkVTQ/0tN8KrGlosHr+5x5npW6zOkMW8z7rXmFS5VhwGy18y8Em8vxPdluIFl1fDR5T4yJKmrd72hn/djp6tVh4hqA+Yy5qjShbbOnyFumcQKBgQCaQDYUwNByzR/y6n+ggDaS35w+Zabg5LNZJ3qNwmo3vfeQlqwaNVuHuqcBQ3MCAo5/PKlwVBtuhteYUjaJJkp2bzV1igU0oc0TvNS0WngQEWVbt5c8KAg0J3jz3gQhc2XQdzmO4eG35mFYxVxeC3VbThMlM9BbN/I5TZTGCeGIXQ==";

    public static void main(String[] args) throws Exception{
        String enstr = "lgXmlyprm+kKpRgH6rubv3qXWQEr7dx9/dB2wsbFVg8XU1HQkzWT3URv+F8d9zek9KoCnT6UxQkfkin4MKrK/pST/MrP3d7SAbbGyvXkLPhchWUbzVUc86QS1bgFwUewBdCF2wY0xkkE6yWWXcSYXpEP2fnZjezp6ImSUtErZlg8y+LPeHVgOrvPJVbFpaof51GqKcBycjwrrMii5LImaWTMvgq90kgeC3O2M8xgvjvbohGpVrAyGTm9Oe/C/vS1osX+uA5Y+2SSZpgaLSLAkeW0d5DdNZjQmkeCwm7Lp3Dqqy9k9V9NNIKfywdbjTcD7z4s5y7CUeVFkEGy8is8MQ==";
        String destr = decryptRSA(enstr);
        System.out.println("加密："+encryptRSA(""));
        System.out.println("解密："+destr);
    }
    /**
     *
     * @Desription TODO 加密数据
     * @param plaintext
     * @return java.lang.String
     * @date 2023/11/28 10:49
     * @Auther TY
     */
    public static String encryptRSA(String plaintext) throws Exception {
        try {
            // 使用RSA算法创建Cipher对象进行加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, restorePublicKey(publicKey));
            // 加密数据
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());

            // 将加密后的数据进行Base64编码
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return plaintext;
    }
    /**
     *
     * @Desription TODO 解密数据
     * @param encryptedText
     * @return java.lang.String
     * @date 2023/11/28 10:50
     * @Auther TY
     */
    public static String decryptRSA(String encryptedText) throws Exception {
        try {
            // 使用RSA算法创建Cipher对象进行解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, restorePrivateKey(privateKey));

            // 解密Base64编码的数据
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

            // 解密数据
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // 将解密后的数据转为字符串
            return new String(decryptedBytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return encryptedText;
    }
    /**
     *
     * @Desription TODO 获取publickey
     * @param publicKeyString
     * @return java.security.PublicKey
     * @date 2023/11/28 10:34
     * @Auther TY
     */
    private static PublicKey restorePublicKey(String publicKeyString) throws Exception {
        // 去除PEM格式的头和尾，只保留Base64编码的公钥部分
        String publicKeyPEM = publicKeyString.replaceAll("\\s", "");

        // 解码Base64并创建X.509公钥规范
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyPEM));
        // 生成公钥
        return keyFactory.generatePublic(keySpec);
    }
    /**
     *
     * @Desription TODO 获取privatekey
     * @param privateKeyString
     * @return java.security.PrivateKey
     * @date 2023/11/28 10:34
     * @Auther TY
     */
    private static PrivateKey restorePrivateKey(String privateKeyString) throws Exception {
        // 去除PEM格式的头和尾，只保留Base64编码的私钥部分
        String privateKeyPEM = privateKeyString.replaceAll("\\s", "");

        // 解码Base64并创建PKCS8私钥规范
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyPEM));

        // 生成私钥
        return keyFactory.generatePrivate(keySpec);
    }
}
