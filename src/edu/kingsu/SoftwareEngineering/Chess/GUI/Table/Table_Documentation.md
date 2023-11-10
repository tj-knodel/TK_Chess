# Table

An array with extra functionality.  Similar to a "bag" or a "multi-set".
Influenced by [lua](https://create.roblox.com/docs/reference/engine/libraries/table).

There are 4-types of tables: 
- `IntTable`, (for `int`'s)
- `DoubleTable`, (for `double`'s)
- `CharTable`,  (for `char`'s)
- `Table`, (for any reference types)

All types inherit from `abs_table` (abstract table).

### Definition: "frozen"
When a table is frozen, it is set to read-only and cannot be modified.

## Summary

### Constructors
    table()
Creates new table with an empty array.
        
    table(tableType[])
Creates a new table with the sent array.

### Methods

    freeze(): void
Freezes the table.

    thaw(): void
Thaws (un-freeze) the table.

    isFrozen(): boolean
Returns the frozen value of the table.

    size(): int
Returns how many elements are in the array.

    print(): void
Prints out the array in the format: "0 1 2 3 4 ...".

    clear(): void
Wipes the array.

     insert(tableType value): void
Inserts a value into the array at the end.

    insert(tableType value, int atIndex): void
Inserts a value at 'atIndex' position. Everything right of the index gets shifted right

    find(tableType value): int
Searches the array for 'value', returns the first index with 'value', else '-1'.

    clone(): table
Returns a new table with the same values as 'this'

    remove(int atIndex): void
Removes whichever element is at 'atIndex'. Everything to the right of 'atIndex' get shifted left.

    at(int atIndex): tableType
Returns whatever element in the array is at 'atIndex'.



## Constructors

### table: *tableObject*
Constructor to create a table. If no parameter is provided, it will start with an empty array. If a parameter is added, the table will be created using that array. The values from the array are copied over, it does not modify the original values.
```java
IntTable data = new IntTable(); // Creates a new table of the type int
CharTable data = new CharTable(); // Creates a new table of the type char
DoubleTable data = new DoubleTable(); // Creates a new table of the type double
Table<Integer> data = new Table<Integer>(); // Creates a new table of the type Integer

int[] myArray = new {2, 4, 5, 6, 7, 8};
IntTable myTable = new IntTable(myArray);  // Create a table of type int with `myArray` 
```

#### **Parameters**
> ``` tableType[] ```  : *(Optional)* Array for the table to start with.

#### **Returns**
>  tableObject


## Methods

### freeze: *void*
"Freezes" a table. Sets it to read only. Any further method calls to modify the table will be ignored.
```java
IntTable myTable = new IntTable();  

myTable.freeze();
```

#### **Parameters**

None.

#### **Returns**
None.

<br>

### thaw: *void*
"Un-freezes" a table. Sets the table to read-and-write if it was ever set to read-only.
```java
IntTable myTable = new IntTable();  

myTable.freeze(); // freezes the table
myTable.thaw(); // Undo the freeze on the table
```

#### **Parameters**
None.

#### **Returns**
None.

<br>

### isFrozen: *boolean*
Returns `true` or `false` whether or not if the table is frozen. Returns `true` if the table is frozen (read-only) or `false` if its not frozen.
```java
IntTable myTable = new IntTable();  

myTable.freeze(); // freezes the table
myTable.thaw(); // Undo the freeze on the table

boolean frozenArray = myTable.isFrozen();

if (frozenArray) {
    System.out.println("The table is frozen and cant be modified!");
 } else {
     System.out.println("The table is not frozen and can be modified!");
 }

```

#### Parameters
None.

### Returns
> boolean

<br>


### size: *int*
Returns how many elements are in the array. ()
```java
IntTable myTable = new IntTable();  

System.out.println("The size of the table is: " + myTable.size());
```

#### Parameters
None.

### Returns
> int

<br>

### size: *int*
Returns how many elements are in the array. ()
```java
IntTable myTable = new IntTable();  

System.out.println("The size of the table is: " + myTable.size());
```

#### Parameters
None.

### Returns
> int

<br>