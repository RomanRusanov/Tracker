package ru.rrusanov;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.core.Is.is;

/**
 * Class test StubInput.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 03.05.17
 */
public class StubInputTest {
    /**
     * check method StubInput test.
     */
    @Test
    public void thenStubInputAskInvokeWithStringWhenReturnSameString() {
        final String[] expect = {"Test String!", "1", "2", "3"};
        final Input stubInput = new StubInput(new String[]{"Test String!", "1", "2", "3"});
        for (int i = 0; i < expect.length; i++) {
            final String result = stubInput.ask("");
            String expectFor = expect[i];
            Assert.assertThat(result, is(expectFor));
        }
    }
}