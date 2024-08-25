package com.insurance.premium.enums;

public enum BundeslandISO {


    DE_DE(1.0, "Default"),
    DE_NI(1.2, "Niedersachsen"),
    DE_HB(0.8, "Bremen"),
    DE_BB(1.1, "Brandenburg"),
    DE_SN(0.9, "Sachsen"),
    DE_SH(1.3, "Schleswig-Holstein"),
    DE_BY(1.4, "Bayern"),
    DE_HH(0.95, "Hamburg"),
    DE_BW(1.25, "Baden-Württemberg"),
    DE_NW(1.35, "Nordrhein-Westfalen"),
    DE_BE(0.75, "Berlin"),
    DE_ST(1.05, "Sachsen-Anhalt"),
    DE_RP(1.4, "Rheinland-Pfalz"),
    DE_SL(0.85, "Saarland"),
    DE_MV(1.1, "Mecklenburg-Vorpommern"),
    DE_HE(1.3, "Hessen"),
    DE_TH(0.9, "Thüringen");

    private final Double regionFactor;
    private final String bundesland;


    BundeslandISO(Double regionFactor, String bundesland) {
        this.regionFactor = regionFactor;
        this.bundesland = bundesland;
    }

    public Double getRegionFactor() {
        return regionFactor;
    }

    public String getBundesland() {
        return bundesland;
    }

    public static BundeslandISO fromBundesland(String bundesland) {
        for (BundeslandISO iso : BundeslandISO.values()) {
            if (iso.getBundesland().equalsIgnoreCase(bundesland)) {
                return iso;
            }
        }
        return BundeslandISO.DE_DE;
    }
}
