package edu.training.news_portal.bean;

public class RegistrationInfo {

	private final String email;
	private final String name;
	private final String password;

	private RegistrationInfo(RegBuilder builder) {
		email = builder.getEmail();
		name = builder.getName();
		password = builder.getPassword();
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public static class RegBuilder implements Builder<RegistrationInfo> {

		private String email;
		private String name;
		private String password;

		public RegBuilder() {
		}

		public String getEmail() {
			return email;
		}

		public String getName() {
			return name;
		}

		public String getPassword() {
			return password;
		}

		public RegBuilder email(String email) {
			this.email = email;
			return this;
		}

		public RegBuilder name(String name) {
			this.name = name;
			return this;
		}

		public RegBuilder password(String password) {
			this.password = password;
			return this;
		}

		@Override
		public RegistrationInfo build() {
			return new RegistrationInfo(this);
		}

	}
}
