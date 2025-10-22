package edu.training.news_portal.bean;

import java.util.Objects;

public class User {
	private int id;
	private String name;
	private String email;
	private Role role;

	public User() {
	}
	
	public User(String name, String email) {
		this.name = name;
		this.email = email;
		this.role = Role.GUEST;
	}

	public User(String name, String email, Role role) {
		this.name = name;
		this.email = email;
		this.role = role;
	}

	public User(int id, String name, String email, Role role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, name, role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(role, other.role);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", role=" + role + "]";
	}

}
