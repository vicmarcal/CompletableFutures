import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SupplyAsyncThenApplyAsyncWithExecutor {

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
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " ThenApplyAsync: Finished Execution");
            return "Hello" + name;
        }, executor).thenApplyAsync((name) -> {
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
            // We keep the sleep at 8001. So we can be sure the Main don't kill them!
            // But ey! What happens with sleep(0)! Executor doesn't care about main() dead
            // and keeps the Asyncs running!!
            Thread.sleep(16000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + Thread.currentThread().getName() + ": Reached the end and Now it is: " + LocalDateTime.now());
    }
}
