package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CombineUtil {

    /**
     * Given a list of elements E, generate a stream of all combinations of these elements of length size.
     *
     * @param list the list to generate combinations of
     * @param size the number of elements in this combination
     * @return the combinations as a stream
     */
    public static <E> Stream<List<E>> combinations(List<E> list, int size) {
        if (size == 0) {
            return Stream.of(Collections.emptyList());
        }
        return IntStream.range(0, list.size()).boxed().flatMap(
            index -> combinations(list.subList(index + 1, list.size()), size - 1).map(tail -> join(list.get(index), tail)));
    }

    /**
     * Given a list head and a list tail, create a new list.
     */
    private static <E> List<E> join(E head, List<E> tail) {
        List<E> newList = new ArrayList<>(tail);
        newList.add(0, head);
        return newList;
    }
}
