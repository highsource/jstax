package org.hisrc.jstax.grammar.state;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.operation.Operation;
import org.hisrc.jstax.grammar.production.character.Ch;

public class Transition {

	private final Ch ch;
	private final Operation operation;
	private final State nextState;

	public Transition(Ch ch, Operation operation, State nextState) {
		this.ch = Validate.notNull(ch);
		this.operation = Validate.notNull(operation);
		this.nextState = nextState;
	}

	public Ch getCh() {
		return this.ch;
	}

	public Operation getOperation() {
		return this.operation;
	}

	public State getNextState() {
		return this.nextState;
	}

	@Override
	public String toString() {
		return "(" + this.ch + ")" + "->" + this.nextState;
	}

}
