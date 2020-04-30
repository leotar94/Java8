package com.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Album {
	private String autore;
	private List<String> canzoni;
	private int anno;
	private String titolo;
	public String getAutore() {
		return autore;
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}
	public List<String> getCanzoni() {
		return canzoni;
	}
	public void setCanzoni(List<String> canzni) {
		this.canzoni = canzni;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public Album(String autore,String titolo, int anno, String ...canzoni) {
		super();
		this.autore = autore;
		this.canzoni = new ArrayList<String>(Arrays.asList(canzoni));
		this.anno = anno;
		this.titolo = titolo;
	}
	@Override
	public String toString() {
		return "Album [autore=" + autore + ", canzoni=" + canzoni + ", anno=" + anno + ", titolo=" + titolo + "]";
	}
	

}
