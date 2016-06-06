package edu.search.ml.pojo;

import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class DocRefInfo {

    private Map<Integer, SortedSet<Integer>> blockRefs = new TreeMap<>();

    public Map<Integer, SortedSet<Integer>> getBlockRefs() {
	return blockRefs;
    }

    public DocRefInfo addBlockReference(Integer docNo, Collection<Integer> blocks) {
	if (!blockRefs.containsKey(docNo)) {
	    blockRefs.put(docNo, new TreeSet<Integer>());
	}
	blockRefs.get(docNo).addAll(blocks);
	return this;
    }

    @Override
    public String toString() {
	return "DocRefInfo [blockRefs=" + blockRefs + "]";
    }
}
