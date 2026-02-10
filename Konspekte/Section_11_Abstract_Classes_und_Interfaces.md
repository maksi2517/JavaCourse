# Section 11: Abstrakte Klassen & Interfaces

---

## 11.1 Abstrakte Klassen (Abstract Classes)

### Was ist eine abstrakte Klasse?

Eine **abstrakte Klasse** ist eine Klasse, von der **keine Objekte erstellt werden können**. Sie dient als Vorlage (Template) für Kindklassen und kann sowohl fertige Methoden als auch **abstrakte Methoden** (ohne Implementierung) enthalten.

```java
public abstract class Animal {
    protected String type;
    private String size;
    private double weight;

    public Animal(String type, String size, double weight) {
        this.type = type;
        this.size = size;
        this.weight = weight;
    }

    // Abstrakte Methoden: KEINE Implementierung, nur Signatur
    public abstract void move(String speed);
    public abstract void makeNoise();

    // Konkrete Methode: HAT eine Implementierung
    public final String getExplicitType() {
        return getClass().getSimpleName() + " (" + type + ")";
    }
}
```

### Warum abstrakt?

```java
// Das geht NICHT:
Animal animal = new Animal("animal", "big", 100);   // COMPILE ERROR!

// Das geht:
Dog dog = new Dog("Wolf", "big", 100);               // Konkrete Kindklasse
Animal animal = new Dog("Wolf", "big", 100);          // Polymorphismus: OK!
```

**Logik dahinter:** Ein "generisches Tier" ergibt keinen Sinn – man hat immer einen **konkreten** Hund, Fisch oder Pferd. Aber alle Tiere haben gemeinsame Eigenschaften (Typ, Größe, Gewicht) und gemeinsames Verhalten (sich bewegen, Geräusche machen).

### Abstrakte Methoden

Eine **abstrakte Methode** hat:
- Das Schlüsselwort `abstract`
- **Keinen Methodenkörper** (keine geschweifte Klammern, nur Semikolon)
- Muss von **jeder konkreten Kindklasse** implementiert werden

```java
// In der abstrakten Klasse:
public abstract void move(String speed);     // Kein Body!
public abstract void makeNoise();            // Kein Body!

// In der konkreten Kindklasse (MUSS implementiert werden):
@Override
public void move(String speed) {
    if (speed.equals("slow")) {
        System.out.println(getExplicitType() + " walking");
    } else {
        System.out.println(getExplicitType() + " running");
    }
}
```

### Konkrete vs Abstrakte Methoden in abstrakten Klassen

Eine abstrakte Klasse kann **beides** haben:

| Typ | Beschreibung | Beispiel |
|-----|-------------|---------|
| **Abstrakte Methode** | Kein Body, muss überschrieben werden | `public abstract void move(String speed);` |
| **Konkrete Methode** | Hat Body, kann überschrieben werden | `public void eat() { ... }` |
| **Final Methode** | Hat Body, kann **NICHT** überschrieben werden | `public final String getExplicitType() { ... }` |

### Das Schlüsselwort `final`

```java
public final String getExplicitType() {
    return getClass().getSimpleName() + " (" + type + ")";
}
```

`final` bei Methoden bedeutet: Diese Methode kann in Kindklassen **nicht überschrieben** werden. Das garantiert, dass alle Subtypen das gleiche Verhalten haben.

`final` kann auch auf Klassen angewendet werden:
```java
public final class String { ... }   // String kann NICHT erweitert werden!
```

### Abstrakte Zwischenklasse: Mammal

Man kann auch eine abstrakte Klasse von einer anderen abstrakten Klasse erben lassen:

```java
abstract class Mammal extends Animal {

    public Mammal(String type, String size, double weight) {
        super(type, size, weight);
    }

    @Override
    public void move(String speed) {
        System.out.print(getExplicitType() + " ");
        System.out.println(speed.equals("slow") ? "walks" : "runs");
    }

    // Neue abstrakte Methode NUR für Säugetiere
    public abstract void shedHair();
}
```

**Hierarchie:**
```
Animal (abstract)
  ├── move() [abstract]
  ├── makeNoise() [abstract]
  ├── getExplicitType() [final, konkret]
  │
  ├── Mammal extends Animal (abstract)
  │     ├── move() [konkret – implementiert für alle Säugetiere]
  │     ├── shedHair() [abstract – neu hinzugefügt]
  │     │
  │     ├── Dog extends Mammal (konkret)
  │     │     ├── move() [überschrieben]
  │     │     ├── makeNoise() [implementiert]
  │     │     └── shedHair() [implementiert]
  │     │
  │     └── Horse extends Mammal (konkret)
  │           ├── move() [erbt von Mammal, NICHT überschrieben]
  │           ├── makeNoise() [implementiert]
  │           └── shedHair() [implementiert]
  │
  └── Fish extends Animal (konkret)
        ├── move() [implementiert]
        └── makeNoise() [implementiert]
```

**Dog** überschreibt `move()` aus Mammal mit eigenem Verhalten.
**Horse** erbt `move()` von Mammal und überschreibt es **nicht** – es nutzt die Standard-Implementierung ("walks"/"runs").
**Fish** erbt direkt von Animal (nicht von Mammal) und hat kein `shedHair()`.

### Polymorphismus mit abstrakten Klassen und `instanceof`

```java
ArrayList<Animal> animals = new ArrayList<>();
animals.add(new Dog("German Shepherd", "big", 150));
animals.add(new Fish("Goldfish", "small", 1));
animals.add(new Horse("Clydesdale", "large", 1000));

for (Animal animal : animals) {
    doAnimalStuff(animal);                          // Polymorphismus!
    if (animal instanceof Mammal currentMammal) {    // Pattern Matching
        currentMammal.shedHair();                    // Nur für Säugetiere
    }
}

private static void doAnimalStuff(Animal animal) {
    animal.makeNoise();   // Ruft die richtige Version auf (Dog/Fish/Horse)
    animal.move("slow");  // Ruft die richtige Version auf
}
```

### `ArrayList<Animal>` – Kurze Einführung

Ein `ArrayList` ist wie ein Array, aber mit **dynamischer Größe**:

```java
import java.util.ArrayList;

ArrayList<Animal> animals = new ArrayList<>();   // Leere Liste
animals.add(new Dog(...));                        // Element hinzufügen
animals.add(new Fish(...));                       // Verschiedene Subtypen!

for (Animal animal : animals) {                   // Durchlaufen mit for-each
    animal.makeNoise();
}
```

---

## 11.2 Praxisbeispiel: Product Store (Abstract Challenge)

### Abstrakte Basisklasse

```java
public abstract class ProductForSale {
    protected String type;
    protected double price;
    protected String description;

    public ProductForSale(String type, double price, String description) {
        this.type = type;
        this.price = price;
        this.description = description;
    }

    // Konkrete Methoden – für alle Produkte gleich
    public double getSalePrice(int qty) {
        return qty * price;
    }

    public void printPricedItem(int qty) {
        System.out.printf("%2d qty at $%8.2f each, %-15s %-35s %n",
                qty, price, type, description);
    }

    // Abstrakte Methode – jedes Produkt zeigt Details anders
    public abstract void showDetails();
}
```

### Konkrete Klassen

```java
public class Furniture extends ProductForSale {
    public Furniture(String type, double price, String description) {
        super(type, price, description);
    }

    @Override
    public void showDetails() {
        System.out.println("This " + type + " was manufactured in North Carolina");
        System.out.printf("The price of the piece is %6.2f %n", price);
        System.out.println(description);
    }
}

public class ArtObject extends ProductForSale {
    public ArtObject(String type, double price, String description) {
        super(type, price, description);
    }

    @Override
    public void showDetails() {
        System.out.println("This " + type + " is a beautiful reproduction");
        System.out.printf("The price of the piece is %6.2f %n", price);
        System.out.println(description);
    }
}
```

### Record als Datencontainer

```java
record OrderItem(int qty, ProductForSale product) { }
```

Ein `record` wird hier für einzelne Bestellpositionen verwendet – unveränderbar und kompakt.

### Store-Klasse mit ArrayList

```java
public class Store {
    private static ArrayList<ProductForSale> storeProducts = new ArrayList<>();

    public static void main(String[] args) {
        storeProducts.add(new ArtObject("Oil Painting", 1350, "Impressionistic work"));
        storeProducts.add(new Furniture("Desk", 500, "Mahogany Desk"));

        // Bestellung erstellen:
        var order1 = new ArrayList<OrderItem>();
        addItemToOrder(order1, 1, 2);   // 2× Produkt an Index 1
        addItemToOrder(order1, 0, 1);   // 1× Produkt an Index 0
        printOrder(order1);
    }

    public static void listProducts() {
        for (var item : storeProducts) {
            item.showDetails();   // Polymorphismus: richtige showDetails() wird aufgerufen
        }
    }

    public static void printOrder(ArrayList<OrderItem> order) {
        double salesTotal = 0;
        for (var item : order) {
            item.product().printPricedItem(item.qty());   // record Getter: product(), qty()
            salesTotal += item.product().getSalePrice(item.qty());
        }
        System.out.printf("Sales Total: $%6.2f %n", salesTotal);
    }
}
```

**Kernkonzept:** Die Store-Klasse arbeitet nur mit `ProductForSale` – sie weiß nicht und muss nicht wissen, ob es Furniture oder ArtObject ist. Das ist die **Kraft der Abstraktion**.

---

## 11.3 Interfaces

### Was ist ein Interface?

Ein **Interface** ist ein **Vertrag** (Contract). Es definiert **was** eine Klasse tun muss, aber nicht **wie**. Eine Klasse, die ein Interface implementiert, **verspricht**, alle Methoden des Interfaces zu haben.

```java
interface FlightEnabled {
    void takeOff();
    void land();
    void fly();
}

interface Trackable {
    void track();
}
```

### Interface vs Abstrakte Klasse

| Eigenschaft | Interface | Abstrakte Klasse |
|------------|-----------|-----------------|
| Schlüsselwort | `interface` | `abstract class` |
| Instanziierbar? | Nein | Nein |
| Konstruktor? | **Nein** | Ja |
| Felder? | Nur `public static final` (Konstanten) | Beliebige Felder |
| Methoden | Abstract (Standard), default, static, private | Alles möglich |
| Mehrfachvererbung? | **Ja** (eine Klasse kann mehrere Interfaces implementieren) | **Nein** (nur eine Elternklasse) |
| Typische Verwendung | "Kann fliegen", "Ist verfolgbar" (Fähigkeiten) | "Ist ein Tier" (Identität) |

### Ein Interface implementieren

```java
public class Bird extends Animal implements Trackable, FlightEnabled {

    @Override
    public void move() {
        System.out.println("Flaps wings");
    }

    @Override
    public void takeOff() {
        System.out.println(getClass().getSimpleName() + " is taking off");
    }

    @Override
    public void land() {
        System.out.println(getClass().getSimpleName() + " is landing");
    }

    @Override
    public void fly() {
        System.out.println(getClass().getSimpleName() + " is flying");
    }

    @Override
    public void track() {
        System.out.println(getClass().getSimpleName() + "'s coordinates recorded");
    }
}
```

**Bird** erbt von `Animal` (IS-A Tier) **UND** implementiert `FlightEnabled` und `Trackable` (CAN-DO fliegen und verfolgt werden). Das ist der Hauptvorteil von Interfaces: **Mehrfachimplementierung**.

### Verschiedene Klassen, gleiche Interfaces

```java
public class Jet implements FlightEnabled, Trackable { ... }
public class Truck implements Trackable { ... }
```

- `Bird`: extends Animal + implements FlightEnabled, Trackable
- `Jet`: implements FlightEnabled, Trackable (kein Animal!)
- `Truck`: implements Trackable (kann NICHT fliegen)

**Ein Jet ist kein Tier, kann aber fliegen und verfolgt werden – genau wie ein Vogel.** Das ist unmöglich mit reiner Vererbung, aber einfach mit Interfaces.

### Interface-Variablen (Polymorphismus)

Ein Objekt kann über verschiedene Interface-Typen referenziert werden:

```java
Bird bird = new Bird();

Animal animal = bird;           // Als Tier behandeln
FlightEnabled flier = bird;     // Als fliegendes Objekt behandeln
Trackable tracked = bird;       // Als verfolgbares Objekt behandeln

animal.move();       // OK: move() ist in Animal
flier.takeOff();     // OK: takeOff() ist in FlightEnabled
tracked.track();     // OK: track() ist in Trackable

// Aber:
flier.move();        // FEHLER! FlightEnabled kennt move() nicht
tracked.fly();       // FEHLER! Trackable kennt fly() nicht
```

**Jeder Referenztyp erlaubt nur Zugriff auf die Methoden DIESES Typs**, auch wenn das Objekt mehr kann.

### Konstanten in Interfaces

Alle Felder in einem Interface sind automatisch `public static final`:

```java
interface FlightEnabled {
    double MILES_TO_KM = 1.60934;    // Automatisch public static final!
    double KM_TO_MILES = 0.621371;   // Automatisch public static final!
}

// Verwendung:
double milesTraveled = kmsTraveled * FlightEnabled.KM_TO_MILES;
```

### Default-Methoden (Java 8+)

Interfaces können Methoden mit einer **Standard-Implementierung** haben:

```java
interface FlightEnabled {
    void takeOff();
    void land();
    void fly();

    // Default-Methode: HAT eine Implementierung
    default FlightStages transition(FlightStages stage) {
        FlightStages nextStage = stage.getNextStage();
        System.out.println("Transitioning from " + stage + " to " + nextStage);
        return nextStage;
    }
}
```

**Warum default-Methoden?**
1. Man kann Interfaces **nachträglich erweitern**, ohne alle implementierenden Klassen zu brechen
2. Gemeinsame Logik für alle Implementierungen bereitstellen
3. Klassen können die default-Methode überschreiben oder die Standard-Implementierung nutzen

### Default-Methode überschreiben

```java
public class Jet implements FlightEnabled, Trackable {

    @Override
    public FlightStages transition(FlightStages stage) {
        System.out.println(getClass().getSimpleName() + " transitioning");
        return FlightEnabled.super.transition(stage);   // Default-Implementierung aufrufen
    }
}
```

**`FlightEnabled.super.transition(stage)`** – So ruft man die default-Methode des Interfaces explizit auf.

### Private Methoden in Interfaces (Java 9+)

```java
interface OrbitEarth extends FlightEnabled {

    void achieveOrbit();

    // Private statische Methode: Hilfsmethode, nicht von außen sichtbar
    private static void log(String description) {
        var today = new java.util.Date();
        System.out.println(today + ": " + description);
    }

    // Private Instanzmethode: Nur innerhalb des Interfaces nutzbar
    private void logStage(FlightStages stage, String description) {
        description = stage + ": " + description;
        log(description);
    }

    @Override
    default FlightStages transition(FlightStages stage) {
        FlightStages nextStage = FlightEnabled.super.transition(stage);
        logStage(stage, "Beginning Transition to " + nextStage);   // Private Methode nutzen
        return nextStage;
    }
}
```

### Interface-Vererbung

Interfaces können von **anderen Interfaces** erben:

```java
interface OrbitEarth extends FlightEnabled {
    void achieveOrbit();   // Neue Methode
    // Erbt: takeOff(), land(), fly(), transition()
}
```

Eine Klasse die `OrbitEarth` implementiert, muss **alle Methoden beider Interfaces** implementieren.

### Enum mit Interface

Auch Enums können Interfaces implementieren:

```java
enum FlightStages implements Trackable {
    GROUNDED, LAUNCH, CRUISE, DATA_COLLECTION;

    @Override
    public void track() {
        if (this != GROUNDED) {
            System.out.println("Monitoring " + this);
        }
    }

    public FlightStages getNextStage() {
        FlightStages[] allStages = values();
        return allStages[(ordinal() + 1) % allStages.length];
    }
}
```

### Record mit Interface

```java
record DragonFly(String name, String type) implements FlightEnabled {
    @Override
    public void takeOff() { }

    @Override
    public void land() { }

    @Override
    public void fly() { }
}
```

### Listen mit Interface-Typen

```java
LinkedList<FlightEnabled> fliers = new LinkedList<>();
fliers.add(bird);    // Bird implements FlightEnabled
fliers.add(jet);     // Jet implements FlightEnabled
// fliers.add(truck);// FEHLER! Truck implements nur Trackable, nicht FlightEnabled

// Besser: Gegen das Interface programmieren
List<FlightEnabled> betterFliers = new LinkedList<>();
```

**Best Practice:** Deklariere Variablen mit dem **Interface-Typ** (z.B. `List<>`) statt dem konkreten Typ (z.B. `LinkedList<>`). So kann die Implementierung später leicht gewechselt werden.

---

## 11.4 Abstrakte Klassen vs Interfaces – Wann was verwenden?

### Entscheidungshilfe

| Frage | Verwende |
|-------|---------|
| "Ist X ein Y?" (Identität) | **Abstrakte Klasse** |
| "Kann X Y?" (Fähigkeit) | **Interface** |
| Gemeinsamer Zustand (Felder) nötig? | **Abstrakte Klasse** |
| Mehrere "Typen" gleichzeitig? | **Interface** (Mehrfachimplementierung) |
| Konstruktor nötig? | **Abstrakte Klasse** |
| Nur Methoden-Vertrag? | **Interface** |

### Typisches Muster: Kombination beider

```java
// Identität: Bird IST ein Animal
public abstract class Animal {
    public abstract void move();
}

// Fähigkeiten: Bird KANN fliegen und KANN verfolgt werden
interface FlightEnabled { ... }
interface Trackable { ... }

// Kombination:
public class Bird extends Animal implements FlightEnabled, Trackable { ... }
```

---

## 11.5 Zusammenfassung der wichtigsten Konzepte

| Konzept | Beschreibung |
|---------|-------------|
| **abstract class** | Klasse die nicht instanziiert werden kann; dient als Vorlage |
| **abstract method** | Methode ohne Body; muss von konkreter Kindklasse implementiert werden |
| **final method** | Methode die NICHT überschrieben werden kann |
| **final class** | Klasse die NICHT erweitert werden kann |
| **interface** | Vertrag: definiert WAS, nicht WIE; nur Methodensignaturen |
| **implements** | Klasse implementiert ein oder mehrere Interfaces |
| **default method** | Interface-Methode mit Standard-Implementierung (Java 8+) |
| **private method** | Hilfsmethode innerhalb eines Interfaces (Java 9+) |
| **Konstanten** | Interface-Felder sind automatisch `public static final` |
| **Interface-Vererbung** | Interface extends Interface (Mehrfachvererbung möglich) |
| **Mehrfachimplementierung** | Eine Klasse kann mehrere Interfaces implementieren |
| **ArrayList** | Dynamische Liste, kann verschiedene Subtypen halten |
| **Interface-Variable** | Erlaubt nur Zugriff auf die Interface-Methoden |
| **`InterfaceName.super`** | Zugriff auf die default-Methode des Interfaces |
| **Enum implements Interface** | Auch Enums können Interfaces implementieren |
| **Record implements Interface** | Auch Records können Interfaces implementieren |

---

*Dieser Konspekt basiert auf dem Code und den Übungen der Section 11 des Java-Kurses.*
