import java.util.Map;

public interface FormServerMBean {

    void test();

    String generateForm(String className);

    Map<String, String> readData(String data);

    boolean validateForm(String data, String className) throws Exception;

}
