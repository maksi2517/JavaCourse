package at.maksim;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

public class AustrianSelfEmployedCalculator {

    // Rundung/Mathematik (muss vor erster Verwendung stehen)
    private static final RoundingMode RM = RoundingMode.HALF_UP;
    private static final int MONEY_SCALE = 2;
    private static final MathContext MC = new MathContext(20, RM);

    // ======= CONFIGURATIONS-BEREICH (leicht anpassbar) =======

    // Steuertarif (laufende Bezüge) – Grenzen sind "bis einschließlich".
    // Werte ggf. jährlich aktualisieren!
    private static final TaxBracket[] BRACKETS_2025 = new TaxBracket[]{
            new TaxBracket(bd("13308"), rate("0.00")),
            new TaxBracket(bd("21617"), rate("0.20")),
            new TaxBracket(bd("35836"), rate("0.30")),
            new TaxBracket(bd("56000"), rate("0.40")),
            new TaxBracket(bd("90000"), rate("0.48")),
            new TaxBracket(bd("1000000"), rate("0.50")),
            new TaxBracket(null, rate("0.55")) // über 1 Mio.
    };

    // SVS-Sätze (GSVG) – ggf. jährlich prüfen
    private static final BigDecimal RATE_PV = rate("0.185");   // Pensionsversicherung 18,5%
    private static final BigDecimal RATE_KV = rate("0.068");   // Krankenversicherung 6,8%
    private static final BigDecimal RATE_SV_VORSORGE = rate("0.0153"); // Selbstantigenvorsorge 1,53% (BMSVG)
    private static final BigDecimal UV_MONTHLY = bd("12.07");        // Unfallversicherung pauschal/Monat

    // Beitragsgrundlage: Mindest-/Höchstbemessung (optional aktivierbar)
    private static final boolean APPLY_MIN_MAX_BMG = false; // auf true setzen, wenn gewünschte Werte bekannt
    private static final BigDecimal MIN_BMG_ANNUAL = bd("0");      // z.B. 2025 anpassen
    private static final BigDecimal MAX_BMG_ANNUAL = bd("0");      // z.B. 2025 anpassen

    // Gewinnfreibetrag (Grundfreibetrag)
    private static final BigDecimal GFB_RATE = rate("0.15");       // 15 %
    private static final BigDecimal GFB_CAP = bd("33000");       // bis 33.000 € ohne Investitionen


    // ======= Ende Konfiguration =======

    private enum Mode { ECONOMIC, BESCHIED }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Rechner Selbständigkeit (AT) – SVS + ESt inkl. Angestellten-Einkünfte ===");
        System.out.println("Alle Beträge in EUR. Drücke Enter für die Demo-Werte am Ende.");

        // Eingaben
        BigDecimal seMonthlyRevenue = ask(sc,
                "Monatlicher Umsatz (ohne USt) Selbständigkeit [z.B. 2400]: ");
        BigDecimal seMonthlyCosts = ask(sc,
                "Monatliche absetzbare Betriebsausgaben [z.B. 500]: ");
        BigDecimal employedAnnualTaxableLaufend = ask(sc,
                "Summe der steuerpflichtigen laufenden Angestellten-Einkünfte p.a. (nach DN-SV, ohne SZ) [z.B. 7729]: ");

        if (seMonthlyRevenue == null || seMonthlyCosts == null || employedAnnualTaxableLaufend == null) {
            // Demo mit den Zahlen aus deiner Beschreibung (vereinfachte Annahmen)
            seMonthlyRevenue = bd("2400");
            seMonthlyCosts = bd("500");
            // Näherung: 12 × 750 € abzüglich DN-SV (ca. 14.12%) => ~7.729 €
            employedAnnualTaxableLaufend = bd("7729");
            System.out.println("\nDemo-Eingaben verwendet:");
            System.out.println("  Umsatz/Monat: " + fmt(seMonthlyRevenue));
            System.out.println("  Kosten/Monat: " + fmt(seMonthlyCosts));
            System.out.println("  Laufende Angestellten-Einkünfte p.a.: " + fmt(employedAnnualTaxableLaufend));
        }

        Result rEconomic = computeAll(seMonthlyRevenue, seMonthlyCosts, employedAnnualTaxableLaufend, Mode.ECONOMIC);
        Result rBescheid = computeAll(seMonthlyRevenue, seMonthlyCosts, employedAnnualTaxableLaufend, Mode.BESCHIED);

        System.out.println("\n--- Ergebnis: Wirtschaftliche Sicht (Einrechnung PV+KV in die Basis) ---");
        printResult(rEconomic);
        System.out.println("\n--- Ergebnis: SVS nach ESt-Bescheid/Schätzung (inkl. GFB in der Basisformel) ---");
        printResult(rBescheid);
    }

    // Kernfunktion mit Mode
    private static Result computeAll(BigDecimal seMonthlyRevenue,
                                     BigDecimal seMonthlyCosts,
                                     BigDecimal employedAnnualTaxableLaufend,
                                     Mode mode) {

        BigDecimal months = bd("12");
        BigDecimal seRevenueAnnual = seMonthlyRevenue.multiply(months, MC);
        BigDecimal seCostsAnnual   = seMonthlyCosts.multiply(months, MC);
        BigDecimal seProfitBeforeSVS = seRevenueAnnual.subtract(seCostsAnnual, MC).max(bd("0"));

        BigDecimal ratePVKV = RATE_PV.add(RATE_KV, MC);      // r1 = PV+KV
        BigDecimal rateR    = ratePVKV.add(RATE_SV_VORSORGE, MC); // r = PV+KV+Vorsorge
        BigDecimal one = bd("1");
        BigDecimal g = GFB_RATE;                              // 0.15 üblich

        BigDecimal svsBaseAnnual;
        BigDecimal uv = UV_MONTHLY.multiply(months, MC);

        if (mode == Mode.ECONOMIC) {
            // ECONOMIC (wirtschaftliche Einrechnung – bisheriges Verhalten):
            svsBaseAnnual = safeDiv(seProfitBeforeSVS, one.subtract(ratePVKV, MC));
        } else {
            // BESCHIED (SVS nach ESt-Bescheid/Schätzung, inkl. GFB):
            BigDecimal numerator   = (one.subtract(g, MC)).multiply(seProfitBeforeSVS.subtract(uv, MC), MC);
            BigDecimal denominator = one.subtract(ratePVKV, MC).add((one.subtract(g, MC)).multiply(rateR, MC), MC);
            svsBaseAnnual = safeDiv(numerator, denominator);

            BigDecimal pvProbe = svsBaseAnnual.multiply(RATE_PV, MC);
            BigDecimal kvProbe = svsBaseAnnual.multiply(RATE_KV, MC);
            BigDecimal vorsProbe = svsBaseAnnual.multiply(RATE_SV_VORSORGE, MC);
            BigDecimal gaProbe = seProfitBeforeSVS.subtract(pvProbe, MC).subtract(kvProbe, MC).subtract(vorsProbe, MC).subtract(uv, MC);

            if (gaProbe.compareTo(GFB_CAP) > 0) {
                BigDecimal gfbConst = GFB_RATE.multiply(GFB_CAP, MC);
                svsBaseAnnual = safeDiv(seProfitBeforeSVS.subtract(uv, MC).subtract(gfbConst, MC), one.add(RATE_SV_VORSORGE, MC));
            }
        }

        if (APPLY_MIN_MAX_BMG) {
            if (svsBaseAnnual.compareTo(MIN_BMG_ANNUAL) < 0) svsBaseAnnual = MIN_BMG_ANNUAL;
            if (MAX_BMG_ANNUAL.signum() > 0 && svsBaseAnnual.compareTo(MAX_BMG_ANNUAL) > 0) svsBaseAnnual = MAX_BMG_ANNUAL;
        }

        BigDecimal pv = svsBaseAnnual.multiply(RATE_PV, MC);
        BigDecimal kv = svsBaseAnnual.multiply(RATE_KV, MC);
        BigDecimal vorsorge = svsBaseAnnual.multiply(RATE_SV_VORSORGE, MC);

        BigDecimal seProfitAfterSVS = seProfitBeforeSVS.subtract(pv, MC).subtract(kv, MC)
                .subtract(vorsorge, MC).subtract(uv, MC);

        // Gewinnfreibetrag
        BigDecimal gfbBase = seProfitAfterSVS.max(bd("0"));
        BigDecimal gfb = gfbBase.min(GFB_CAP).multiply(GFB_RATE, MC);
        BigDecimal seTaxable = seProfitAfterSVS.subtract(gfb, MC).max(bd("0"));

        // Einkommensteuer auf Summe laufend:
        BigDecimal taxableCombined = employedAnnualTaxableLaufend.add(seTaxable, MC);
        BigDecimal taxCombined = calcIncomeTax(taxableCombined, BRACKETS_2025);
        BigDecimal taxEmployedOnly = calcIncomeTax(employedAnnualTaxableLaufend, BRACKETS_2025);
        BigDecimal addlTaxDueToSE = taxCombined.subtract(taxEmployedOnly, MC).max(bd("0"));

        // Netto aus Selbständigkeit:
        BigDecimal seNet = seProfitBeforeSVS
                .subtract(pv, MC).subtract(kv, MC)
                .subtract(vorsorge, MC).subtract(uv, MC)
                .subtract(addlTaxDueToSE, MC);

        Result r = new Result();
        r.seRevenueAnnual = seRevenueAnnual;
        r.seCostsAnnual = seCostsAnnual;
        r.seProfitBeforeSVS = seProfitBeforeSVS;
        r.svsBaseAnnual = svsBaseAnnual;
        r.pv = pv; r.kv = kv; r.vorsorge = vorsorge; r.uv = uv;
        r.seProfitAfterSVS = seProfitAfterSVS;
        r.gfb = gfb;
        r.seTaxable = seTaxable;
        r.employedAnnualTaxableLaufend = employedAnnualTaxableLaufend;
        r.taxEmployedOnly = taxEmployedOnly;
        r.taxCombined = taxCombined;
        r.addlTaxDueToSE = addlTaxDueToSE;
        r.seNet = seNet;

        return r;
    }

    // Einkommensteuer nach progressivem Tarif (piecewise)
    private static BigDecimal calcIncomeTax(BigDecimal taxable, TaxBracket[] table) {
        BigDecimal remaining = taxable.max(bd("0"));
        BigDecimal lower = bd("0");
        BigDecimal tax = bd("0");

        for (TaxBracket br : table) {
            BigDecimal upper = br.upperInclusive; // kann null sein (letzte Stufe)
            BigDecimal span;
            if (upper == null) {
                span = remaining.max(bd("0"));
            } else {
                BigDecimal width = upper.subtract(lower, MC).max(bd("0"));
                span = remaining.min(width);
            }

            if (span.signum() > 0) {
                tax = tax.add(span.multiply(br.rate, MC), MC);
                remaining = remaining.subtract(span, MC);
            }

            if (upper != null) lower = upper;
            if (remaining.signum() <= 0) break;
        }
        return tax.max(bd("0"));
    }

    // ======= Hilfszeug =======

    private static BigDecimal ask(Scanner sc, String prompt) {
        System.out.print(prompt);
        String s = sc.nextLine().trim();
        if (s.isEmpty()) return null;
        return new BigDecimal(s.replace(",", ".")).setScale(MONEY_SCALE, RM);
    }

    private static void printResult(Result r) {
        System.out.println("Umsatz (p.a.):           " + fmt(r.seRevenueAnnual));
        System.out.println("Betriebsausgaben (p.a.): " + fmt(r.seCostsAnnual));
        System.out.println("Gewinn vor SVS:          " + fmt(r.seProfitBeforeSVS));
        System.out.println();
        System.out.println("SVS-Beitragsgrundlage:   " + fmt(r.svsBaseAnnual));
        System.out.println("  PV 18,5%:              " + fmt(r.pv));
        System.out.println("  KV 6,8%:               " + fmt(r.kv));
        System.out.println("  Vorsorge 1,53%:        " + fmt(r.vorsorge));
        System.out.println("  Unfallvers. (12x):     " + fmt(r.uv));
        System.out.println("Gewinn nach SVS:         " + fmt(r.seProfitAfterSVS));
        System.out.println("Gewinnfreibetrag:        " + fmt(r.gfb));
        System.out.println("SE steuerpflichtig:      " + fmt(r.seTaxable));
        System.out.println();
        System.out.println("Lfd. Angestellten-Einkünfte (p.a.): " + fmt(r.employedAnnualTaxableLaufend));
        System.out.println("ESt nur Angestellt:                 " + fmt(r.taxEmployedOnly));
        System.out.println("ESt Angestellt+SE:                  " + fmt(r.taxCombined));
        System.out.println("Zusätzliche ESt wegen SE:           " + fmt(r.addlTaxDueToSE));
        System.out.println();
        System.out.println("NETTO aus Selbständigkeit (p.a.):   " + fmt(r.seNet));
        System.out.println("NETTO / Monat (12tel):              " + fmt(div(r.seNet, bd("12"))));
                System.out.println();
        System.out.println("Effektive Belastung aus SE:");
        BigDecimal svsTotal = r.pv.add(r.kv).add(r.vorsorge).add(r.uv);
        BigDecimal estTotal = r.addlTaxDueToSE;
        BigDecimal totalLevies = svsTotal.add(estTotal);
        System.out.println("  SVS gesamt:            " + fmt(svsTotal) + " (" + pct(svsTotal, r.seProfitBeforeSVS) + ")");
        System.out.println("  ESt gesamt:            " + fmt(estTotal) + " (" + pct(estTotal, r.seProfitBeforeSVS) + ")");
        System.out.println("  SVS+ESt gesamt:        " + fmt(totalLevies) + " (" + pct(totalLevies, r.seProfitBeforeSVS) + ")");
    }

    private static BigDecimal bd(String s) {
        return new BigDecimal(s).setScale(MONEY_SCALE, RM);
    }

    private static BigDecimal rate(String s) {
        return new BigDecimal(s); // keine Rundung – volle Präzision
    }

    private static BigDecimal safeDiv(BigDecimal a, BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division durch 0 in safeDiv()");
        }
        return a.divide(b, MC);
    }

    private static BigDecimal div(BigDecimal a, BigDecimal b) {
        return a.divide(b, MONEY_SCALE, RM);
    }

    private static String fmt(BigDecimal x) {
        return x.setScale(MONEY_SCALE, RM).toPlainString() + " €";
    }

    private static String pct(BigDecimal part, BigDecimal total) {
        if (total.signum() == 0) return "-";
        BigDecimal p = part.multiply(new BigDecimal("100"), MC).divide(total, 2, RM);
        return p.toPlainString() + " %";
    }

    // Datenklassen
    private static class TaxBracket {
        BigDecimal upperInclusive; // null = offene Obergrenze
        BigDecimal rate;
        TaxBracket(BigDecimal upperInclusive, BigDecimal rate) {
            this.upperInclusive = upperInclusive;
            this.rate = rate;
        }
    }

    public static class Result {
        BigDecimal seRevenueAnnual, seCostsAnnual, seProfitBeforeSVS;
        BigDecimal svsBaseAnnual, pv, kv, vorsorge, uv;
        BigDecimal seProfitAfterSVS, gfb, seTaxable;
        BigDecimal employedAnnualTaxableLaufend;
        BigDecimal taxEmployedOnly, taxCombined, addlTaxDueToSE;
        BigDecimal seNet;
    }
}