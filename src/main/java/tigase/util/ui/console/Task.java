/**
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
package tigase.util.ui.console;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * Created by andrzej on 09.05.2017.
 */
public class Task {

	private final Optional<Supplier<List<CommandlineParameter>>> additionalParameterSupplier;
	private final Optional<String> description;
	private final String name;
	private Executor<Properties> function;

	private Task(Builder builder) {
		this.name = builder.name;
		this.function = builder.function;
		this.description = Optional.ofNullable(builder.description);
		this.additionalParameterSupplier = Optional.ofNullable(builder.additionalParameterSupplier);
	}

	public String getName() {
		return name;
	}

	public Optional<String> getDescription() {
		return description;
	}

	public List<CommandlineParameter> getAdditionalParameters() {
		if (additionalParameterSupplier.isPresent()) {
			return additionalParameterSupplier.get().get();
		}
		return Collections.emptyList();
	}

	public void execute(Properties props) throws Exception {
		function.execute(props);
	}

	public interface Executor<T> {

		void execute(T parameter) throws Exception;

	}

	public static class Builder {

		private Supplier<List<CommandlineParameter>> additionalParameterSupplier;
		private String description;
		private Executor<Properties> function;
		private String name;

		public Builder() {

		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder function(Executor<Properties> function) {
			this.function = function;
			return this;
		}

		public Builder additionalParameterSupplier(Supplier<List<CommandlineParameter>> supplier) {
			this.additionalParameterSupplier = supplier;
			return this;
		}

		public Task build() {
			if (name == null || name.isEmpty()) {
				throw new IllegalStateException("Name of a task is REQUIRED!");
			}
			if (function == null) {
				throw new IllegalStateException("Function of a task is REQUIRED");
			}
			return new Task(this);
		}

	}

}
