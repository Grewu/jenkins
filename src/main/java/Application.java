import config.AppConfig;
import controller.CommentController;
import controller.DepartmentController;
import controller.EmployeeController;
import controller.ProjectController;
import controller.TaskController;
import model.dto.request.CommentRequest;
import model.dto.request.DepartmentRequest;
import model.dto.request.EmployeeRequest;
import model.dto.request.ProjectRequest;
import model.dto.request.TaskRequest;
import model.entity.enums.Position;
import model.entity.enums.Status;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.time.LocalDateTime;

public class Application {
    public static void main(String[] args) throws IOException {

        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        testCommentController(context);

        testDepartmentController(context);

        testEmployeeController(context);

        testTaskController(context);

        testProjectController(context);
    }

    private static void testCommentController(AnnotationConfigApplicationContext context) throws IOException {
        var commentController = context.getBean(CommentController.class);

        System.out.println("=====Controller====");
        System.out.println("CREATE: " + commentController.create(new CommentRequest(10L, 10L, 2L, "Initial api.",
                LocalDateTime.of(2025, 1, 15, 10, 0)))
        );
        System.out.println("CREATE: " + commentController.create(new CommentRequest(11L, 10L, 2L, "Create db.",
                LocalDateTime.of(2025, 1, 15, 10, 0)))
        );

        var comment = commentController.getById(10L);
        System.out.println("GET BY ID: " + comment);

        commentController.delete(11L);
        System.out.println("DELETE WITH ID 11L ");

        var updateComment = commentController.update(
                new CommentRequest(10L, 1L, 2L, "Create new API",
                        LocalDateTime.of(2024, 1, 15, 10, 0)));
        System.out.println("UPDATE  " + updateComment);

        System.out.println("GET BY ID: " + commentController.getById(10L));
        System.out.println("==============");
    }

    private static void testDepartmentController(AnnotationConfigApplicationContext context) throws IOException {
        var departmentController = context.getBean(DepartmentController.class);

        System.out.println("=====Department====");
        System.out.println("CREATE: " + departmentController.create(new DepartmentRequest(10L, "Developer")
        ));
        System.out.println("CREATE: " + departmentController.create(new DepartmentRequest(11L, "Java")
        ));

        var department = departmentController.getById(10L);
        System.out.println("GET BY ID: " + department);

        departmentController.delete(11L);
        System.out.println("DELETE WITH ID 11L ");

        var updateDepartment = departmentController.update(
                new DepartmentRequest(10L, "Marketing"));
        System.out.println("UPDATE  " + updateDepartment);
        System.out.println("GET BY ID: " + departmentController.getById(10L));

        System.out.println("==============");
    }

    private static void testEmployeeController(AnnotationConfigApplicationContext context) throws IOException {
        var employeeController = context.getBean(EmployeeController.class);
        System.out.println("=====Employee====");

        System.out.println("CREATE  " + employeeController.create(
                new EmployeeRequest(10L, "Pavel", "Pavlovich", Position.DEVELOPER, 1L,
                        "email", "password")));
        System.out.println("CREATE  " + employeeController.create(new EmployeeRequest(11L, "Sasha",
                "Sashovich",
                Position.MANAGER, 2L,
                "email", "password")));

        var employee = employeeController.getById(10L);
        System.out.println("GET BY ID: " + employee);

        employeeController.delete(11L);
        System.out.println("DELETE WITH ID 11L ");
        var updateEmployee = employeeController.update(
                new EmployeeRequest(10L, "Pavel", "Pavlovich", Position.MANAGER, 2L,
                        "new_email", "new_password"));
        System.out.println("UPDATE  " + updateEmployee);

        System.out.println("GET BY ID: " + employeeController.getById(10L));

        System.out.println("==============");
    }

    private static void testTaskController(AnnotationConfigApplicationContext context) throws IOException {
        System.out.println("=====Task====");
        var taskController = context.getBean(TaskController.class);

        var createTask1 = taskController.create(
                new TaskRequest(10L,
                        "Task1", 101L,
                        1001L, LocalDateTime.now().plusDays(10),
                        Status.COMPLETED, 10L));

        System.out.println("CREATE  " + createTask1);

        var createTask2 = taskController.create(new TaskRequest(11L,
                "Task2", 102L, 1002L,
                LocalDateTime.now().plusDays(15), Status.IN_PROGRESS, 1L));
        System.out.println("CREATE  " + createTask2);

        var task = taskController.getById(10L);
        System.out.println("GET BY ID: " + task);

        // Удаление записи
        taskController.delete(11L);
        System.out.println("DELETE WITH ID 11L ");

        // Обновление записи
        var updateTask = taskController.update(
                new TaskRequest(10L, "UpdatedTask",
                        101L, 1001L,
                        LocalDateTime.now().plusDays(20),
                        Status.NOT_STARTED, 0L));
        System.out.println("UPDATE  " + updateTask);

        // Повторное получение обновленной записи
        System.out.println("GET BY ID: " + taskController.getById(10L));

        System.out.println("==============");
    }

    private static void testProjectController(AnnotationConfigApplicationContext context) throws IOException {
        var projectController = context.getBean(ProjectController.class);

        System.out.println("=====Project====");

        var createProject1 = projectController.create(
                new ProjectRequest(10L, "Project1",
                        "Description1", LocalDateTime.now(), LocalDateTime.now().plusMonths(6)));
        System.out.println("CREATE  " + createProject1);

        var createProject2 = projectController.create(
                new ProjectRequest(11L, "Project2",
                        "Description2", LocalDateTime.now().plusMonths(1),
                        LocalDateTime.now().plusMonths(7)));
        System.out.println("CREATE  " + createProject2);

        var project = projectController.getById(10L);
        System.out.println("GET BY ID: " + project);

        projectController.delete(11L);
        System.out.println("DELETE WITH ID 2L ");

        var updateProject = projectController.update(
                new ProjectRequest(10L, "UpdatedProject",
                        "UpdatedDescription", LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusMonths(8)));
        System.out.println("UPDATE  " + updateProject);

        System.out.println("GET BY ID: " + projectController.getById(10L));

        System.out.println("==============");
    }
}
