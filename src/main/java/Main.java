import config.AppConfig;
import controller.Controller;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        var controller = context.getBean(Controller.class);
        controller.performAction();
    }
}
