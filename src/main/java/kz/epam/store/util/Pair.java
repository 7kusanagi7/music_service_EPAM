package kz.epam.store.util;

import java.util.Objects;

public class Pair<T, V>{
    private final T object1;
    private final V object2;

    public Pair(T object1, V object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(object1, pair.object1) && Objects.equals(object2, pair.object2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object1, object2);
    }
}
