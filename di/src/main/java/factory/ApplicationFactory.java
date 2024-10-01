package factory;

import context.ApplicationContext;

public class ApplicationFactory {
    public static ApplicationContext createContext(String... packageNames) {
        try {
            return new ApplicationContext(packageNames);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create ApplicationContext", e);
        }
    }
}
