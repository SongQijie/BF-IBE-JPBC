package cn.zjut.acising.controller;

import cn.zjut.acising.entity.Result;
import cn.zjut.acising.ibe.pojo.CipherText;
import cn.zjut.acising.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 公开参数加密服务
 * @author: SongQijie
 * @date: Created in 8:47 下午 2020/4/19
 * @version: 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/api/xppcs")
@Api(tags = "公开参数加密服务")
public class XPPCSController {

    private UserService userService;

    @Autowired
    public XPPCSController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/encrypt", method = RequestMethod.GET)
    @ApiOperation(value = "加密")
    public Object encrypt(String userId, String domain, String message, String receiveUserId, String receiveDomain) {
        String crypt = userService.encrypt(userId, domain, message, receiveUserId, receiveDomain);
        return Result.success(crypt);
    }

//    @RequestMapping(value = "/verify",method = RequestMethod.POST)
//    @ApiOperation(value="验证签名")
//    public Object verify(){
//        return 1;
//    }


}
