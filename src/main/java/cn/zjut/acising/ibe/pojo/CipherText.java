package cn.zjut.acising.ibe.pojo;

import it.unisa.dia.gas.jpbc.Element;

import java.util.ArrayList;

/**
 * @description: 密文
 * @author: SongQijie
 * @date: Created in 4:12 下午 2020/4/17
 * @version: 1.0
 **/
public class CipherText {

    private Element U;

    private ArrayList<byte[]> V;

    public CipherText(Element u, ArrayList<byte[]> v) {
        U = u;
        V = v;
    }

    public Element getU() {
        return U;
    }

    public void setU(Element u) {
        U = u;
    }

    public ArrayList<byte[]> getV() {
        return V;
    }

    public void setV(ArrayList<byte[]> v) {
        V = v;
    }

    @Override
    public String toString() {
        return "{" +
                "U=" + U +
                ", V=" + V +
                '}';
    }
}
