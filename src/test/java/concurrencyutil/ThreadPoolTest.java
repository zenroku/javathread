package concurrencyutil;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    @Test
    void create(){
        // inisial awal jumlah thread yang akan standby
        var minThread = 10;

        // maksimum thread yang akan digunakan
        var maxThread = 100;

        // timeout waktu ketika threadpool sedang idle akan menhapus thread yang tidak berjalan dan akan di set ulang ke minThread
        var alive = 1;
        var aliveTime = TimeUnit.MINUTES;

        // batas antrian dari eksekusi thread runnable
        var queue = new ArrayBlockingQueue<Runnable>(100);

        var threadPool = new ThreadPoolExecutor(minThread,maxThread,alive,aliveTime,queue);
    }

    // mengeksekusi runnable ke dalam queue
    @Test
    void execute() throws InterruptedException {
        var minThread = 10;
        var maxThread = 100;
        var alive = 1;
        var aliveTime = TimeUnit.MINUTES;
        var queue = new ArrayBlockingQueue<Runnable>(100);

        var threadPool = new ThreadPoolExecutor(minThread,maxThread,alive,aliveTime,queue);

        Runnable runnable = () ->{
            try {
                Thread.sleep(1000);
                System.out.println("Runnable from thread : " + Thread.currentThread().getName());
            } catch (Exception e){
                e.printStackTrace();
            }
        };

        // eksekusi disini
        threadPool.execute(runnable);

        // untuk simulasi
        // Thread.sleep(3000);

        // untuk mengatur pemberhentian threadpool ada 3 macam fungsi yang dapat dilakukan
        // 1. threadPool.shutdown() => akan mati ketika semua task selesai
        // 2. threadPool.shutdownNow() => semua task akan dipaksa mati
        // 3. threadPool.awaitTermination(berapa lama (long), masukan TimeUnit nya) => memastikan bahwa threadpool akan mati pada rentang waktu yang ditentukan
    }

    // berfungsi untuk membatasi thread yang berlebih untuk menjaga program dari exception OutOfMemory
    @Test
    void testRejectedExecution() throws InterruptedException {
        var minThread = 10;
        // disini kita set thread hanya sampai 100
        var maxThread = 100;
        var alive = 1;
        var aliveTime = TimeUnit.MINUTES;
        var queue = new ArrayBlockingQueue<Runnable>(10);
                                                                                          // masukkin classnya disini
        var threadPool = new ThreadPoolExecutor(minThread,maxThread,alive,aliveTime,queue,new LogRejectedHandler());

        // akan tetapi disini kita melakukan eksekusi thread sebanyak 1000
        for (int i = 0; i < 1000; i++) {
            Runnable runnable = () ->{
                try {
                    Thread.sleep(1000);
                    System.out.println("Runnable from thread : " + Thread.currentThread().getName());
                } catch (Exception e){
                    e.printStackTrace();
                }
            };

            threadPool.execute(runnable);
        }

        // akan ada sekitar 890 thread yang akan ditolak karena berlebih
        // kenapa lebih 10 ? disini karena saya kurang paham juga wakowakwoa
        threadPool.awaitTermination(1,TimeUnit.DAYS);
    }

    public static class LogRejectedHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("Task " + r + " is rejected");
        }
    }
}
