package context;


import annotation.Autowired;
import annotation.Component;
import annotation.Value;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import util.PropertiesUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private final Map<Class<?>, Object> beans = new HashMap<>();

    public ApplicationContext(String... packageNames) throws Exception {
        initContext(packageNames);
    }

    private void initContext(String... packageNames) throws Exception {
        var reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(packageNames)
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(Scanners.TypesAnnotated));

        var components = reflections.getTypesAnnotatedWith(Component.class);
        for (var component : components) {
            Object bean = createBean(component);
            beans.put(component, bean);

            for (var interfaces : component.getInterfaces()) {
                beans.put(interfaces, bean);
            }
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
                String propertiesValue = PropertiesUtil.get(value);
                field.set(bean, propertiesValue);
            }
        }
    }

    private Object createBean(Class<?> clazz) throws Exception {
        var constructors = clazz.getDeclaredConstructors();
        for (var constructor : constructors) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                var parameterTypes = constructor.getParameterTypes();
                var dependencies = new Object[parameterTypes.length];

                for (int i = 0; i < parameterTypes.length; i++) {
                    Object dependency = beans.get(parameterTypes[i]);
                    dependencies[i] = dependency;
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

