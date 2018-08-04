import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FormServer implements FormServerMBean {
    @Override
    public void test() {
        System.out.println("test from bean");
    }

    @Override
    public String generateForm(String className) {
        FormGenerator generator = new FormGenerator();
        return generator.generateForm(className);
    }

    @Override
    public Map<String, String> readData(String data) {
        Map<String, String> result = new HashMap<String, String>();
        String[] values = data.split("\\|");
        for (String value : values) {
            String[] keyValue = value.split(":");
            result.put(keyValue[0], keyValue[1]);
        }
        return result;
    }

    @Override
    public boolean validateForm(String data, String className) throws Exception {
        Class cls = Class.forName(className);
        Object instance = cls.newInstance();
        Field[] fields = cls.getFields();
        Map<String, String> results = readData(data); // access time O(1)
        for (Field field : fields) {
            if (results.containsKey(field.getName())) {
                try {
                    field.set(instance, results.get(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        ValidationEngine engine = new ValidationEngine();
        return engine.validateForm((AdvancedUserForm) instance);
    }
}
