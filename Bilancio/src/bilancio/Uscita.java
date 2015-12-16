package bilancio;

import java.io.Serializable;
import java.util.Date;

/**
 * Implementa una uscita come voce di bilancio
 * @author Mirco Romagnoli
 * @see Voce
 */
public class Uscita extends Voce implements Serializable {
	/**
	 * Versione della classe
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Istanzia una uscita
	 * @param data data dell'uscita
	 * @param ammontare ammontare dell'uscita
	 * @param descrizione descrizione dell'uscita
	 */
	public Uscita(Date data, double ammontare, String descrizione) {
		super(data, descrizione, ammontare) ;
	}
	
	/**
	 * Restituisce l'ammontare dell'uscita con il segno negativo
	 */
	@Override
	public double getAmm() {
		return -super.getAmm();
	}
}
