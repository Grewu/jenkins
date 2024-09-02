import config.AppConfig;
import controller.EmployeeController;
import model.dto.request.EmployeeRequest;
import model.entity.enums.Position;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Application {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        testEmployeeController(context);
        context.close();
        System.out.println("Context is closed");
    }

    private static void testEmployeeController(AnnotationConfigApplicationContext context) throws ExecutionException, InterruptedException {
        var employeeController = context.getBean(EmployeeController.class);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<String> getAllEmployeesTask = employeeController::getAll;

        Callable<String> createEmployeeTask = () -> employeeController.create(
                new EmployeeRequest(
                        10L,
                        "Pavel",
                        "Pavlovich",
                        Position.DEVELOPER,
                        1L,
                        "email",
                        "password"));

        Callable<String> getEmployeeByIdTask = () -> employeeController.getById(10L);

        Callable<String> updateEmployeeTask = () -> employeeController.update(
                new EmployeeRequest(
                        10L,
                        "Pavel",
                        "Pavlovich",
                        Position.MANAGER,
                        2L,
                        "new_new_email",
                        "new_password"));

        Callable<String> deleteEmployee = () -> String.valueOf(employeeController.delete(10L));


        System.out.println("GET ALL" + executor.submit(getAllEmployeesTask).get());
        System.out.println("CREATE " + executor.submit(createEmployeeTask).get());
        System.out.println("GET BY ID" + executor.submit(getEmployeeByIdTask).get());
        System.out.println("UPDATE " + executor.submit(updateEmployeeTask).get());
        System.out.println("DELETE " + executor.submit(deleteEmployee).get());

        executor.shutdown();
    }
}
