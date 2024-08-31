import config.AppConfig;
import controller.EmployeeController;
import model.dto.request.EmployeeRequest;
import model.entity.enums.Position;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.time.LocalDateTime;

public class Application {
    public static void main(String[] args) throws IOException {

        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        testEmployeeController(context);
    }

    private static void testEmployeeController(AnnotationConfigApplicationContext context) throws IOException {
        var employeeController = context.getBean(EmployeeController.class);
        System.out.println("=====Employee====");
        String all = employeeController.getAll();
        System.out.println(all);
        System.out.println("CREATE  " + employeeController.create(
                new EmployeeRequest(10L, "Pavel", "Pavlovich", Position.DEVELOPER, 1L,
                        "email", "password")));


        var employee = employeeController.getById(10L);
        System.out.println("GET BY ID: " + employee);

        var updateEmployee = employeeController.update(
                new EmployeeRequest(10L, "Pavel", "Pavlovich", Position.MANAGER, 2L,
                        "new_new_email", "new_password"));
        System.out.println("UPDATE  " + updateEmployee);
        boolean delete = employeeController.delete(10L);
        System.out.println("DELETE WITH ID 10L " + delete);
        System.out.println(employeeController.getAllEmployeesWithComments());

        LocalDateTime startDate = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 3, 1, 9, 0, 0);


        System.out.println(employeeController.getEmployeesCommentsByDateRange(startDate, endDate));
        System.out.println(employeeController.getEmployeesCommentsByDepartmentId(4L));
        System.out.println("==============");
    }

}
