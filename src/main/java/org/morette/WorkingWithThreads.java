package org.morette;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class WorkingWithThreads {
    public void run() throws InterruptedException {
        Set<String> platformThreads = ConcurrentHashMap.newKeySet();

        List<Thread> threads = IntStream.range(0, 1_000).mapToObj(i -> {
            return Thread.ofVirtual().start(() -> {
                System.out.println("My virtual thread");
                System.out.println("Thread ID: " + Thread.currentThread().threadId());
                platformThreads.add(readWorkerName());
            });
        }).toList();
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Platform threads used: " + platformThreads.size());
    }

    String readWorkerName() {
        String name = Thread.currentThread().toString();
        int il = name.indexOf("worker");
        return name.substring(il);
    }
}
