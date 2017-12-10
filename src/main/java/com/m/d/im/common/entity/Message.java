package com.m.d.im.common.entity;

/**
 * Created by 马东 on 2017/12/9.
 *
 * @Author:madong
 * @Description:
 * @Date:Create in 1:18 2017/12/9
 * 关关雎鸠，在河之洲，
 * 窈窕淑女，君子好逑。
 */
public class Message {
    private String sender;
    private String msgType;
    private String receiver;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

}
