package cn.hfbin.lzzmjx.socket;

import cn.hfbin.lzzmjx.service.EquimentService;
import cn.hfbin.lzzmjx.utils.AscllUtils;
import cn.hfbin.lzzmjx.utils.DataAnalysisUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler extends ChannelHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("server channel active... ");
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String s = AscllUtils.bytesToHexString(req);
        log.info("Server (解析数据):" + s);
        DataAnalysisUtils dataAnalysisUtils = new DataAnalysisUtils();
        byte[] analysis = dataAnalysisUtils.analysis(s);
        ctx.writeAndFlush(Unpooled.copiedBuffer(analysis));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
            throws Exception {
        ctx.flush();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t)
            throws Exception {
        ctx.close();
    }

}
