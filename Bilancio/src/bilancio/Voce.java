package bilancio;

import java.util.Date;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Implementa la voce del bilancio
 * @author Mirco Romagnoli
 *
 */
public abstract class Voce implements Serializable {
	/**
	 * Versione della classe
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Data della voce
	 */
	private Date data ;
	/**
	 * Descrizione della voce sottoforma di testo scritto dall'utente
	 */
	private String descrizione ;
	/**
	 * Ammontare della voce
	 */
	private double amm ;
	
	/**
	 * Istanzia una voce di bilancio
	 * @param data data della voce
	 * @param descrizione descrizione della voce
	 * @param ammontare ammontare della voce
	 */
	public Voce(Date data, String descrizione, double ammontare) {
		this.data = data ;
		this.descrizione = descrizione;
		amm = ammontare ;
	}

	/**
	 * @return la data della voce
	 */
	public Date getData() {
		return data;
	}

	/**
	 * Modifica la data della voce
	 * @param data nuova data
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * Modifica la data della voce, la data deve essere nel formato gg/mm/aaaa
	 * @param data nuova data sottoforma di stringa
	 */
	public void setData(String data) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy") ;
		try {
			this.data = format.parse(data) ;
		} catch (ParseException e) {
			System.out.println("Impossibile modificare la data " + e);
		}
	}
	
	/**
	 * @return descrizione della voce
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * Modifica la descrizione della voce
	 * @param descrizione nuova descrizione
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	/**
	 * Restituisce l'ammontare
	 * @return ammontare della voce
	 */
	public double getAmm() {
		return amm;
	}

	/**
	 * Modifica l'ammontare della voce
	 * @param amm nuovo ammontare
	 */
	public void setAmm(double amm) {
		this.amm = amm;
	}
	
	@Override
	public boolean equals (Object obj) {
		Voce comp = (Voce)obj ;
		return data.equals(comp.getData()) && descrizione.equals(comp.getDescrizione()) && (amm == comp.getAmm()) ;
	}
}
