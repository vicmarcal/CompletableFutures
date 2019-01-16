import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SupplyAsyncThenApplyAsyncWithSuppliersAsync {

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
            CompletableFuture<String> Rocks = CompletableFuture.supplyAsync(()->{
                try {
                    System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " SupplyAsyncInner: Started Execution");
                   //Play with this sleep time of SupplyAsyncInner to check how it affects the ThenApplyAsync times
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " SupplyAsyncInner: Finished Execution");
                return "ROCKS!!!";
            });
            //And while the parallel task is being executed we execute a block code that could take 5 seconds...
            try {
                
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if(Rocks.isDone()) {
                String rocksa = null;
                //As it is done in time, the time differences in these printls should be almost none.
                System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " SupplyAsyncInner done in time");
                try {
                    rocksa = Rocks.get();
                } catch (InterruptedException | ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " ThenApplyAsync: Finished Execution");
                return "Hello" + name + rocksa;
            }else {
                //However here Rocks.get will really block making real differences.
                //Even more, the differences should be exactly equal to the sleep() differences of ThenApplyAsync and SupplyAsyncInner.
                String rocksa = null;
                System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " SupplyAsyncInner is not done in time");
                try {
                    rocksa = Rocks.get();
                } catch (InterruptedException | ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " ThenApplyAsync: Finished Execution");
                return "Hello" + name + rocksa;        
            }

        }).thenApplyAsync((name) -> {
            System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " ThenApplyAsync2: Started Execution");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " ThenApplyAsync2: Finished Execution");
            return "Hello" + name;
        });

        try {
            // We need to give the main thread a long execution time or it would cut threads execution.
            Thread.sleep(20001);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " Reached the end");
    }
}

