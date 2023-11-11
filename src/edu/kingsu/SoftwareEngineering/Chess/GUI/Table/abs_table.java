/**
 * table Class; An array with extra functionality
 * Influenced by lua:
 * https://create.roblox.com/docs/reference/engine/libraries/table
 * 
 * @author Noah Bulas
 * @version V 2.0 - Al.28,23
 */
package edu.kingsu.SoftwareEngineering.Chess.GUI.Table;
public abstract class abs_table {

    boolean frozen = false;

    abs_table() {}

    //public int size()
    // public void print()
    // public void clear()
   // public void insert(tableType value)
    //public void insert(tableType value, int atIndex)
    // public table<tableType> clone()
    // public int find(tableType value)
    // public void remove(int atIndex)
    //public tableType at(int atIndex)

    /**
     * Freeze the table, sets its to read-only
     */
    public void freeze() {
        frozen = true;
    }

    /**
     * Thaws (unfreeze the table), allows it to be read and write.
     */
    public void thaw() {
        frozen = true;
    }

    /**
     * Returns the frozen value of the table
     * @return true if table is frozen, false if table is not frozen
     */
    public boolean isFrozen() {
        return frozen;
    }


}
