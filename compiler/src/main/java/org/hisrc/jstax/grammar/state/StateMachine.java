package org.hisrc.jstax.grammar.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.collections4.map.MultiValueArrayMap;
import org.hisrc.jstax.grammar.operation.Operation;
import org.hisrc.jstax.grammar.production.character.Ch;

public class StateMachine {

	private final static int DEFAULT_SIZE = 1024;

	private int count = 0;
	private List<State> states = new ArrayList<State>(DEFAULT_SIZE);
	private Set<State> statesSet = new HashSet<State>(DEFAULT_SIZE);
	private Map<Integer, State> idStatesMap = new HashMap<Integer, State>(
			DEFAULT_SIZE);
	private Map<String, State> nameStateMap = new HashMap<String, State>(
			DEFAULT_SIZE);

	private List<Transition> transitions = new ArrayList<Transition>(
			DEFAULT_SIZE);

	private MultiMap<State, Transition> stateTransitions = new MultiValueArrayMap<State, Transition>();

	private State initialState;
	private State terminalState;
	
	public State getInitialState() {
		return this.initialState;
	}

	public void setInitialState(State initialState) {
		Validate.notNull(initialState);
		this.initialState = initialState;
	}

	public State getTerminalState() {
		return this.terminalState;
	}

	public void setTerminalState(State terminalState) {
		Validate.notNull(terminalState);
		this.terminalState = terminalState;
	}

	public State createState(String name) {
		final int id = count;
		final String stateName = (name == null ? "STATE" : name) + "_" + id;
		final State state = new State(id, stateName);
		if (nameStateMap.containsKey(state.getName())) {
			throw new IllegalArgumentException("State with name [" + name
					+ "] is already defined.");
		}
		states.add(state);
		statesSet.add(state);
		idStatesMap.put(id, state);
		nameStateMap.put(name, state);
		count++;
		return state;
	}

	public Transition createTransition(State from, Ch ch, Operation operation,
			State to) {
		Validate.notNull(from);
		Validate.notNull(ch);
		Validate.notNull(to);
		Validate.isTrue(statesSet.contains(from));
		Validate.isTrue(statesSet.contains(to));
		final Transition transition = new Transition(ch, operation, to);

		final Collection<Transition> existingTransitions = getTransitions(from);
		if (existingTransitions != null) {
			// TODO check that transitions do not intersect
		}
		this.transitions.add(transition);
		this.stateTransitions.put(from, transition);
		return transition;
	}

	@SuppressWarnings("unchecked")
	private Collection<Transition> getTransitions(State from) {
		final Collection<Transition> collection = (Collection<Transition>) this.stateTransitions
				.get(from);
		if (collection == null) {
			return Collections.emptyList();
		}
		return collection;
	}

	public Transition getTransition(State state, char _char) {
		Validate.notNull(state);
		Validate.isTrue(this.statesSet.contains(state));
		final Collection<Transition> transitions = getTransitions(state);
		for (Transition transition : transitions) {
			if (transition.getCh().matches(_char)) {
				return transition;
			}
		}
		return null;
	}
}
