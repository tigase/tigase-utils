/*
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2015 "Tigase, Inc." <office@tigase.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License,
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
 */
package tigase.form;

import tigase.xml.Element;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wojciech Kapcia
 */
public class MultiItemForm extends AbstractForm {

	private final List<Fields> items = new ArrayList<>();
	private Fields reported = null;

	public MultiItemForm( ) {
		super( "result", null, null );
	}

	public MultiItemForm( String title ) {
		super( "result", title, null );
	}

	public MultiItemForm( Element form ) {
		super( form );
	}

	@Override
	public Element getElement() {
		if (reported == null || items == null ) {
			return null;
		}
		Element form = super.getElement();
		Element report = new Element("reported");
		for ( Field field : reported.getAllFields() ) {
			report.addChild( field.getElement() );
		}
		form.addChild( report);
		for (Fields fields : this.items) {
			Element item = new Element("item");
			for ( Field field : fields.getAllFields()) {
				if ( reported.is( field.getVar() ) ){
					item.addChild( field.getElement( false, false ) );
				}
			}
			if (item.getChildren() != null && !item.getChildren().isEmpty()) {
			form.addChild(item);
			}
		}

		return form;
	}

	public void addItem(Fields i) {
		items.add( i);
		if ( reported == null ){
			reported = new Fields();
			for ( Field field : i.getAllFields()) {
				reported.addField( field.cloneShalow() );
			}
		}
	}

	public List<Fields> getAllItems() {
		return items;
	}


	public static void main( String[] args ) {

		MultiItemForm multiItemForm = new MultiItemForm("tytul");
		multiItemForm.setInstruction( "bla");

		Fields f = new Fields();
		f.addField( Field.fieldTextSingle( "var1", "val11", null));
		f.addField( Field.fieldTextSingle( "var2", "val12", null));
		f.addField( Field.fieldTextSingle( "var3", "val13", null));
		multiItemForm.addItem( f );
		f = new Fields();
		f.addField( Field.fieldTextSingle( "var1", "val21", null));
		f.addField( Field.fieldTextSingle( "var5", "val22", null));
		multiItemForm.addItem( f );
		f = new Fields();
		f.addField( Field.fieldTextSingle( "var4", "val31", null));
		f.addField( Field.fieldTextSingle( "var5", "val32", null));
		multiItemForm.addItem( f );


		System.out.println( multiItemForm.getElement() );


		multiItemForm = new MultiItemForm("tytul");

		System.out.println( multiItemForm.getElement() );


	}
}
