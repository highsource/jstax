package org.hisrc.jstax.grammar.parser;

import java.text.MessageFormat;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Ch;
import org.hisrc.jstax.grammar.state.State;
import org.hisrc.jstax.grammar.state.StateMachine;
import org.hisrc.jstax.grammar.state.Transition;
import org.hisrc.jstax.io.CharConstants;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.ParseException;
import org.hisrc.jstax.io.Result;

public class StateMachineParser {

	private final StateMachine stateMachine;

	public StateMachineParser(final StateMachine stateMachine) {
		Validate.notNull(stateMachine);
		this.stateMachine = stateMachine;
	}

	public void parse(Input input, Result result, ErrorHandler errorHandler) {

		State currentState = this.stateMachine.getInitialState();
		final State terminalState = this.stateMachine.getTerminalState();

		while (currentState != terminalState) {
			final char _char = input.peekChar();

			if (_char == CharConstants.EOF) {
				// TODO
				errorHandler
						.error(new ParseException(
								"Finished end of stream, but the state machine did not yet reached the terminal state.",
								input.getLocator()));
				return;
			}
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
			currentState = transition.getNextState();
		}

	}
}