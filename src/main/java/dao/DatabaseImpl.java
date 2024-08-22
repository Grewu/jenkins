package dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import util.ParametersHolder;

@Repository
public class DatabaseImpl implements Database {
    private final ParametersHolder parametersHolder;

    @Autowired
    public DatabaseImpl(ParametersHolder parametersHolder) {
        this.parametersHolder = parametersHolder;
    }

    @Override
    public String execute() {
        return parametersHolder.getSomeText();
    }
}
