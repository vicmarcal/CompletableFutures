import java.time.LocalDateTime;
import java.util.concurrent.*;

public class SupplyAsyncThenApply {

    public static void main(String[] args) {
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
        }).thenApply(name -> {
            System.out.println("ThenApply: Started Executing in " + Thread.currentThread().getName() + " and Now it is: " + LocalDateTime.now());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("ThenApply: Finished Executing in" + Thread.currentThread().getName() + "and Now it is:" + LocalDateTime.now());
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
        System.out.println("Main: Reached the get " + Thread.currentThread().getName() + " and Now it is: " + LocalDateTime.now());
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
