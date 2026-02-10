# Extras: Exceptions, Tricky Codes & Privat-Projekte

---

## Inhaltsverzeichnis

1. [Exceptions (Ausnahmebehandlung)](#1-exceptions-ausnahmebehandlung)
2. [Tricky Java Codes – Rätsel & Erklärungen](#2-tricky-java-codes)
3. [Privat-Projekte](#3-privat-projekte)

---

## 1. Exceptions (Ausnahmebehandlung)

### 1.1 Was ist eine Exception?

Eine **Exception** ist ein Fehler, der **während der Laufzeit** (nicht beim Kompilieren!) auftritt. Exceptions unterbrechen den normalen Programmfluss.

### 1.2 Häufige Exception-Typen

| Exception | Auslöser | Beispiel |
|---|---|---|
| `ArithmeticException` | Division durch 0 | `5 / 0` |
| `NullPointerException` | Zugriff auf `null`-Referenz | `null.nextInt()` |
| `ArrayIndexOutOfBoundsException` | Ungültiger Array-Index | `arr[7]` bei `arr.length == 5` |
| `InputMismatchException` | Falsche Eingabe beim Scanner | Text statt Zahl eingeben |
| `NumberFormatException` | String → Zahl fehlschlägt | `Integer.parseInt("abc")` |
| `StringIndexOutOfBoundsException` | Ungültiger String-Index | `"Hi".charAt(5)` |

### 1.3 Exception-Hierarchie

```
Throwable
├── Error            ← Schwerwiegend (OutOfMemoryError, StackOverflowError)
│                      → Sollte man NICHT fangen!
└── Exception
    ├── RuntimeException (unchecked)
    │   ├── ArithmeticException
    │   ├── NullPointerException
    │   ├── ArrayIndexOutOfBoundsException
    │   ├── ClassCastException
    │   └── IllegalArgumentException
    └── IOException, SQLException... (checked)
        → Müssen mit try-catch oder throws deklariert werden
```

**Unchecked Exceptions** (RuntimeException): Der Compiler zwingt dich nicht, sie zu behandeln.
**Checked Exceptions**: Der Compiler erzwingt Behandlung (try-catch oder `throws`-Deklaration).

### 1.4 try-catch-finally

```java
Scanner scanner = new Scanner(System.in);
try {
    int number = scanner.nextInt();     // Kann InputMismatchException werfen
    System.out.println(number);
} catch (InputMismatchException exception) {
    System.out.println("Bitte eine Zahl eingeben!");
} finally {
    scanner.close();                     // Wird IMMER ausgeführt
    System.out.println("Scanner geschlossen");
}
```

| Block | Wann wird er ausgeführt? |
|---|---|
| `try` | Immer – enthält den "riskanten" Code |
| `catch` | Nur wenn die passende Exception auftritt |
| `finally` | **IMMER** – egal ob Exception oder nicht |

**Wichtig:** `finally` eignet sich zum Aufräumen (Dateien schließen, Ressourcen freigeben).

### 1.5 Mehrere catch-Blöcke

Man kann mehrere `catch`-Blöcke verwenden, um verschiedene Exceptions unterschiedlich zu behandeln:

```java
ExceptionThrower exceptionThrower = new ExceptionThrower();
try {
    exceptionThrower.divide();
} catch (ArithmeticException e) {
    System.out.println(e.getMessage());          // Spezifische Nachricht
} catch (InputMismatchException e) {
    System.out.println("Falsche Eingabe!");      // Andere Exception
} catch (Exception e) {
    System.out.println("Ein Fehler ist aufgetreten");  // Catch-All
}
```

**Reihenfolge ist wichtig!** Spezifischere Exceptions müssen **vor** allgemeineren stehen:
- ✅ `ArithmeticException` → `Exception`
- ❌ `Exception` → `ArithmeticException` (kompiliert nicht!)

### 1.6 throw – Eigene Exceptions werfen

Mit `throw` kann man manuell eine Exception auslösen:

```java
public void divide() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Bitte geben Sie den Zahl ein:");
    int number = scanner.nextInt();

    if (number == 0) {
        throw new ArithmeticException("Nicht durch 0 teilen!");
    }
    if (number == 1) {
        throw new ArithmeticException("Nicht durch 1 teilen!");
    }

    System.out.println(10 / number);
}
```

**`throw`** = Exception manuell werfen (in einer Methode)
**`throws`** = Deklaration, dass eine Methode eine Exception werfen *kann*:

```java
public void readFile() throws IOException {
    // Code der IOException werfen kann
}
```

### 1.7 getMessage()

Jede Exception hat eine Nachricht, die man mit `getMessage()` abrufen kann:

```java
catch (ArithmeticException e) {
    System.out.println(e.getMessage());  // "Nicht durch 0 teilen!"
}
```

### 1.8 Best Practices

| Regel | Erklärung |
|---|---|
| Spezifisch fangen | `catch (ArithmeticException e)` statt `catch (Exception e)` |
| Nicht leer fangen | `catch (Exception e) { }` → Fehler werden verschluckt! |
| finally für Cleanup | Scanner, Streams, Connections schließen |
| Nicht für Logik nutzen | Exceptions sind für **Ausnahmen**, nicht für normalen Kontrollfluss |
| Früh werfen, spät fangen | Exception dort werfen, wo der Fehler entsteht; dort fangen, wo man sinnvoll reagieren kann |

---

## 2. Tricky Java Codes

Diese Sammlung deckt häufige Java-Fallstricke und Überraschungen auf. Jedes Rätsel wird zuerst als "Puzzle" (ungelöst) und dann mit Erklärung präsentiert.

---

### 2.1 Integer Overflow – `MainInt`

**Puzzle:** Was gibt dieser Code aus?
```java
System.out.println((24*60*60*1000*1000) / (24*60*60*1000));
System.out.println((24*60*60*1000*1000l) / (24*60*60*1000));
```

**Erwartung:** `1000` in beiden Fällen?

**Tatsächliche Ausgabe:**
```
5
1000
```

**Erklärung:**
- `24*60*60*1000*1000` wird als **int** berechnet (alle Literale sind `int`)
- Das Ergebnis ist `86.400.000.000`, aber `int` kann maximal `2.147.483.647` speichern
- Es kommt zum **Integer Overflow** → das Ergebnis wird abgeschnitten
- Mit dem Suffix `l` (z.B. `1000l`) wird die Berechnung als **long** durchgeführt → kein Overflow

**Weitere Beispiele:**
```java
System.out.println((int) 24*60*60*1000*1000);  // Cast wirkt nur auf 24!
System.out.println(24*60*60*1000*1000l);        // 86400000000 (long)
```

> **Merke:** Numerische Literale in Java sind standardmäßig `int`. Bei großen Berechnungen immer `L` oder `l` verwenden!

---

### 2.2 Byte-Cast-Kette – `MainByte`

**Puzzle:** Was gibt dieser Code aus?
```java
System.out.println((byte) -1);
System.out.println((char) (byte) -1);
System.out.println((int) (char) (byte) -1);
System.out.println((int) (char) -1);
```

**Ausgabe:**
```
-1
(leeres Zeichen / ?)
65535
65535
```

**Erklärung – Schritt für Schritt:**

| Schritt | Wert | Bits | Erklärung |
|---|---|---|---|
| `-1` als int | -1 | `11111111 11111111 11111111 11111111` | 32 Bit, signed |
| `(byte) -1` | -1 | `11111111` | 8 Bit, signed → immer noch -1 |
| `(char)(byte) -1` | 65535 | `11111111 11111111` | Byte → int (sign extension zu -1) → char (**unsigned** 16 Bit!) |
| `(int)(char)(byte) -1` | 65535 | `00000000 00000000 11111111 11111111` | char ist unsigned → zero extension |

> **Merke:** `char` ist der **einzige unsigned** Datentyp in Java (0 bis 65535). Bei Casts von signed zu char findet Sign Extension statt, dann Interpretation als unsigned.

---

### 2.3 Float-Ungenauigkeit – `MainFloat`

**Puzzle:** Was gibt dieser Code aus?
```java
float f = 0.0f;
for (int i = 0; i < 10; i++) {
    f += 0.10f;
}
System.out.println(f);
System.out.println(6.0 - 5.40);
```

**Erwartung:** `1.0` und `0.6`?

**Tatsächliche Ausgabe:**
```
1.0000001
0.5999999999999996
```

**Erklärung:**
- Gleitkommazahlen (`float`, `double`) werden im **IEEE 754**-Format gespeichert
- **0.1 kann nicht exakt binär dargestellt werden** (ähnlich wie 1/3 nicht exakt dezimal darstellbar ist)
- Jede Addition akkumuliert den Rundungsfehler
- `float` hat nur ~7 Dezimalstellen Genauigkeit, `double` ~15-16

> **Merke:** Für exakte Berechnungen (z.B. Geld) → `BigDecimal` verwenden! Niemals `float`/`double` für Finanzberechnungen.

---

### 2.4 Integer Comparison – `MainComparision`

**Puzzle:** Was gibt dieser Code aus?
```java
Integer a, b;
a = 29;
b = 29;
System.out.println(a == b);  // ???

// Und wenn man stattdessen:
a = 129;
b = 129;
System.out.println(a == b);  // ???
```

**Ausgabe:**
```
true   // bei 29
false  // bei 129
```

**Erklärung:**
- `Integer` ist ein **Objekt** (Wrapper-Klasse), kein primitiver Typ
- `==` vergleicht bei Objekten die **Referenz** (Speicheradresse), nicht den Wert!
- Java cached `Integer`-Objekte im Bereich **-128 bis 127** (Integer Cache)
- Werte in diesem Bereich → **gleiches Objekt** → `==` ist `true`
- Werte außerhalb → **verschiedene Objekte** → `==` ist `false`

```java
// Korrekt: Werte vergleichen mit .equals()
System.out.println(a.equals(b));  // true, egal welcher Wert
```

> **Merke:** Wrapper-Klassen (`Integer`, `Long`, etc.) immer mit `.equals()` vergleichen, nie mit `==`!

---

### 2.5 Infinite Loop mit Integer-Objekten – `MainInfinitLoop`

**Puzzle:** Endlosschleife oder nicht?
```java
Integer i = new Integer(1);
Integer j = new Integer(1);

while (i <= j && j <= i && i != j) {
    System.out.println(".");
}
```

**Antwort:** **Endlosschleife!**

**Erklärung:**
- `i <= j` → Unboxing zu `int`: `1 <= 1` → `true`
- `j <= i` → Unboxing zu `int`: `1 <= 1` → `true`
- `i != j` → **Referenzvergleich** (weil `!=` bei Objekten): verschiedene Objekte → `true`
- Alle drei Bedingungen sind `true` → Schleife läuft ewig

> **Merke:** `<=` und `>=` erzwingen Unboxing (Wertevergleich), aber `==` und `!=` vergleichen Referenzen bei Objekten!

---

### 2.6 NaN-Schleife – `MainIninitLoop2`

**Puzzle:** Wie erstellt man eine Variable `i` vom Typ `double`, sodass `i != i` gilt?
```java
double i = ???;
while (i != i) {
    System.out.println(".");
}
```

**Lösung:**
```java
double i = Double.NaN;
// Oder:
double i = 0.0 / 0.0;
```

**Erklärung:**
- `NaN` (Not a Number) ist ein spezieller Wert in IEEE 754
- **NaN ist mit NICHTS gleich, nicht einmal mit sich selbst!**
- `Double.NaN != Double.NaN` → `true`
- `Double.NaN == Double.NaN` → `false`
- Deshalb: Die Schleife ist eine **Endlosschleife**

> **Merke:** `NaN != NaN` ist `true`! Um auf NaN zu prüfen: `Double.isNaN(x)`

---

### 2.7 Unicode-Troll – `MainInfiniteLoop2Troll`

**Puzzle:** Warum ist das eine Endlosschleife?
```java
int i = 1;
int і = 2;

while (i != і) {
    System.out.println(".");
}
```

**Erklärung:**
- Die beiden Variablen `i` und `і` sehen gleich aus, sind aber **verschiedene Unicode-Zeichen**!
- `i` = lateinisches Kleinbuchstabe i (U+0069)
- `і` = kyrillisches Kleinbuchstabe i (U+0456)
- Java erlaubt Unicode-Zeichen in Variablennamen
- `1 != 2` → immer `true` → Endlosschleife

> **Merke:** Java-Bezeichner können Unicode-Zeichen enthalten. Visually identische Zeichen aus verschiedenen Alphabeten sind verschiedene Bezeichner!

---

### 2.8 Labels & Kommentare – `MainLabel`

**Puzzle:** Kompiliert dieser Code?
```java
System.out.print("URL:");
http://www.ilfattoquotidiano.it;
System.out.println("FTP:");
ftp://ftp.emergency.org/pub/;
```

**Antwort:** **Ja, kompiliert!**

**Erklärung:**
- `http:` wird als **Label** interpretiert (wie bei `break myLabel;`)
- `//www.ilfattoquotidiano.it;` wird als **Kommentar** interpretiert
- Gleiches gilt für `ftp:` (Label) und `//ftp.emergency.org/pub/;` (Kommentar)
- Labels sind gültige Java-Syntax (werden bei verschachtelten Schleifen mit `break`/`continue` verwendet)

> **Merke:** `identifier:` ist ein Label in Java. `//` leitet einen Kommentar ein. Zusammen sieht es wie eine URL aus, ist aber gültiger Java-Code!

---

### 2.9 Compound Assignment & Casting – `MainShort`

**Puzzle:** Warum kompiliert das eine, aber nicht das andere?
```java
short x = 0;
int i = 100000;
// x = x + i;    // KOMPILIERT NICHT!

short l = 0;
int m = 100000;
l += m;           // KOMPILIERT! Warum?
```

**Ausgabe von `l`:** `-31072` (nicht 100000!)

**Erklärung:**
- `x = x + i` → `x + i` ergibt einen `int` (Java rechnet immer mindestens mit `int`)
- `int` → `short` ist ein **Narrowing Cast** → Compiler-Fehler ohne expliziten Cast
- `l += m` ist **syntaktischer Zucker** für: `l = (short)(l + m)`
- Der Compound-Assignment-Operator enthält einen **impliziten Cast**!
- 100000 passt nicht in `short` (-32768 bis 32767) → Overflow → `-31072`

> **Merke:** `a += b` ist NICHT identisch mit `a = a + b`! Der Compound-Operator castet implizit zurück zum Typ von `a`.

---

### 2.10 Unicode in Javadoc – `MainHelloWorld`

**Puzzle:** Warum kompiliert die gelöste Version, aber die ungelöste nicht?

**Ungelöst (kompiliert nicht):**
```java
/**
 * @see c:\andrea\programming\u3070\java\Test.java
 */
```

**Gelöst (kompiliert):**
```java
/**
 * @see c:\andrea\programming\\units\java\Test.java
 */
```

**Erklärung:**
- Java verarbeitet **Unicode-Escapes** (`\uXXXX`) **vor** dem Kompilieren
- `\u3070` wird als Unicode-Zeichen interpretiert (ば = Hiragana BA)
- In der ungelösten Version wird `\u3070` im Javadoc-Kommentar als Unicode interpretiert und verursacht unerwartete Zeichen
- In der gelösten Version: `\\u` → der Backslash wird escaped, kein Unicode-Escape

> **Merke:** `\uXXXX` wird in Java **überall** verarbeitet – auch in Kommentaren und Strings! Unicode-Escapes sind die allererste Phase der Kompilierung.

---

### 2.11 String-Verkettung mit Vorzeichen – `MainString`

**Puzzle:** Was gibt dieser Code aus?
```java
String myStrangeString = + 1 + - - + - - + + + + - 1 + " ";
System.out.print(myStrangeString);
```

**Ausgabe:** `0 ` (Null mit Leerzeichen)

**Erklärung – Schritt für Schritt:**
1. Die `+` und `-` vor Zahlen sind **unäre Operatoren** (Vorzeichen)
2. `+ 1` = +1 = `1`
3. `- - + - - + + + + - 1`:
   - Parsing von rechts: `- 1` = -1
   - `+ (-1)` = -1, `+ (-1)` = -1, `+ (-1)` = -1
   - `- (-1)` = 1, `- (1)` = -1
   - `+ (-1)` = -1
4. `1 + (-1)` = `0` (int-Addition, weil kein String beteiligt)
5. `0 + " "` = `"0 "` (String-Konkatenation)

> **Merke:** Unäre `+` und `-` Operatoren haben höhere Priorität als binäre. Die String-Verkettung beginnt erst, wenn ein String-Operand erscheint.

---

### 2.12 `char`-Literal und Unicode – `MainChar`

**Code:**
```java
char cr = 0x000A;  // Unicode für Linefeed (Zeilenumbruch)
System.out.println(cr);
```

**Ausgabe:** Ein Zeilenumbruch (Leerzeile)

**Erklärung:**
- `char` kann direkt mit Hex-Werten initialisiert werden
- `0x000A` = 10 dezimal = ASCII/Unicode für "Line Feed" (Zeilenumbruch)
- `System.out.println(cr)` gibt den Zeilenumbruch aus + ein weiterer durch `println`

---

### 2.13 `@Deprecated` – `MainDeprecated`

**Code:**
```java
@Deprecated
public static void myMethod() { }

public static void main(String[] args) {
    myMethod();  // Warnung, aber kompiliert!
}
```

**Erklärung:**
- `@Deprecated` markiert eine Methode als **veraltet** (sollte nicht mehr verwendet werden)
- Der Compiler gibt eine **Warnung** aus, aber der Code **kompiliert trotzdem**
- In IDEs wird die Methode durchgestrichen dargestellt: ~~myMethod()~~
- Wird oft bei API-Änderungen verwendet, um alte Methoden auslaufen zu lassen

---

### 2.14 Zusammenfassung: Tricky Code Fallstricke

| Thema | Fallstrick | Lösung |
|---|---|---|
| **Integer Overflow** | `int`-Literale bei großen Berechnungen | `L`-Suffix verwenden |
| **Byte/Char Casting** | `char` ist unsigned, sign extension | Cast-Ketten verstehen |
| **Float-Genauigkeit** | 0.1 nicht exakt darstellbar | `BigDecimal` verwenden |
| **Integer ==** | Cache nur -128 bis 127 | `.equals()` verwenden |
| **Object ==** | Referenz- statt Wertvergleich | `.equals()` verwenden |
| **NaN** | `NaN != NaN` ist true | `Double.isNaN()` verwenden |
| **Unicode-Bezeichner** | Gleich aussehende Zeichen | ASCII-Zeichen verwenden |
| **Labels** | `http:` ist ein gültiges Label | Semantik kennen |
| **Compound Assignment** | `+=` castet implizit | Typ-Kompatibilität prüfen |
| **Unicode-Escapes** | `\uXXXX` auch in Kommentaren | `\\u` zum Escapen |
| **String-Verkettung** | Unäre Operatoren vor Verkettung | Klammern verwenden |
| **@Deprecated** | Warnung, kein Fehler | Markierte Methoden ersetzen |

---

## 3. Privat-Projekte

### 3.1 Austrian Self-Employed Calculator

Ein umfangreiches Konsolenprogramm zur Berechnung der **Steuer- und Sozialversicherungsbelastung** für Selbständige in Österreich.

#### Verwendete Java-Konzepte

| Konzept | Verwendung im Projekt |
|---|---|
| **BigDecimal** | Exakte Finanzberechnungen (kein `float`/`double`!) |
| **MathContext & RoundingMode** | Kontrollierte Rundung bei Divisionen |
| **Enum** | `Mode { ECONOMIC, BESCHIED }` für Berechnungsmodi |
| **Inner Class** | `TaxBracket` (Steuerstufe), `Result` (Ergebnis-Container) |
| **Static Methods** | Alle Berechnungen als statische Hilfsmethoden |
| **Scanner** | Benutzereingaben mit Fallback auf Demo-Werte |
| **Array** | `TaxBracket[]` für den Steuertarif |
| **Formatierung** | Eigene `fmt()` und `pct()` Methoden |

#### BigDecimal – Warum nicht double?

```java
// FALSCH für Geldbeträge:
double preis = 0.1 + 0.2;  // = 0.30000000000000004

// RICHTIG:
BigDecimal preis = new BigDecimal("0.1").add(new BigDecimal("0.2"));  // = 0.3 exakt
```

Wichtige BigDecimal-Methoden:

| Methode | Beschreibung |
|---|---|
| `add(other)` | Addition |
| `subtract(other)` | Subtraktion |
| `multiply(other)` | Multiplikation |
| `divide(other, scale, roundingMode)` | Division mit Rundung |
| `compareTo(other)` | Vergleich (-1, 0, 1) |
| `max(other)` / `min(other)` | Maximum/Minimum |
| `setScale(scale, roundingMode)` | Dezimalstellen setzen |
| `signum()` | Vorzeichen (-1, 0, 1) |

> **Wichtig:** Bei BigDecimal `compareTo()` statt `==` oder `.equals()` verwenden! (`new BigDecimal("1.0").equals(new BigDecimal("1.00"))` ist `false`!)

#### Progressiver Steuertarif (Implementierung)

```java
private static BigDecimal calcIncomeTax(BigDecimal taxable, TaxBracket[] table) {
    BigDecimal remaining = taxable.max(bd("0"));
    BigDecimal lower = bd("0");
    BigDecimal tax = bd("0");

    for (TaxBracket br : table) {
        BigDecimal width = upper.subtract(lower).max(bd("0"));
        BigDecimal span = remaining.min(width);    // Nur den Teil in dieser Stufe
        tax = tax.add(span.multiply(br.rate));      // Steuer für diese Stufe
        remaining = remaining.subtract(span);       // Rest für nächste Stufe
        if (remaining.signum() <= 0) break;
    }
    return tax;
}
```

Das ist ein klassischer **Piecewise-Algorithmus** (stückweise Berechnung), wie er bei progressiven Steuersätzen verwendet wird.

---

### 3.2 Singleton Pattern

Das **Singleton-Pattern** stellt sicher, dass von einer Klasse nur **eine einzige Instanz** existiert.

#### Implementierung (Thread-Safe mit Double-Checked Locking)

```java
public class Singleton {

    private static volatile Singleton instance;   // volatile für Thread-Sicherheit
    private String data;

    private Singleton(String data) {              // Private Konstruktor!
        this.data = data;
    }

    public static Singleton getInstance(String data) {
        Singleton result = instance;
        if (result == null) {                      // 1. Check (ohne Lock)
            synchronized (Singleton.class) {       // Lock
                result = instance;
                if (result == null) {              // 2. Check (mit Lock)
                    instance = result = new Singleton(data);
                }
            }
        }
        return result;
    }
}
```

#### Schlüsselkonzepte

| Konzept | Erklärung |
|---|---|
| **Private Konstruktor** | Verhindert `new Singleton()` von außen |
| **Static Instance** | Einzige Instanz wird in Klassenvariable gespeichert |
| **`volatile`** | Garantiert, dass alle Threads den aktuellen Wert sehen |
| **`synchronized`** | Nur ein Thread kann gleichzeitig in den Block |
| **Double-Checked Locking** | Erst ohne Lock prüfen (Performance), dann mit Lock erstellen |

#### Warum Double-Checked Locking?

```java
// Einfache Version (langsamer):
public static synchronized Singleton getInstance() {
    if (instance == null) {
        instance = new Singleton();
    }
    return instance;
}
// Problem: JEDER Aufruf muss auf das Lock warten, auch wenn instance schon existiert

// Double-Checked Version:
// Erster Check OHNE synchronized → schnell, wenn instance schon existiert
// Zweiter Check MIT synchronized → sicher bei gleichzeitiger Erstellung
```

#### Verwendung

```java
Singleton s1 = Singleton.getInstance("Erster Aufruf");
Singleton s2 = Singleton.getInstance("Zweiter Aufruf");
// s1 == s2 → true (gleiche Instanz!)
// s2.data = "Erster Aufruf" (zweiter Aufruf ändert nichts!)
```

---

### 3.3 Präsenztag-Code

Die Dateien im `Praesenztag`-Ordner sind die gelösten Versionen der Tricky Codes aus Abschnitt 2, erweitert mit **Kommentaren und Erklärungen** des Professors. Sie enthalten die gleichen Puzzles mit Anmerkungen wie:

- Warum `char` unsigned ist
- Wie Integer-Caching funktioniert
- Was Labels in Java sind
- Wie `\uXXXX` verarbeitet wird

---

## Gesamtzusammenfassung: Wichtigste Regeln

| # | Regel |
|---|---|
| 1 | **Exceptions** mit spezifischen `catch`-Blöcken fangen, nie leere catch-Blöcke |
| 2 | **`finally`** für Ressourcen-Cleanup verwenden (oder try-with-resources) |
| 3 | **`throw`** für eigene Fehler, **`throws`** für Deklaration |
| 4 | Bei großen Zahlen **`L`-Suffix** verwenden (Integer Overflow vermeiden) |
| 5 | **`BigDecimal`** für Geld/exakte Berechnungen, nie `float`/`double` |
| 6 | Wrapper-Klassen mit **`.equals()`** vergleichen, nie mit `==` |
| 7 | **`NaN != NaN`** ist `true` → `Double.isNaN()` verwenden |
| 8 | **`+=`** enthält impliziten Cast (≠ `a = a + b`) |
| 9 | **`\uXXXX`** wird vor dem Kompilieren überall verarbeitet |
| 10 | **Singleton**: Private Konstruktor + statische getInstance() + volatile |
