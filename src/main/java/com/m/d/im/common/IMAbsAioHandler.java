package com.m.d.im.common;

import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.AioHandler;

import java.nio.ByteBuffer;

public abstract class IMAbsAioHandler implements AioHandler<Object, IMPacket, Object>
{
	/**
	 * 编码：把业务消息包编码为可以发送的ByteBuffer
	 */
	@Override
	public ByteBuffer encode(IMPacket packet, GroupContext<Object, IMPacket, Object> groupContext, ChannelContext<Object, IMPacket, Object> channelContext)
	{
		byte[] body = packet.getBody();
		int bodyLen = 0;
		if (body != null)
		{
			bodyLen = body.length;
		}

		int allLen = IMPacket.HEADER_LENGHT + bodyLen;
		ByteBuffer buffer = ByteBuffer.allocate(allLen);
		buffer.order(groupContext.getByteOrder());

		buffer.putInt(bodyLen);

		if (body != null)
		{
			buffer.put(body);
		}
		return buffer;
	}

	/**
	 * 解码：把接收到的ByteBuffer，解码成应用可以识别的业务消息包
	 */
	@Override
	public IMPacket decode(ByteBuffer buffer, ChannelContext<Object, IMPacket, Object> channelContext) throws AioDecodeException
	{
		int readableLength = buffer.limit() - buffer.position();
		if (readableLength < IMPacket.HEADER_LENGHT)
		{
			return null;
		}

		int bodyLength = buffer.getInt();

		if (bodyLength < 0)
		{
			throw new AioDecodeException("bodyLength [" + bodyLength + "] is not right, remote:" + channelContext.getClientNode());
		}

		int neededLength = IMPacket.HEADER_LENGHT + bodyLength;
		int test = readableLength - neededLength;
		if (test < 0) // 不够消息体长度(剩下的buffe组不了消息体)
		{
			return null;
		} else
		{
			IMPacket imPacket = new IMPacket();
			if (bodyLength > 0)
			{
				byte[] dst = new byte[bodyLength];
				buffer.get(dst);
				imPacket.setBody(dst);
			}
			return imPacket;
		}
	}
}
