package it.polito.tdp.poweroutages.model;

public class Archi implements Comparable<Archi>{
	Nerc n1;
	Nerc n2;
	Double peso;
	
	public Archi(Nerc n1, Nerc n2, Double peso) {
		this.n1 = n1;
		this.n2 = n2;
		this.peso = peso;
	}

	public Nerc getN1() {
		return n1;
	}

	public void setN1(Nerc n1) {
		this.n1 = n1;
	}

	public Nerc getN2() {
		return n2;
	}

	public void setN2(Nerc n2) {
		this.n2 = n2;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Archi o) {
		return -this.getPeso().compareTo(o.getPeso());
	}

	@Override
	public String toString() {
		return  n2 + " " + peso;
	}
	
	
	

}
