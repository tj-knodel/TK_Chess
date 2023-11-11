/*
 
    TABLE CLASS DOCUMENTATION

    CONSTRUCTORS:
        table(); Creates new table with an empty array
        table(tableType[]); Creates a new table with the sent array

    METHODS:
    size(): int; returns how many elements are in the array
    
    print(): void; Prints out the array in the format: "0 1 2 3 4 ..... \n"

    clear(): void; wipes the array,
    
    insert(int value): void; Inserts 'int' element at the end of the array

    insert(int value, int atIndex): void; Inserts 'int' element at position 'int' array, everything to the right of index gets shifted right

    clone(): table<tableType>; Returns a new table with the same values of this.
        ex. table<tableType> anotherTbl = originalTbl.clone();

    find(int value): int; Searches array for value, returns the first index with value, else '-1'

    remove(int atIndex): void; removes whatever element is at index 'int'. If removed index is before the end, everything past it shifts left
    
    at(int atIndex): tableType; returns whatever element is in the array at index 'int'

    ** DEFINITION - FROZEN: When a table is frozen, it cannot be changed by the table class and is set to read only. 
                            While frozen, the array can still be changed by indexing: 'myTable.array[0] = n'. 
                            table.insert(n , 0); // this function does nothing while the table is frozen. 
    
    freeze(): void; Freeze a table
    
    thaw(): void; Thaws (un-freeze) a table
    
    isFrozen(): boolean; returns true if the table is frozen, returns false if the table is not frozen. 
 */


package edu.kingsu.SoftwareEngineering.Chess.GUI.Table;
import java.util.ArrayList;
import java.util.Collections;

/**
 * table Class; An array with extra functionality
 * Influenced by lua:
 * https://create.roblox.com/docs/reference/engine/libraries/table
 *
 * @author Noah Bulas
 * @version V 2.0 - Al.28,23
 * @param <tableType> the Reference type to be stored in the array
 */
public class Table<tableType> extends abs_table {

    // ----------------------------------------------------
    // ------------------Variables------------------------
    // ----------------------------------------------------

    /**
     * Array List which holds the table data
     */
    protected ArrayList<tableType> array = new ArrayList<>();

    // ----------------------------------------------------
    // ------------------Constructors----------------------
    // ----------------------------------------------------
    /**
     * Default Constructor, initializes table
     */
    public Table() {
    }
 
    /**
     * Constructor, initializes table with given array
     * @param sentArray Array to initialize with
     */
    Table(tableType[] sentArray) {
        Collections.addAll(array, sentArray);
    }

    // ----------------------------------------------------
    // ------------------Member Methods--------------------
    // ----------------------------------------------------
    /**
     * Size of the array (elements used)
     * @return int; number of elements in the array
     */
    public int size() {
        return array.size();
    }

    /**
     * Prints the array in the format:
     * 0 1 3 4 6 7 8 9 10 11 12 13 14 15
     */
    public void print() {
        for (int i = 0; i < size(); ++i) {
            System.out.print(array.get(i) + " ");
        }
        System.out.println();
    }

    /**
     * Clears the array to empty
     */
    public void clear() {
        if (frozen)
            return;
        array.clear();
    }

    /**
     * Inserts element into the array, (at the end)
     * @param value value that's inserted into the array
     */
    public void insert(tableType value) {
        if (frozen)
            return;
        array.add(value);
    }

    /**
     * Inserts elements into the array at specified index, everything to the right of the index gets shifted right
     * @param value   value that's being inserted into the array
     * @param atIndex what index that value is being inserted at
     */
    public void insert(tableType value, int atIndex) {
        if (frozen)
            return;
        array.add(atIndex, value);
    }

    /**
     * Clones the table, the table being indexed remains unchanged
     * @return a new intTable object.
     */
    public Table<tableType> clone() {
        Table<tableType> newTable; // Create new table
        newTable = new Table<tableType>();
        // Copy new table to old table
        for (int i = 0; i < size(); ++i) {
            newTable.insert(array.get(i));
        }
        return newTable;
    }

    /**
     * Searches the array for the specified value
     * @param value Value to search for
     * @return returns the index when the value first occurred, otherwise -1
     */
    public int find(tableType value) {
        for (int i = 0; i < size(); ++i) {
            if (value == array.get(i))
                return i;
        }
        return -1;
    }


    /**
     * returns whichever element is at the specified index
     * @param atIndex the index to get an element from
     * @return the element at the index
     */
    public tableType at(int atIndex) {
        return array.get(atIndex);
    }

    /**
     * Removes whichever element is at the specified index
     * @param atIndex the index to remove an element from
     */
    public void remove(int atIndex) {
        if (frozen)
            return;
        array.remove(atIndex);
    }


}