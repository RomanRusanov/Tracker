package ru.rrusanov;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.core.Is.is;

/**
 * Class .
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 08.02.17
 */
public class StubInputTest {
    /**
     * check method StubInput test.
     */
    @Test
    public void thenStubInputAskInvokeWithStringWhenReturnSameString() {
        final String expect = "Test String!";
        final Input stubInput = new StubInput(new String[]{"Test String!"});
        final String result = stubInput.ask("");

        Assert.assertThat(result, is(expect));
    }

}