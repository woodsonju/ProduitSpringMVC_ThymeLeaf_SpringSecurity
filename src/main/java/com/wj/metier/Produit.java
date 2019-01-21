package com.wj.metier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Produit implements Serializable{
	
	@Id @GeneratedValue
	private Long id;
	
	@NotNull //Ajout d'annotation de validation : NotNull => Je n'accepte pas que la designation soit null
	@Size(min=1, max=80) //Le nombre de caractère doit être compris entre 4 et 15
	private String designation;
	
	@DecimalMin("10")  //Le prix doit être superieur 10
	private double prix;
	
	private int quantite;
	
	public Produit() {
		super();
	}

	public Produit(String designation, double prix, int quantite) {
		super();
		this.designation = designation;
		this.prix = prix;
		this.quantite = quantite;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	
	

}
