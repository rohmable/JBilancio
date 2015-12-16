package bilancio;

import java.io.Serializable;
import java.util.Vector;

/**
 * Fornisce un archivio per la gestione di piu' elementi di un tipo
 * @author Mirco Romagnoli
 *
 * @param <E> il tipo dell'archivio
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Vector.html">Vector</a>
 */
public class Archivio<E> implements Serializable{
	/**
	 * Versione dell'archivio
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Vettore che contiene i dati dell'archivio
	 */
	private Vector<E> arch ;
	
	/**
	 * Istanzia un archivio della grandezza specificata
	 * @param size grandezza dell'archivio
	 */
	public Archivio(int size) {
		arch = new Vector<>(size) ;
	}
	
	/**
	 * Istanzia un archivio di grandezza 10
	 */
	public Archivio() {
		arch = new Vector<>() ;
	}

	/**
	 * Restituisce l'oggetto all'indice index
	 * @param index indice dell'oggetto
	 * @return l'oggetto all'interno dell'archivio all'indice index
	 */
	public E get(int index) {
		return arch.elementAt(index) ;
	}
	
	/**
	 * Aggiunge l'elemento alla fine dell'archivio. La grandezza dell'archivio viene incrementata di 1
	 * @param object oggetto da inserire nell'archivio
	 */
	public void add(E object) {
		arch.addElement(object);
	}
	
	/**
	 * Rimuove il primo oggetto del vettore uguale ad object (se viene trovato) e ricompatta l'archivio
	 * per eliminare il vuoto
	 * @param object oggetto da eliminare
	 */
	public void remove(E object) {
		arch.removeElement(object) ;
	}
	
	/**
	 * Elimina l'oggetto di indice index (se compreso tra 1 e la grandezza dell'archivio - 1)
	 * @param index indice dell'oggetto da rimuovere
	 */
	public void remove(int index) {
		try {
			arch.removeElementAt(index);
		} catch (ArrayIndexOutOfBoundsException exception){
		}
	}
	
	/**
	 * @return il numero di componenti dell'archivio
	 */
	public int size() {
		return arch.size();
	}
	
}
