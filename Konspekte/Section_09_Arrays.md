# Section 9: Arrays

---

## 9.1 Was ist ein Array?

Ein **Array** ist eine Datenstruktur, die eine **feste Anzahl** von Elementen **gleichen Typs** speichert. Alle Elemente liegen hintereinander im Speicher und werden über einen **Index** angesprochen.

### Wichtige Eigenschaften:
- **Feste Größe**: Einmal erstellt, kann die Größe nicht mehr geändert werden
- **Index startet bei 0**: Das erste Element hat Index 0, das letzte hat Index `length - 1`
- **Gleicher Typ**: Alle Elemente müssen denselben Datentyp haben
- **Standardwerte**: `int` → 0, `double` → 0.0, `boolean` → false, `String` → null

---

## 9.2 Arrays erstellen

### Methode 1: Deklaration mit Größe

```java
int[] myIntArray = new int[10];      // 10 Elemente, alle mit 0 initialisiert
double[] myDoubleArray = new double[10];  // 10 Elemente, alle mit 0.0
```

### Methode 2: Direkte Initialisierung (Array-Literal)

```java
int[] firstTen = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
String[] sArray = {"Able", "Jane", "Mark", "Ralph", "David"};
```

### Methode 3: Anonymes Array (bei späterer Zuweisung)

```java
int[] newArray;
newArray = new int[] {5, 4, 3, 2, 1};   // new int[] ist hier PFLICHT!
// newArray = {5, 4, 3, 2, 1};          // ← FEHLER! Geht nur bei Deklaration
```

---

## 9.3 Auf Array-Elemente zugreifen

### Lesen und Schreiben

```java
int[] myIntArray = new int[10];
myIntArray[0] = 45;     // Erstes Element setzen
myIntArray[5] = 50;     // Sechstes Element setzen (Index 5!)

int first = myIntArray[0];   // Wert lesen: 45
```

### Länge eines Arrays

```java
int arrayLength = firstTen.length;   // KEINE Klammern! length ist ein Feld, keine Methode
System.out.println("length = " + arrayLength);   // → 10

// Letztes Element:
int lastElement = firstTen[firstTen.length - 1];  // → 10
```

**Achtung:** `length` ohne `()` – im Gegensatz zu `String.length()` mit Klammern!

### ArrayIndexOutOfBoundsException

```java
int[] arr = new int[5];     // Gültige Indizes: 0, 1, 2, 3, 4
arr[5] = 10;                // ← RUNTIME ERROR! Index 5 existiert nicht!
arr[-1] = 10;               // ← RUNTIME ERROR! Negative Indizes gibt es nicht!
```

---

## 9.4 Arrays durchlaufen (Iteration)

### Methode 1: Klassische for-Schleife

```java
for (int i = 0; i < newArray.length; i++) {
    System.out.print(newArray[i] + " ");
}
```

**Vorteile:** Zugriff auf den Index `i`, kann Elemente verändern.

### Methode 2: Enhanced for-Schleife (for-each)

```java
for (int element : newArray) {
    System.out.print(element + " ");
}
```

**Vorteile:** Kürzer, keine IndexOutOfBoundsException möglich.
**Nachteile:** Kein Zugriff auf den Index, kann das Array **nicht verändern**.

### Methode 3: Arrays.toString()

```java
import java.util.Arrays;

System.out.println(Arrays.toString(newArray));   // → [5, 4, 3, 2, 1]
```

Gibt eine formatierte String-Darstellung des gesamten Arrays aus.

### Array mit for-Schleife füllen

```java
int[] newArray = new int[5];
for (int i = 0; i < newArray.length; i++) {
    newArray[i] = newArray.length - i;   // → [5, 4, 3, 2, 1]
}
```

---

## 9.5 Die `java.util.Arrays`-Klasse

Die Klasse `Arrays` bietet viele nützliche statische Methoden für Arrays:

### `Arrays.sort()` – Sortieren

```java
int[] array = {5, 3, 1, 4, 2};
Arrays.sort(array);
// array ist jetzt: [1, 2, 3, 4, 5]

String[] sArray = {"Able", "Jane", "Mark", "Ralph", "David"};
Arrays.sort(sArray);
// sArray ist jetzt: [Able, David, Jane, Mark, Ralph] (alphabetisch)
```

**Wichtig:** `Arrays.sort()` sortiert das **Original-Array** (in-place)! Es wird keine Kopie erstellt.

### `Arrays.fill()` – Alle Elemente auf einen Wert setzen

```java
int[] array = new int[10];
Arrays.fill(array, 5);
// array ist jetzt: [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]
```

### `Arrays.copyOf()` – Kopie erstellen

```java
int[] original = {1, 2, 3, 4, 5};

// Gleiche Größe → exakte Kopie
int[] copy = Arrays.copyOf(original, original.length);
// copy: [1, 2, 3, 4, 5]

// Kleinere Größe → abgeschnitten
int[] smaller = Arrays.copyOf(original, 3);
// smaller: [1, 2, 3]

// Größere Größe → mit Nullen aufgefüllt
int[] larger = Arrays.copyOf(original, 8);
// larger: [1, 2, 3, 4, 5, 0, 0, 0]
```

**Warum kopieren?** Weil Arrays Referenztypen sind (siehe 9.6). Ohne Kopie zeigen zwei Variablen auf das **gleiche** Array.

### `Arrays.binarySearch()` – Element suchen

```java
String[] sArray = {"Able", "David", "Jane", "Mark", "Ralph"};  // MUSS sortiert sein!
int index = Arrays.binarySearch(sArray, "Mark");
if (index >= 0) {
    System.out.println("Found Mark at index " + index);   // → 3
}
```

**Voraussetzung:** Das Array **MUSS sortiert** sein! Sonst ist das Ergebnis undefiniert.

### `Arrays.equals()` – Arrays vergleichen

```java
int[] s1 = {1, 2, 3, 4, 5};
int[] s2 = {1, 2, 3, 4, 5, 0};

if (Arrays.equals(s1, s2)) {
    System.out.println("Gleich");
} else {
    System.out.println("Nicht gleich");   // → Nicht gleich (verschiedene Längen!)
}
```

**Niemals `==` für Array-Vergleiche verwenden!** `==` vergleicht nur die Referenzen (Speicheradressen), nicht die Inhalte.

### `Arrays.toString()` vs `Arrays.deepToString()`

```java
int[] simple = {1, 2, 3};
System.out.println(Arrays.toString(simple));     // → [1, 2, 3]

int[][] multi = {{1, 2}, {3, 4}};
System.out.println(Arrays.toString(multi));      // → [[I@1234, [I@5678]  ← FALSCH! Zeigt Referenzen
System.out.println(Arrays.deepToString(multi));  // → [[1, 2], [3, 4]]   ← RICHTIG! Zeigt Inhalte
```

**Regel:** `toString()` für eindimensionale Arrays, `deepToString()` für mehrdimensionale.

### Übersicht aller wichtigen Arrays-Methoden

| Methode | Beschreibung | Wichtig |
|---------|-------------|---------|
| `Arrays.toString(arr)` | Array als String ausgeben | Für 1D-Arrays |
| `Arrays.deepToString(arr)` | Mehrdimensionale Arrays als String | Für 2D+ Arrays |
| `Arrays.sort(arr)` | Aufsteigend sortieren (in-place) | Verändert Original! |
| `Arrays.fill(arr, val)` | Alle Elemente auf `val` setzen | |
| `Arrays.copyOf(arr, len)` | Kopie mit neuer Länge | Erstellt neues Array |
| `Arrays.binarySearch(arr, key)` | Element suchen | Array MUSS sortiert sein! |
| `Arrays.equals(a, b)` | Inhalt vergleichen | Nicht `==` verwenden! |

---

## 9.6 Array-Referenzen (Sehr wichtig!)

### Arrays sind Referenztypen

Wenn du ein Array einer anderen Variable zuweist, wird **nicht kopiert** – beide Variablen zeigen auf das **gleiche Array** im Speicher:

```java
int[] myIntArray = new int[5];
int[] anotherArray = myIntArray;   // KEINE Kopie! Gleiche Referenz!

anotherArray[0] = 1;
System.out.println(myIntArray[0]);   // → 1 (!!!) – myIntArray wurde auch geändert!
```

**Visualisierung:**
```
myIntArray    ──────┐
                    ├──→ [1, 0, 0, 0, 0]  (ein einziges Array im Speicher)
anotherArray  ──────┘
```

### Arrays als Methodenparameter

Wenn ein Array an eine Methode übergeben wird, erhält die Methode eine **Referenz** auf das Original-Array. Änderungen in der Methode wirken sich auf das Original aus:

```java
private static void modifyArray(int[] array) {
    array[1] = 2;   // Ändert das Original-Array!
}

int[] myIntArray = new int[5];
modifyArray(myIntArray);
System.out.println(myIntArray[1]);   // → 2 (geändert durch die Methode!)
```

### Echte Kopie erstellen

Um ein Array unabhängig zu verändern, muss man eine **Kopie** erstellen:

```java
int[] original = {1, 2, 3, 4, 5};
int[] copy = Arrays.copyOf(original, original.length);

copy[0] = 99;
System.out.println(original[0]);   // → 1 (unverändert!)
System.out.println(copy[0]);        // → 99
```

### Arrays und `instanceof`

```java
Object objectVariable = newArray;
if (objectVariable instanceof int[]) {
    System.out.println("objectVariable is really an int array");
}
```

Arrays sind Objekte in Java und können mit `instanceof` geprüft werden.

---

## 9.7 Variable Argumente (Varargs)

### Was sind Varargs?

**Varargs** (`...`) erlauben es, eine **beliebige Anzahl** von Argumenten an eine Methode zu übergeben. Intern werden sie als Array behandelt.

```java
private static void printText(String... textList) {   // Varargs!
    for (String t : textList) {
        System.out.println(t);
    }
}
```

### Verschiedene Aufrufmöglichkeiten

```java
printText("Hello");                         // 1 Argument
printText("Hello", "World", "again");       // 3 Argumente
printText();                                // 0 Argumente (leeres Array)

String[] splitStrings = "Hello World again".split(" ");
printText(splitStrings);                    // Ein Array direkt übergeben
```

### Regeln für Varargs

1. **Nur ein Vararg pro Methode**
2. **Muss der letzte Parameter sein**: `void method(int x, String... texts)` ✓
3. `void method(String... texts, int x)` ✗ FEHLER!
4. Intern ist ein Vararg einfach ein Array

### `main` mit Varargs

Sogar die `main`-Methode kann mit Varargs geschrieben werden:

```java
public static void main(String... args) {   // Statt String[] args
    // Funktioniert identisch!
}
```

---

## 9.8 Strings und Arrays

### `String.split()` – String in Array aufteilen

```java
String input = "Hello World again";
String[] words = input.split(" ");
// words: ["Hello", "World", "again"]

String csv = "1, 2, 3, 4, 5";
String[] numbers = csv.split(",");
// numbers: ["1", " 2", " 3", " 4", " 5"]  ← Vorsicht: Leerzeichen!
```

### `String.trim()` – Whitespace entfernen

```java
String[] splits = "1, 2, 3".split(",");
int value = Integer.parseInt(splits[1].trim());   // " 2" → "2" → 2
```

### `String.join()` – Array zu String zusammenfügen

```java
String[] sArray = {"first", "second", "third", "fourth", "fifth"};
String joined = String.join(",", sArray);
// → "first,second,third,fourth,fifth"
```

---

## 9.9 Mehrdimensionale Arrays

### 2D-Array erstellen

Ein 2D-Array ist ein "Array von Arrays":

```java
int[][] array2 = new int[4][4];   // 4 Zeilen × 4 Spalten

// Zugriff: array2[zeile][spalte]
array2[0][0] = 1;     // Zeile 0, Spalte 0
array2[1][2] = 5;     // Zeile 1, Spalte 2
```

### 2D-Array füllen

```java
for (int i = 0; i < array2.length; i++) {
    for (int j = 0; j < array2[i].length; j++) {
        array2[i][j] = (i * 10) + (j + 1);
    }
}
// Ergebnis: [[1, 2, 3, 4], [11, 12, 13, 14], [21, 22, 23, 24], [31, 32, 33, 34]]
```

### 2D-Array durchlaufen

```java
// Mit klassischer for-Schleife:
for (int i = 0; i < array2.length; i++) {
    for (int j = 0; j < array2[i].length; j++) {
        System.out.print(array2[i][j] + " ");
    }
    System.out.println();
}

// Mit enhanced for-each:
for (var outer : array2) {
    for (var element : outer) {
        System.out.print(element + " ");
    }
    System.out.println();
}

// Oder einfach:
System.out.println(Arrays.deepToString(array2));
```

### Zeilen ersetzen

Eine einzelne Zeile kann durch ein neues Array ersetzt werden:

```java
array2[1] = new int[] {10, 20, 30};   // Zeile 1 hat jetzt nur 3 Elemente!
```

**Wichtig:** In Java können Zeilen eines 2D-Arrays **unterschiedlich lang** sein ("jagged array" / "unregelmäßiges Array").

### Heterogene Arrays mit `Object[]`

```java
Object[] anyArray = new Object[3];
anyArray[0] = new String[] {"a", "b", "c"};           // String-Array
anyArray[1] = new String[][] {{"1", "2"}, {"3", "4"}}; // 2D-String-Array
anyArray[2] = new int[2][2][2];                         // 3D-int-Array

for (Object element : anyArray) {
    System.out.println("Type = " + element.getClass().getSimpleName());
}
```

---

## 9.10 Zufallszahlen mit `Random`

```java
import java.util.Random;

Random random = new Random();

int randomNumber = random.nextInt(100);    // 0 bis 99
int randomNumber2 = random.nextInt(10);     // 0 bis 9
```

### Array mit Zufallszahlen füllen

```java
private static int[] getRandomArray(int len) {
    Random random = new Random();
    int[] newInt = new int[len];
    for (int i = 0; i < len; i++) {
        newInt[i] = random.nextInt(100);   // Zufallszahl 0-99
    }
    return newInt;
}
```

---

## 9.11 Algorithmen mit Arrays

### Minimum finden

```java
private static int findMin(int[] array) {
    int min = Integer.MAX_VALUE;   // Größtmöglicher int als Startwert
    for (int el : array) {
        if (el < min) {
            min = el;
        }
    }
    return min;
}
```

**Alternative mit erstem Element als Startwert:**
```java
private static int findMin(int[] array) {
    int min = array[0];   // Erstes Element als Startwert
    for (int el : array) {
        if (el < min) {
            min = el;
        }
    }
    return min;
}
```

### Bubble Sort (Absteigend)

Bubble Sort vergleicht benachbarte Elemente und tauscht sie, wenn sie in falscher Reihenfolge sind. Das wird wiederholt, bis keine Tauschvorgänge mehr nötig sind.

```java
public static int[] sortIntegers(int[] array) {
    int[] sorted = Arrays.copyOf(array, array.length);   // Kopie erstellen!

    boolean swapped;
    do {
        swapped = false;
        for (int i = 0; i < sorted.length - 1; i++) {
            if (sorted[i] < sorted[i + 1]) {        // < für absteigend, > für aufsteigend
                int temp = sorted[i];                 // Tauschen
                sorted[i] = sorted[i + 1];
                sorted[i + 1] = temp;
                swapped = true;
            }
        }
    } while (swapped);

    return sorted;
}
```

**Schritt-für-Schritt für [7, 30, 35] (absteigend):**

| Durchgang | Vergleich | Tausch? | Array |
|-----------|-----------|---------|-------|
| 1 | 7 < 30? Ja | 30, 7, 35 | Tausch |
| 1 | 7 < 35? Ja | 30, 35, 7 | Tausch |
| 2 | 30 < 35? Ja | 35, 30, 7 | Tausch |
| 2 | 30 < 7? Nein | 35, 30, 7 | Kein Tausch |
| 3 | Keine Tausche mehr → Fertig | **[35, 30, 7]** | |

### Array umkehren (Reverse) – In-Place

```java
private static void reverse(int[] array) {
    int maxIndex = array.length - 1;
    int halfLength = array.length / 2;

    for (int i = 0; i < halfLength; i++) {
        int temp = array[i];
        array[i] = array[maxIndex - i];
        array[maxIndex - i] = temp;
    }
}
```

**Wie es funktioniert für [1, 2, 3, 4, 5]:**

| Schritt | `i` | Tausche | Array |
|---------|-----|---------|-------|
| 1 | 0 | arr[0] ↔ arr[4] | [5, 2, 3, 4, 1] |
| 2 | 1 | arr[1] ↔ arr[3] | [5, 4, 3, 2, 1] |
| | | arr[2] bleibt (Mitte) | **[5, 4, 3, 2, 1]** |

**Warum nur halbe Länge?** Weil jeder Tausch **zwei** Elemente bewegt. Bei voller Länge würde man alles zweimal tauschen → zurück zum Original!

### Array umkehren – Kopie erstellen

```java
private static int[] reversedCopy(int[] array) {
    int[] reversedArray = new int[array.length];
    int maxIndex = array.length - 1;

    for (int el : array) {
        reversedArray[maxIndex--] = el;   // Von hinten nach vorne füllen
    }

    return reversedArray;
}
```

**Vorteile:** Das Original-Array wird nicht verändert.

### Benutzereingabe als Array einlesen

```java
private static int[] readIntegers() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter a list of integers, separated by commas: ");
    String input = scanner.nextLine();

    String[] splits = input.split(",");
    int[] values = new int[splits.length];

    for (int i = 0; i < splits.length; i++) {
        values[i] = Integer.parseInt(splits[i].trim());   // .trim() entfernt Leerzeichen!
    }

    return values;
}
```

---

## 9.12 `Integer.MAX_VALUE` und `Integer.MIN_VALUE`

Java bietet Konstanten für die Grenzen der primitiven Typen:

```java
Integer.MAX_VALUE    // → 2.147.483.647 (größter int)
Integer.MIN_VALUE    // → -2.147.483.648 (kleinster int)
```

**Typische Verwendung beim Minimum/Maximum finden:**
```java
int min = Integer.MAX_VALUE;   // Start mit dem größtmöglichen Wert
for (int el : array) {
    if (el < min) min = el;     // Jeder Wert wird kleiner sein
}
```

---

## 9.13 Zusammenfassung der wichtigsten Konzepte

| Konzept | Beschreibung |
|---------|-------------|
| **Array erstellen** | `new int[10]` oder `{1, 2, 3}` |
| **Zugriff** | `array[index]` – Index startet bei 0 |
| **Länge** | `array.length` (ohne Klammern!) |
| **for-each** | `for (int el : array)` – einfacher, aber kein Index |
| **Arrays.toString()** | Array als String ausgeben |
| **Arrays.sort()** | Aufsteigend sortieren (in-place) |
| **Arrays.copyOf()** | Echte Kopie erstellen |
| **Arrays.fill()** | Alle Elemente auf einen Wert setzen |
| **Arrays.binarySearch()** | Element suchen (Array muss sortiert sein!) |
| **Arrays.equals()** | Inhalte vergleichen (nicht `==`!) |
| **Referenzsemantik** | Array-Zuweisung kopiert NUR die Referenz, nicht die Daten |
| **Varargs** | `String... args` – beliebig viele Argumente |
| **split/join** | String ↔ Array Konvertierung |
| **2D-Arrays** | `int[][]` – Array von Arrays |
| **deepToString()** | Für mehrdimensionale Arrays |
| **Bubble Sort** | Benachbarte Elemente vergleichen und tauschen |
| **Reverse** | Von beiden Enden zur Mitte tauschen |
| **Random** | `new Random().nextInt(100)` für Zufallszahlen |

---

*Dieser Konspekt basiert auf dem Code und den Übungen der Section 9 des Java-Kurses.*
