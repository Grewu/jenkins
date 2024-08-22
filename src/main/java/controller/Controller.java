package controller;


import org.springframework.beans.factory.annotation.Autowired;
import service.Service;

@org.springframework.stereotype.Controller
public class Controller {
    private final Service service;

    @Autowired
    public Controller(Service service) {
        this.service = service;
    }

    public void performAction() {
        System.out.println("Controller: " + service.execute());
    }
}
