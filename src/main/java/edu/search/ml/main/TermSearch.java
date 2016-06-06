package edu.search.ml.main;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.SortedSet;

import edu.search.ml.index.IndexBuilder;
import edu.search.ml.pojo.DocRefInfo;

public class TermSearch {

    public static void main(String[] args) {
	Path indexPath = Paths.get("/Users/maheshn/Downloads/index.txt");
	String term1 = "de";
	String term2 = "situations";
	try {
	    Map<String, DocRefInfo> indexMap = new IndexBuilder().buildIndex(indexPath.toFile()).getIndexMap();
	    if (indexMap.containsKey(term1) && indexMap.containsKey(term2)) {
		Map<Integer, SortedSet<Integer>> term1DocRefInfo = indexMap.get(term1).getBlockRefs();
		Map<Integer, SortedSet<Integer>> term2DocRefInfo = indexMap.get(term2).getBlockRefs();
		// iterate through first document references
		// and check if each one exists in 2nd term document reference
		for (Integer documentNo : term1DocRefInfo.keySet()) {
		    if (term2DocRefInfo.containsKey(documentNo)) {
			System.out.println("Found in document - " + documentNo);
			// TODO: proximity comparision

		    }
		}
	    } else {
		// Not found, inform back that they are not close to each other
		return;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

}
