package org.hisrc.jstax.grammar.graph;

import org.apache.commons.lang3.Validate;

public class EdgeToVertex {

	private final Edge edge;

	private final Vertex vertex;

	public EdgeToVertex(Edge edge, Vertex vertex) {
		this.edge = Validate.notNull(edge);
		this.vertex = Validate.notNull(vertex);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edge == null) ? 0 : edge.hashCode());
		result = prime * result + ((vertex == null) ? 0 : vertex.hashCode());
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
		EdgeToVertex other = (EdgeToVertex) obj;
		if (edge == null) {
			if (other.edge != null)
				return false;
		} else if (!edge.getOperation().equals(other.edge.getOperation()))
			return false;
		if (vertex == null) {
			if (other.vertex != null)
				return false;
		} else if (!vertex.equals(other.vertex))
			return false;
		return true;
	}

}
