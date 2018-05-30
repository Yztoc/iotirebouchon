package controller;

public class Point {

	public int idPoint;
	public double longitude;
	public double latitude;
	public double hauteur;
	public int idMap;
	
	public Point(int idPoint,double longitude,double latitude,double hauteur,int idMap)
	{
		this.idPoint = idPoint;
		this.longitude = longitude;
		this.latitude = latitude;
		this.hauteur = hauteur;
		this.idMap = idMap;
	}
	
	public String toString()
	{ 
		return "idPoint : " + this.idPoint + "\nLongitude : " + this.longitude + "\nLatitude : " + this.latitude + "\nHauteur ; " + this.hauteur;
	}

	public int getIdPoint() {
		return idPoint;
	}

	public void setIdPoint(int idPoint) {
		this.idPoint = idPoint;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getHauteur() {
		return hauteur;
	}

	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}

	public int getidMap() {
		return idMap;
	}

	public void setidMap(int idMap) {
		this.idMap = idMap;
	}
		
}
