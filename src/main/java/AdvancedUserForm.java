@Form(name = "Advanced User Form")
public class AdvancedUserForm extends UserForm {
    @Label("Sifre")
    @Password
    @Validator(".length>4")
    @Validator(".length<8")
    @Validator("!= null")
    public String password;
}
