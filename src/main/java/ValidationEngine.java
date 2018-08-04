import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.lang.reflect.Field;

public class ValidationEngine {
    public static void main(String... args) throws Exception {
        ValidationEngine engine = new ValidationEngine();
        AdvancedUserForm form = new AdvancedUserForm();
        form.password = "12345";
        form.name = "ekin";
        form.nationality = "FR";
        System.out.println(engine.validateForm(form));
    }

    public boolean validateForm(AdvancedUserForm form) throws Exception {
        boolean valid = true;
        for (Field field : form.getClass().getFields()) { // iterate over the fields of form
            valid = valid & validateField(field, form);
        }
        return valid;
    }

    public boolean validateField(Field field, AdvancedUserForm form) throws Exception {
        Validators validators = field.getAnnotation(Validators.class);
        boolean valid = true;
        if (validators != null) {
            for (Validator validator : validators.value()) {
                ScriptEngineManager manager = new ScriptEngineManager();
                ScriptEngine engine = manager.getEngineByName("nashorn");
                engine.put("field", field.get(form));
                engine.eval("var result=field" + validator.value() + ";"); // field..length>4
                valid = valid & (boolean) engine.get("result");
            }
        }
        return valid;
    }
}
