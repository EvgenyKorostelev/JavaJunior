import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws InvocationTargetException,
            InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<?> requiredClass = Class.forName("java.lang.String");
        printMethods(createObject(requiredClass));
    }

    //печать методов объекта класса
    public static <T> void printMethods(T object) {
        Class<?> objectClass = object.getClass();
        Method[] methodsObject = objectClass.getMethods();
        for (Method method : methodsObject) {
            System.out.println(method.getName() + " (параметров: " + method.getParameterCount() + ")");
        }
    }

    // создание объекта искомого класса
    public static Object createObject(Class<?> currentClass) throws
            InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor[] constructors = currentClass.getConstructors();
        Constructor defaultConstructor = null;
        for (int i = 0; i < constructors.length; i++) {
            if (constructors[i].getParameterCount() == 0) {
                defaultConstructor = constructors[i];
            }
        }
        return defaultConstructor.newInstance(null);
    }
}
