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