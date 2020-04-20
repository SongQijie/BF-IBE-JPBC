package cn.zjut.acising.controller;

import cn.zjut.acising.entity.Result;
import cn.zjut.acising.entity.ResultCode;
import cn.zjut.acising.service.UserService;
import cn.zjut.acising.tools.ObjectStorage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @description: 私钥信息服务
 * @author: SongQijie
 * @date: Created in 8:47 下午 2020/4/19
 * @version: 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/api/xpkis")
@Api(tags = "私钥信息服务")
public class XPKISCobtroller {


    private UserService userService;

    @Autowired
    public XPKISCobtroller(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @ApiOperation(value = "注册ID，通过ID获取公私钥")
    public Object register(String userId, String domain) {
        Map<String, Object> result = userService.register(userId, domain);
        return null == result ? Result.failure(ResultCode.REGISTERALREADY) : Result.success(result);
    }

//    @RequestMapping(value = "/revoke",method = RequestMethod.POST)
//    @ApiOperation(value="注销ID和密钥")
//    public Object revoke(){
//        return 1;
//    }
//
//    @RequestMapping(value = "/recover",method = RequestMethod.POST)
//    @ApiOperation(value="恢复ID私钥")
//    public Object recover(){
//        return 1;
//    }
//
//    @RequestMapping(value = "/reissue",method = RequestMethod.POST)
//    @ApiOperation(value="重新申请ID私钥")
//    public Object reissue(){
//        return 1;
//    }


}
