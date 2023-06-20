import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Main {

    public static void main(String[] args) {
        PasswordEncoder encoder= new BCryptPasswordEncoder();
        String password = encoder.encode("b");
        System.out.println(password);


    }

}
