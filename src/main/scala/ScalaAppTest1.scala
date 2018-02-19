import java.lang.management.ManagementFactory
import java.util.concurrent.Callable

/**
  * Scala 'Callable' example
  *
  * throws java.io.NotSerializableException
  */
object ScalaAppTest1 extends App {

  new ExampleGrid().execute(new Callable[Void] {
    override def call(): Void = {
      val jvmName = ManagementFactory.getRuntimeMXBean.getName
      System.out.println("Callable class: " + getClass.getName)
      System.out.println("My name is '" + jvmName + "'. Hello ScalaApp1!")
      Thread.sleep(1000)
      null
    }
  })

}
