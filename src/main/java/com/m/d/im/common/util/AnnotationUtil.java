package com.m.d.im.common.util;

import com.jfinal.log.Log;
import com.m.d.common.util.ClassScaner;
import com.m.d.im.common.handlers.BaseHandleImpl;
import com.m.d.im.common.interceptor.BaseInterceptor;
import com.m.d.im.common.interceptor.HandlerInterceptor;
import com.m.d.im.common.util.annotation.IMInterceptor;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ${CLASS_NAME}
 * @包名: com.base.im.common.util
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @所属: 华夏九鼎
 * @日期: 2017/8/17 9:23
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 * 此工具暂无用处
 */
public class AnnotationUtil {
    public static BaseInterceptor regiestInterceptor;//拦截器逻辑接口
    private static final Log log = Log.getLog(AnnotationUtil.class);
    private static List<Class<BaseHandleImpl>> BaseHandleImplClassList = ClassScaner.scanSubClass(BaseHandleImpl.class);
    private static void handler(){
        if(BaseHandleImplClassList != null){
            for(Class<?> impl : BaseHandleImplClassList){
                IMInterceptor interceptor = impl.getAnnotation(IMInterceptor.class);
                if(interceptor != null){
                    try {
                        BaseHandleImpl baseHandle = (BaseHandleImpl) impl.newInstance();
                        HandlerInterceptor handlerInterceptor = new HandlerInterceptor(baseHandle,regiestInterceptor);
                        BaseHandleImpl baseHandle1 = (BaseHandleImpl) Proxy.newProxyInstance(handlerInterceptor.getClass().getClassLoader(),baseHandle.getClass().getInterfaces(),handlerInterceptor);
//                        baseHandle1.init()
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
