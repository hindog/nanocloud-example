# NanoCloud/GridKit Issue?

I'm experiencing a couple of "anomalies" with the NanoCloud library and put together some examples with hope that the library devs might be able to shed some light on.

1. Application doesn't shutdown propertly except when running within Unit test 
    
    ```
    "TunnelControl:tunneller" #19 prio=5 os_prio=31 tid=0x00007fed991f9800 nid=0xa103 in Object.wait() [0x0000700011752000]
       java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076eb10180> (a org.gridkit.vicluster.telecontrol.bootstraper.StreamPipe)
        at java.lang.Object.wait(Object.java:502)
        at org.gridkit.vicluster.telecontrol.bootstraper.StreamPipe.waitForData(StreamPipe.java:126)
        - locked <0x000000076eb10180> (a org.gridkit.vicluster.telecontrol.bootstraper.StreamPipe)
        at org.gridkit.vicluster.telecontrol.bootstraper.StreamPipe.bufferRead(StreamPipe.java:61)
        at org.gridkit.vicluster.telecontrol.bootstraper.StreamPipe.access$500(StreamPipe.java:13)
        at org.gridkit.vicluster.telecontrol.bootstraper.StreamPipe$PipeIn.read(StreamPipe.java:208)
        at org.gridkit.vicluster.telecontrol.bootstraper.StreamPipe$PipeIn.read(StreamPipe.java:198)
        at java.io.DataInputStream.readInt(DataInputStream.java:387)
        at org.gridkit.vicluster.telecontrol.bootstraper.TunnellerConnection$Control.run(TunnellerConnection.java:353)
    
    "EXEC[/usr/java/latest/bin/java -Xmx32m -Xms32].220663944" #15 prio=5 os_prio=31 tid=0x00007fed9c8e7800 nid=0x5807 waiting on condition [0x0000700011346000]
       java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at org.gridkit.vicluster.telecontrol.ssh.RemoteSshProcess.waitFor(RemoteSshProcess.java:91)
        at org.gridkit.nanocloud.telecontrol.LocalControlConsole$ProcessObserver.run(LocalControlConsole.java:284)
        at java.lang.Thread.run(Thread.java:748)
    ```

2. Scala example requires the `Callable` to extend `Serializable` whereas the Java examples do not.  Is the library wrapping the `Callable` in some way that might not work for Scala?

Here is a table of the different test cases for demonstration (you can use your IDE -OR- `mvn exec:exec` to execute and edit the `pom.xml` plugin with the class you want to run) 
Class | Description | Executes Remotely? | Proper shutdown?
------|-------------|-------------------|----------------
`JavaAppTest`  | Runs `Callable` within a Java `main` method | YES | NO
`ScalaAppTest1` | Runs `Callable` within within a Scala `App` | `java.io.NotSerializableException` | --
`ScalaAppTest2` | Runs `Callable with Serializable` within within a Scala `App` | YES | NO
`JUnitTest` | Runs `Callable` within within a Java/JUnit `@Test` | YES | YES

**NOTE:** If I add this line before `cloud.shutdown()` in `ExampleGrid`, all examples shutdown OK   
```java
((HostControlConsole)cloud.node(remoteHost).getPragma(ViConf.SPI_CONTROL_CONSOLE)).terminate();
``` 
