package Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Record implements Comparable<Record>,Serializable {
	

	private static final long serialVersionUID = -8726967438405115060L;
	private int recordID;
	private String recordName;
	private int releaseYear;
	private String artist;
	private double price;
	private ArrayList<Song> songsListInRecord;
	
	
	// Constructor
	public Record(int recordID,String recordName, int releaseYear, String artist, double price,ArrayList<Song>songsListInRecord) {
		
		setRecordID(recordID);
		setRecordName(recordName);
		setReleaseYear(releaseYear);
		setArtist(artist);
		setPrice(price);
		this.songsListInRecord = songsListInRecord;
	}


	// Getters
	public int getRecordID() {
		return recordID;
	}

	public String getRecordName() {
		return recordName;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public String getArtist() {
		return artist;
	}

	public double getPrice() {
		return price;
	}
	
	public String getPrice2() {
		String x = String.format("%.1f",this.price);
		return x;
	}

	public ArrayList<Song> getSongsListInRecord() {
		return songsListInRecord;
	}


	// Setters 
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}


	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}


	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}


	public void setArtist(String artist) {
		this.artist = artist;
	}

	
	public void setPrice(double price) {
		this.price = price;
	}


	
	
	// toString - print
		@Override
		public String toString() {
			return "Record Name: " + recordName 
					+ "\nRelease Year: " + releaseYear 
					+ "\nArtist: " + artist 
					+ "\nPrice: "+ getPrice2() 
					+ "\n" + songsToString()+"\n";
		}

	
	// Add song to the Record
	public void addSong(Song song) {
		songsListInRecord.add(song);
	}
	
	// Remove song to the Record
	public void removeSong(Song song) {
		songsListInRecord.remove(song);
	}


	//CompareTo by Release Year & Price
	@Override
	public int compareTo(Record o) {
		if(this.getReleaseYear()-o.getReleaseYear() != 0) {
			return this.getReleaseYear()-o.getReleaseYear();
		}
		return (int) (this.getPrice() - o.getPrice());
	}
	
	// print songs in list
		public String songsToString() {
			String s = "Record Songs:\n";
			int counter = 1;
			for (Song song: songsListInRecord) {
				s += counter + ". " + song.getSongName() + "\n";
				counter++;			
			}
			return s;
		}
}
