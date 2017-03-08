package poker;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class CombineUtilTest {

    @Test
    public void combinations() throws Exception {
        List<List<String>> combinations =
            CombineUtil.combinations(Arrays.asList("a", "b", "c"), 2).collect(Collectors.toList());
        assertEquals(3, combinations.size());
        assertEquals(Arrays.asList("a", "b"), combinations.get(0));
        assertEquals(Arrays.asList("a", "c"), combinations.get(1));
        assertEquals(Arrays.asList("b", "c"), combinations.get(2));
    }
}