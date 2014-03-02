package org.hisrc.jstax.jgrapht.ext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.transform.TransformerConfigurationException;

import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.GraphMLExporter;
import org.jgrapht.ext.IntegerEdgeNameProvider;
import org.jgrapht.ext.IntegerNameProvider;
import org.jgrapht.ext.VertexNameProvider;
import org.slf4j.Logger;
import org.xml.sax.SAXException;

public class GraphMLGraphExporter<V, E> implements GraphExporter<V, E> {

	public void exportGraph(DirectedGraph<V, E> graph,
			VertexNameProvider<V> vertexNameProvider, File targetFile,
			Logger log) throws IOException {
		final GraphMLExporter<V, E> exporter = new GraphMLExporter<V, E>(
				new IntegerNameProvider<V>(), vertexNameProvider,
				new IntegerEdgeNameProvider<E>(), null);
		Writer writer = null;
		try {
			targetFile.getParentFile().mkdirs();
			writer = new FileWriter(targetFile);
			exporter.export(writer, graph);
		} catch (TransformerConfigurationException tcex) {
			throw new IOException(tcex);
		} catch (SAXException saxex) {
			throw new IOException(saxex);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException x) {

				}
			}
		}
	}

}
