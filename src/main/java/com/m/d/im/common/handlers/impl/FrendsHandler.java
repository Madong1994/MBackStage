package com.m.d.im.common.handlers.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.m.d.common.entity.MUser;
import com.m.d.im.common.IMPacket;
import com.m.d.im.common.entity.User;
import com.m.d.im.common.handlers.BaseHandler;
import com.m.d.im.common.protof.RequestModel;
import com.m.d.im.common.protof.ResponseModel;
import com.m.d.im.common.util.HandlerCode;
import com.m.d.im.common.util.RequestCode;
import com.m.d.im.common.util.ResponseCode;
import com.m.d.im.common.util.annotation.IMRequest;
import com.m.d.im.common.util.tool.IMSend;
import com.m.d.im.common.util.tool.ProtoBufUtil;
import com.m.d.im.common.util.tool.ResultMsg;
import com.m.d.im.common.util.tool.ResultMsgCode;
import com.m.d.service.UserQuery;
import org.tio.core.ChannelContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 马东 on 2017/12/8.
 *
 * @Author:madong
 * @Description:
 * @Date:Create in 20:52 2017/12/8
 * 关关雎鸠，在河之洲，
 * 窈窕淑女，君子好逑。
 */
@IMRequest(requestCode = RequestCode.FRENDS)
public class FrendsHandler implements BaseHandler {
    @Override
    public String init(RequestModel.ImRequest imRequest, ChannelContext<Object, IMPacket, Object> channelContext) {
        String requestJson = imRequest.getData();
        List<MUser> musers = UserQuery.me().findFrends(requestJson);
        List<User> users = new ArrayList<>();
        for(MUser mUser : musers){
            User user = new User();
            user.setUserName(mUser.getUserName());
            user.setUserNum(mUser.getUserNum());
            users.add(user);
        }
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setResultCode(ResultMsgCode.FRENDS_SCUSSE);
        String json = JSON.toJSONString(users);
        resultMsg.setObject(json);
        resultMsg.setResultMsg("获取好友成功!");

        ResponseModel.ImResponse imResponse = ProtoBufUtil.responseModelFactory(ResponseCode.RES_FRENDS, HandlerCode.RESPONSE,"0","0", System.currentTimeMillis()+"", JSONObject.toJSONString(resultMsg));
        IMSend.send(channelContext,imResponse);
        return null;
    }
}
