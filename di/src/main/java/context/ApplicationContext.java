package context;


import annotation.Autowired;
import annotation.Component;
import annotation.Value;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private final Map<Class<?>, Object> beans = new HashMap<>();

    public ApplicationContext(String packageName) throws Exception {
        initContext(packageName);
    }

    private void initContext(String packageName) throws Exception {
        var reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(Scanners.TypesAnnotated)
                .filterInputsBy(new FilterBuilder().includePackage(packageName)));

        var components = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> component : components) {
            beans.put(component, createBean(component));
        }

        for (Object bean : beans.values()) {
            injectMethods(bean);
            injectionFields(bean);
        }
    }

    private void injectMethods(Object bean) throws IllegalAccessException, InvocationTargetException {
        var methods = bean.getClass().getDeclaredMethods();
        for (var method : methods) {
            if (method.isAnnotationPresent(Autowired.class) && method.getParameterCount() == 1) {
                var dependency = beans.get(method.getParameterTypes()[0]);
                if (dependency != null) {
                    method.setAccessible(true);
                    method.invoke(bean, dependency);
                }
            }
        }
    }

    private void injectionFields(Object bean) throws IllegalAccessException {
        var fields = bean.getClass().getDeclaredFields();
        for (var field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                var dependency = beans.get(field.getType());
                if (dependency != null) {
                    field.set(bean, dependency);
                }
            }

            if (field.isAnnotationPresent(Value.class)) {
                field.setAccessible(true);
                var value = field.getAnnotation(Value.class).value();
                field.set(bean, value);
            }
        }
    }

    private Object createBean(Class<?> clazz) throws Exception {
        var constructors = clazz.getConstructors();
        for (var constructor : constructors) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                var parameterTypes = constructor.getParameterTypes();
                var dependencies = new Object[parameterTypes.length];

                for (int i = 0; i < parameterTypes.length; i++) {
                    dependencies[i] = beans.get(parameterTypes[i]);
                }

                constructor.setAccessible(true);
                return constructor.newInstance(dependencies);
            }
        }

        return clazz.getDeclaredConstructor().newInstance();
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beans.get(clazz));
    }
}
