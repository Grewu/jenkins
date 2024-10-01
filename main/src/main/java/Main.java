import context.ApplicationContext;
import controller.Controller;
import factory.ApplicationFactory;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = ApplicationFactory.createContext("service", "controller", "dao","util");
        var controller = context.getBean(Controller.class);
        controller.performAction();
    }
}
