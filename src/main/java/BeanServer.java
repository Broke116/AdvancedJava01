import javax.management.*;
import java.lang.management.ManagementFactory;

public class BeanServer {
    public static void main(String... args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("com.amadeus.ist:type=FormServer"); // Mbean NAME
        mbs.registerMBean(new FormServer(), name);
        System.out.println("waiting forever");
        Thread.sleep(Long.MAX_VALUE); // this application must run forever.
    }
}
