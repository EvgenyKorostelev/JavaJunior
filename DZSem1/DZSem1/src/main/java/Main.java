import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        System.out.println("Среднее значение всех четных чисел в списке: " +
                new ArrayList<>(Collections.singleton(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(num -> num % 2 == 0).mapToDouble(Integer::doubleValue).average()))
                .getFirst().getAsDouble());
    }
}
