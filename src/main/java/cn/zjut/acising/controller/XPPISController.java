package cn.zjut.acising.controller;

import cn.zjut.acising.entity.Result;
import cn.zjut.acising.entity.ResultCode;
import cn.zjut.acising.service.PkgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.ElementPow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.midi.Soundbank;

/**
 * @description: 公开参数信息服务
 * @author: SongQijie
 * @date: Created in 8:47 下午 2020/4/19
 * @version: 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/api/xppisc")
@Api(tags = "公开参数信息服务")
public class XPPISController {

    private PkgService pkgService;

    @Autowired
    public XPPISController(PkgService pkgService){
        this.pkgService = pkgService;
    }

    @RequestMapping(value = "/locate", method = RequestMethod.GET)
    @ApiOperation(value = "查询ID所在域的公开参数和安全密钥策略【安全策略后续再加】")
    public Object locate(String userId, String domain) {
        Element pPub = pkgService.getPublicParameters(domain);
        return pPub == null ? Result.failure(ResultCode.NOTFOUND) : Result.success(pPub.toBytes());
    }

    @RequestMapping(value = "/newpkg", method = RequestMethod.GET)
    @ApiOperation(value = "产生新的PKG")
    public Object newPkg(String domain) {
        pkgService.getNewPkg(domain);
        return new Result();
    }


//    @RequestMapping(value = "/validate", method = RequestMethod.GET)
//    @ApiOperation(value = "验证ID对应的pp是否有效")
//    public Object validate() {
//        return 1;
//    }
}
