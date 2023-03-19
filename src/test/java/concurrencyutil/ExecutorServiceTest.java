package concurrencyutil;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {
    // executor service merupakan versi simple dari ThreadPoolExecutor, direkomendasikan pakai yg ini tp kalau butuh config yg spesifik bisa pake yang Sebelumnya
    @Test
    void create() throws InterruptedException {
        // ada 3 jenis yg pertama pake newSingleThreadExecutor jadi cuman 1 thread pakenya
         ExecutorService threadPool = Executors.newSingleThreadExecutor();
        // yang kedua bisa di setting mau brp jumlah threadnya pake :
        // ExecutorService threadPool = Executors.newFixedThreadPool(2);
        // yang terakhir pake unlimited thread, jadi fleksibel bisa membengkak sendiri threadnya
        // ExecutorService threadPool = Executors.newCachedThreadPool();


        for (int i = 0; i < 10; i++) {
            Runnable runnable = ()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Test executors " + Thread.currentThread().getName());
            };

            threadPool.execute(runnable);
        }

        threadPool.awaitTermination(15, TimeUnit.SECONDS);
    }
}
