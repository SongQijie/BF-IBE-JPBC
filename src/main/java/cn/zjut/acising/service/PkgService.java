package cn.zjut.acising.service;

import cn.zjut.acising.ibe.pkg.PKG;
import cn.zjut.acising.ibe.pojo.SystemParameter;
import cn.zjut.acising.tools.ObjectStorage;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: SongQijie
 * @date: Created in 12:07 上午 2020/4/20
 * @version: 1.0
 **/
@Service
public class PkgService {


    public Boolean getNewPkg(String domain) {
        SystemParameter systemParameter = PKG.getNewPkg();
        ObjectStorage.PKGPARAMETERS.put(domain, systemParameter);
        return true;
    }

    public Element getPublicParameters(String domain) {
        SystemParameter systemParameter = ObjectStorage.PKGPARAMETERS.get(domain);
        if (null == systemParameter) {
            return null;
        }
        Element pPub = systemParameter.getPpub();
        return pPub;
    }

}
