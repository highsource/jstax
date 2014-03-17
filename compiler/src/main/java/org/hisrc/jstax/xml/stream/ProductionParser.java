package org.hisrc.jstax.xml.stream;

import java.text.MessageFormat;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang3.Validate;
import org.hisrc.jstax.grammar.graph.Edge;
import org.hisrc.jstax.grammar.graph.StateMachineBuilder;
import org.hisrc.jstax.grammar.graph.Vertex;
import org.hisrc.jstax.grammar.graph.impl.EdgeImpl;
import org.hisrc.jstax.grammar.graph.impl.EndVertexImpl;
import org.hisrc.jstax.grammar.graph.impl.StartVertexImpl;
import org.hisrc.jstax.grammar.graph.optimizer.CompositeGraphOptimizer;
import org.hisrc.jstax.grammar.production.Production;
import org.hisrc.jstax.grammar.production.character.Ch;
import org.hisrc.jstax.grammar.state.State;
import org.hisrc.jstax.grammar.state.StateMachine;
import org.hisrc.jstax.grammar.state.Transition;
import org.hisrc.jstax.io.ErrorHandler;
import org.hisrc.jstax.io.Input;
import org.hisrc.jstax.io.ParseException;
import org.hisrc.jstax.io.Result;
import org.hisrc.jstax.io.impl.StringResult;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DefaultDirectedGraph;

public class ProductionParser implements XMLStreamReader {

	private StateMachine stateMachine;
	private State currentState;
	private final State terminalState;
	private Input input;
	private final Result result;
	private ErrorHandler errorHandler;
	private Consumer consumer = new ConsumerImpl();

	public ProductionParser(final Production production, final Input input,
			final ErrorHandler errorHandler) {
		Validate.notNull(production);
		this.errorHandler = Validate.notNull(errorHandler);
		this.input = Validate.notNull(input);

		final DirectedGraph<Vertex, Edge> graph = new DefaultDirectedGraph<Vertex, Edge>(
				new EdgeFactory<Vertex, Edge>() {
					@Override
					public Edge createEdge(Vertex sourceVertex,
							Vertex targetVertex) {
						return new EdgeImpl();
					}
				});

		final Vertex start = new StartVertexImpl();
		final Vertex end = new EndVertexImpl();
		graph.addVertex(start);
		graph.addVertex(end);
		production.buildGraph(graph, start, end);
		new CompositeGraphOptimizer(graph).optimize();
		this.stateMachine = new StateMachineBuilder().buildStateMachine(graph);
		this.currentState = this.stateMachine.getInitialState();
		this.terminalState = this.stateMachine.getTerminalState();
		this.result = new StringResult();
	}

	public int next() {
		while (consumer.getEventType() == -1 && currentState != terminalState) {
			final char _char = input.peekChar();
			final Transition transition = this.stateMachine.getTransition(
					currentState, _char);
			if (transition == null) {
				this.stateMachine.getTransition(currentState, _char);
				// TODO
				errorHandler
						.error(new ParseException(
								MessageFormat
										.format("Could not detect the next state from the state [{0}] for the character [{1}].",
												currentState, _char), input
										.getLocator()));
				return -1;
			}
			final Ch ch = transition.getCh();
			ch.read(input, result, errorHandler);
			transition.getOperation().execute(result, consumer);
			currentState = transition.getNextState();
		}

		this.eventType = consumer.getEventType();
		this.text = consumer.getText();
		this.piTarget = consumer.getPITarget();
		this.piData = consumer.getPIData();
		consumer.flush();
		return this.eventType;
	}

	private int eventType = -1;
	private String text;
	private String piTarget;
	private String piData;

	@Override
	public int getEventType() {
		return this.eventType;
	}

	@Override
	public String getText() {
		return this.text;
	}

	@Override
	public String getPITarget() {
		return this.piTarget;
	}

	@Override
	public String getPIData() {
		return this.piData;
	}

	// TODO

	@Override
	public Object getProperty(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void require(int type, String namespaceURI, String localName)
			throws XMLStreamException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getElementText() throws XMLStreamException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int nextTag() throws XMLStreamException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasNext() throws XMLStreamException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() throws XMLStreamException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getNamespaceURI(String prefix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isStartElement() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEndElement() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCharacters() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWhiteSpace() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAttributeValue(String namespaceURI, String localName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAttributeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public QName getAttributeName(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttributeNamespace(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttributeLocalName(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttributePrefix(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttributeType(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttributeValue(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAttributeSpecified(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNamespaceCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getNamespacePrefix(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNamespaceURI(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamespaceContext getNamespaceContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getTextCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTextCharacters(int sourceStart, char[] target,
			int targetStart, int length) throws XMLStreamException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTextStart() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTextLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasText() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QName getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNamespaceURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isStandalone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean standaloneSet() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCharacterEncodingScheme() {
		// TODO Auto-generated method stub
		return null;
	}

}
