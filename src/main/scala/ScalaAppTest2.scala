import java.lang.management.ManagementFactory
import java.util.concurrent.Callable

/**
  * Scala 'Callable with Serializable' example
  *
  * Runs OK, doesn't exit
  */
object ScalaAppTest2 extends App {

  new ExampleGrid().execute(new Callable[Void] with Serializable {
    override def call(): Void = {
      val jvmName = ManagementFactory.getRuntimeMXBean.getName
      System.out.println("Callable class: " + getClass.getName)
      System.out.println("My name is '" + jvmName + "'. Hello ScalaApp2!")
      Thread.sleep(1000)
      null
    }
  })
  
}
