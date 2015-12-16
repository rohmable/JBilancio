package bilancio;

import java.io.Serializable;
import java.util.Vector;

/**
 * Provides an archive for the management of elements of the same type
 * @author Mirco Romagnoli
 * @version {@value #serialVersionUID}
 * @param <E> Type of the archive
 * @see Vector
 * @see Serializable
 */
public class Archive<E> implements Serializable{
	/**
	 * Version of the archive
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Vector that contains data of the archive
	 * @see Vector
	 */
	private Vector<E> arch ;
	
	/**
	 * Instantiates an archive of the specified length
	 * @param size Size of the archive
	 */
	public Archive(int size) {
		arch = new Vector<>(size) ;
	}
	
	/**
	 * Instantiates an archive of length 10
	 */
	public Archive() {
		arch = new Vector<>() ;
	}

	/**
	 * Returns the object at {@code index}
	 * @param index Index of the object
	 * @return Object inside the archive at {@code index}
	 */
	public E get(int index) {
		return arch.elementAt(index) ;
	}
	
	/**
	 * Appends the element {@code object}. The archive's length is increased by 1
	 * @param object Object to insert in the archive
	 */
	public void add(E object) {
		arch.addElement(object);
	}
	
	/**
	 * Removes the first object that equals {@code object} (if it's found) and compacts the archive
	 * @param object Object to remove
	 */
	public void remove(E object) {
		arch.removeElement(object) ;
	}
	
	/**
	 * Remove the object of {@code index} (if between 1 and the archive's length - 1)
	 * @param index Index of the object to remove
	 */
	public void remove(int index) {
		try {
			arch.removeElementAt(index);
		} catch (ArrayIndexOutOfBoundsException exception){
		}
	}
	
	/**
	 * @return Size of the array
	 */
	public int size() {
		return arch.size();
	}
	
}
