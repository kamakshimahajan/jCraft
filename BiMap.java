import java.util.*;

// BiMap class represents a generic bidirectional map
public class BiMap<K1, K2, V> {

    // Node class represents key-value pair in the map
    private class Node {
        K1 key1;
        K2 key2;
        V value;

        // Constructor to initialize Node with key1, key2, and value
        public Node(K1 key1, K2 key2, V value) {
            this.key1 = key1;
            this.key2 = key2;
            this.value = value;
        }
    }

    // Initial size of the array of buckets
    private int N = 4;
    // Current number of elements in the map
    private int n = 0;
    // Array of linked lists (buckets) to store key-value pairs
    private LinkedList<Node> buckets[];

    // Suppressing unchecked warnings for generic array creation
    @SuppressWarnings("unchecked")
    // Constructor to initialize the BiMap
    public BiMap() {
        this.N = 4;
        this.buckets = new LinkedList[N];
        for (int i = 0; i < buckets.length; i++) {
            this.buckets[i] = new LinkedList<>();
        }
    }

    // Search for a key in a specific bucket of the map
    private int searchInLL(K1 key1, K2 key2, int bi) {
        LinkedList<Node> ll = buckets[bi];
        for (int i = 0; i < ll.size(); i++) {
            if (ll.get(i).key1 == key1 && ll.get(i).key2 == key2) {
                return i; // Return the index if found
            }
        }
        return -1; // Return -1 if not found
    }

    // Hash function to determine the bucket index for a given key pair
    private int hashFunction(K1 key1, K2 key2) {
        int bi1 = Math.abs(key1.hashCode());
        int bi2 = Math.abs(key2.hashCode());
        return (bi1 + bi2) % N; // Combine hash codes and take modulo to get bucket index
    }

    // Rehash the map when load factor exceeds 2.0
    private void reHash() {
        LinkedList<Node> old[] = buckets;
        buckets = new LinkedList[N * 2];
        for (int i = 0; i < N * 2; i++) {
            buckets[i] = new LinkedList<>();
        }
        for (int i = 0; i < N; i++) {
            LinkedList<Node> ll = buckets[i];
            for (int j = 0; j < ll.size(); j++) {
                Node node = ll.get(j);
                put(node.key1, node.key2, node.value); // Reinsert elements into the new array
            }
        }
        N *= 2; // Update the size of the array
    }

    // Put a key-value pair into the map
    public void put(K1 key1, K2 key2, V value) {
        int bi = hashFunction(key1, key2); // Calculate bucket index
        int di = searchInLL(key1, key2, bi); // Search for key in the bucket
        if (di == -1) {
            buckets[bi].add(new Node(key1, key2, value)); // Add new key-value pair
            n++;
        } else {
            Node node = buckets[bi].get(di);
            node.value = value; // Update value if key already exists
        }
        double lambda = (double) n / N;
        if (lambda > 2.0) {
            reHash(); // Rehash if load factor exceeds 2.0
        }
    }

    // Get the value associated with the given key pair
    public V get(K1 key1, K2 key2) {
        int bi = hashFunction(key1, key2);
        int di = searchInLL(key1, key2, bi);
        if (di == -1) {
            return null; // Key pair not found
        } else {
            Node node = buckets[bi].get(di);
            return node.value; // Return the value associated with the key pair
        }
    }

    // Remove the key-value pair associated with the given key pair
    public V remove(K1 key1, K2 key2) {
        int bi = hashFunction(key1, key2);
        int di = searchInLL(key1, key2, bi);
        if (di == -1) {
            return null; // Key pair not found
        } else {
            Node node = buckets[bi].remove(di);
            n--;
            return node.value; // Return the removed value
        }
    }

    // Check if the map contains the given key pair
    public boolean containsKey(K1 key1, K2 key2) {
        int bi = hashFunction(key1, key2);
        int di = searchInLL(key1, key2, bi);
        return di != -1; // Return true if key pair found, otherwise false
    }

    // KeyPair class represents a pair of keys
    public class KeyPair {
        K1 key1;
        K2 key2;

        // Constructor to initialize KeyPair with key1 and key2
        public KeyPair(K1 key1, K2 key2) {
            this.key1 = key1;
            this.key2 = key2;
        }
    }

    // Get a list of all key pairs in the map
    public List<KeyPair> keySet() {
        List<KeyPair> keySet = new ArrayList<>();
        for (int i = 0; i < buckets.length; i++) {
            LinkedList<Node> ll = buckets[i];
            for (int j = 0; j < ll.size(); j++) {
                Node node = ll.get(j);
                KeyPair pair = new KeyPair(node.key1, node.key2);
                keySet.add(pair); // Add key pair to the list
            }
        }
        return keySet;
    }
}
