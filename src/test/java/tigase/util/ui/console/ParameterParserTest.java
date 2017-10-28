/*
 * ParameterParserTest.java
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

package tigase.util.ui.console;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.Properties;

public class ParameterParserTest {

	@Test(expected = IllegalArgumentException.class)
	public void testInteractiveMode() {
		ParameterParser parser = new ParameterParser(true);

		final CommandlineParameter dbTypeParameter = new CommandlineParameter.Builder("T", "db-type").description(
				"Database server type").options("derby", "mysql", "postgresql", "sqlserver").required(true).build();
		final CommandlineParameter schemaVersionParameter = new CommandlineParameter.Builder("V",
		                                                                                     "schema-version").description(
				"Intended version of the schema to be loaded").options("4", "5", "5-1", "7-1").build();

		parser.addOption(dbTypeParameter);
		parser.addOption(schemaVersionParameter);
		Assert.assertTrue(parser.getOptionByLetter("I").isPresent());

		String[] params = {};

		Properties properties = parser.parseArgs(params);
		System.out.println(properties);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingRequiredParameter() {

		ParameterParser parser = new ParameterParser();

		final CommandlineParameter dbTypeParameter = new CommandlineParameter.Builder("T", "db-type").description(
				"Database server type").options("derby", "mysql", "postgresql", "sqlserver").required(true).build();
		final CommandlineParameter schemaVersionParameter = new CommandlineParameter.Builder("V",
		                                                                                     "schema-version").description(
				"Intended version of the schema to be loaded").options("4", "5", "5-1", "7-1").build();

		parser.addOption(dbTypeParameter);
		parser.addOption(schemaVersionParameter);

		String[] params = {"--schema-version=5-1"};

		parser.parseArgs(params);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddingSameOption() {

		ParameterParser parser = new ParameterParser();

		final CommandlineParameter help = new CommandlineParameter.Builder("H", "help").build();
		final CommandlineParameter hostname = new CommandlineParameter.Builder("H","hostname").build();

		parser.addOption(help);
		parser.addOption(hostname);
	}

	@Test
	public void testMissingRequiredParameterValue() {

		ParameterParser parser = new ParameterParser();

		final CommandlineParameter dbTypeParameter = new CommandlineParameter.Builder("T", "db-type").description(
				"Database server type")
				.options("derby", "mysql", "postgresql", "sqlserver")
				.defaultValue("derby")
				.required(true)
				.build();
		final CommandlineParameter schemaVersionParameter = new CommandlineParameter.Builder("V",
		                                                                                     "schema-version").description(
				"Intended version of the schema to be loaded").options("4", "5", "5-1", "7-1").build();

		parser.addOption(dbTypeParameter);
		parser.addOption(schemaVersionParameter);

		String[] params = new String[0];

		Properties properties = parser.parseArgs(params);
//		System.out.println(properties);

		Assert.assertNotNull(properties);
		Assert.assertEquals("derby", properties.getProperty(dbTypeParameter.getFullName().get()));
		Assert.assertNull(properties.getProperty(schemaVersionParameter.getFullName().get()));
	}

	@Test
	public void testParseArgs() throws Exception {

		ParameterParser parser = new ParameterParser();

		final CommandlineParameter dbTypeParameter = new CommandlineParameter.Builder("T", "db-type").description(
				"Database server type")
				.options("derby", "mysql", "postgresql", "sqlserver")
				.defaultValue("mysql")
				.required(true)
				.build();
		final CommandlineParameter schemaVersionParameter = new CommandlineParameter.Builder("V",
		                                                                                     "schema-version").description(
				"Intended version of the schema to be loaded").options("4", "5", "5-1", "7-1").build();

		parser.addOption(dbTypeParameter);
		parser.addOption(schemaVersionParameter);

		Assert.assertTrue(!parser.getOptionByLetter("Y").isPresent());

		final Optional<CommandlineParameter> parameter = parser.getOptionByLetter("T");
		Assert.assertTrue(parameter.isPresent());
		Assert.assertEquals("db-type", parameter.get().getFullName().orElse("n/a"));

		final Optional<String> paramValue = parameter.get().getValue();
		Assert.assertFalse(paramValue.isPresent());

		String[] params = {"--schema-version=5-1", "-T", "postgresql"};

		Properties properties = parser.parseArgs(params);

		Assert.assertNotNull(properties);
		Assert.assertEquals("postgresql", properties.getProperty(dbTypeParameter.getFullName().get()));
		Assert.assertEquals("5-1", properties.getProperty(schemaVersionParameter.getFullName().get()));

//		parser.getOptions((commandlineParameter) -> !commandlineParameter.isSecret()).forEach(System.out::println);
//		parser.getOptions((commandlineParameter) -> !commandlineParameter.getValue().isPresent()).forEach(System.out::println);

	}



}