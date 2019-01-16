import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SupplyAsyncThenApplyAsyncParallelizing2SupplyAsyncExecutor1RunAsyncThenApplyAsync2 {

    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(2);
        System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " Started Execution");
        CompletableFuture<String> bluulinkRocks = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " SupplyAsync: Started Execution");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " SupplyAsync: Finished Execution");
            return "ROCKS!!!";
        }).thenApplyAsync((name) -> {
            System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " ThenApplyAsync: Started Execution");
            //Launch a new task 
            CompletableFuture<String> Rocks1 = CompletableFuture.supplyAsync(()->{
                try {
                    System.out.println("SupplierAsyncInner sees:" + name);
                    System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " SupplyAsyncInner: Started Execution");
                   //Play with this sleep time of SupplyAsyncInner to check how it affects the ThenApplyAsync times
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " SupplyAsyncInner: Finished Execution");
                return "ROCKS1!!!";
            });
            CompletableFuture.runAsync(()->{
                try {
                    System.out.println("RunnerAsyncInner sees:" + name);
                    System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " RunAsyncInner: Started Execution");
                   //Play with this sleep time of SupplyAsyncInner to check how it affects the ThenApplyAsync times
                    Thread.sleep(7000);
                    System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " RunAsyncInner: I don't return a shit. Just I print " + name);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " RunAsyncInner: Finished Execution");
            });
            CompletableFuture<String> Rocks2 = CompletableFuture.supplyAsync(()->{
                try {
                    System.out.println("SupplyAsyncInner2 sees:" + name);
                    System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " SupplyAsyncInner2: Started Execution");
                   //Play with this sleep time of SupplyAsyncInner to check how it affects the ThenApplyAsync times
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " SupplyAsyncInner2: Finished Execution");
                return "ROCKS2!!!";
            });
            
            //And while the parallel task is being executed we execute a block code that could take 5 seconds...
            try {
                 Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " ThenApplyAsync: Finished Execution");
            
            String combined = null;
            
            String rocksa = null;
            String rocksb = null;
            try {
                
                 rocksa = Rocks1.get();
                 rocksb = Rocks2.get();
                 combined = rocksa + rocksb;
                
                /*combined = Stream.of(Rocks1, Rocks2)
                                    .map(CompletableFuture::join)
                                    .collect(Collectors.joining(" "));
                */
                System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " ThenApplyAsync: Finished Combine");
                
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return combined;
            
        }).thenApplyAsync((result) -> {
            System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " ThenApplyAsync2: Started Execution");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " ThenApplyAsync2: Finished Execution");
            return result.toUpperCase();
        });

        try {
            // We need to give the main thread a long execution time or it would cut threads execution.
            Thread.sleep(30001);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " Reached the get");
        try {
            System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " " + bluulinkRocks.get());
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " Reached the end");
    }
}

