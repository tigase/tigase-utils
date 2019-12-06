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
package tigase.xmpp.rsm;

import tigase.xml.Element;

public class RSM {

	public static final String XMLNS = "http://jabber.org/protocol/rsm";
	private static final String[] SET_AFTER_PATH = {"set", "after"};
	private static final String[] SET_BEFORE_PATH = {"set", "before"};
	private static final String[] SET_INDEX_PATH = {"set", "index"};

	String after = null;
	String before = null;
	Integer count = null;
	String first = null;
	boolean hasBefore = false;
	Integer index = null;
	String last = null;
	int max = 100;

	public static RSM parseRootElement(Element e, int defaultMax) {
		return new RSM(defaultMax).fromElement(e);
	}

	public static RSM parseRootElement(Element e) {
		return RSM.parseRootElement(e, 100);
	}

	public RSM(int defaultMax) {
		this.max = defaultMax;
	}

	public RSM() {
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public boolean hasBefore() {
		return hasBefore;
	}

	public void setHasBefore(boolean hasBefore) {
		this.hasBefore = hasBefore;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public void setResults(Integer count, String first, String last) {
		this.count = count;
		this.first = first;
		this.last = last;
		this.index = null;
	}

	public void setResults(Integer count, Integer index) {
		this.count = count;
		this.index = index;
		this.first = null;
		this.last = null;
	}

	public RSM fromElement(Element e) {
		if (e == null) {
			return this;
		}
		if (e.getXMLNS() != XMLNS) {
			Element x = e.getChild("set", RSM.XMLNS);
			return fromElement(x);
		}

		Element param = e.getChild("max");

		if (param != null) {
			max = Integer.parseInt(param.getCData());
		}
		after = e.getCDataStaticStr(SET_AFTER_PATH);
		Element beforeEl = e.findChildStaticStr(SET_BEFORE_PATH);
		if (beforeEl != null) {
			hasBefore = true;
			before = beforeEl.getCData();
		}
		String indexStr = e.getCDataStaticStr(SET_INDEX_PATH);
		if (indexStr != null) {
			index = Integer.parseInt(indexStr);
		}

		return this;
	}

	public Element toElement() {
		Element set = new Element("set");

		set.setXMLNS(XMLNS);
		if ((first != null) && (last != null) || count != null) {
			if (first != null) {
				Element firstEl = new Element("first", first.toString());
				set.addChild(firstEl);
				if (index != null) {
					firstEl.setAttribute("index", index.toString());
				}
			}
			if (last != null) {
				set.addChild(new Element("last", last.toString()));
			}
			if (count != null) {
				set.addChild(new Element("count", count.toString()));
			}
		} else {
			set.addChild(new Element("max", String.valueOf(max)));
			if (after != null) {
				set.addChild(new Element("after", after));
			}
		}

		return set;
	}
}
