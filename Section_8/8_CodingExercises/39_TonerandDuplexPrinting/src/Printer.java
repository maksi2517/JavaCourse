public class Printer {
    private int tonerLevel;
    private int pagesPrinted;
    private boolean duplex;

    public Printer(int tonerLevel, boolean duplex) {
        this.tonerLevel = (tonerLevel >= 0 && tonerLevel <= 100) ? tonerLevel : -1;
        this.pagesPrinted = 0;
        this.duplex = duplex;
    }

    public int addToner(int tonerAmount) {
        if (tonerAmount > 0 && tonerAmount <= 100) {
            if (tonerLevel + tonerAmount > 100) {
                return -1; // Exceeds maximum capacity
            }
            tonerLevel += tonerAmount;
            return tonerLevel;
        }
        return -1; // Invalid tonerAmount
    }

    public int printPages(int pages) {
        if (pages <= 0) {
            return 0; // No pages to print
        }
        int pagesToPrint;
        if (duplex) {
            System.out.println("Printing in duplex mode");
            pagesToPrint = pages /2 + pages % 2;
        }
        else {
            pagesToPrint = pages;
        }
        pagesPrinted += pagesToPrint;
        return pagesToPrint;
    }

    public int getPagesPrinted() {
        return pagesPrinted;
    }
}
