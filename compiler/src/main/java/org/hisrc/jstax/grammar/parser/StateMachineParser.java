package org.hisrc.jstax.grammar.parser;

import java.text.MessageFormat;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.production.character.Ch;
import org.hisrc.jstax.grammar.state.State;
import org.hisrc.jstax.grammar.state.StateMachine;
import org.hisrc.jstax.grammar.state.Transition;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.ParseException;
import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.xml.stream.XMLStreamWriter;

public class StateMachineParser {

	private final StateMachine stateMachine;

	public StateMachineParser(final StateMachine stateMachine) {
		Validate.notNull(stateMachine);
		this.stateMachine = stateMachine;
	}

	public void parse(Input input, Result result, XMLStreamWriter writer, ErrorHandler errorHandler) {

		State currentState = this.stateMachine.getInitialState();
		final State terminalState = this.stateMachine.getTerminalState();

		while (currentState != terminalState) {
			final char _char = input.peekChar();
			final Transition transition = this.stateMachine.getTransition(
					currentState, _char);
			if (transition == null) {
				// TODO
				errorHandler
						.error(new ParseException(
								MessageFormat
										.format("Could not detect the next state from the state [{0}] for the character [{1}].",
												currentState, _char), input
										.getLocator()));
				return;
			}
			final Ch ch = transition.getCh();
			ch.read(input, result, errorHandler);
			transition.getOperation().execute(result, writer);
			currentState = transition.getNextState();
		}

	}
}
