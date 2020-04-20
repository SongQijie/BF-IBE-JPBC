package cn.zjut.acising.ibe.utils;

import it.unisa.dia.gas.plaf.jpbc.util.Arrays;
import org.bouncycastle.crypto.digests.SHA256Digest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * @description:
 * @author: SongQijie
 * @date: Created in 4:20 下午 2020/4/17
 * @version: 1.0
 **/
public class Utils {

    public static byte[] xor(byte[] a, byte[] b) {
        byte[] result = new byte[Math.min(a.length, b.length)];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (((int) a[i]) ^ ((int) b[i]));
        }
        return result;
    }

    /**
     * sha256加密
     *
     * @param data
     * @return
     */
    public static byte[] sha256(byte[] data) {
        SHA256Digest dgst = new SHA256Digest();
        dgst.reset();
        dgst.update(data, 0, data.length);
        int digestSize = dgst.getDigestSize();
        byte[] hash = new byte[digestSize];
        dgst.doFinal(hash, 0);
        return hash;
    }

    public static ArrayList<byte[]> slice(byte[] msg) {

        ArrayList<byte[]> list = new ArrayList<byte[]>();

        // 待优化
        // boxCount 表示分组数目
        int boxCount = ((msg.length % 32) == 0)
                ? (msg.length / 32) :
                ((msg.length / 32) + 1);

        for (int i = 0; i < boxCount - 1; ++i) {
            list.add(Arrays.copyOfRange(msg,
                    i * 32, (i + 1) * 32));
        }
        list.add(Arrays.copyOfRange(msg,
                (boxCount - 1) * 32, msg.length));
        return list;
    }

    public static byte[] splice(ArrayList<byte[]> byteMessage) {

        int boxCount = byteMessage.size();

        // byteSum 表示总字节数大小
        int byteSum = (32 * (boxCount - 1)) +
                byteMessage.get(boxCount - 1).length;
        byte[] temp = new byte[byteSum];

        for (int i = 0; i < boxCount - 1; ++i) {
            for (int t = 0; t < 32; ++t) {
                temp[i * 32 + t] = byteMessage.get(i)[t];
            }
        }
        for (int i = 0; i < byteMessage.get(boxCount - 1).length; ++i) {
            temp[32 * (boxCount - 1) + i] =
                    byteMessage.get(boxCount - 1)[i];
        }
        return temp;
    }


    /**
     * 加密
     * 1.构造密钥生成器
     * 2.根据ecnodeRules规则初始化密钥生成器
     * 3.产生密钥
     * 4.创建和初始化密码器
     * 5.内容加密
     * 6.返回字符串
     */
    public static String AESEncode(String encodeRules, String content) {

        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            //keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            keygen.init(128, random);
            //3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byte_encode = content.getBytes("utf-8");
            //9.根据密码器的初始化方式--加密：将数据加密
            byte[] byte_AES = cipher.doFinal(byte_encode);
            //10.将加密后的数据转换为字符串
            //这里用Base64Encoder中会找不到包
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            String AES_encode = new String(new BASE64Encoder().encode(byte_AES));
            //11.将字符串返回
            return AES_encode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //如果有错就返加nulll
        return null;
    }

    /**
     * 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     */
    public static String AESDncode(String encodeRules, String content) {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            //keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            keygen.init(128, random);
            //3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
            /*
             * 解密
             */
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //如果有错就返加nulll
        return null;
    }

    public static void main(String[] args) {
        String content = "songqijie";
        String key = "text";
        String re = AESEncode(key, content);
        System.out.println(re);
        System.out.println(AESDncode(key, re));
    }

}
