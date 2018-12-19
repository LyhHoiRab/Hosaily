package com.lab.hosaily.core.im.service;


import com.lab.hosaily.core.account.dao.UserDao;

import com.lab.hosaily.core.im.consts.Constants;
import com.lab.hosaily.core.im.consts.IMConfig;
import com.lab.hosaily.core.im.consts.IMUserType;
import com.lab.hosaily.core.im.dao.IMUserDao;

import com.lab.hosaily.core.im.entity.IMUser;
import com.lab.hosaily.core.im.utils.IMUtils;
import com.lab.hosaily.core.im.utils.SignatureUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.utils.RedisUtils;
import redis.clients.jedis.ShardedJedis;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class IMUserServiceImpl implements IMUserService{

    @Autowired
    private IMUserDao imUserDao;

    @Autowired
    private UserDao userDao;


    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> getByAccount(String accountId, String headImgUrl, String appid, String nickname, String jpush, String versionName) {
        //todo
//        默认客户注册app的时候自动注册IM账号，保存在IMUser，
//        需要用IM聊天的时候自动用acountId访问后台拿到自己IM信息给前端登录，
//        客户和导师都一样。


        //查询IM用户
        IMUser imUser = imUserDao.getByRelationIdAndType(accountId, IMUserType.SELLER);

        Map<String, Object> map = new HashMap<String, Object>();

        if(imUser == null){
            //私钥
            String imPrivateKey = getIMPrivateKey();
            //获取签名
            String sig = SignatureUtils.get(IMConfig.SDK_APPID, accountId, imPrivateKey);

            //创建IM用户
            imUser = new IMUser();
            imUser.setRelationId(accountId);
            imUser.setSig(sig);
            imUser.setSdkAppId(IMConfig.SDK_APPID);
            imUser.setName(nickname);
            imUser.setNickname(nickname);
            imUser.setType(IMUserType.SELLER);
            imUserDao.saveOrUpdate(imUser);

            //注册IM
            IMUtils.accountImport(IMConfig.SDK_APPID, IMConfig.ADMINISTRATOR, IMConfig.ADMINISTRATOR_SIG, imUser);
        }

        System.out.println("aAAAAAAAAAAAAAA");
        System.out.println("aAAAAAAAAAAAAAA: " + imUser.getRelationId());
        map.put("relationId", imUser.getRelationId());
        map.put("sig", imUser.getSig());
        map.put("sdkAppId", imUser.getSdkAppId());
        map.put("name", imUser.getName());
        map.put("nickname", imUser.getNickname());
        map.put("type", imUser.getType());
//        map.put("luckyPackage", wechat.getLuckyPackage());
//        map.put("passNewFriend", wechat.getPassNewFriend());
//        map.put("scan", wechat.getScan());
//        map.put("showWxno", wechat.getShowWxno());


        return map;
    }



    public String getIMPrivateKey(){
        try{


            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource resource = resolver.getResource(Constants.IM_PRIVATE_KEY_HSL);


            //读取文件
            FileInputStream stream = new FileInputStream(resource.getFile());
            //缓冲
            byte[] buffer = new byte[4096];
            //结果集
            StringBuffer sb = new StringBuffer();
            //读取长度
            int length = 0;

            while((length = stream.read(buffer)) != -1){
                sb.append(new String(buffer, 0, length));
            }
            String privateKey = sb.toString();

            //关闭流
            stream.close();


            return privateKey;
        }catch(Exception e){

            throw new DataAccessException(e.getMessage(), e);
        }
    }




    public static void main(String[] args) {
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource resource = resolver.getResource(Constants.IM_PRIVATE_KEY_HSL);
//        System.out.println("Constants.IM_PRIVATE_KEY: " + Constants.IM_PRIVATE_KEY_HSL);
//        try {
//            System.out.println("Constants.IM_PRIVATE_KEY: " + resource.getFile());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        IMUser imUser = new IMUser();

        imUser.setRelationId("asdfffff");
        imUser.setName("test");
        imUser.setHeadImgUrl("asdasd");
        IMUtils.accountImport(IMConfig.SDK_APPID, IMConfig.ADMINISTRATOR, IMConfig.ADMINISTRATOR_SIG, imUser);
    }
}
