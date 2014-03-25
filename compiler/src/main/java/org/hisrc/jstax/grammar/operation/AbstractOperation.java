package org.hisrc.jstax.grammar.operation;

import org.apache.commons.lang3.Validate;

public abstract class AbstractOperation implements Operation {

	@Override
	public boolean isTrivial() {
		return false;
	}

	@Override
	public Operation join(Operation that) {
		Validate.notNull(that);
		if (this == that) {
			return this;
		} else if (that.isTrivial()) {
			return this;
		} else {
			if (this.isTrivial()) {
				return that;
			} else {
				throw new UnsupportedOperationException(
						"Can not join two non-trivial operations.");
			}
		}
	}
}
