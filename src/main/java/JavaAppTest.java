import java.lang.management.ManagementFactory;
import java.util.concurrent.Callable;

/**
 * Java 'Callable' example
 *
 * Runs OK, doesn't exit
 */
public class JavaAppTest extends ExampleGrid {
    public static void main(String[] args) throws Exception {
        new ExampleGrid().execute(new Callable<Void>() {
            public Void call() throws Exception {
                String jvmName = ManagementFactory.getRuntimeMXBean().getName();
                System.out.println("Callable class: " + getClass().getName());
                System.out.println("My name is '" + jvmName + "'. Hello JavaApp!");
                Thread.sleep(1000);
                return null;
            }
        });
    }
}
