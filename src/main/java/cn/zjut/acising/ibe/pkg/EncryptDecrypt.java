package cn.zjut.acising.ibe.pkg;

import cn.zjut.acising.ibe.pojo.CipherText;
import cn.zjut.acising.ibe.pojo.PlainText;
import cn.zjut.acising.ibe.pojo.SystemParameter;
import cn.zjut.acising.ibe.pojo.UserPpKey;
import cn.zjut.acising.ibe.utils.Utils;
import cn.zjut.acising.tools.ObjectStorage;
import it.unisa.dia.gas.jpbc.Element;

import java.util.ArrayList;

/**
 * @description:
 * @author: SongQijie
 * @date: Created in 11:54 下午 2020/4/19
 * @version: 1.0
 **/
public class EncryptDecrypt {


    /**
     * 加密
     *
     * @param message 明文
     * @param userId  用户id
     * @return
     */
    public static CipherText encrypt(String message, SystemParameter systemParameter, UserPpKey userKey) {
        Element r = systemParameter.getR();
        //计算U=rP
        Element U = systemParameter.getP().mulZn(r).getImmutable();
        //计算V = m(xor)H2(e(Qid,Ppub)^r)
        Element V1 = systemParameter.getPairing().pairing(userKey.getQid(), systemParameter.getPpub()).getImmutable();
        V1 = V1.powZn(r).getImmutable();
        V1 = systemParameter.getG2().newElement().setFromHash(V1.toBytes(), 0, V1.getLengthInBytes()).getImmutable();
        System.out.println(V1.toBytes());
        //明文和H2值,即上面的V1异或
        byte[] sha256_V1 = Utils.sha256(V1.toBytes());
        ArrayList<byte[]> plainArray = Utils.slice(message.getBytes());
        //分组异或
        ArrayList<byte[]> cipherV = new ArrayList<byte[]>();
        for (int i = 0; i < plainArray.size(); ++i) {
            cipherV.add(Utils.xor(plainArray.get(i), sha256_V1));
        }
        CipherText cipherText = new CipherText(U, cipherV);
        return cipherText;
    }

    /**
     * 解密
     *
     * @param cipherText
     * @param Did
     * @return
     */
    public static PlainText decrypt(CipherText cipherText, Element Did, String domain) {
        SystemParameter systemParameter = ObjectStorage.PKGPARAMETERS.get(domain);
        Element U = cipherText.getU();
        Element m1 = systemParameter.getPairing().pairing(Did, U.getImmutable());
        m1 = systemParameter.getG2().newElement().setFromHash(m1.toBytes(), 0, m1.getLengthInBytes()).getImmutable();

        ArrayList<byte[]> plainBytes = new ArrayList<byte[]>();
        ArrayList<byte[]> cipherV = cipherText.getV();
        byte[] sha256_m1 = Utils.sha256(m1.toBytes());
        for (byte[] aCipherV : cipherV) {
            plainBytes.add(Utils.xor(aCipherV, sha256_m1));
        }
        byte[] plainTextByte = Utils.splice(plainBytes);
        return new PlainText(new String(plainTextByte));
    }


}
