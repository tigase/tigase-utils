/*
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

import org.junit.Test;
import tigase.xml.Element;

import static org.junit.Assert.assertEquals;

public class FieldTest {

	/*
	<field var='field-name'
		 type='{field-type}'
		 label='description'>
		<desc/>
		<required/>
		<value>field-value</value>
		<option label='option1-label'>
			<value>option1-value</value>
		</option>
		<option label='option2-label'>
			<value>option2-value</value>
			</option>
	</field>
	 */

	@Test
	public void testConstructor() {
		final Element fieldElement = new Element("field", new String[]{"var", "type", "label"},
												 new String[]{"field-name", String.valueOf(Field.FieldType.text_single),
															  "description"});
		fieldElement.addChild(new Element("desc"));
		fieldElement.addChild(new Element("required"));

		final Element option1 = new Element("option", new String[]{"label"}, new String[]{"option1-label"});
		option1.addChild(new Element("value", "option1-value"));
		fieldElement.addChild(option1);

		final Element option2 = new Element("option", "option2-value", new String[]{"label"},
											new String[]{"option2-label"});
		option2.addChild(new Element("value", "option2-value"));
		fieldElement.addChild(option2);

		final Field field = new Field(fieldElement);
		assertEquals(2, field.getOptionLabels().length);
		assertEquals(2, field.getOptionValues().length);
	}
}