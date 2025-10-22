package edu.training.news_portal.bean;

public enum NewsGroups {
	SOCIETY(1), TECHNOLOGIES(2), FINANCE(3), AUTO(4);

	private final int id;

	NewsGroups(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static NewsGroups fromId(int id) throws IllegalArgumentException{
		for (NewsGroups group : values()) {
			if (group.id == id) {
				return group;
			}
		}
		throw new IllegalArgumentException("Invalid group id: " + id);
	}
}
