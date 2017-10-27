/*
 * DNSResolverFactory.java
 *
 * Tigase Jabber/XMPP Utils
 * Copyright (C) 2004-2017 "Artur Hefczyc" <artur.hefczyc@tigase.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
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
 * $Rev$
 * Last modified by $Author$
 * $Date$
 */

package tigase.util.dns;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DNSResolverFactory {

	private static volatile DNSResolverIfc instance = null;
	public static final String TIGASE_RESOLVER_CLASS = "tigase-resolver-class";
	
	private static final Logger log = Logger.getLogger( DNSResolverFactory.class.getName() );

	public static void setDnsResolverClassName(String property) {
		Class<?> clazz = null;
		try {
			if ( property != null ){
				clazz = Class.forName( property );
			}

			if ( clazz != null ){
				DNSResolverFactory.instance = instance = (DNSResolverIfc) clazz.newInstance();
			}

		} catch ( ClassNotFoundException | InstantiationException | IllegalAccessException ex ) {
			log.log(Level.SEVERE, "Failed initialization of class: {0} (property: {1}), using default: {2}",
					new Object[] { clazz, property, DNSResolverDefault.class.getCanonicalName() } );
		}
		if (instance == null) {
			instance = new DNSResolverDefault();
		}
	}

	static {
		setDnsResolverClassName(System.getProperty( TIGASE_RESOLVER_CLASS ));
	}

	public static DNSResolverIfc getInstance() {
		return instance;
	}

}
