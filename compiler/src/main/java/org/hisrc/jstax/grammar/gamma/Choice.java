package org.hisrc.jstax.grammar.gamma;

import java.util.List;


public interface Choice extends Production {
	
	public List<Production> getOptions();
	
	public void add(Production option);

}
