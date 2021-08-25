package Classes;

import java.io.Serializable;

public class Song implements Comparable<Song>,Serializable {
	
	
	private static final long serialVersionUID = -6843815043840555031L;
	private int songID;
	private String songName;
	 
	
	// Constructor
	public Song(int songID,String songName) {
		
		setSongID(songID);
		setSongName(songName);
	}


	// Getters
	public int getSongID() {
		return songID;
	}

	public String getSongName() {
		return songName;
	}


	// Setters
	public void setSongName(String songName) {
		this.songName = songName;
	}
	
	public void setSongID(int songID) {
		this.songID = songID;
	}


	// toString
	@Override
	public String toString() {
		return songID + " - " + songName + "\n";
	}


	//CompareTo by Song Name
	@Override
	public int compareTo(Song o) {

		return this.getSongName().compareTo(o.getSongName());
	}

}
