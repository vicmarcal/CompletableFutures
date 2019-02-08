import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class SupplyAsyncThenApply {

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
        }).thenApply(name -> {
            System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " ThenApply: Started Execution");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch blocks
                e.printStackTrace();
            }
            System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " ThenApply: Finished Execution");
            return "Hello " + name;
        });

        try {
            // We keep the sleep at 8001. So we can be sure the Main don't kill them!
            // But ey! What happens with sleep(0)! Executor doesn't care about main() dead
            // and keeps the Asyncs running!!
            Thread.sleep(12001);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(LocalDateTime.now() + " - " + Thread.currentThread().getName() + " Reached the end");
        // Block and get the result of the future.
        // If the Main thread sleep is less than 10.000, bluulinkRocks futures didnt yet
        // executed fully....so Main will have to wait
        // If the Main thread sleep is greater than 10.000, bluulinkRocks futures are
        // executed fully and there won't be any blockage.
        /*
        try {
            System.out.println(bluulinkRocks.get() + Thread.currentThread().getName() + " and Now it is: " + LocalDateTime.now());
            System.out.println("Main: Retrieved the get " + Thread.currentThread().getName() + " and Now it is: " + LocalDateTime.now());
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }

}
