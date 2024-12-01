package dev.httpmarco.netline.utils;

import io.netty5.channel.EventLoop;
import io.netty5.channel.MultithreadEventLoopGroup;
import io.netty5.channel.ServerChannel;
import io.netty5.channel.ServerChannelFactory;
import io.netty5.channel.epoll.Epoll;
import io.netty5.channel.epoll.EpollHandler;
import io.netty5.channel.epoll.EpollServerSocketChannel;
import io.netty5.channel.epoll.EpollSocketChannel;
import io.netty5.channel.nio.NioHandler;
import io.netty5.channel.socket.SocketChannel;
import io.netty5.channel.socket.nio.NioServerSocketChannel;
import io.netty5.channel.socket.nio.NioSocketChannel;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class NetworkNettyUtils {

    @Contract("_ -> new")
    public static @NotNull MultithreadEventLoopGroup createEventLoopGroup(int threads) {
        return new MultithreadEventLoopGroup(threads, Epoll.isAvailable() ? EpollHandler.newFactory() : NioHandler.newFactory());
    }

    public static @NotNull SocketChannel createChannelFactory(EventLoop loop) {
        return Epoll.isAvailable() ? new EpollSocketChannel(loop) : new NioSocketChannel(loop);
    }

    @Contract(pure = true)
    public static @NotNull ServerChannelFactory<? extends ServerChannel> generateChannelFactory() {
        return Epoll.isAvailable() ? EpollServerSocketChannel::new : NioServerSocketChannel::new;
    }
}
