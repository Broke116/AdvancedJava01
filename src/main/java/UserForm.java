@Form(name = "Kullanici Giris Formu")
public class UserForm {
    @Label("Ad")
    @Validator("!=null")
    public String name;
    @Label("Soyad")
    @Validator("!=null")
    public String surname;
    @Label("Kullanici Adi")
    @Validator("!=null")
    public String username;
    @Label("Uyruk")
    @Validator("=='TR'")
    @Validator("!=null")
    public String nationality;
}
