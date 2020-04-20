package cn.zjut.acising.controller;

import cn.zjut.acising.entity.Result;
import cn.zjut.acising.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 私钥加密服务
 * @author: SongQijie
 * @date: Created in 8:48 下午 2020/4/19
 * @version: 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/api/xpkcs")
@Api(tags = "私钥加密服务")
public class XPKCSController {

//    @RequestMapping(value = "/sign", method = RequestMethod.GET)
//    @ApiOperation(value = "签名")
//    public Object sign() {
//        return 1;
//    }

    private UserService userService;

    @Autowired
    public XPKCSController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/decrypt", method = RequestMethod.POST)
    @ApiOperation(value = "解密")
    public Object decrypt(String cipherText, String domain, String userId) {
        String message = userService.decrypt(cipherText, domain, userId);
        return Result.success(message);
    }

}
