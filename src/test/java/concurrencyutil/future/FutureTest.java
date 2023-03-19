package concurrencyutil.future;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

// future adalah versi thread yang bisa return value yang dikombinasikan dengan Callable yang berbalik dari Runnable yang hanya void
public class FutureTest {

    @Test
    void create() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Callable<String> testCallable = () -> {
          Thread.sleep(1000);
          return "Test Returning value from callable";
        };

        Future<String> submit = service.submit(testCallable);
        // ambil value menggunakan get() yang memerlukan throw ExecutionException, InterruptedException
        System.out.println(submit.get());

        // balikan boolean yang mengartikan bahwa submit tiba2 di cancel di tengah proses
        // submit.isCancelled();
        // memerintahkan submit mencancel seluruh operasi di tengah jalan, perlu diingat kalau pake thread sleep bakal kena throw exception
        // submit.cancel(true);
        // balikan boolean yang mengartikan bahwa submit sudah berhasil return value
        // submit.isDone();
    }

    // menggunakan fungsi invokeAll/invokeAny
    @Test
    void multipleFuture() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(10);

        List<Callable<String>> callableList = IntStream.range(1,10).mapToObj(value -> (Callable<String>) () -> {
            Thread.sleep(value * 500L);
            return String.valueOf(value);
        }).toList();

        // eksekusinya langsung semua
         List<Future<String>> futures = service.invokeAll(callableList);

        // mengambil salah satu Callable yang selesai duluan
        // String future = service.invokeAny(callableList);

        for (var future : futures){
            System.out.println(future.get());
        }

//        System.out.println(future);
    }
}
