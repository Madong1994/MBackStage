package com.m.d.web.controller;

import com.jfinal.core.Controller;
import com.m.d.common.entity.MUser;
import com.m.d.common.router.RouterMapping;
import com.m.d.im.common.util.tool.MD5Util;
import com.m.d.service.UserQuery;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @类名: RegistController
 * @包名: com.m.im.mweb.controller
 * @描述: 注册
 * @日期: 2017/10/9 9:43
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
@RouterMapping(url = "/mimRegist")
public class RegistController extends Controller{
    public void index(){
        MUser user = new MUser();
        user.setUserName("系统消息");
        user.setUserNum("100000");

        try {
            user.setPwd(MD5Util.EncoderByMD5("mim100000"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        UserQuery.me().register(user);
        renderJson("woodjaksjncdkalfmc");
    }
}
