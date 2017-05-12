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

	private final String name;
	private Executor<Properties> function;
	private final Optional<String> description;
	private final Optional<Supplier<List<CommandlineParameter>>> additionalParameterSupplier;

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

	public static class Builder {

		private String name;
		private String description;
		private Executor<Properties> function;
		private Supplier<List<CommandlineParameter>> additionalParameterSupplier;

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

	public interface Executor<T> {

		void execute(T parameter) throws Exception;

	}

}
