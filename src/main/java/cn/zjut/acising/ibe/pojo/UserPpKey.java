package cn.zjut.acising.ibe.pojo;

import it.unisa.dia.gas.jpbc.Element;

/**
 * @description: 用户公私钥
 * @author: SongQijie
 * @date: Created in 12:04 下午 2020/4/17
 * @version: 1.0
 **/
public class UserPpKey {

    //user public key
    private Element Qid;

    //user private key
    private Element Did;

    public UserPpKey(Element Qid, Element Did){
        this.Qid = Qid;
        this.Did = Did;
    }

    public Element getQid() {
        return Qid;
    }

    public void setQid(Element qid) {
        Qid = qid;
    }

    public Element getDid() {
        return Did;
    }

    public void setDid(Element did) {
        Did = did;
    }
}
