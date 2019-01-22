/**
 * Tigase Utils - Utilities module
 * Copyright (C) 2004 Tigase, Inc. (office@tigase.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */
package tigase.form;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import tigase.xml.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wojtek
 */
public class MultiItemFormTest {

	public MultiItemFormTest() {
	}

	@Before
	public void setUp() {
	}

	@Test
	public void testFormInitialisedWithFirstItem() {

		final String title = "tytul";
		final String instructions = "instruction";

		ArrayList<String> expValues = new ArrayList<>();

		// MultiItemForm report header is initialised with items from first field!
		// Only one var in this MIM
		MultiItemForm multiItemForm = new MultiItemForm(title);
		multiItemForm.setInstruction(instructions);

		Fields f = new Fields();
		f.addField(Field.fieldTextSingle("var1", "val11", null));
		expValues.add("val11");
		multiItemForm.addItem(f);
		f = new Fields();
		f.addField(Field.fieldTextSingle("var1", "val21", null));
		expValues.add("val21");
		f.addField(Field.fieldTextSingle("var5", "val22", null));
		multiItemForm.addItem(f);
		f = new Fields();
		f.addField(Field.fieldTextSingle("var4", "val34", null));
		f.addField(Field.fieldTextSingle("var5", "val35", null));
		multiItemForm.addItem(f);
		f = new Fields();
		f.addField(Field.fieldTextSingle("var1", "val41", null));
		expValues.add("val41");
		f.addField(Field.fieldTextSingle("var2", "val42", null));
		f.addField(Field.fieldTextSingle("var3", "val43", null));
		multiItemForm.addItem(f);

//		System.out.println( multiItemForm.getElement().toStringPretty() );
		Assert.assertNotNull(multiItemForm.getElement().findChildStaticStr(new String[]{"x", "title"}));
		Assert.assertEquals(title,
							multiItemForm.getElement().findChildStaticStr(new String[]{"x", "title"}).getCData());

		Assert.assertNotNull(multiItemForm.getElement().findChildStaticStr(new String[]{"x", "instructions"}));
		Assert.assertEquals(instructions, multiItemForm.getElement()
				.findChildStaticStr(new String[]{"x", "instructions"})
				.getCData());

		Assert.assertNotNull(multiItemForm.getElement().findChildStaticStr(new String[]{"x", "reported"}));
		Assert.assertEquals(1, multiItemForm.getElement()
				.findChildStaticStr(new String[]{"x", "reported"})
				.getChildren()
				.size());

		Assert.assertNotNull(multiItemForm.getElement().findChildren((Element c3) -> c3.getName() == "item"));
		Assert.assertEquals(3, multiItemForm.getElement().findChildren((Element c3) -> c3.getName() == "item").size());

		ArrayList<String> vals = new ArrayList<>();
		for (Element items : multiItemForm.getElement().findChildren((Element c3) -> c3.getName() == "item")) {
			List<Element> fields = items.findChildren((Element c3) -> c3.getName() == "field");
			for (Element field : fields) {
				if (field.getChild("value") != null && field.getChild("value").getCData() != null) {
					vals.add(field.getChild("value").getCData());
				}
			}
		}
		Assert.assertEquals(expValues, vals);

	}

	@Test
	public void testFormWithExplicitReported() {

		final String title = "tytul";
		final String instructions = "instruction";

		ArrayList<String> expValues = new ArrayList<>();

		// MultiItemForm report header is initialised with items from first field!
		// Only one var in this MIM
		MultiItemForm multiItemForm = new MultiItemForm(title);
		multiItemForm.setInstruction(instructions);

		Fields reported = new Fields();
		reported.addField(Field.fieldTextSingle("var1", null, "variable1 label"));
		reported.addField(Field.fieldTextSingle("var2", null, "variable2 label"));
		reported.addField(Field.fieldTextSingle("var3", null, "variable3 label"));
		reported.addField(Field.fieldTextSingle("var4", null, "variable4 label"));
		reported.addField(Field.fieldTextSingle("var5", null, "variable5 label"));
		multiItemForm.setReported(reported.getAllFields());

		Fields f = new Fields();
		f.addField(Field.fieldTextSingle("var1", "val11", null));
		expValues.add("val11");
		multiItemForm.addItem(f);
		f = new Fields();
		f.addField(Field.fieldTextSingle("var1", "val21", null));
		expValues.add("val21");
		f.addField(Field.fieldTextSingle("var5", "val25", null));
		expValues.add("val25");
		multiItemForm.addItem(f);
		f = new Fields();
		f.addField(Field.fieldTextSingle("var4", "val34", null));
		expValues.add("val34");
		f.addField(Field.fieldTextSingle("var5", "val35", null));
		expValues.add("val35");
		multiItemForm.addItem(f);
		f = new Fields();
		f.addField(Field.fieldTextSingle("var1", "val41", null));
		expValues.add("val41");
		f.addField(Field.fieldTextSingle("var2", "val42", null));
		expValues.add("val42");
		f.addField(Field.fieldTextSingle("var3", "val43", null));
		expValues.add("val43");
		multiItemForm.addItem(f);

//		System.out.println( multiItemForm.getElement().toStringPretty() );

		Assert.assertNotNull(multiItemForm.getElement().findChildStaticStr(new String[]{"x", "title"}));
		Assert.assertEquals(title,
							multiItemForm.getElement().findChildStaticStr(new String[]{"x", "title"}).getCData());

		Assert.assertNotNull(multiItemForm.getElement().findChildStaticStr(new String[]{"x", "instructions"}));
		Assert.assertEquals(instructions, multiItemForm.getElement()
				.findChildStaticStr(new String[]{"x", "instructions"})
				.getCData());

		Assert.assertNotNull(multiItemForm.getElement().findChildStaticStr(new String[]{"x", "reported"}));
		Assert.assertEquals(reported.getAllFields().size(), multiItemForm.getElement()
				.findChildStaticStr(new String[]{"x", "reported"})
				.getChildren()
				.size());

		Assert.assertNotNull(multiItemForm.getElement().findChildren((Element c3) -> c3.getName() == "item"));
		Assert.assertEquals(4, multiItemForm.getElement().findChildren((Element c3) -> c3.getName() == "item").size());

		ArrayList<String> vals = new ArrayList<>();
		for (Element items : multiItemForm.getElement().findChildren((Element c3) -> c3.getName() == "item")) {
			List<Element> fields = items.findChildren((Element c3) -> c3.getName() == "field");
			for (Element field : fields) {
				if (field.getChild("value") != null && field.getChild("value").getCData() != null) {
					vals.add(field.getChild("value").getCData());
				}
			}
		}
		Assert.assertEquals(expValues, vals);

	}

}
