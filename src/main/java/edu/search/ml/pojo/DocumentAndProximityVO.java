package edu.search.ml.pojo;

public class DocumentAndProximityVO {

    private Integer document;

    private int proximity;

    public int getProximity() {
	return proximity;
    }

    public void setProximity(int proximity) {
	this.proximity = proximity;
    }

    public Integer getDocument() {
	return document;
    }

    public void setDocument(Integer document) {
	this.document = document;
    }

    @Override
    public String toString() {
	return "DocumentAndProximityVO [document=" + document + ", proximity=" + proximity + "]";
    }

}
