import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.util.concurrent.Callable;

/**
 * JUnit/Java 'Callable' example
 *
 * Runs OK, exits OK!
 */

public class JUnitTest {

    @Test
    public void testGrid() throws Exception {
        new ExampleGrid().execute(new Callable<Void>() {
            public Void call() throws Exception {
                String jvmName = ManagementFactory.getRuntimeMXBean().getName();
                System.out.println("Callable class: " + getClass().getName());
                System.out.println("My name is '" + jvmName + "'. Hello JUnit!");
                Thread.sleep(1000);
                return null;
            }
        });
    }
}