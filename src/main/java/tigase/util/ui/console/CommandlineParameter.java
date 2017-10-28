/*
 * CommandlineParameter.java
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

import java.util.*;
import java.util.function.Function;

public class CommandlineParameter {

	private final String defaultValue;
	private final String description;
	private final String fullName;
	private final boolean requireArguments;
	private final boolean required;
	private final boolean secret;
	private final List<String> selectionOptions;
	private final String singleLetter;
	private final Optional<Function<String, List<CommandlineParameter>>> valueDependentParametersProvider;
	private String value;
	private Class type;

	private CommandlineParameter(Builder builder) {
		this.singleLetter = builder.singleLetter;
		this.fullName = builder.fullName;
		this.defaultValue = builder.defaultValue;
		this.description = builder.description;
		this.secret = builder.secret;
		this.requireArguments = builder.requireArguments;
		this.required = builder.required;
		this.selectionOptions = builder.selectionOptions;
		this.type = builder.type;
		this.valueDependentParametersProvider = Optional.ofNullable(builder.valueDependentParametersProvider);
	}

	// TODO: add fields dependency? for example admin pass on admin JID local-part?

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		CommandlineParameter that = (CommandlineParameter) o;

		return singleLetter != null && singleLetter.equals(that.singleLetter) ||
				fullName != null && fullName.equals(that.fullName);
	}

	/**
	 * Retrieves default value for this parameter option
	 *
	 * @return Optional for the default value
	 */
	public Optional<String> getDefaultValue() {
		return null != defaultValue ? Optional.of(defaultValue) : Optional.empty();
	}

	/**
	 * Retrieves description for this parameter option
	 *
	 * @return Optional for the description
	 */
	public Optional<String> getDescription() {
		return null != description ? Optional.of(description) : Optional.empty();
	}

	/**
	 * Retrieves full name for this parameter option
	 *
	 * @param includeDash controls whether dashes should be included
	 *
	 * @return Optional for the full name (which may include dashes depending on parameter)
	 */
	public Optional<String> getFullName(boolean includeDash) {
		return (null == fullName ? Optional.empty() : Optional.of((includeDash ? "--" : "") + fullName));
	}

	/**
	 * Retrieves full name for this parameter option
	 *
	 * @return Optional for the full name
	 */
	public Optional<String> getFullName() {
		return getFullName(false);
	}

	/**
	 * Retrieves list of possible selection options for this parameter option
	 *
	 * @return Optional list of the possible selection options
	 */
	public Optional<List<String>> getSelectionOptions() {
		return (null != selectionOptions && !selectionOptions.isEmpty()
		        ? Optional.of(selectionOptions)
		        : Optional.empty());
	}

	/**
	 * Retrieves single letter identification for this parameter option
	 *
	 * @return Optional for the single letter
	 */
	public Optional<String> getSingleLetter() {
		return getSingleLetter(false);
	}

	/**
	 * Retrieves single letter identification for this parameter option
	 *
	 * @param includeDash controls whether dash should be included
	 *
	 * @return Optional for the single letter (which may include dash depending on parameter)
	 */
	public Optional<String> getSingleLetter(boolean includeDash) {
		return (null == singleLetter
		        ? Optional.empty()
		        : Optional.of((includeDash ? "-" : "") + singleLetter));

	}

	/**
	 * Retrives expected class of a parameter
	 *
	 * @return
	 */
	public Class getType() {
		return type;
	}

	/**
	 * Retrieves stored value for this parameter option
	 *
	 * @return Optional with the stored value
	 */
	public Optional<String> getValue() {
		return (null == value ? Optional.empty() : Optional.of(value));
	}

	/**
	 * Sets value for this parameter option
	 *
	 * @param value to be set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public List<CommandlineParameter> getValueDependentParameters() {
		if (valueDependentParametersProvider.isPresent() && getValue().isPresent()) {
			return valueDependentParametersProvider.get().apply(value);
		}
		return Collections.emptyList();
	}

	public boolean hasValueDependentParameters() {
		return valueDependentParametersProvider.isPresent();
	}

	@Override
	public int hashCode() {
		int result = singleLetter != null ? singleLetter.hashCode() : 0;
		result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
		return result;
	}

	/**
	 * Retrieves information whether this parameter option requires arguments
	 *
	 * @return true if the parameter option requires arguments
	 */
	public boolean isRequireArguments() {
		return requireArguments;
	}

	/**
	 * Retrieves information whether this parameter option is required
	 *
	 * @return true if the parameter option is required
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * Retrieves information whether this parameter option is secret
	 *
	 * @return true if the parameter option is secret
	 */
	public boolean isSecret() {
		return secret;
	}
	
	/**
	 * Sets the value from the configured default if present
	 */
	public void setValueFromDefault() {
		if (getDefaultValue().isPresent()) {
			this.value = getDefaultValue().get();
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("CommandlineParameter{");
		sb.append("singleLetter='").append(singleLetter).append('\'');
		sb.append(", fullName='").append(fullName).append('\'');
		sb.append(", defaultValue='").append(defaultValue).append('\'');
		sb.append(", description='").append(description).append('\'');
		sb.append(", secret=").append(secret);
		sb.append(", requireArguments=").append(requireArguments);
		sb.append(", required=").append(required);
		sb.append(", selectionOptions=").append(selectionOptions);
		sb.append(", value='").append(value).append('\'');
		sb.append('}');
		return sb.toString();
	}

	public String toStringSimple() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("letter='").append(singleLetter).append('\'');
		sb.append(", name='").append(fullName).append('\'');
		sb.append('}');
		return sb.toString();
	}

	/**
	 * Creates a {@link CommandlineParameter} builder
	 */
	public static class Builder {

		// Either of the two is required parameter
		private final String fullName;
		private final String singleLetter;
		// Optional parameters - initialized to default values
		private String defaultValue = null;
		private String description = null;
		private boolean requireArguments = true;
		private boolean required = false;
		private boolean secret = false;
		private List<String> selectionOptions = null;
		private Class type = String.class;
		private Function<String, List<CommandlineParameter>> valueDependentParametersProvider;

		/**
		 * Constructs a {@link CommandlineParameter} builder. It takes as parameters both "single-letter" and
		 * "full-name" of which one is mandatory
		 *
		 * @param singleLetter single letter identification of the option
		 * @param fullName full named identification of the option
		 */
		public Builder(String singleLetter, String fullName) {
			if (null == singleLetter && null == fullName) {
				throw new IllegalArgumentException("Either single letter or full-length parameter is required");
			}
			this.singleLetter = singleLetter;
			this.fullName = fullName;

		}

		/**
		 * Creates a concrete {@link CommandlineParameter} from particular builder
		 *
		 * @return constructed {@link CommandlineParameter}
		 */
		public CommandlineParameter build() {
			return new CommandlineParameter(this);
		}

		/**
		 * Sets default value for the parameter option
		 *
		 * @param defaultValue default value to be set
		 *
		 * @return current Builder object
		 */
		public Builder defaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
			return this;
		}

		/**
		 * Sets the description for the parameter option
		 *
		 * @param description description to be set
		 *
		 * @return current Builder object
		 */
		public Builder description(String description) {
			this.description = description;
			return this;
		}

		/**
		 * Sets possible options for the parameter option
		 *
		 * @param options array of possible options
		 *
		 * @return current Builder object
		 */
		public Builder options(String... options) {
			if (null == this.selectionOptions) {
				this.selectionOptions = new ArrayList<>();
			}
			if (null != options && options.length > 0) {
				this.selectionOptions.addAll(Arrays.asList(options));
			}

			return this;
		}

		/**
		 * Sets whether particular parameter option requires arguments
		 *
		 * @param required whether the option needs parameter
		 *
		 * @return current Builder object
		 */
		public Builder requireArguments(boolean required) {
			requireArguments = required;
			return this;
		}

		/**
		 * Sets whether particular parameter option is required - if yes then it's mandatory to set it's value or provide default.
		 *
		 * @param required whether the option is required
		 *
		 * @return current Builder object
		 */
		public Builder required(boolean required) {
			this.required = required;
			return this;
		}

		/**
		 * Sets the parameter option as secret which influences how it will be obtained in interactive mode (won't be printed, useful for passwords)
		 *
		 * @return current Builder object
		 */
		public Builder secret() {
			secret = true;
			return this;
		}

		public Builder type(Class type) {
			this.type = type;
			return this;
		}

		public Builder valueDependentParametersProvider(Function<String, List<CommandlineParameter>> provider) {
			this.valueDependentParametersProvider = provider;
			return this;
		}
	}
}
