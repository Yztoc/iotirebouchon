package controller;

import java.sql.Date;

public class Map {

	public int idMap;
	public String nomMap;
	public Date dateMapGenere;
	public int vol_idVol;
	public double neLat;
	public double neLng;
	public double soLat;
	public double soLng;
	
	public Map(int idMap, String nomMap,Date dateMapGenere,int vol_idVol,double neLat,double neLng,double soLat,double soLng)
	{
		this.idMap = idMap;
		this.nomMap = nomMap;
		this.dateMapGenere = dateMapGenere;
		this.vol_idVol = vol_idVol;
	}

	@Override
	public String toString() {
		return "Map [idMap=" + idMap + "\nnomMap=" + nomMap + "\ndateMapGenere=" + dateMapGenere + "\nvol_idVol="
				+ vol_idVol + "]";
	}

	public int getIdMap() {
		return idMap;
	}

	public void setIdMap(int idMap) {
		this.idMap = idMap;
	}

	public String getNomMap() {
		return nomMap;
	}

	public void setNomMap(String nomMap) {
		this.nomMap = nomMap;
	}

	public Date getDateMapGenere() {
		return dateMapGenere;
	}

	public void setDateMapGenere(Date dateMapGenere) {
		this.dateMapGenere = dateMapGenere;
	}

	public int getVol_idVol() {
		return vol_idVol;
	}

	public void setVol_idVol(int vol_idVol) {
		this.vol_idVol = vol_idVol;
	}

	public double getNeLat() {
		return neLat;
	}

	public void setNeLat(double neLat) {
		this.neLat = neLat;
	}

	public double getNeLng() {
		return neLng;
	}

	public void setNeLng(double neLng) {
		this.neLng = neLng;
	}

	public double getSoLat() {
		return soLat;
	}

	public void setSoLat(double soLat) {
		this.soLat = soLat;
	}

	public double getSoLng() {
		return soLng;
	}

	public void setSoLng(double soLng) {
		this.soLng = soLng;
	}
	
	
	
	
	
}
