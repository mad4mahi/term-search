package edu.search.ml.index;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.search.ml.pojo.DocumentAndProximityVO;

public class IndexMap {

    // 2 terms are considered near if their proximity rank is less than this
    private static int PROXIMITY_THRESHOLD = 100;

    private final Map<String, Map<Integer, Collection<Integer>>> indexMap = new HashMap<String, Map<Integer, Collection<Integer>>>();

    public void addTermReferences(String term, Integer docNo, Collection<Integer> blocks) {
	if (!indexMap.containsKey(term)) {
	    Map<Integer, Collection<Integer>> blockRef = new TreeMap<Integer, Collection<Integer>>();
	    blockRef.put(docNo, blocks);
	    indexMap.put(term, blockRef);
	} else {
	    indexMap.get(term).put(docNo, blocks);
	}
    }

    public DocumentAndProximityVO findProximity(String term1, String term2) {
	if (indexMap.isEmpty())
	    throw new RuntimeException("No Index information built");
	if (!indexMap.containsKey(term1) || !indexMap.containsKey(term2)) {
	    return null;
	}
	Map<Integer, Collection<Integer>> term1DocRefInfo = indexMap.get(term1);
	Map<Integer, Collection<Integer>> term2DocRefInfo = indexMap.get(term2);
	DocumentAndProximityVO details = null;
	for (Integer documentNo : term1DocRefInfo.keySet()) {
	    if (term2DocRefInfo.containsKey(documentNo)) {
		// Find the proximity
		int proximity = getProximityRank(term1DocRefInfo.get(documentNo), term2DocRefInfo.get(documentNo));
		if (proximity >= PROXIMITY_THRESHOLD)
		    continue;
		if (details == null) {
		    details = new DocumentAndProximityVO();
		    details.setDocument(documentNo);
		    details.setProximity(proximity);
		} else if (details.getProximity() > proximity) {
		    details.setDocument(documentNo);
		    details.setProximity(proximity);
		}
	    }
	}
	return details;
    }

    private int getProximityRank(Collection<Integer> set1, Collection<Integer> set2) {
	int proximityIndex = PROXIMITY_THRESHOLD;
	for (Integer no1 : set1) {
	    for (Integer no2 : set2) {
		int proximity = difference(no1.intValue(), no2.intValue());
		if (proximityIndex > proximity) {
		    proximityIndex = proximity;
		}
	    }
	}
	return proximityIndex;
    }

    public int difference(int a, int b) {
	return Math.abs(a - b);
    }

    @Override
    public String toString() {
	return "DocRefInfo [blockRefs=" + indexMap + "]";
    }
}
