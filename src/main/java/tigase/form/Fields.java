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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Wojciech Kapcia
 */
public class Fields {

	private List<Field> fields = new ArrayList<Field>();
	private Map<String, Field> fieldsByVar = new HashMap<String, Field>();
	protected final Logger log = Logger.getLogger( this.getClass().getName() );

	public void addField( final Field field ) {
		Field cf = ( field.getVar() != null ) ? this.fieldsByVar.get( field.getVar() ) : null;

		if ( cf != null ){
			int p = this.fields.indexOf( cf );

			this.fields.remove( cf );
			this.fields.add( p, field );
		} else {
			this.fields.add( field );
		}
		if ( field.getVar() != null ){
			this.fieldsByVar.put( field.getVar(), field );
		}
	}

	public void clear() {
		this.fields.clear();
		this.fieldsByVar.clear();
	}

	public Field get( String var ) {
		return this.fieldsByVar.get( var );
	}

	public List<Field> getAllFields() {
		return this.fields;
	}

	public Boolean getAsBoolean( String var ) {
		Field f = get( var );

		if ( f != null ){
			String v = f.getValue();

			if ( v == null ){
				return null;
			} else if ( "1".equals( v ) || "true".equals( v ) ){
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else {
			return null;
		}
	}

	public Integer getAsInteger( String var ) {
		Field f = get( var );

		if ( f != null ){
			String v = f.getValue();

			return Integer.parseInt( v );
		} else {
			return null;
		}
	}

	public Long getAsLong( String var ) {
		Field f = get( var );

		if ( f != null ){
			String v = f.getValue();

			return Long.parseLong( v );
		} else {
			return null;
		}
	}

	public String getAsString( String var ) {
		Field f = get( var );

		if ( f != null ){
			String v = f.getValue();

			return v;
		} else {
			return null;
		}
	}

	public String[] getAsStrings( String var ) {
		Field f = get( var );

		if ( f != null ){
			String[] v = f.getValues();

			return v;
		} else {
			return null;
		}
	}

	public boolean is( String var ) {
		return this.fieldsByVar.containsKey( var );
	}

	public void removeField( final String var ) {
		Field cf = this.fieldsByVar.remove( var );

		if ( cf != null ){
			this.fields.remove( cf );
		}
	}

}
