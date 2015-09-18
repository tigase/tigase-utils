/*
 * Field.java
 *
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2012 "Artur Hefczyc" <artur.hefczyc@tigase.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 *
 */

package tigase.form;

import tigase.xml.Element;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * <p>
 * Created: 2007-05-27 10:56:06
 * </p>
 *
 * @author bmalkow
 */
public class Field implements Comparable<Field>{
	private String description;
	private String label;
	private String[] optionLabels;
	private String[] optionValues;
	private boolean required;
	private FieldType type;
	private String[] values;
	private String var;

	public Field(Element fieldElement) {
		this.var = fieldElement.getAttributeStaticStr("var");

		String $type = fieldElement.getAttributeStaticStr("type");

		this.type  = ($type == null)
								 ? FieldType.text_single
								 : FieldType.getFieldTypeByName($type);
		this.label = fieldElement.getAttributeStaticStr("label");

		Element d = fieldElement.getChild("desc");

		if (d != null) {
			this.description = d.getCData();
		}
		this.required = fieldElement.getChild("required") != null;

		List<String> valueList        = new LinkedList<String>();
		List<String> optionsLabelList = new LinkedList<String>();
		List<String> optionsValueList = new LinkedList<String>();

		if (fieldElement.getChildren() != null) {
			for (Element element : fieldElement.getChildren()) {
				if ("value".equals(element.getName())) {
					String v = element.getCData();

					if (v != null) {
						valueList.add(v);
					}
				} else if ("value".equals(element.getName())) {
					optionsLabelList.add(element.getAttributeStaticStr("label"));

					Element v = element.getChild("value");

					optionsValueList.add(v.getCData());
				}
			}
		}
		this.values       = valueList.toArray(new String[] {});
		this.optionLabels = optionsLabelList.toArray(new String[] {});
		this.optionValues = optionsValueList.toArray(new String[] {});
	}

	private Field(FieldType type) {
		this.type = type;
	}

	private Field(FieldType type, String var) {
		this.type = type;
		this.var  = var;
	}

	@Override
	public int compareTo( Field o ) {
		return this.var.compareTo( o.getVar());
	}

	public static enum FieldType {
		bool("boolean"), fixed("fixed"), hidden("hidden"), jid_single("jid-single"),
		list_multi("list-multi"), list_single("list-single"), text_multi("text-multi"),
		text_private("text-private"), text_single("text-single");

		private String desc;


		private FieldType(String desc) {
			this.desc = desc;
		}

		public static FieldType getFieldTypeByName(String name) {
			if ("boolean".equals(name)) {
				return bool;
			} else {
				return FieldType.valueOf(name.replace("-", "_"));
			}
		}

		@Override
		public String toString() {
			return desc;
		}
	}

	public static Field fieldBoolean(String var, Boolean value, String label) {
		Field field = new Field(FieldType.bool);

		field.label = label;
		field.var   = var;
		if ((value != null) && value) {
			field.values = new String[] { "1" };
		} else if ((value != null) &&!value) {
			field.values = new String[] { "0" };
		}

		return field;
	}

	public static Field fieldFixed(String value) {
		Field field = new Field(FieldType.fixed);

		field.values = new String[] { value };

		return field;
	}

	public static Field fieldHidden(String var, String value) {
		Field field = new Field(FieldType.hidden, var);

		field.values = new String[] { value };

		return field;
	}

	public static Field fieldJidSingle(String var, String value, String label) {
		Field field = new Field(FieldType.jid_single, var);

		field.label  = label;
		field.values = new String[] { value };

		return field;
	}

	public static Field fieldListMulti(String var, String[] values, String label,
																		 String[] optionsLabel, String[] optionsValue) {
		if ((optionsLabel != null) && (optionsLabel.length != optionsValue.length)) {
			throw new RuntimeException("Invalid optionsLabel and optinsValue length");
		}

		Field field = new Field(FieldType.list_multi, var);

		field.label        = label;
		field.values       = values;
		field.optionLabels = optionsLabel;
		field.optionValues = optionsValue;

		return field;
	}

	public static Field fieldListSingle(String var, String value, String label,
					String[] optionsLabel, String[] optionsValue) {
		if ((optionsLabel != null) && (optionsLabel.length != optionsValue.length)) {
			throw new RuntimeException("Invalid optionsLabel and optinsValue length");
		}

		Field field = new Field(FieldType.list_single, var);

		field.label        = label;
		field.values       = new String[] { value };
		field.optionLabels = optionsLabel;
		field.optionValues = optionsValue;

		return field;
	}

	public static Field fieldTextMulti(String var, String value, String label) {
		Field field = new Field(FieldType.text_multi, var);

		field.label  = label;
		field.values = new String[] { value };

		return field;
	}

	public static Field fieldTextMulti(String var, String[] values, String label) {
		Field field = new Field(FieldType.text_multi, var);

		field.label  = label;
		field.values = values;

		return field;
	}

	public static Field fieldTextPrivate(String var, String value, String label) {
		Field field = new Field(FieldType.text_private, var);

		field.label  = label;
		field.values = new String[] { value };

		return field;
	}

	public static Field fieldTextSingle(String var, String value, String label) {
		Field field = new Field(FieldType.text_single, var);

		field.label  = label;
		field.values = new String[] { value };

		return field;
	}

	public static Boolean getAsBoolean(Field f) {
		if (f != null) {
			String v = f.getValue();

			if (v == null) {
				return null;
			} else if ("1".equals(v) || "true".equals(v)) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else {
			return null;
		}
	}

	public static void main(String[] args) {

		// <field var='pubsub#presence_based_delivery'
		// type='boolean'><value>false</value></field>
		Element field = new Element("field", new String[] { "var", "type" },
																new String[] { "pubsub#presence_based_delivery",
						"boolean" });

		field.addChild(new Element("value", "true"));

		Field f = new Field(field);

		System.out.println(f);
		System.out.println(getAsBoolean(f));
	}

	public Field cloneShalow() {
		Field field = new Field( type, var );
		field.setLabel( label );
		return field;
	}

	public String getDescription() {
		return description;
	}

	public Element getElement() {
		return getElement( true, true);
	}

	public Element getElement(boolean type, boolean label) {
		Element field = new Element("field");

		if (this.var != null) {
			field.setAttribute("var", var);
		}
		if (type && this.type != null) {
			field.setAttribute("type", this.type.toString());
		}
		if (label && this.label != null) {
			field.setAttribute("label", this.label);
		}
		if (this.description != null) {
			field.addChild(new Element("desc", this.description));
		}
		if (this.required) {
			field.addChild(new Element("required"));
		}
		if (this.values != null) {
			for (String value : this.values) {
				field.addChild(new Element("value", value));
			}
		}
		if (this.optionValues != null) {
			for (int i = 0; i < this.optionValues.length; i++) {
				Element option = new Element("option");

				if ((this.optionLabels != null) && (i < this.optionLabels.length)) {
					option.setAttribute("label", this.optionLabels[i]);
				}

				Element vo = new Element("value");

				if ((this.optionValues[i] != null) &&!"".equals(this.optionValues[i])) {
					vo.setCData(this.optionValues[i]);
				}
				option.addChild(vo);
				field.addChild(option);
			}
		}

		return field;
	}

	public String getLabel() {
		return label;
	}

	public String[] getOptionLabels() {
		return optionLabels;
	}

	public String[] getOptionValues() {
		return optionValues;
	}

	public FieldType getType() {
		return type;
	}

	public String getValue() {
		if ((this.values != null) && (this.values.length > 0)) {
			return this.values[0];
		} else {
			return null;
		}
	}

	public String[] getValues() {
		return values;
	}

	public String getVar() {
		return var;
	}

	public boolean isRequired() {
		return required;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setOptionLabels(String[] optionLabels) {
		this.optionLabels = optionLabels;
	}

	public void setOptionValues(String[] optionValues) {
		this.optionValues = optionValues;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public void setVar(String var) {
		this.var = var;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (this.values == null) {
			sb.append("*null*");
		} else {
			for (String c : this.values) {
				sb.append("[");
				sb.append(c);
				sb.append("] ");
			}
		}

		return this.var + " = " + sb.toString();
	}
}

