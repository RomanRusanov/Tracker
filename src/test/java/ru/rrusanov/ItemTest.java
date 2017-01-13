package ru.rrusanov;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import ru.rrusanov.models.Item;
/**
 * Class test all methods item class.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 10.01.17
 */
public class ItemTest {
    /**
     * Then in item Id created.
     * When Id not null link.
     **/
    @Test
    public  void thenItemCreateedWhenIdNotNull() {
        Item item = new Item();
        Assert.assertNotNull(item.getId());
    }
    /**
     * Then name = Ann.
     * When setName return Ann.
     **/
    @Test
    public void thenNameAnnWhenGetNameReturnAnn() {
        final String expect = "Ann";
        Item item = new Item();
        item.setName("Ann");
        final String result = item.getName();
        Assert.assertThat(result, is(expect));
    }
    /**
     * Then description = "First desc".
     * When return "First desc".
     **/
    @Test
    public void thenDescriptionSetWhenRetunIt() {
        final String expect = "First desc";
        Item item = new Item();
        item.setDescription("First desc");
        final String result = item.getDescription();
        Assert.assertThat(result, is(expect));
    }
    /**
     * Then date of create set = 100.
     * When return 100.
     **/
    @Test
    public void thenCreateSetWhenRetunIt() {
        final long expect = 100L;
        Item item = new Item();
        item.setCreate(100L);
        final long result = item.getCreate();
        Assert.assertThat(result, is(expect));
    }
    /**
     * Then comment = "First comment".
     * When return "First comment".
     **/
    @Test
    public void thenCommentSetWhenRetunIt() {
        final String expect = "First comment";
        Item item = new Item();
        item.setComment("First comment");
        final String result = item.getComment();
        Assert.assertThat(result, is(expect));
    }

}
