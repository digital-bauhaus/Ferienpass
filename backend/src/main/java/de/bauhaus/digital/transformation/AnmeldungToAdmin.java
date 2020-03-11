package de.bauhaus.digital.transformation;

import de.bauhaus.digital.domain.Arzt;
import de.bauhaus.digital.domain.Behinderung;
import de.bauhaus.digital.domain.Kontakt;
import de.bauhaus.digital.domain.Teilnehmer;

import java.time.LocalDate;

public class AnmeldungToAdmin {

    public static Teilnehmer mapAnmeldedataToTeilnehmer(AnmeldungJson anmeldungJson) {

        Teilnehmer teilnehmer = Teilnehmer.newBuilder().build();

        mappeBasisInformationen(anmeldungJson, teilnehmer);

        mappeAllergienKrankheitenNotfallkontaktEtc(anmeldungJson, teilnehmer);

        mappeDatenZuBehinderungen(anmeldungJson, teilnehmer);

        mappeErklaerung(anmeldungJson, teilnehmer);

        // Kein Feld für Datenschutzerklärung - es wird aber davon ausgegangen, dass
        // dies das Anmeldungs-Frontend abfängt

        return teilnehmer;
    }

    private static void mappeErklaerung(AnmeldungJson anmeldungJson, Teilnehmer teilnehmer) {
//        teilnehmer.setDarfAlleinNachHause(mappeYesOrNoToBoolean(anmeldungJson.getDeclarationGoingHomeAloneAllowed()));
//        teilnehmer.setDarfReiten(mappeYesOrNoToBoolean(anmeldungJson.getDeclarationHorseRidingAllowed()));
//        teilnehmer.setDarfSchwimmen(mappeYesOrNoToBoolean(anmeldungJson.getDeclarationSwimmingAllowed()));
//        teilnehmer.setSchwimmAbzeichen(anmeldungJson.getDeclarationSwimmingBadge());
    }

    private static boolean mappeYesOrNoToBoolean(String yesOrNo) {
        if("yes".equals(yesOrNo)) {
            return true;
        }
        return false;
    }

    private static void mappeDatenZuBehinderungen(AnmeldungJson anmeldungJson, Teilnehmer teilnehmer) {

//        teilnehmer.setLiegtBehinderungVor(anmeldungJson.getDisabilitiesDisabilityExistent());
//        Behinderung behinderung = new Behinderung();
//
//        if(anmeldungJson.getDisabilitiesDisabilityExistent()) {
//            mappeMerkzeichen(anmeldungJson, behinderung);
//
//            behinderung.setRollstuhlNutzungNotwendig(anmeldungJson.getDisabilitiesWheelchair());
//            behinderung.setWeitereHilfsmittel(anmeldungJson.getDisabilitiesAdditionalTools());
//            behinderung.setWertmarkeVorhanden(anmeldungJson.getDisabilitiesTokenAvailable());
//
//            mappeBegleitpersonenDaten(anmeldungJson, behinderung);
//            behinderung.setEingeschraenkteSinne(mappeEingeschraenkteSinne(anmeldungJson));
//
//            behinderung.setHinweiseZumUmgangMitDemKind(anmeldungJson.getDisabilitiesCompanionAdditionalNotes());
//        }
//        teilnehmer.setBehinderung(behinderung);
    }

    private static String mappeEingeschraenkteSinne(AnmeldungJson anmeldungJson) {
        return new StringBuilder()
                .append(anmeldungJson.getDisabilitiesAffectedSenses0())
                .append("; ")
                .append(anmeldungJson.getDisabilitiesAffectedSenses1())
                .append("; ")
                .append(anmeldungJson.getDisabilitiesAffectedSenses2())
                .append("; ")
                .append(anmeldungJson.getDisabilitiesAffectedSenses3())
                .toString();
    }

    private static void mappeBegleitpersonenDaten(AnmeldungJson anmeldungJson, Behinderung behinderung) {
//        behinderung.setBegleitungNotwendig(anmeldungJson.getDisabilitiesCompanionRequired());
//        if (behinderung.isBegleitungNotwendig()) {
//            behinderung.setBegleitpersonPflege(anmeldungJson.getDisabilitiesCompanionForNursing());
//            behinderung.setBegleitpersonMedizinischeVersorgung(anmeldungJson.getDisabilitiesCompanionForHealthCare());
//            behinderung.setBegleitpersonMobilitaet(anmeldungJson.getDisabilitiesCompanionForMobility());
//            behinderung.setBegleitpersonOrientierung(anmeldungJson.getDisabilitiesCompanionForOrientation());
//            behinderung.setBegleitpersonSozialeBegleitung(anmeldungJson.getDisabilitiesCompanionSocial());
//            behinderung.setUnterstuetzungSucheBegleitperson(anmeldungJson.getDisabilitiesCompanionHelpFindingRequired());
//            behinderung.setGewohnterBegleitpersonenDienstleister(anmeldungJson.getDisabilitiesCompanionUsualService());
//            behinderung.setBeantragungKostenuebernahmeBegleitperson(anmeldungJson.getDisabilitiesCompanionCostTakeover());
//        }
    }

    private static void mappeMerkzeichen(AnmeldungJson anmeldungJson, Behinderung behinderung) {
//        behinderung.setMerkzeichen_AussergewoehnlicheGehbehinderung_aG(anmeldungJson.getDisabilitiesMarkAg());
//        behinderung.setMerkzeichen_Hilflosigkeit_H(anmeldungJson.getDisabilitiesMarkH());
//        behinderung.setMerkzeichen_Blind_Bl(anmeldungJson.getDisabilitiesMarkBl());
//        behinderung.setMerkzeichen_Gehoerlos_Gl(anmeldungJson.getDisabilitiesMarkGl());
//        behinderung.setMerkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B(anmeldungJson.getDisabilitiesMarkB());
//        behinderung.setMerkzeichen_BeeintraechtigungImStrassenverkehr_G(anmeldungJson.getDisabilitiesMarkG());
//        behinderung.setMerkzeichen_Taubblind_TBL(anmeldungJson.getDisabilitiesMarkTbl());
    }


    private static void mappeBasisInformationen(AnmeldungJson anmeldungJson, Teilnehmer teilnehmer) {
//        teilnehmer.setNachname(anmeldungJson.getBaseFamilyName());
//        teilnehmer.setVorname(anmeldungJson.getBaseForename());
//        teilnehmer.setGeburtsdatum(mappeGeburtsdatum(anmeldungJson));
//        teilnehmer.setStrasse(anmeldungJson.getBaseStreetName() + " " + anmeldungJson.getBaseHouseNumber());
//        teilnehmer.setPostleitzahl(anmeldungJson.getBaseZipCode());
//        teilnehmer.setWohnort(anmeldungJson.getBaseResidence());
//        teilnehmer.setTelefon(anmeldungJson.getBasePhoneNumber());
//        teilnehmer.setEmail(anmeldungJson.getBaseEmail());
    }

    private static void mappeAllergienKrankheitenNotfallkontaktEtc(AnmeldungJson anmeldungJson, Teilnehmer teilnehmer) {
//        teilnehmer.setAllergien(anmeldungJson.getConditionsAllergies0()+"\n"+anmeldungJson.getConditionsAllergies1()+"\n"+anmeldungJson.getConditionsAllergies2()+
//                "\n"+anmeldungJson.getConditionsAllergies3()+"\n"+anmeldungJson.getConditionsAllergies4());
//        teilnehmer.setKrankheiten(anmeldungJson.getConditionsDiseases0()+"\n"+anmeldungJson.getConditionsDiseases1()+"\n"+anmeldungJson.getConditionsDiseases2()+
//                "\n"+anmeldungJson.getConditionsDiseases3()+"\n"+anmeldungJson.getConditionsDiseases4()+"\n");
//
//        teilnehmer.setEssenWeitereLimitierungen(anmeldungJson.getConditionsNutrition0()+"\n"+anmeldungJson.getConditionsNutrition1()+"\n"+anmeldungJson.getConditionsNutrition2()+"\n"+
//                anmeldungJson.getConditionsNutrition3()+"\n"+anmeldungJson.getConditionsNutrition4()+"\n");
//        // TODO: ist setErlaubeMedikamentation() das Gleiche wie "Behandlungserlaubnis bei Erkrankungen und Unfällen"? Nein. Es gibt jetzt 2 Felder dafür
//        teilnehmer.setErlaubeMedikamentation(mappeYesOrNoToBoolean(anmeldungJson.getConditionsChildTreatmentAllowed()));
//
//        teilnehmer.setKrankenkasse(anmeldungJson.getConditionsHealthInsurance());
//        teilnehmer.setNotfallKontakt(mappeNotfallKontakt(anmeldungJson));
//
//        teilnehmer.setArzt(mappeArzt(anmeldungJson));
    }

//    private static Arzt mappeArzt(AnmeldungJson anmeldungJson) {
//        return new Arzt(
//                anmeldungJson.getConditionsFamilyDoctorName(),
//                anmeldungJson.getConditionsFamilyDoctorAddress(),
//                anmeldungJson.getConditionsFamilyDoctorPhoneNumber());
//    }

//    private static Kontakt mappeNotfallKontakt(AnmeldungJson anmeldungJson) {
//        return new Kontakt(
//                anmeldungJson.getConditionsEmergencyName(),
//                anmeldungJson.getConditionsEmergencyAddress(),
//                anmeldungJson.getConditionsEmergencyPhoneNumber());
//    }


    private static LocalDate mappeGeburtsdatum(AnmeldungJson anmeldungJson) {
        return LocalDate.of(
                Integer.valueOf(anmeldungJson.getBaseBirthdateYear()),
                Integer.valueOf(anmeldungJson.getBaseBirthdateMonth()),
                Integer.valueOf(anmeldungJson.getBaseBirthdateDay()));
    }
}
