package util;

import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class Util {

    public static <T> T getRandomElementFromCollection(Collection<T> collection) {
        if (collection.isEmpty()) {
            throw new RuntimeException("Empty collection");
        }
        return (T) collection.toArray()[new Random().nextInt(collection.size())];
    }


}
