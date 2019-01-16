import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SupplyAsyncThenApplyAsyncWithExecutor {

    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(2);
        System.out.println("Main: Started Executing in " + Thread.currentThread().getName() + " and Now it is: " + LocalDateTime.now());
        CompletableFuture<String> bluulinkRocks = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("SupplyAsync: Started Executing in " + Thread.currentThread().getName() + " and Now it is: " + LocalDateTime.now());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("SupplyAsync: Finished Executing in " + Thread.currentThread().getName() + " and Now it is: " + LocalDateTime.now());
            return "ROCKS!!!";
        }).thenApplyAsync((name) -> {
            System.out.println("ThenApplyAsync: Started Executing in " + Thread.currentThread().getName() + " and Now it is: " + LocalDateTime.now());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("ThenApplyAsync: Finished Executing in" + Thread.currentThread().getName() + "and Now it is:" + LocalDateTime.now());
            return "Hello" + name;
        }, executor).thenApplyAsync((name) -> {
            System.out.println("ThenApplyAsync2: Started Executing in " + Thread.currentThread().getName() + " and Now it is: " + LocalDateTime.now());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("ThenApplyAsync2: Finished Executing in" + Thread.currentThread().getName() + "and Now it is:" + LocalDateTime.now());
            return "Hello" + name;
        });

        try {
            // We keep the sleep at 8001. So we can be sure the Main don't kill them!
            // But ey! What happens with sleep(0)! Executor doesn't care about main() dead
            // and keeps the Asyncs running!!
            Thread.sleep(16000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": Reached the end and Now it is: " + LocalDateTime.now());
    }
}
