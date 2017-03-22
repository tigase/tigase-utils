package tigase.util.ui.console;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandlineParameterTest {

	@Test
	public void testEqual() {
		final CommandlineParameter l10 = new CommandlineParameter.Builder("l", "letter").build();
		final CommandlineParameter l11 = new CommandlineParameter.Builder("L", "letter").build();
		final CommandlineParameter l12 = new CommandlineParameter.Builder("L", "Letter").build();
		final CommandlineParameter l20 = new CommandlineParameter.Builder(null, "letter").build();
		final CommandlineParameter l30 = new CommandlineParameter.Builder("l", null).build();

		Assert.assertEquals(l10,l20);
		Assert.assertEquals(l10,l11);
		Assert.assertEquals(l10,l30);
		Assert.assertNotSame(l20,l30);
	}
}