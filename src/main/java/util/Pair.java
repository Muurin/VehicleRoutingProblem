package util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Pair<T> {

    private T left;
    private T right;

    @Override
    public String toString(){
        return "{" + left.toString() + ", " +right.toString() + "}";
    }

}
