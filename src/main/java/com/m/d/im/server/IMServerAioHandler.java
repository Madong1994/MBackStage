package com.m.d.im.server;


import com.m.d.im.common.DispatcherHandler;
import com.m.d.im.common.IMAbsAioHandler;
import com.m.d.im.common.IMPacket;
import com.m.d.im.common.interceptor.BaseInterceptor;
import com.m.d.im.common.interceptor.impl.LoginInterceptor;
import com.m.d.im.common.protof.RequestModel;
import com.m.d.im.common.util.HandlerCode;
import org.tio.core.ChannelContext;
import org.tio.server.intf.ServerAioHandler;

/**
 * @author tanyaowu
 * @创建时间 2016年11月18日 上午9:13:15
 * @操作列表 编号 | 操作时间 | 操作人员 | 操作说明 (1) | 2016年11月18日 | tanyaowu | 新建类
 */
public class IMServerAioHandler extends IMAbsAioHandler implements ServerAioHandler<Object, IMPacket, Object> {


    /**
     * 处理消息
     * 1、解密
     * 2、分发到相应的处理类
     */
    @Override
    public Object handler(IMPacket packet, ChannelContext<Object, IMPacket, Object> channelContext)
            throws Exception {
        byte[] body = packet.getBody();
        if (body != null) {
            /**
             * 1、验证是否已登录，如果已经登录不拦截，如果没有登录，先登录
             */
            RequestModel.ImRequest imRequest = RequestModel.ImRequest.parseFrom(body);
            if (imRequest.getHandler() == HandlerCode.REQUEST) {
                registInterceptor(imRequest);
                DispatcherHandler.handler(imRequest, channelContext);
            }
        }
        return null;
    }

    /**
     * 根据自己的逻辑注册拦截器
     * @param imRequest
     */
    private void registInterceptor(RequestModel.ImRequest imRequest){
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        DispatcherHandler.b_interceptor = (BaseInterceptor) loginInterceptor;
    }
}

