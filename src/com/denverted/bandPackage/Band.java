package com.denverted.bandPackage;

import java.net.URL;

public class Band {
	
	//=============================================================
	// CLASS FIELDS
	//=============================================================
	int fromCO; //0 = no, 1 = yes
	String genre;
	String description;
	String name;
	URL songSample;
	//String popularity;
	//=============================================================

	public int getFromCO() {
		return fromCO;
	}

	public void setFromCO(int fromCO) {
		this.fromCO = fromCO;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URL getSongSample() {
		return songSample;
	}

	public void setSongSample(URL songSample) {
		this.songSample = songSample;
	}

	
	public Band() {
		
	}
	
	
}
