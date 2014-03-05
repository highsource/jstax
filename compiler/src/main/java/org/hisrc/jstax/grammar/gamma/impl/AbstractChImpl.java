package org.hisrc.jstax.grammar.gamma.impl;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.gamma.Ch;
import org.hisrc.jstax.grammar.gamma.CharRange;
import org.hisrc.jstax.grammar.gamma.CharRanges;
import org.hisrc.jstax.grammar.gamma.Edge;
import org.hisrc.jstax.grammar.gamma.Vertex;
import org.hisrc.jstax.io.CharConstants;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.Locator;
import org.hisrc.jstax.io.ParseException;
import org.hisrc.jstax.io.Result;
import org.jgrapht.DirectedGraph;

public abstract class AbstractChImpl extends AbstractProduction implements Ch,
		CharRanges {

	private final List<Ch> unmodifiableElements;

	public AbstractChImpl() {
		this.unmodifiableElements = Collections.unmodifiableList(Collections
				.<Ch> singletonList(this));
	}

	@Override
	public List<Ch> getElements() {
		return this.unmodifiableElements;
	}

	@Override
	public void read(Input input, Result result, ErrorHandler handler) {
		final char ch = input.peekChar();
		if (ch == CharConstants.EOF) {
			final Locator locator = input.getLocator();
			handler.error(new ParseException(
					"Could not read the next character, the stream is empty.",
					locator));
		} else {
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
		graph.addEdge(start, middle);
		graph.addEdge(middle, end);
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
