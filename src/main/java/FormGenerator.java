import java.lang.reflect.Field;

public class FormGenerator {

    public static void main(String... args) {
        FormGenerator generator = new FormGenerator();
        System.out.println(generator.generateForm("AdvancedUserForm"));
    }

    public String generateForm(String className) {
        // normally we must use string buffers
        String generatedHtml = "";
        // first I need a class
        Class classData = null;
        try {
            classData = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        Form form = (Form) classData.getAnnotation(Form.class);
        String label = form == null ? "Yet another form" : form.name();
        generatedHtml += "<form><fieldset><legend>" + label + "</legend>";
        Field[] fields = classData.getFields();
        for (Field field : fields) {
            generatedHtml += generateField(field);
        }
        return generatedHtml + "</fieldset></form>";
    }

    public String generateField(Field field) {
        Label label = field.getAnnotation(Label.class);
        String inputType = field.getAnnotation(Password.class) == null ? "text" : "password"; // checking whether that field has this annotation or not.
        String labelData = label == null ? field.getName() : label.value();
        return "<label for=\"" + field.getName() + "\">" + labelData + "</label><input type = \"" + inputType + "\" id = \"" + field.getName() + "\" name = \"" + field.getName() + "\"/>";
    }
}
