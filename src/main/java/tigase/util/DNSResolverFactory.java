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
package tigase.util;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DNSResolverFactory {

	private static volatile DNSResolverIfc instance = null;
	protected static final String TIGASE_RESOLVER_CLASS = "tigase-resolver-class";
	
	private static final Logger log = Logger.getLogger( DNSResolverFactory.class.getName() );

	public static DNSResolverIfc getInstance() {
		DNSResolverIfc instance = DNSResolverFactory.instance;

		if ( instance == null ){
			synchronized ( DNSResolverIfc.class ) {

				Class<?> clazz = null;
				String property = System.getProperty( TIGASE_RESOLVER_CLASS );
				try {
					if ( property != null ){
						clazz = Class.forName( property );
					}

					if ( clazz != null ){
						DNSResolverFactory.instance = instance = (DNSResolverIfc) clazz.newInstance();
					}

				} catch ( ClassNotFoundException | InstantiationException | IllegalAccessException ex ) {
					log.log( Level.SEVERE, "Failed initialization of class: {0} (property: {1}), using default: {2}",
									 new Object[] { clazz, property, DNSResolverDefault.class.getCanonicalName() } );
				}
				instance = DNSResolverFactory.instance;
				if ( instance == null ){
					DNSResolverFactory.instance = instance = new DNSResolverDefault();
				}
			}
		}

		return instance;

	}

}
