package edu.search.ml.index;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class IndexMapBuilder {

    private IndexMap indexMap = new IndexMap();

    public IndexMap buildIndex(File file) throws IOException {
	LineIterator it = FileUtils.lineIterator(file, "UTF-8");
	try {
	    while (it.hasNext()) {
		addToIndexMap(it.nextLine());
	    }
	} finally {
	    LineIterator.closeQuietly(it);
	}
	return indexMap;
    }

    private void addToIndexMap(String line) {
	String[] parts = line.split(" ", 3);
	if (parts.length < 3)
	    return;
	// Split the input index line into 3 parts ==> 1st part is Term iD#, 2nd
	// is Actual Term and 3rd is the Block inverted index details!
	// Example : ([3] [biodanza] [{0,1,7} {1,1,1} {2,1,1}])

	// We process 3rd part of the string to load the references
	String[] references = parts[2].split(" ");
	for (String blockInfo : references) {
	    String[] refInfo = blockInfo.split(",");
	    Set<Integer> blocks = new TreeSet<Integer>();
	    for (int i = 2; i < refInfo.length; i++) {
		blocks.add(Integer.parseInt(refInfo[i]));
	    }
	    indexMap.addTermReferences(parts[1], Integer.parseInt(refInfo[0]), blocks);
	}
    }

}
