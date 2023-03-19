package concurrencyutil.future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class CompletableFutureTest {
//    Future but more efficient function build

    ExecutorService executor = Executors.newFixedThreadPool(10);

    public Future<String> getValue(){
        CompletableFuture<String> future = new CompletableFuture<>();

        executor.execute(()-> {
            try {
                Thread.sleep(2000);
                // wajib pakai complete untuk mengembalikan nilainya, kalau tidak thread ini tidak akan selesai
                future.complete("Value has generated");
            } catch (InterruptedException e){
                future.completeExceptionally(e);
            }
        });

        return future;
    }
    
    @Test
    void normal() throws ExecutionException, InterruptedException {
        Future<String> result = getValue();
        System.out.println(result.get());
    }

    public CompletableFuture<String> getValueForCompletionStage(){
        CompletableFuture<String> future = new CompletableFuture<>();

        executor.submit(()-> {
            try {
                Thread.sleep(5000);
                future.complete("Muhammad Zahir Fathurrahman");
            } catch (InterruptedException e){
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    // interupsi sebelu value dikembalikan
    @Test
    void interruptFutureValue() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = getValueForCompletionStage();

        CompletableFuture<String[]> result = future
                .thenApply(String::toUpperCase)
                .thenApply(string-> string.split(" "));

        for (String s : result.get()){
            System.out.println(s);
        }
    }
}
