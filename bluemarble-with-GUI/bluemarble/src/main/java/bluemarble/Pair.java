package bluemarble;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair {
    private int x;
    private int y;

    Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}