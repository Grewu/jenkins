package service;

import annotation.Autowired;
import annotation.Component;
import dao.Database;

@Component
public class ServiceImpl implements Service {
    private Database database;

    @Autowired
    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public String execute() {
        return database.execute();
    }
}
