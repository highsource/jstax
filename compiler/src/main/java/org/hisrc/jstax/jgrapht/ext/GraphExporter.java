package org.hisrc.jstax.jgrapht.ext;

import java.io.File;
import java.io.IOException;

import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.VertexNameProvider;
import org.slf4j.Logger;

public interface GraphExporter<V, E> {

	public void exportGraph(final DirectedGraph<V, E> graph,
			VertexNameProvider<V> vertexNameProvider, File targetFile, Logger log)
			throws IOException;

}
