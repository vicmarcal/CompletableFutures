import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class SupplyAsyncThenApplyAsync {

    public static void main(String[] args) {
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
        }).thenApplyAsync(name -> {
            System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " ThenApplyAsync: Started Execution");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " ThenApplyAsync: Finished Execution");
            return "Hello " + name;
        });

        try {
            // We keep the sleep at 8001. So we can be sure the Main don't kill them!
            // But ey! What happens with sleep(0)! Executor doesn't care about main() dead
            // and keeps the Asyncs running!!
            Thread.sleep(3001);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " Reached the get");
        // Block and get the result of the future.
        // If the Main thread sleep is less than 10.000, bluulinkRocks futures didnt yet
        // executed fully....so Main will have to wait
        // If the Main thread sleep is greater than 10.000, bluulinkRocks futures are
        // executed fully and there won't be any blockage.
/*
        try {
            System.out.println(bluulinkRocks.get());
            System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " Retrieved the get ");
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }
}
