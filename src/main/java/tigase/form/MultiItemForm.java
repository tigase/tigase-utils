/*
 * MultiItemForm.java
 *
 * Tigase Jabber/XMPP Utils
 * Copyright (C) 2004-2017 "Tigase, Inc." <office@tigase.com>
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

import tigase.xml.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Wojciech Kapcia
 */
public class MultiItemForm
		extends Form {

	private final List<Fields> items = new ArrayList<>();
	private Fields reported = null;

	public static void main(String[] args) {

		MultiItemForm multiItemForm = new MultiItemForm("tytul");
		multiItemForm.setInstruction("bla");

		Fields f = new Fields();
		f.addField(Field.fieldTextSingle("var1", "val11", null));
		f.addField(Field.fieldTextSingle("var2", "val12", null));
		f.addField(Field.fieldTextSingle("var3", "val13", null));
		multiItemForm.addItem(f);
		f = new Fields();
		f.addField(Field.fieldTextSingle("var1", "val21", null));
		f.addField(Field.fieldTextSingle("var5", "val22", null));
		multiItemForm.addItem(f);
		f = new Fields();
		f.addField(Field.fieldTextSingle("var4", "val31", null));
		f.addField(Field.fieldTextSingle("var5", "val32", null));
		multiItemForm.addItem(f);

		System.out.println(multiItemForm.getElement().toStringPretty());

		multiItemForm = new MultiItemForm("tytul");

		System.out.println(multiItemForm.getElement());

	}

	public MultiItemForm() {
		super("result", null, null);
	}

	public MultiItemForm(String title) {
		super("result", title, null);
	}

	public MultiItemForm(Element form) {
		super(form);
	}

	public MultiItemForm(Form form) {
		super("result", null, null);
		setReported(form.getAllFields());
	}

	@Override
	public Element getElement() {
		if (reported == null || items == null) {
			return null;
		}
		Element form = super.getElement();
		Element report = new Element("reported");
		for (Field field : reported.getAllFields()) {
			report.addChild(field.getElement());
		}
		form.addChild(report);
		for (Fields fieldsItems : this.items) {
			Element item = new Element("item");
			for (Field field : fieldsItems.getAllFields()) {
				item.addChild(field.getElement(false, false));
			}
			if (item.getChildren() != null && !item.getChildren().isEmpty()) {
				form.addChild(item);
			}
		}

		return form;
	}

	public void addItem(Fields i) {
		if (reported == null) {
			reported = new Fields();
			for (Field field : i.getAllFields()) {
				reported.addField(field.cloneShalow());
			}
			if (log.isLoggable(Level.WARNING)) {
				log.log(Level.WARNING, "Initialised MultiItemForm with first item vars: {0}", reported.getAllFields());
			}
		}

		Iterator<Field> iterator = i.getAllFields().iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (!reported.is(field.getVar())) {
				if (log.isLoggable(Level.FINEST)) {
					log.log(Level.FINEST, "variable {0} of added {1} does not match reported fields: {2} - removing!",
							new Object[]{field.getVar(), i.getAllFields(), reported.getAllFields()});
				}
				iterator.remove();
			}
		}
		Collections.sort(i.getAllFields());

		if (i.getAllFields().size() > 0) {
			for (Field repField : reported.getAllFields()) {
				if (i.get(repField.getVar()) == null) {
					i.addField(Field.fieldTextSingle(repField.getVar(), null, null));
				}
			}
		}

		items.add(i);
	}

	public List<Fields> getAllItems() {
		return items;
	}

	public void setReported(List<Field> fields) {
		if (reported == null) {
			reported = new Fields();
		}
		for (Field field : fields) {
			if (field.getType().equals(Field.FieldType.hidden) || field.getType().equals(Field.FieldType.fixed)) {
				continue;
			}
			reported.addField(field.cloneShalow());
		}
	}
}
