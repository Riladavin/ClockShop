package Pair;


import lombok.Getter;
import lombok.Setter;

public class Pair<T, U> {
    @Getter
    @Setter
    private T key;

    @Getter
    @Setter
    private U value;

    public Pair(T key, U value) {
        this.key = key;
        this.value = value;
    }
}
