package tigase.util.ui.console;

import java.util.Optional;
import java.util.Properties;

/**
 * Created by andrzej on 09.05.2017.
 */
public class Task {

	private final String name;
	private Executor<Properties> function;
	private final Optional<String> description;

	private Task(Builder builder) {
		this.name = builder.name;
		this.function = builder.function;
		this.description = Optional.ofNullable(builder.description);
	}

	public String getName() {
		return name;
	}

	public Optional<String> getDescription() {
		return description;
	}

	public void execute(Properties props) throws Exception {
		function.execute(props);
	}

	public static class Builder {

		private String name;
		private String description;
		private Executor<Properties> function;

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
