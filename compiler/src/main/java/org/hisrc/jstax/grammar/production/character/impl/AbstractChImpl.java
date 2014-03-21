package org.hisrc.jstax.grammar.production.character.impl;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.ChVertexImpl;
import org.hisrc.jstax.grammar.graph.impl.EdgeImpl;
import org.hisrc.jstax.grammar.operation.None;
import org.hisrc.jstax.grammar.operation.Operation;
import org.hisrc.jstax.grammar.production.character.Ch;
import org.hisrc.jstax.grammar.production.character.CharRange;
import org.hisrc.jstax.grammar.production.character.CharRanges;
import org.hisrc.jstax.grammar.production.impl.AbstractProduction;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Locator;
import org.hisrc.jstax.io.ParseException;
import org.hisrc.jstax.io.Result;
import org.jgrapht.DirectedGraph;

public abstract class AbstractChImpl extends AbstractProduction implements
		CharRanges {

	private final Operation operation;
	private final List<Ch> unmodifiableElements;

	public AbstractChImpl(Operation operation, String name) {
		super(Validate.notNull(name));
		this.operation = Validate.notNull(operation);
		this.unmodifiableElements = Collections.unmodifiableList(Collections
				.<Ch> singletonList(this));
	}

	public AbstractChImpl(String name) {
		this(None.INSTANCE, Validate.notNull(name));
	}

	@Override
	public Operation getOperation() {
		return operation;
	}

	@Override
	public List<Ch> getElements() {
		return this.unmodifiableElements;
	}

	@Override
	public void read(Input input, Result result, ErrorHandler handler) {
		final char ch = input.peekChar();
		// if (ch == CharConstants.EOF) {
		// final Locator locator = input.getLocator();
		// handler.error(new ParseException(
		// "Could not read the next character, the stream is empty.",
		// locator));
		// } else
		{
			if (startsInput(input)) {
				result.pushChar(input.readChar());
			} else {
				final Locator locator = input.getLocator();
				handler.error(new ParseException(
						MessageFormat
								.format("Unexpected character [{0}] in input stream at [{1}, {2}].",
										ch, locator.getLineNumber(),
										locator.getColumnNumber()), locator));
			}
		}
	}

	@Override
	public void buildGraph(DirectedGraph<Vertex, Edge> graph, Vertex start,
			Vertex end) {
		final Vertex middle = new ChVertexImpl(this);
		graph.addVertex(middle);
		graph.addEdge(start, middle, new EdgeImpl());
		graph.addEdge(middle, end, new EdgeImpl(getOperation()));
	}

	@Override
	public boolean intersects(Ch that) {
		Validate.notNull(that);
		for (CharRange thisCharRange : this.getCharRanges()) {
			for (CharRange thatCharRange : that.getCharRanges()) {
				if (thisCharRange.getTo() >= thatCharRange.getFrom()
						&& thisCharRange.getFrom() <= thatCharRange.getTo()) {
					return true;
				}
			}
		}
		return false;
	}

}
