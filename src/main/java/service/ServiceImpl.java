package service;

import dao.Database;
import org.springframework.beans.factory.annotation.Autowired;
@org.springframework.stereotype.Service
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
