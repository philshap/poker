package poker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CardTest {

    @Parameterized.Parameters(name = "{index}: card 1 = {0}, card 2 = {1}, compare = {2}")
    public static Object[][] parameters() throws Exception {
        return new Object[][]{
            {new Card("AD"), new Card("KD"), -1},
            {new Card("KD"), new Card("AD"), 1},
            {new Card("AD"), new Card("AD"), 0},
            {new Card("AD"), new Card("2D"), -1}
        };
    }

    @Parameterized.Parameter
    public Card card1;

    @Parameterized.Parameter(1)
    public Card card2;

    @Parameterized.Parameter(2)
    public int expectedCompare;

    @Test
    public void testCompareTo() throws Exception {
        assertEquals(expectedCompare, (int) Math.signum(card1.compareTo(card2)));
    }
}