package controller;

import java.sql.Date;

public class Vol {

	public int idVol;
	public String nomVol;
	public String nomPilote;
	public Date dateVol;
	public double dureDuVol;
	public int batterieLife;
	public int puissanceMoteur;
	
	public Vol(int idVol,String nomVol,String nomPilote,Date dateVol,double dureDuVol, int batterieLife, int puissanceMoteur)
	{
		this.idVol = idVol;
		this.nomVol = nomVol;
		this.nomPilote = nomPilote;
		this.dateVol = dateVol;
		this.dureDuVol = dureDuVol;
		this.batterieLife = batterieLife;
		this.puissanceMoteur = puissanceMoteur;
	}

	@Override
	public String toString() {
		return "Vol [idVol=" + idVol + ", nomVol=" + nomVol + ", nomPilote=" + nomPilote + ", dateVol=" + dateVol
				+ ", dureDuVol=" + dureDuVol + ", batterieLife=" + batterieLife + ", puissanceMoteur=" + puissanceMoteur
				+ "]";
	}

	public int getIdVol() {
		return idVol;
	}

	public void setIdVol(int idVol) {
		this.idVol = idVol;
	}

	public String getNomVol() {
		return nomVol;
	}

	public void setNomVol(String nomVol) {
		this.nomVol = nomVol;
	}

	public String getNomPilote() {
		return nomPilote;
	}

	public void setNomPilote(String nomPilote) {
		this.nomPilote = nomPilote;
	}

	public Date getDateVol() {
		return dateVol;
	}

	public void setDateVol(Date dateVol) {
		this.dateVol = dateVol;
	}

	public double getDureDuVol() {
		return dureDuVol;
	}

	public void setDureDuVol(double dureDuVol) {
		this.dureDuVol = dureDuVol;
	}

	public int getBatterieLife() {
		return batterieLife;
	}

	public void setBatterieLife(int batterieLife) {
		this.batterieLife = batterieLife;
	}

	public int getPuissanceMoteur() {
		return puissanceMoteur;
	}

	public void setPuissanceMoteur(int puissanceMoteur) {
		this.puissanceMoteur = puissanceMoteur;
	}
	
	
}
