package edu.search.ml.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import edu.search.ml.pojo.DocRefInfo;

public class IndexBuilder {

    private final Map<String, DocRefInfo> indexMap = new HashMap<>();

    public IndexBuilder buildIndex(File file) throws IOException {
	LineIterator it = FileUtils.lineIterator(file, "UTF-8");
	try {
	    while (it.hasNext()) {
		addToIndexMap(it.nextLine());
	    }
	} finally {
	    LineIterator.closeQuietly(it);
	}
	return this;
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
	DocRefInfo docRefInfo = new DocRefInfo();
	for (String blockInfo : references) {
	    populateBlockInfo(docRefInfo, blockInfo);
	}
	indexMap.put(parts[1], docRefInfo);
    }

    private void populateBlockInfo(DocRefInfo docRefInfo, String refInfo) {
	String[] blockInfo = refInfo.split(",");
	List<Integer> blocks = new ArrayList<Integer>();
	for (int i = 2; i < blockInfo.length; i++) {
	    blocks.add(Integer.parseInt(blockInfo[i]));
	}
	docRefInfo.addBlockReference(Integer.parseInt(blockInfo[0]), blocks);
    }

    public Map<String, DocRefInfo> getIndexMap() {
	// TODO: return a read only Map
	return indexMap;
    }

}
