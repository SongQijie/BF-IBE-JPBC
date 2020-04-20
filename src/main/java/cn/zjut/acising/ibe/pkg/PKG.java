package cn.zjut.acising.ibe.pkg;

import cn.zjut.acising.ibe.pojo.SystemParameter;
import cn.zjut.acising.ibe.pojo.UserPpKey;
import cn.zjut.acising.tools.ObjectStorage;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import javafx.beans.binding.ObjectExpression;

import java.util.Objects;

/**
 * @description:
 * @author: SongQijie
 * @date: Created in 11:20 上午 2020/4/17
 * @version: 1.0
 **/
public class PKG {


    public SystemParameter systemInit() {
        //---------------系统初始化-------------------
        Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");
        PairingFactory.getInstance().setUsePBCWhenPossible(true);
        checkSymmetric(pairing);

        //G1加法循环群
        Field G1 = pairing.getG1();
        //P是G1的生成元
        Element P = G1.newRandomElement().getImmutable();
        //G2,即GT，乘法子群，满足双线性映射e:G1 x G1 -> G2
        //在JPBC里面  function e : G1 x G2 -> GT
        Field G2 = pairing.getGT();

        Field Zr = pairing.getZr();
        //随机生成主密钥s
        Element s = Zr.newRandomElement().getImmutable();
        //计算Ppub=sP
        Element Ppub = P.mulZn(s);

        //随机算则r属于Zp
        Element r = pairing.getZr().newRandomElement().getImmutable();

        SystemParameter systemParameter = new SystemParameter(G1, G2, Zr, s, Ppub, P, pairing,r);

        return systemParameter;

    }

    private void checkSymmetric(Pairing pairing) {
        if (!pairing.isSymmetric()) {
            throw new RuntimeException("密钥不对称");
        }
    }

    /**
     * 生成新的PKG
     *
     * @return
     */
    public static SystemParameter getNewPkg() {
        PKG pkg = new PKG();
        SystemParameter sp = pkg.systemInit();
        return sp;
    }

    /**
     * 根据用户id生成用户密钥对
     *
     * @param userId
     * @return
     */
    public static UserPpKey privateKeyGen(SystemParameter systemParameter, String userId) {
        //用户通过身份ID生成公钥
        Element Qid = systemParameter.getG1().newElement().setFromHash(userId.getBytes(), 0, userId.length()).getImmutable();
        //通过系统主密钥生成私钥
        Element Did = Qid.mulZn(systemParameter.getS()).getImmutable();
        UserPpKey userKey = new UserPpKey(Qid, Did);
        return userKey;
    }


//Hash
//Element H1 = pairing.getG1().newElement().setFromHash("IDu".getBytes(), 0, 1).getImmutable();
//Element H2 = pairing.getGT().newElement().setFromHash("IDu".getBytes(), 0, 1).getImmutable();


}
