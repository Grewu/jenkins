package util;

import annotation.Component;
import annotation.Value;

@Component
public class ParametersHolder {

    @Value("my.param.db")
    private String someText;

    public String getSomeText() {
        return someText;
    }
}
