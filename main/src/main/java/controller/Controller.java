package controller;

import annotation.Autowired;
import annotation.Component;
import service.Service;

@Component
public class Controller {
    @Autowired
    private Service service;

    public void performAction() {
        System.out.println("Controller: " + service.execute());
    }
}
