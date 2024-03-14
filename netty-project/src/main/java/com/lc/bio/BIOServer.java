package com.lc.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
public class BIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        log.info("服务启动");
        while (true) {
            log.info("等待连接");
            final Socket accept = serverSocket.accept();
            log.info("服务连接成功");
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handler(accept);
                }
            });
        }
    }

    /**
     * 处理
     *
     * @param accept
     */
    private static void handler(Socket accept) {
        InputStream inputStream = null;
        try {
            inputStream = accept.getInputStream();
            byte[] bytes = new byte[1024];
            while (true) {
                log.info("read");
                int len = inputStream.read(bytes);
                if (len != -1) {
                    log.info(new String(bytes, 0, len));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            log.info("服务关闭");
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                accept.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
