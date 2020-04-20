package cn.zjut.acising.service;

import cn.zjut.acising.ibe.pkg.EncryptDecrypt;
import cn.zjut.acising.ibe.pkg.PKG;
import cn.zjut.acising.ibe.pojo.CipherText;
import cn.zjut.acising.ibe.pojo.SystemParameter;
import cn.zjut.acising.ibe.pojo.UserPpKey;
import cn.zjut.acising.ibe.utils.Utils;
import cn.zjut.acising.tools.ObjectStorage;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;
import sun.jvm.hotspot.oops.ObjectHeap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * @description:
 * @author: SongQijie
 * @date: Created in 11:12 下午 2020/4/20
 * @version: 1.0
 **/
@Service
public class UserService {

    public Map<String, Object> register(String userId, String domain) {
        //判断有没注册过
        Object d = ObjectStorage.USERDOMAIN.get(userId);
        if (null != d) {
            return null;
        }
        SystemParameter systemParameter = ObjectStorage.PKGPARAMETERS.get(domain);
        Element pPub = systemParameter.getPpub();
        UserPpKey userPpKey = PKG.privateKeyGen(systemParameter, userId);
        //存储用户id
        ObjectStorage.USERDOMAIN.put(userId, domain);
        ObjectStorage.USERDOMAIN.put(userId + "-key", userPpKey);
        Map<String, Object> result = new HashMap<>();
        result.put("sysPubKey", pPub.toBytes());
        result.put("userPriKey", userPpKey.getDid().toBytes());
        return result;
    }

    private String getAesPwdByIbe(String domain, String userId) {
        SystemParameter systemParameter = ObjectStorage.PKGPARAMETERS.get(domain);
        UserPpKey userPpKey = (UserPpKey) ObjectStorage.USERDOMAIN.get(userId + "-key");
        CipherText cipherText = EncryptDecrypt.encrypt(ObjectStorage.PASSWORD, systemParameter, userPpKey);
        StringBuffer cipher = new StringBuffer();
        for(byte[] b:cipherText.getV()){
            cipher.append(new String((b)));
        }
        return cipher.toString();
    }


    public String encrypt(String userId, String domain, String message, String receiveUserId, String receiveDomain) {
        //校验
        String cipher = getAesPwdByIbe(receiveDomain, receiveUserId);
        String crypt = Utils.AESEncode(cipher, message);
        return crypt;
    }

    public String decrypt(String cipherText, String domain, String userId) {
        String cipher = getAesPwdByIbe(domain, userId);
        String decrypt = Utils.AESDncode(cipher, cipherText);
        return decrypt;
    }


}
