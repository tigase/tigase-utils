package tigase.util.ui.console;

import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * General purpose command-line parser accepting (if defined) both
 * single-letter (and space separated value) and full names (and equal-sign separated value).
 * It also supports default values and generation of help based on configured options.
 *
 */
public class ParameterParser {

	private static final Logger log = Logger.getLogger(ParameterParser.class.getName());
	private final boolean interactiveMode;
	private final List<CommandlineParameter> options;
	private final Map<String, CommandlineParameter> optionsByLetter;
	private final Map<String, CommandlineParameter> optionsByName;
	private final CommandlineParameter helpOption = new CommandlineParameter.Builder(null, "help").description(
			"Print the help").requireArguments(false).build();
	private final CommandlineParameter interactiveOption = new CommandlineParameter.Builder("I",
	                                                                                        "interactive").description(
			"Enable interactive mode, which will result in prompting for missing parameters")
			.requireArguments(false)
			.build();
	private Optional<Task> task = Optional.empty();
	private Optional<List<Task>> tasks = Optional.empty();

	public static void main(String[] args) {

		ParameterParser parser = new ParameterParser(true);

		parser.addOption(new CommandlineParameter.Builder("T", "db-type").description("Database server type")
				                 .options("derby", "mysql", "postgresql", "sqlserver")
				                 .defaultValue("mysql")
				                 .required(true)
				                 .build());
		parser.addOption(new CommandlineParameter.Builder("V", "schema-version").description(
				"Intended version of the schema to be loaded")
				                 .options("4", "5", "5-1", "7-1")
				                 .required(true)
				                 .defaultValue("7-1")
				                 .build());

		parser.addOption(new CommandlineParameter.Builder("D", "db-name").description(
				"Name of the database that will be created and to which schema will be loaded")
				                 .defaultValue("tigasedb")
				                 .required(true)
				                 .build());
		parser.addOption(
				new CommandlineParameter.Builder("H", "db-host").description("Address of the database instance")
						.defaultValue("localhost")
						.required(true)
						.build());
		parser.addOption(new CommandlineParameter.Builder("U", "db-user-name").description(
				"Name of the user that will be created specifically to access Tigase XMPP Server")
				                 .defaultValue("tigase_user")
				                 .required(true)
				                 .build());
		parser.addOption(new CommandlineParameter.Builder("P", "db-user-password").description(
				"Password of the user that will be created specifically to access Tigase XMPP Server")
				                 .defaultValue("tigase_pass")
				                 .required(true)
				                 .secret()
				                 .build());
		parser.addOption(new CommandlineParameter.Builder("R", "db-root-name").description(
				"Database root account username used to create tigase user and database")
				                 .defaultValue("root")
				                 .required(true)
				                 .build());
		parser.addOption(new CommandlineParameter.Builder("A", "db-root-password").description(
				"Database root account password used to create tigase user and database")
				                 .defaultValue("root")
				                 .secret()
				                 .required(true)
				                 .build());
		parser.addOption(new CommandlineParameter.Builder("F", "file").description(
				"Comma separated list of SQL files that will be processed").build());
		parser.addOption(new CommandlineParameter.Builder("L", "log-level").description(
				"Java Logger level during loading process").defaultValue("CONFIG").build());
		parser.addOption(new CommandlineParameter.Builder("S", "use-ssl").description(
				"Enable SSL support for database connection (if database supports it)")
				                 .requireArguments(false)
				                 .defaultValue("false")
				                 .build());
		parser.addOption(new CommandlineParameter.Builder("J", "admin-jid-name").description(
				"Comma separated list of administrator JID(s)").build());
		parser.addOption(new CommandlineParameter.Builder("N", "admin-jid-password").description(
				"Password that will be used for the entered JID(s) - one for all configured administrators")
				                 .defaultValue("pass")
				                 .secret()
				                 .build());

//		System.out.println(parser.getOptionByLetter("t"));
//		System.out.println(parser.getOptionByLetter("a"));
//		System.out.println(parser.getOptionByLetter("d"));
//
//		System.out.println("pass:");
//		parser.getOptions(CommandlineParameter::isSecret).forEach(System.out::println);
//		System.out.println();
//		System.out.println("not secret:");
//		parser.getOptions((commandlineParameter) -> !commandlineParameter.isSecret()).forEach(System.out::println);
//
//		System.out.println(parser.getHelp());
//

//		args = new String[]{"-H"};
//		args = new String[]{"--help"};
//		args = new String[]{"-T", "postgresql"};
//		args = new String[]{"-T", "mysql",
//							"--db-host=localhost",
//							"--log-level=ALL",
//							"--admin-jid-name=admin@localhost",
//							"--admin-jid-password=pass",
//							"-R", "root",
//							"-A", "root",
//							"-S",
//							"-D", "tigase72tts",
//							"--schema-version=7-1",
//							"-U", "tigase72tts",
//							"-P", "tigase72tts",
//							"-file", "database/mysql-message-archiving-schema-1.3.0.sql,database/mysql-pubsub-schema-3.3.0.sql,database/mysql-muc-schema-2.5.0.sql,database/mysql-socks5-schema.sql"};

		Properties properties;
		if (null == args || args.length == 0 || (properties = parser.parseArgs(args)) == null) {
			System.out.println(parser.getHelp());
		} else {
			System.out.println("properties: " + properties);
		}

//		parser.getOptions((commandlineParameter) -> !commandlineParameter.isSecret()).forEach(System.out::println);
//		parser.getOptions((commandlineParameter) -> !commandlineParameter.getValue().isPresent()).forEach(System.out::println);


	}

	/**
	 * Constructs new {@code ParameterParser} without any options and without interactive mode
	 */
	public ParameterParser() {
		this(null, false);
	}

	/**
	 * Constructs new {@code ParameterParser} without any options and allowing enabling interactive mode
	 *
	 * @param interactiveMode indicates whether interactive mode should be enabled (include it in the options and handle
	 * accordingly)
	 */
	public ParameterParser(boolean interactiveMode) {
		this(null, interactiveMode);
	}

	/**
	 * Constructs new {@code ParameterParser} with initial options and allowing enabling interactive mode
	 *
	 * @param options initial list of available options
	 * @param interactiveMode indicates whether interactive mode should be enabled (include it in the options and handle
	 * accordingly)
	 */
	public ParameterParser(List<CommandlineParameter> options, boolean interactiveMode) {
		this.options = new ArrayList<>();
		this.optionsByName = new HashMap<>();
		this.optionsByLetter = new HashMap<>();
		this.interactiveMode = interactiveMode;
		addOption(helpOption);

		if (interactiveMode) {
			addInteractiveModeCommand();
		}
		if (null != options && !options.isEmpty()) {
			addOptions(options);
		}
	}

	private void addInteractiveModeCommand() {
		if (this.interactiveMode) {
			addOption(interactiveOption);
		}
	}

	/**
	 * Adds {@link CommandlineParameter} to list of available parameters
	 *
	 * @param option a {@link CommandlineParameter} that should be added
	 *
	 * @throws IllegalArgumentException is thrown when the same option (either same name or same short-letter) exists in
	 * the defined list.
	 */
	public void addOption(CommandlineParameter option) throws IllegalArgumentException {
		if (option != null && !this.options.contains(option)) {
			this.options.add(option);
			if (option.getSingleLetter().isPresent()) {
				this.optionsByLetter.put(option.getSingleLetter().get(), option);
			}
			if (option.getFullName().isPresent()) {
				this.optionsByName.put(option.getFullName().get(), option);
			}
		} else {
			throw new IllegalArgumentException(
					"Option already exists!"
							+ " Existing: " + this.options.get(this.options.indexOf(option)).toStringSimple()
							+ " added: " + (option != null ? option.toStringSimple() : "n/a")

			);
		}
	}

	/**
	 * Add collection of {@link CommandlineParameter} to available options. Calls internally {@link
	 * ParameterParser#addOption(tigase.util.ui.console.CommandlineParameter)}
	 *
	 * @param options collection of {@link CommandlineParameter} to be added.
	 */
	public void addOptions(List<CommandlineParameter> options) {
		if (null != options && !options.isEmpty()) {
			options.forEach(this::addOption);
		}
	}

	private boolean addOptionsIfMissing(List<CommandlineParameter> options) {
		if (null != options && !options.isEmpty()) {
			return options.stream().filter(option -> !this.options.contains(option)).map(option -> {
				this.addOption(option);
				return option;
			}).count() > 0;
		}
		return false;
	}

	/**
	 * Generates help output with default instruction.
	 *
	 * @return String with default instruction.
	 */
	public String getHelp() {
		return getHelp(null);
	}

	/**
	 * @param executionCommand an example of the execution command, for example {@code $ java -cp \"jars/*.jar\"
	 * tigase.util.DBSchemaLoader [options]}
	 *
	 * @return string representing all the available options and their description
	 */
	public String getHelp(String executionCommand) {
		if (null == executionCommand) {
			executionCommand = "$ java -cp \"path_to_binary.jar\" some.package.Class " + (tasks.isPresent() ? "[task] " : "") + "[options]" + "\n\t\t" +
					"if the option defines default then <value> is optional";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:").append("\n");
		sb.append("\t").append(executionCommand).append("\n");
		sb.append("\n");
		if (tasks.isPresent()) {
			sb.append("Tasks:");
			for (Task task : tasks.get()) {
				sb.append("\n\t");
				sb.append(task.getName());
				task.getDescription().ifPresent(str -> sb.append("\t-\t").append(str));
			}
			sb.append("\n\n");
		}
		sb.append("Options:");

		List<CommandlineParameter> parameterDependentParameters = getOptions().stream().filter(option -> option.hasValueDependentParameters()).collect(
				Collectors.toList());
		if (!parameterDependentParameters.isEmpty()) {
			sb.append("\n\tThis is not a full list of possible parameters. Please pass values for ");
			if (tasks.isPresent()) {
				sb.append("task and ");
			}
			sb.append("following parameters to get full list of parameters: ");
			sb.append(parameterDependentParameters.stream()
							  .map(option -> option.getSingleLetter(true)
									  .orElseGet(() -> option.getFullName(true).get()))
							  .collect(Collectors.joining(", ")));
		}

		for (CommandlineParameter option : options) {
			sb.append("\n\n\t");
			option.getSingleLetter(true).ifPresent((str) -> sb.append(str));
			if (option.getSingleLetter(true).isPresent()) {
				if (option.isRequireArguments()) {
					sb.append(" value");
				}
				sb.append(',').append(' ');
			}
			option.getFullName(true).ifPresent(sb::append);
			if (option.getFullName(true).isPresent()) {
				if (option.isRequireArguments()) {
					sb.append("=value");
				}
				sb.append(',').append(' ');

			}
			if (!option.isRequired()) {
				sb.append(" (optional)");
			}
			sb.append("\n\t\t");
			option.getDescription().ifPresent(sb::append);
			option.getSelectionOptions().ifPresent(e -> sb.append(", possible values: ").append(e));
			option.getDefaultValue().ifPresent(e -> sb.append(" (default: ").append(e).append(')'));
		}
		sb.append("\n");
		return sb.toString();
	}

	/**
	 * Returns {@link CommandlineParameter} that matches passed parameter
	 *
	 * @param letter single-letter identification of the {@link CommandlineParameter}
	 *
	 * @return an Optional {@link CommandlineParameter} that matches passed letter.
	 */
	public Optional<CommandlineParameter> getOptionByLetter(String letter) {
		return (null == this.optionsByLetter.get(letter)
		        ? Optional.empty()
		        : Optional.of(this.optionsByLetter.get(letter)));
	}

	/**
	 * Returns {@link CommandlineParameter} that matches passed parameter
	 *
	 * @param commandName full-name identification of the {@link CommandlineParameter}
	 *
	 * @return an Optional {@link CommandlineParameter} that matches passed name.
	 */
	public Optional<CommandlineParameter> getOptionByName(String commandName) {
		return (null == this.optionsByName.get(commandName)
		        ? Optional.empty()
		        : Optional.of(this.optionsByName.get(commandName)));
	}

	/**
	 * Retrieves List with all defined {@link CommandlineParameter} options
	 *
	 * @return List with all defined {@link CommandlineParameter} options
	 */
	public List<CommandlineParameter> getOptions() {
		return Collections.unmodifiableList(options);
	}

	/**
	 * Retrieves List with all defined {@link CommandlineParameter} options that matches passed Predicate
	 *
	 * @param predicate by which list should be filtered
	 *
	 * @return List of {@link CommandlineParameter} filtered to matching options.
	 */
	public List<CommandlineParameter> getOptions(Predicate<? super CommandlineParameter> predicate) {
		return options.stream().filter(predicate).collect(Collectors.toList());
	}

	/**
	 * Retrieves List with all defined {@link CommandlineParameter} options names that matches passed Predicate
	 *
	 * @param predicate by which list should be filtered
	 *
	 * @return List of {@link CommandlineParameter} names filtered to matching options.
	 */
	public List<String> getOptionsNames(Predicate<? super CommandlineParameter> predicate) {
		return getOptions(predicate)
				.stream()
				.map(CommandlineParameter::getFullName)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

	public Optional<Task> getTask() {
		return task;
	}

	public boolean isInteractiveMode() {
		return interactiveMode;
	}

	/**
	 * Main parsing method which takes as an input array of parameters and returns a Properties object with parsed
	 * parameter/values. It supports both single letter format ({@code -X value} as well as full name ({@code
	 * --name=value}). In case no value for defined option was passed but it's defined as required and contains default
	 * value it will be included in the resulting Properties. If interactive mode is enabled, there will be a prompt for
	 * all missing options (defined but not included in passed parameter)
	 *
	 * @param args an input array of parameters and defined values.
	 *
	 * @return Properties with parameter/value pairs matching defined options.
	 */
	public Properties parseArgs(String[] args) {
		log.log(Level.FINEST, "Parsing arguments: " + Arrays.toString(args));
		Properties props = new Properties();

		// so we can run parse many times
		for (CommandlineParameter p : getOptions()) {
			p.setValue(null);
		}

		int i = 0;
		task = Optional.empty();
		if (tasks.isPresent()) {
			if (args.length > 0) {
				task = tasks.get().stream().filter(t -> t.getName().equals(args[0])).findAny();
				if (task.isPresent()) {
					i++;
				}
			}
			if (!task.isPresent()) {
				return null;
			} else {
				addOptions(task.get().getAdditionalParameters());
			}
		}

		while (parseArgsToProperties(props, i, args)) {
			
		}

		// let's check if this is interactive mode and if it wasn't disabled in passed argument
		// if not - ask user for input for missing parameters
		// otherwise - use default values from option (if available)
		final Predicate<CommandlineParameter> missingValuesPredicate = (p) -> !p.getValue().isPresent();

		final boolean help = getOptionByName("help").isPresent();
		final Optional<String> help1 = getOptionByName("help").get().getValue();
		if (help &&
				Boolean.valueOf(help1.orElse("false"))) {
			return null;
		}

		if (interactiveMode && getOptionByName("interactive").isPresent() &&
				Boolean.valueOf(getOptionByName("interactive").get().getValue().orElse("false"))) {

			log.log(Level.FINEST, "Using interactive mode for {0}", getOptionsNames(missingValuesPredicate));
			System.out.println("Using interactive mode for: " + getOptionsNames(missingValuesPredicate) +
					                   ", skipped options will use default if available");

			final ConsoleIfc console = new SystemConsole();

			for (CommandlineParameter e : getOptions(missingValuesPredicate)) {
				if (e.equals(helpOption) || e.equals(interactiveOption)) {
					continue;
				}
				String label = (e.getFullName().isPresent() ? e.getFullName().get() : "") +
						(e.getDefaultValue().isPresent() ? " [" + e.getDefaultValue().orElse("n/a") + "]" : "") + ": ";

				String val = e.isSecret() ? new String(console.readPassword(label)) : console.readLine(label);
				if (null != val && !val.isEmpty()) {
					e.setValue(val);
					addOptionsIfMissing(e.getValueDependentParameters());
				}
			}
		}

		log.log(Level.FINEST, "Using defaults for mode for {0}", getOptionsNames(missingValuesPredicate));
		getOptions(missingValuesPredicate).forEach(CommandlineParameter::setValueFromDefault);

		final Predicate<CommandlineParameter> missingAndRequired = (p) -> !p.getValue().isPresent() && p.isRequired();
		log.log(Level.FINEST, "Still missing values for: {0}, of which required are: {1}",
		        new Object[]{getOptionsNames(missingValuesPredicate), getOptionsNames(missingAndRequired)});

		if (getOptions(missingAndRequired).size() > 0) {
			throw new IllegalArgumentException(
					"You are missing arguments! Missing are: " + getOptionsNames(missingAndRequired));
		}

		// fill out properties map
		for (CommandlineParameter e : getOptions((p) -> p.getValue().isPresent())) {
			if (!e.equals(interactiveOption) && !e.equals(helpOption) && e.getFullName().isPresent() &&
					e.getValue().isPresent()) {
				props.putIfAbsent(e.getFullName().get(), e.getValue().get());
			}
		}

		return props;
	}

	private boolean parseArgsToProperties(Properties props, int i, String[] args) {
		boolean added = false;
		// lets parse input, options are detected as follows
		// * if "-" -> single-letter + space separator
		// * if "--" -> full-name + "=" as separator
		// if the value for the parameter is provided - use it
		// if it's missing - use default (but only for the passed options)
		for (; i < args.length; i++) {
			String arg = args[i];
			String key = null;
			String value = null;
			final Optional<CommandlineParameter> optionOption;
			if (args[i].startsWith("--")) {
				if (args[i].contains("=")) {
					key = args[i].substring(2, args[i].indexOf("="));
				} else {
					key = args[i].substring(2);
				}
				optionOption = getOptionByName(key);

				if (!optionOption.isPresent()) {
					continue;
				}

				CommandlineParameter option = optionOption.get();
				if (option.isRequireArguments()) {
					if (args[i].contains("=")) {
						value = args[i].substring(arg.indexOf("=") + 1, args[i].length());
						option.setValue(value);
					} else if (args.length - 1 > i) {
						value = args[i+1];
						i++;
					} else if (option.getDefaultValue().isPresent()) {
						value = option.getDefaultValue().get();
						option.setValue(value);
					}
				} else {
					Boolean b = true;
					if (option.getDefaultValue().isPresent()) {
						// let's flip the default
						b = !Boolean.valueOf(option.getDefaultValue().get());
					}
					option.setValue(b.toString());
				}
				added |= addOptionsIfMissing(option.getValueDependentParameters());
			} else if (args[i].startsWith("-")) {
				key = args[i].substring(1, args[i].length());
				optionOption = getOptionByLetter(key);

				if (!optionOption.isPresent()) {
					continue;
				}

				CommandlineParameter option = optionOption.get();
				if (option.getFullName().isPresent()) {
					key = option.getFullName().get();
				}

				if (option.isRequireArguments()) {
					if (i + 1 < args.length) {
						value = args[i + 1];
						option.setValue(value);
					} else if (option.getDefaultValue().isPresent()) {
						value = option.getDefaultValue().get();
						option.setValue(value);
					}
				} else {
					Boolean b = true;
					if (option.getDefaultValue().isPresent()) {
						// let's flip the default
						b = !Boolean.valueOf(option.getDefaultValue().get());
					}
					option.setValue(b.toString());
				}
				added |= addOptionsIfMissing(option.getValueDependentParameters());
			} else {
				log.log(Level.FINE, "Checked item is not valid: %1$s, possibly value of parameter", args[i]);
			}
			if (null != key && null != value) {
				props.put(key, value);
			}
		}
		return added;
	}

	/**
	 * Removes given option from list of supported options.
	 *
	 * @param option {@code commandlineParameter} option to be removed
	 */
	public void removeOption(CommandlineParameter option) {
		if (null != option) {
			options.remove(option);
			option.getSingleLetter().ifPresent(optionsByLetter::remove);
			option.getFullName().ifPresent(optionsByName::remove);
		}
	}

	/**
	 * Removes given option from list of supported options by it's single letter
	 *
	 * @param singleLetter identification of the option
	 */
	public void removeOptionByLetter(String singleLetter) {
		final Optional<CommandlineParameter> option = getOptionByLetter(singleLetter);
		option.ifPresent(this::removeOption);
	}

	/**
	 * Removes given option from list of supported options by it's full name
	 *
	 * @param name full name identification of the option
	 */
	public void removeOptionByName(String name) {
		final Optional<CommandlineParameter> option = getOptionByName(name);
		option.ifPresent(this::removeOption);
	}

	public void setTasks(Task[] supportedTasks) {
		this.tasks = Optional.ofNullable(supportedTasks == null ? null : Arrays.asList(supportedTasks));
	}
}
