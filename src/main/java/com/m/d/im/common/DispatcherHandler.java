package com.m.d.im.common;


import com.jfinal.log.Log;
import com.m.d.im.common.handlers.BaseHandler;
import com.m.d.im.common.interceptor.BaseInterceptor;
import com.m.d.im.common.interceptor.HandlerInterceptor;
import com.m.d.im.common.protof.RequestModel;
import com.m.d.im.common.util.MClassScaner;
import com.m.d.im.common.util.annotation.IMInterceptor;
import com.m.d.im.common.util.annotation.IMRequest;
import org.tio.core.ChannelContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: DispatcherHandler.class
 * @包名: com.base.im.common
 * @描述: 通过@IMRequst注解寻找处理类
 * @所属: 华夏九鼎
 * @日期: 2017/8/16 14:51
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class DispatcherHandler {
    public static BaseInterceptor b_interceptor;//拦截器逻辑接口
    private static final Log log = Log.getLog(DispatcherHandler.class);

    public static String handler(RequestModel.ImRequest imRequest, ChannelContext<Object, IMPacket, Object> channelContext) {
        List<Class> BaseHandleImplClassList = MClassScaner.scanSubClass(BaseHandler.class,BaseHandler.class);
        if (BaseHandleImplClassList != null) {
            for (Class<?> impl : BaseHandleImplClassList) {
                IMRequest annotation = impl.getAnnotation(IMRequest.class);
                IMInterceptor interceptor = impl.getAnnotation(IMInterceptor.class);
                if (null != annotation) {
                    try {
                        if(interceptor != null){
                            //使用注解拦截器
                            BaseHandler baseHandle = (BaseHandler) impl.newInstance();
                            interThrows();
                            HandlerInterceptor handlerInterceptor = new HandlerInterceptor(baseHandle,b_interceptor);
                            BaseHandler baseHandle1 = (BaseHandler) Proxy.newProxyInstance(handlerInterceptor.getClass().getClassLoader(),baseHandle.getClass().getInterfaces(),handlerInterceptor);
                            return baseHandle1.init(imRequest, channelContext);
                        }else {
                            //没有使用拦截器
                            Method method = impl.getMethod("init", RequestModel.ImRequest.class, ChannelContext.class);
                            Object object = impl.newInstance();
                            return (String) method.invoke(object, imRequest, channelContext);
                        }

                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
                    }
                }
            }
        }
        return null;
    }
    private static void interThrows() throws Exception{
        if(b_interceptor == null) {
            throw new NullPointerException("regiestInterceptor=null,可能原因注册地方出错，请换在IMServerAioHandler类中registInterceptor()方法中注册");
        }
    }
}
