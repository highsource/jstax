package org.hisrc.jstax.grammar.state;

import org.apache.commons.lang3.Validate;

public class State {

	private final int id;
	private final String name;

	State(int id, String name) {
		Validate.isTrue(id >= 0);
		Validate.notNull(name);
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final State other = (State) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
