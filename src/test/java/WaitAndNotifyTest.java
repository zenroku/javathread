import org.junit.jupiter.api.Test;

public class WaitAndNotifyTest {
    private String message = null;
    final Object lock = new Object();

    @Test
    void waitLock() throws InterruptedException {
        var thread1 = new Thread(() -> {
           synchronized (lock){
               try {
                   lock.wait();
                   System.out.println(message);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
        });

        var thread2 = new Thread(() -> {
           synchronized (lock){
               message = "Zahir Fathurrahman";
               lock.notify();
           }
        });

        // konsepnya jalananin thread yg nunggu dulu baru jalananin thread yg ngasih nilai (wait first and notify)
        // kalau sekiranya cuman 1 thread aja yg mungkin wait bisa pakai notify() saja
        // kalau banyak fungsi yang pake wait dari locknya pake notifyAll() biar yang lain nunggu dapet notify nya
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }
}
