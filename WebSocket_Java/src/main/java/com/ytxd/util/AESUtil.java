package com.ytxd.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.google.api.client.repackaged.org.apache.commons.codec.DecoderException;
import com.qiniu.util.Hex;
import com.qiniu.util.Hex.HexDecodeException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * ASE加密
 */
public final class AESUtil {
    /*
     * 加密
     * 	1.构造密钥生成器
     *	2.根据ecnodeRules规则初始化密钥生成器
     * 	3.产生密钥
     * 	4.创建和初始化密码器
     * 	5.内容加密
     * 	6.返回字符串
     */
    private static String AESEncode(String encodeRules, String content) {
        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            // 2.根据ecnodeRules规则初始化密钥生成器
            // 生成一个128位的随机源,根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            // 3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byte_encode = content.getBytes("utf-8");
            // 9.根据密码器的初始化方式--加密：将数据加密
            byte[] byte_AES = cipher.doFinal(byte_encode);
            // 10.将加密后的数据转换为字符串
            // 这里用Base64Encoder中会找不到包
            // 解决办法：
            // 在项目的Build path中先移除JRE System Library，再添加库JRE System
            // Library，重新编译后就一切正常了。
            String AES_encode = new String(new BASE64Encoder().encode(byte_AES));
            // 11.将字符串返回
            return AES_encode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 如果有错就返加nulll
        return null;
    }

    /*
     * 解密 解密过程：
     *	1.同加密1-4步
     * 	2.将加密后的字符串反纺成byte[]数组
     * 	3.将加密内容解密
     */
    private static String AESDncode(final String encodeRules, String content) {
        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            // 2.根据ecnodeRules规则初始化密钥生成器
            // 生成一个128位的随机源,根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            // 3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 8.将加密并编码后的内容解码成字节数组
            byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
            /*
             * 解密
			 */
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        // 如果有错就返加nulll
        return null;
    }
    	
    /**
     * 比较安全的字符串转换
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 比较安全的字符串转换
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }
    
    /**
     * 加密字符串
     *
     * @param secretKey
     * @param Str
     * @return
     */
    public static String encrypt(String secretKey, String Str) {
        //return str2HexStr(AESEncode(secretKey, Str));
    	return AESEncode(secretKey, Str);
    }

    /**
     * 解密字符串
     *
     * @param secretKey
     * @param str
     * @return
     */
    public static String decode(String secretKey, String str) {
        //return AESDncode(secretKey, hexStr2Str(str));
    	return AESDncode(secretKey, str);
    }
    
    /**
     * 加密字符串
     *
     * @param secretKey
     * @param Str
     * @return
     */
    public static String encrypt(String Str) {
        return str2HexStr(AESEncode("string@password1234", Str));
    }

    /**
     * 解密字符串
     *
     * @param secretKey
     * @param str
     * @return
     */
    public static String decode(String str) {
        return AESDncode("string@password1234", hexStr2Str(str));
    }    
    
	/**
     * SHA1加密，结果为小写
     */
		public static String SHA1(String decript) {
		     try {
		         MessageDigest digest = java.security.MessageDigest
		                 .getInstance("SHA-1");
		         digest.update(decript.getBytes());
		        byte messageDigest[] = digest.digest();
		        // Create Hex String
		        StringBuffer hexString = new StringBuffer();
		        // 字节数组转换为 十六进制 数
		        for (int i = 0; i < messageDigest.length; i++) {
		            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
		            if (shaHex.length() < 2) {
		                hexString.append(0);
		            }
		            hexString.append(shaHex);
		        }
		        return hexString.toString();
		
		    } catch (NoSuchAlgorithmException e) {
		        e.printStackTrace();
		    }
		    return "";
		}
		
			/**
		     * MD5加密
		     */
		public static String md5s(String plainText) {
		    String str = "";
		    try {
		        MessageDigest md = MessageDigest.getInstance("MD5");
		            md.update(plainText.getBytes());
		            byte b[] = md.digest();
		
		           int i;
		
		           StringBuffer buf = new StringBuffer("");
		       for (int offset = 0; offset < b.length; offset++) {
		           i = b[offset];
		           if (i < 0)
		               i += 256;
		           if (i < 16)
		               buf.append("0");
		           buf.append(Integer.toHexString(i));
		       }
		       str = buf.toString();
		   } catch (NoSuchAlgorithmException e) {
		       e.printStackTrace();
		   }
		   return str;
		}
		
		public static String aes_encrypt(String password, String strKey) {
			try {
				SecretKey key = generateMySQLAESKey(strKey,"ASCII");
				Cipher cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.ENCRYPT_MODE, key);
				byte[] cleartext = password.getBytes("UTF-8");
				byte[] ciphertextBytes = cipher.doFinal(cleartext);
				return new String(Hex.encodeHex(ciphertextBytes));

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
			return null;
		}

		public static String aes_decrypt(String content, String aesKey) throws HexDecodeException, DecoderException{
			try {
				SecretKey key = generateMySQLAESKey(aesKey,"ASCII");
				Cipher cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.DECRYPT_MODE, key);
				byte[] cleartext = Hex.decodeHex(content.toCharArray());
				byte[] ciphertextBytes = cipher.doFinal(cleartext);
				return new String(ciphertextBytes, "UTF-8");

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
			return null;
		}

		
		public static SecretKeySpec generateMySQLAESKey(final String key, final String encoding) {
			try {
				final byte[] finalKey = new byte[16];
				int i = 0;
				for(byte b : key.getBytes(encoding))
					finalKey[i++%16] ^= b;			
				return new SecretKeySpec(finalKey, "AES");
			} catch(UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}

	public static void main(String[] args) throws Exception{
		System.out.println(AESUtil.aes_decrypt("881126C298045E6A4D901E8C610B8036","userPassword"));
	}
}