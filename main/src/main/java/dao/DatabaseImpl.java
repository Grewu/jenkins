package dao;

import annotation.Autowired;
import annotation.Component;
import util.ParametersHolder;

@Component
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
