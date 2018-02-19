import org.gridkit.nanocloud.*;
import org.gridkit.nanocloud.telecontrol.HostControlConsole;
import org.gridkit.vicluster.ViConf;

import java.lang.management.ManagementFactory;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Created by Aaron Hiniker (ahiniker@atomtickets.com)
 * 2/18/18
 * Copyright (c) Atom Tickets, LLC
 */
public class ExampleGrid {

    private Cloud cloud = null;

    private String remoteHost = "10.50.3.132";
    private Optional<String> remoteJava = Optional.of("/usr/java/latest/bin/java");
    private Optional<String> remoteUser = Optional.of("ec2-user");
    private Optional<String> privateKey = Optional.of("~/.ssh/clouderaProdKeyPair.pem");

    public <T> void execute(final Callable<T> callable) throws Exception {

        cloud = CloudFactory.createCloud();
        RemoteNode.at(cloud.node("**")).setRemoteNodeType();

        remoteJava.ifPresent(java -> RemoteNode.at(cloud.node("**")).setRemoteJavaExec(java));
        remoteUser.ifPresent(user -> RemoteNode.at(cloud.node("**")).setRemoteAccount(user));
        privateKey.ifPresent(key -> RemoteNode.at(cloud.node("**")).setSshPrivateKey(key));

        cloud.node(remoteHost);

        RemoteNode.at(cloud.node(remoteHost)).setRemoteHost(remoteHost);

        cloud.node("**").touch();

        // Say hello world
        cloud.node("**").exec(callable);

        Thread.sleep(300);

        // Un-commenting the following allows this example to work in all scenarios
        //((HostControlConsole)cloud.node(remoteHost).getPragma(ViConf.SPI_CONTROL_CONSOLE)).terminate();
        
        cloud.shutdown();
    }

}
