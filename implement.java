import java.security.KeyPair;

public class implement {
    public static void main(String args[]) {
        BiMap<Integer, Integer, Integer> map = new BiMap();
        map.put(1, 1, 11);
        map.put(2, 2, 22);
        map.put(3, 3, 33);
        System.out.println(map.get(1, 1));
        map.remove(1, 1);
        System.out.println(map.get(1, 1));
        // using var
        for (var k : map.keySet()) {
            System.out.println("Keys: " + k.key1 + " and " + k.key2 + " with value : " + map.get(k.key1, k.key2));
        }
        // without using var
        for (BiMap<Integer, Integer, Integer>.KeyPair key : map.keySet()) {
            System.out
                    .println("Keys: " + key.key1 + " and " + key.key2 + " with value : " + map.get(key.key1, key.key2));
        }
    }
}
// The var keyword in Java is a type inference feature introduced in Java 10 as
// part of the local variable type inference (JEP 286). It allows you to declare
// local variables without explicitly specifying their types, letting the
// compiler infer the type based on the assigned value.
