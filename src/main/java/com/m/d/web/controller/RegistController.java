package com.m.d.web.controller;

import com.jfinal.core.Controller;
import com.m.d.common.router.RouterMapping;

/**
 * @类名: RegistController
 * @包名: com.m.im.mweb.controller
 * @描述: 注册
 * @日期: 2017/10/9 9:43
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
@RouterMapping(url = "/test")
public class RegistController extends Controller{
    public void index(){
        renderJson("woodjaksjncdkalfmc");
    }
}
