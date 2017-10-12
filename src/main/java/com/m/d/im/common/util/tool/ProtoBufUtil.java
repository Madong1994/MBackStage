package com.m.d.im.common.util.tool;


import com.m.d.im.common.protof.ResponseModel;

/**
 * Created by 马东 on 2017/9/24.
 */
public class ProtoBufUtil {
    public static ResponseModel.ImResponse responseModelFactory(int code, int handle, String uniqueOne, String uniqueTwo, String time, String data){
        ResponseModel.ImResponse.Builder builder = ResponseModel.ImResponse.newBuilder();
        builder.setCode(0);//0表示成功，1表示失败
        builder.setHandler(0);//0表示响应，1表示请求
        builder.setUniqueOne(uniqueOne);
        builder.setUniqueTwo(uniqueTwo);
        builder.setTime(time);
        builder.setObjectJson(data);
        ResponseModel.ImResponse imResponse = builder.build();
        return imResponse;
    }
}
