package com.lc.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLoadBalanceService implements LoadBalance {

    private AtomicInteger atomicInteger = new AtomicInteger(0);


    public final int getAndIncrement() {
        int current;
        int next;
        do {
            current = atomicInteger.get();
            next = current > Integer.MAX_VALUE ? 0 : current + 1;
        } while (!this.atomicInteger.compareAndSet(current, next));
        System.out.println("第" + next + "次访问" + next);
        return next;

    }

    /**
     * @param instanceList
     * @return
     */
    @Override
    public ServiceInstance instances(List<ServiceInstance> instanceList) {

        int index = getAndIncrement() % instanceList.size();
        return instanceList.get(index);
    }

    public static void main(String[] args) {
        // 创建一个 AtomicInteger 原子整数类的实例
        AtomicInteger atomicInteger = new AtomicInteger(0);
        int current;
        int next;
        do {
            current = atomicInteger.get();
            next = current > Integer.MAX_VALUE ? 0 : current + 1;
        } while (!atomicInteger.compareAndSet(current, next));
        System.out.println("next" + next);

        Thread thread1 = new Thread(() -> {
            // 循环尝试将值设置为 10
            while (!atomicInteger.compareAndSet(1, 10)) {
                System.out.println("Thread 1 failed to set value");
            }
            System.out.println("atomicInteger" + atomicInteger.get());
            System.out.println("Thread 1 set value to 10");
            System.out.println("atomicInteger" + atomicInteger.get());
        });

        Thread thread2 = new Thread(() -> {
            // 尝试将值设置为 20
            if (atomicInteger.compareAndSet(20, 30)) {
                System.out.println("Thread 2 set value to 20");
            }
        });

        thread1.start();
        thread2.start();

        // 等待线程执行完毕
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 输出最终的值
        System.out.println("Final value: " + atomicInteger.get());
    }
}
