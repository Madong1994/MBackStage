package com.m.d.im.common.handlers.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.m.d.im.common.IMPacket;
import com.m.d.im.common.entity.TextMessage;
import com.m.d.im.common.handlers.BaseHandler;
import com.m.d.im.common.protof.RequestModel;
import com.m.d.im.common.protof.ResponseModel;
import com.m.d.im.common.util.HandlerCode;
import com.m.d.im.common.util.IMcacheMap;
import com.m.d.im.common.util.RequestCode;
import com.m.d.im.common.util.ResponseCode;
import com.m.d.im.common.util.annotation.IMRequest;
import com.m.d.im.common.util.tool.IMSend;
import com.m.d.im.common.util.tool.ProtoBufUtil;
import org.tio.core.ChannelContext;

/**
 * Created by 马东 on 2017/12/10.
 *
 * @Author:madong
 * @Description:
 * @Date:Create in 22:00 2017/12/10
 * 关关雎鸠，在河之洲，
 * 窈窕淑女，君子好逑。
 */
@IMRequest(requestCode = RequestCode.SENMSG)
public class SenMessageHandler implements BaseHandler {
    @Override
    public String init(RequestModel.ImRequest imRequest, ChannelContext<Object, IMPacket, Object> channelContext) {
        String dataJson = imRequest.getData();
        JSONObject jsonObject = JSON.parseObject(dataJson);
        //todo 此处应该通过消息类型动态匹配相应的消息类
        String msgType = jsonObject.getString("msgType");
        String sender = null;
        String receiver = null;
        String msg = null;
        ChannelContext<Object, IMPacket, Object> receiverChannel = null;
        if("TEXT".equals(msgType)){
            TextMessage textMessage = JSON.parseObject(dataJson,TextMessage.class);
            sender = textMessage.getSender();
            receiver = textMessage.getReceiver();
            msg = textMessage.getMsg();
            receiverChannel = (ChannelContext<Object, IMPacket, Object>) IMcacheMap.cacheMap.get(receiver);
            if(receiverChannel != null){
                ResponseModel.ImResponse imResponse = ProtoBufUtil.responseModelFactory(ResponseCode.RES_SENDMSG, HandlerCode.RESPONSE,"0","0", System.currentTimeMillis()+"", JSONObject.toJSONString(textMessage));
                IMSend.send(receiverChannel,imResponse);
            }
        }

        System.out.println("????发送消息过来了？");
        return null;
    }
}
