package org.hisrc.jstax.grammar.state;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Ch;

public class Transition {

	private final Ch ch;
	private final State nextState;

	public Transition(Ch ch, State nextState) {
		Validate.notNull(ch);
		Validate.notNull(nextState);
		this.ch = ch;
		this.nextState = nextState;
	}

	public Ch getCh() {
		return this.ch;
	}

	public State getNextState() {
		return this.nextState;
	}

	@Override
	public String toString() {
		return "(" + this.ch + ")" + "->" + this.nextState;
	}

}
