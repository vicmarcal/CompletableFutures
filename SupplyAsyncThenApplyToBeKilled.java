import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;


public class SupplyAsyncThenApplyToBeKilled {

    public static void main(String[] args) {
        System.out.println("Main: Started Executing in " + Thread.currentThread().getName() + " and Now it is: " + LocalDateTime.now());
        CompletableFuture.supplyAsync(() -> {
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
            // Sleep(0):Main is so small that SupplyAsync dies and with it all the rest
            // Sleep(<5000): Dies at SupplyAsync
            // Sleep(>5000): Dies at ThenApply
            Thread.sleep(5210);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Main: Finished at " + Thread.currentThread().getName() + " and Now it is: " + LocalDateTime.now());
    }
}
