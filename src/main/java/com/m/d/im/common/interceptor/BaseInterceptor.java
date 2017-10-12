package com.m.d.im.common.interceptor;


import com.m.d.im.common.IMPacket;
import org.tio.core.ChannelContext;

/**
 * Created by 马东 on 2017/8/23.
 * 其他拦截器实现此基本拦截器接口
 */
public interface BaseInterceptor {
    public void before(ChannelContext<Object, IMPacket, Object> channelContext);
    public void after(ChannelContext<Object, IMPacket, Object> channelContext);

}
