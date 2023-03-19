import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    @Test
    void timerTest() throws InterruptedException {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Delayed job");
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask,2_000);

        Thread.sleep(3_000);
    }

    @Test
    void timerWithPeriod() throws InterruptedException{
        TimerTask timerTask =  new TimerTask() {
            @Override
            public void run() {
                System.out.println("Delayed Job");
            }
        };

        Timer timer = new Timer();
        // period berarti fungsinya akan menjalankan proses timerTask berulang kali setiap 2 detik, sedangkan delay untuk delay pertama saja
        timer.schedule(timerTask,2000,2000);
        // jika aplikasi tidak mati timer ini tidak akan pernah berhenti
        Thread.sleep(10000);
    }
}
