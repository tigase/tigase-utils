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

import tigase.xml.Element;

import java.util.List;
import java.util.logging.Logger;

/**
 * <p> Created: 2007-05-27 11:41:02 </p>
 *
 * @author bmalkow
 */
public class AbstractForm {

	protected static final Logger log = Logger.getLogger(AbstractForm.class.getName());
	protected String instruction;
	protected String title;
	protected String type;

	public AbstractForm(Element form) {
		this.type = form.getAttributeStaticStr("type");
		log.finest("Retriving Data Form type " + this.type);

		List<Element> children = form.getChildren();

		if (children != null) {
			for (Element sub : children) {
				if ("title".equals(sub.getName())) {
					this.title = sub.getCData();
					log.finest("read Data Form title [" + this.title + "]");
				} else if ("instructions".equals(sub.getName())) {
					this.instruction = sub.getCData();
					log.finest("read Data Form instruction [" + this.instruction + "]");
				}
			}
		}
	}

	public AbstractForm(String type, String title, String instruction) {
		this.type = type;
		this.title = title;
		this.instruction = instruction;
	}

	public Element getElement() {
		Element form = new Element("x");

		form.setAttribute("xmlns", "jabber:x:data");
		if (type != null) {
			form.setAttribute("type", type);
		}
		if (this.title != null) {
			form.addChild(new Element("title", this.title));
		}
		if (this.instruction != null) {
			form.addChild(new Element("instructions", this.instruction));
		}

		return form;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeOrElse(String defValue) {
		if (type == null) {
			return defValue;
		}
		return type;
	}

	public boolean isType(String type) {
		return this.type != null && this.type.equals(type);
	}
}
