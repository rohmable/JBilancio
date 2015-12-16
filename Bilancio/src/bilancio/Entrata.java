package bilancio;

import java.io.Serializable;
import java.util.Date;

/**
 * Implementa un entrata come voce di bilancio
 * @author Mirco Romagnoli
 * @see Voce
 */
public class Entrata extends Voce implements Serializable {
	
	/**
	 * Versione della classe
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Istanzia una entrata
	 * @param data data dell'entrata
	 * @param ammontare ammontare dell'entrata
	 * @param descrizione descrizione dell'entrata
	 */
	public Entrata(Date data, double ammontare, String descrizione) {
		super(data, descrizione, ammontare) ;
	}
}
