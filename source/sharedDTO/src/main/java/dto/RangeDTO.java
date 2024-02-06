package dto;

import java.io.Serializable;

public record RangeDTO(Integer start, Integer end) implements Serializable {
    @Override
    public String toString() {
        return "[" + start + ", " + end + "]";
    }
}