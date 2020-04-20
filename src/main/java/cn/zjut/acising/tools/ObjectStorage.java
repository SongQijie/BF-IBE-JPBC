package cn.zjut.acising.tools;

import cn.zjut.acising.ibe.pojo.SystemParameter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 相关静态存储
 * @author: SongQijie
 * @date: Created in 12:23 上午 2020/4/20
 * @version: 1.0
 **/
public class ObjectStorage {

    public static Map<String, SystemParameter> PKGPARAMETERS = new ConcurrentHashMap<>();

    public static ExpiryMap<String, Object> USERDOMAIN = ExpiryMap.getInstance();

    public static String PASSWORD = "acising";

}
