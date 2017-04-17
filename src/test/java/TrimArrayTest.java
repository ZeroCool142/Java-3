import org.junit.Assert;
import org.junit.Test;
import ru.geekbrains.java3.l6.ArrayUtils;

public class TrimArrayTest {


    @Test(expected = RuntimeException.class)
    public void find4() {
        ArrayUtils.trimArray(new int[]{1, 2, 3});
    }

    @Test(expected = RuntimeException.class)
    public void emptyArray() {
        ArrayUtils.trimArray(new int[]{});
    }

    @Test
    public void arrTest1() {
        Assert.assertArrayEquals(ArrayUtils.trimArray(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}), new int[]{1, 7});
    }

    @Test
    public void arrTest2() {
        Assert.assertArrayEquals(ArrayUtils.trimArray(new int[]{4}), new int[]{});
    }

}
