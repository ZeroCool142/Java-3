import org.junit.Assert;
import org.junit.Test;
import ru.geekbrains.java3.l6.ArrayUtils;


public class CheckArrayTest {


    @Test(expected = RuntimeException.class)
    public void emptyArr() {
        ArrayUtils.checkArray(new int[]{});
    }

    @Test
    public void containOtherNumbers() {
        Assert.assertFalse(ArrayUtils.checkArray(new int[]{1, 2, 3}));
    }

    @Test
    public void onlyOne() {
        Assert.assertFalse(ArrayUtils.checkArray(new int[]{1, 1, 1}));
    }

    @Test
    public void onlyFour() {
        Assert.assertFalse(ArrayUtils.checkArray(new int[]{4, 4, 4}));
    }

    @Test
    public void goodArray() {
        Assert.assertTrue(ArrayUtils.checkArray(new int[]{1, 4, 1}));
    }

}
