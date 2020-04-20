package cn.zjut.acising.ibe.pojo;


import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import lombok.Data;

/**
 * @description: 系统参数
 * @author: SongQijie
 * @date: Created in 11:22 上午 2020/4/17
 * @version: 1.0
 **/
@Data
public class SystemParameter {

    //加法群
    private Field G1;

    //乘法群
    private Field G2;

    private Field Zr;

    //主密钥，需要保密
    private Element s;

    //公钥
    private Element Ppub;

    private Element P;

    private Pairing pairing;

    private Element r;

    public SystemParameter(Field g1, Field g2, Field zr, Element s, Element ppub, Element p, Pairing pairing, Element r) {
        G1 = g1;
        G2 = g2;
        Zr = zr;
        this.s = s;
        Ppub = ppub;
        P = p;
        this.pairing = pairing;
        this.r = r;
    }
}
