import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class LegeSystem {

    IndeksertListe<Pasient> pasientListe = new IndeksertListe<Pasient>();
    IndeksertListe<Legemiddel> legemiddelListe = new IndeksertListe<Legemiddel>();
    IndeksertListe<Lege> LegeListe = new IndeksertListe<Lege>();
    IndeksertListe<Resept> ReseptListe = new IndeksertListe<Resept>();

    public void hentFile(String filenavn) throws UgyldigListeindeks, UlovligUtskrift {
        try {
            File file = new File(filenavn);
            Scanner myleser = new Scanner(file);
            String linje = myleser.nextLine();

            while (myleser.hasNextLine()) {
                // PASIENTLISTE
                if (linje.contains("# Pasienter")) {
                    linje = myleser.nextLine();
                    while (myleser.hasNextLine() && !linje.startsWith("#")) {
                        linje = linje.strip();
                        String[] kolonner = linje.split(",");
                        String navn = kolonner[0];
                        String foedselsnummer = kolonner[1];
                        Pasient pasient = new Pasient(navn, foedselsnummer);

                        pasientListe.leggTil(pasient);
                        if (myleser.hasNextLine())
                            linje = myleser.nextLine();

                        // LEGEMIDLERS LISTE
                    }
                    // System.out.println("sjekke Pasienter");
                }
                if (linje.contains("# Legemidler (navn,type,pris,virkestoff,[styrke])")) {
                    linje = myleser.nextLine();
                    while (myleser.hasNextLine() && !linje.startsWith("#")) {
                        linje = linje.strip();
                        String[] kolonner = linje.split(",");
                        String navn = kolonner[0];
                        String Legemiddeltype = kolonner[1];
                        int legemiddelPris = Integer.parseInt(kolonner[2]);// det bytter variabel fra String til double

                        double legemiddelVirkestofet;
                        if (kolonner.length >= 4 && kolonner[3] != null) {
                            legemiddelVirkestofet = Double.parseDouble(kolonner[3]);
                        } else {
                            legemiddelVirkestofet = 0.0;
                        }

                        if (Legemiddeltype.toLowerCase().equals("vanlig")) {

                            Legemiddel legemiddel = new Vanlig(navn, legemiddelPris, legemiddelVirkestofet);

                            legemiddelListe.leggTil(legemiddel);
                            linje = myleser.nextLine();
                        } else if (Legemiddeltype.toLowerCase().equals("narkotisk")
                                || Legemiddeltype.toLowerCase().equals("vanedannende")) {
                            int LegemiddelStyrke = Integer.parseInt(kolonner[4]);
                            if (Legemiddeltype.toLowerCase().equals("narkotisk")) {
                                Legemiddel legemiddel = new Narkotisk(navn, legemiddelPris, legemiddelVirkestofet,
                                        LegemiddelStyrke);
                                legemiddelListe.leggTil(legemiddel);
                                linje = myleser.nextLine();
                            } else if (Legemiddeltype.toLowerCase().equals("vanedannende")) {
                                Legemiddel legemiddel = new Vanedannende(navn, legemiddelPris, legemiddelVirkestofet,
                                        LegemiddelStyrke);
                                legemiddelListe.leggTil(legemiddel);
                                linje = myleser.nextLine();
                            }
                        }

                    }
                }//LEGELISTE
                 if(linje.contains("# Leger")){
                    linje=myleser.nextLine();
                    while(myleser.hasNextLine() && !linje.startsWith("#")){
                        linje=linje.strip();
                        String[] kolonner=linje.split(",");
                        String navn=kolonner[0];
                        String legeKontrolnummer=kolonner[1];
                        if(!legeKontrolnummer.equals("0")){//fant spesialister
                            Lege spesialister=new Spesialist(navn, legeKontrolnummer);
                            LegeListe.leggTil(spesialister);
                        } else {
                            Lege vanligLege = new Lege(navn);
                            LegeListe.leggTil(vanligLege);

                        }
                        linje = myleser.nextLine();

                    }
                if(linje.contains("# Resepter")){
                        linje=myleser.nextLine();
                        while(myleser.hasNextLine() && !linje.startsWith("#")){
                        linje=linje.strip();
                        String[] kolonner=linje.split(",");
                        int legemiddelNummer=Integer.parseInt(kolonner[0]); // skal peke legemiddelListe
                        String LegesNavn=kolonner[1];// ska peke LegeListe
                        int PasientID=Integer.parseInt(kolonner[2]);// ska peke PasientListe
                        String ReseptType=kolonner[3];// ska sette if betinget for alle typene
                        if(ReseptType.toLowerCase().equals("blaa")){
                            int blaaReseptsreit=Integer.parseInt(kolonner[4]);
                            Resept blaaResept;
                            for (int teller = 0; teller < LegeListe.storrelsen; teller++) {

                                if (LegeListe.hent(teller).legensNavn.equals(LegesNavn)) {
                                    blaaResept = LegeListe.hent(teller).skrivBlaaResept(
                                            legemiddelListe.hent(legemiddelNummer), pasientListe.hent(PasientID),
                                            blaaReseptsreit, LegeListe.hent(teller));
                                    ReseptListe.leggTil(blaaResept);
                                }
                            }

                        } else if (ReseptType.toLowerCase().equals("hvit")) {
                            int hvitReseptsreit = Integer.parseInt(kolonner[4]);
                            Resept hvitResept;
                            for (int teller = 0; teller < LegeListe.storrelsen; teller++) {
                                if (LegeListe.hent(legemiddelNummer).legensNavn.equals(LegesNavn)) {// fant samme Dr.
                                    hvitResept = LegeListe.hent(teller).skrivHvitResept(
                                            legemiddelListe.hent(legemiddelNummer), pasientListe.hent(PasientID),
                                            hvitReseptsreit, LegeListe.hent(teller));
                                    ReseptListe.leggTil(hvitResept);
                                }
                            }
                        } else if (ReseptType.toLowerCase().equals("p")) {
                            int pReseptsreit = Integer.parseInt(kolonner[4]);
                            Resept pResept;
                            for (int teller = 0; teller < LegeListe.storrelsen; teller++) {
                                if (LegeListe.hent(legemiddelNummer).legensNavn.equals(LegesNavn)) {
                                    pResept = LegeListe.hent(teller).skrivPResept(
                                            legemiddelListe.hent(legemiddelNummer), pasientListe.hent(PasientID),
                                            pReseptsreit, LegeListe.hent(teller));
                                    ReseptListe.leggTil(pResept);
                                }
                            }
                        } else if (ReseptType.toLowerCase().equals("militaer")) {
                            Resept milResept;
                            for (int teller = 0; teller < LegeListe.storrelsen; teller++) {
                                if (LegeListe.hent(legemiddelNummer).legensNavn.equals(LegesNavn)) {

                                    milResept = LegeListe.hent(teller).skrivMilResept(
                                            legemiddelListe.hent(legemiddelNummer), pasientListe.hent(PasientID),
                                            LegeListe.hent(teller));
                                    ReseptListe.leggTil(milResept);
                                }
                            }
                        }
                        linje=myleser.nextLine();}
                }   
                linje=myleser.nextLine(); }
            myleser.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }

    }/*
      * class VaarIterator implements Iterator{
      * Node noden=start;
      * 
      * @Override
      * public boolean hasNext(){
      * return noden !=null;
      * }
      * 
      * @Override
      * public String next(){
      * String hentesUt= noden.verdi;
      * noden=noden.neste;
      * return hentesUt;
      * }
      * 
      * }
      * 
      * public Iterator<String>iterator(){
      * return new VaarIterator();
      * }
      */

    public void hentPasienter() {
        for (int teller = 0; teller < pasientListe.storrelsen; teller++) {
            System.out.println(pasientListe.hent(teller));
        }
    }

    public void hentLege() {
        for (int teller = 0; teller < LegeListe.storrelsen; teller++) {
            System.out.println(LegeListe.hent(teller));
        }
    }

    public void hentLegemidler() {
        for (int teller = 0; teller < legemiddelListe.storrelsen; teller++) {
            System.out.println(legemiddelListe.hent(teller));
        }

    }

    public void hentResepter() {
        for (int teller = 0; teller < ReseptListe.storrelsen; teller++) {
            System.out.println(ReseptListe.hent(teller));
        }
    }

    public void BrukerResept() {
        Scanner brukersInput = new Scanner(System.in);
        System.out.println("Hvilken pasient vil du se resepter for?");
        for (int teller = 0; teller < pasientListe.storrelsen; teller++) {
            System.out.println(teller + ": " + pasientListe.hent(teller));
        }
        int brukerns = brukersInput.nextInt();
        System.out.print("Valgt pasient: ");
        System.out.println(pasientListe.hent(brukerns));
        System.out.println("Hvilken resept vil du bruke?");
        for (int teller = 0; teller < ReseptListe.storrelsen; teller++) {
            System.out.println(
                    teller + " " + ReseptListe.hent(teller).legemiddel1.navn + " " + ReseptListe.hent(teller).reit);
        }
        int brukerensResept = brukersInput.nextInt();
        System.out.println();
        if (ReseptListe.hent(brukerensResept).reit == 0) {
            System.out.println("Kunne ikke bruke resept paa " + ReseptListe.hent(brukerensResept).legemiddel1.navn);
        } else {
            ReseptListe.hent(brukerensResept).reit = ReseptListe.hent(brukerensResept).reit - 1;
            System.out.println("Brukte resept paa " + ReseptListe.hent(brukerensResept).legemiddel1.navn + " "
                    + " Antall gjenvaerende reit:" + ReseptListe.hent(brukerensResept).reit);
        }
    }

    public void statistikk(){
        Scanner statikInput = new Scanner(System.in);
        System.out.println("Her er det Menyen som du kan velge:"+"\n"+
        "1 Totalt antall utskrevne resepter på vanedannende legemidler"+"\n"+
        "2 Totalt antall utskrevne resepter på narkotiske legemidler"+"\n"+
        "3. List opp navnene på alle leger (i alfabetisk rekkefølge) som har skrevet ut" +"\n"+
        "minst en resept på narkotiske legemidler, og antallet slike resepter per lege."+"\n"+
        "4.List opp navnene på alle pasienter som har minst en gyldig resept på"+"\n"+
        "narkotiske legemidler og, for disse, skriv ut antallet per pasient" +"\n");
        int input = statikInput.nextInt();
        if (input== 1){
            int antallVanedannende = 0;
            for(int teller=0; teller < ReseptListe.storrelsen; teller++){
                if(ReseptListe.hent(teller).legemiddel1 instanceof Vanedannende)
                antallVanedannende ++;
            }
            System.out.println(antallVanedannende); 
        }
        if (input== 2){
            int antallNarkotisk = 0;
            for(int teller=0; teller < ReseptListe.storrelsen; teller++){
                if(ReseptListe.hent(teller).legemiddel1 instanceof Narkotisk)
                antallNarkotisk ++;
            }
            System.out.println(antallNarkotisk); 
    }
        if (input ==3){
            int antallResept = 0;
            for(int teller=0; teller < ReseptListe.storrelsen; teller++){
                if(ReseptListe.hent(teller).legemiddel1 instanceof Narkotisk){
                    System.out.println(ReseptListe.hent(teller).legensNavn); 
                    for(int count=0;count <ReseptListe.hent(teller).legensNavn.utskrevneResepter.storrelsen;count++){
                        if(ReseptListe.hent(teller).legensNavn.utskrevneResepter.hent(count).legemiddel1 instanceof Narkotisk){
                            antallResept++;
                        } 
                    }
                    System.out.println(ReseptListe.hent(teller).legensNavn.utskrevneResepter.); 
                }
            }
            System.out.println( antallResept);
        }
        if (input ==4){
            int antallPasient = 0;
            for(int teller=0; teller < ReseptListe.storrelsen; teller++){
                if(ReseptListe.hent(teller).legemiddel1 instanceof Narkotisk){
                    System.out.println(ReseptListe.hent(teller).PasientID.navn); 
                    for(int count=0;count <ReseptListe.hent(count).PasientID.PasientReseptListe.storrelsen;count++){
                        if(ReseptListe.hent(count).PasientID.PasientReseptListe.hent(count).legemiddel1 instanceof Narkotisk){
                            antallPasient++;
                        } 
                    }
                    System.out.println(ReseptListe.hent(teller).legensNavn.utskrevneResepter.); 
                }
            }
        }
}

    public void BrukerResept() {
        Scanner brukersInput = new Scanner(System.in);
        System.out.println("Hvilken pasient vil du se resepter for?");
        for (int teller = 0; teller < pasientListe.storrelsen; teller++) {
            System.out.println(teller + ": " + pasientListe.hent(teller));
        }
        int brukerns = brukersInput.nextInt();
        System.out.print("Valgt pasient: ");
        System.out.println(pasientListe.hent(brukerns));
        System.out.println("Hvilken resept vil du bruke?");
        for (int teller = 0; teller < ReseptListe.storrelsen; teller++) {
            System.out.println(
                    teller + " " + ReseptListe.hent(teller).legemiddel1.navn + " " + ReseptListe.hent(teller).reit);
        }
        int brukerensResept = brukersInput.nextInt();
        System.out.println();
        if (ReseptListe.hent(brukerensResept).reit == 0) {
            System.out.println("Kunne ikke bruke resept paa " + ReseptListe.hent(brukerensResept).legemiddel1.navn);
        } else {
            ReseptListe.hent(brukerensResept).reit = ReseptListe.hent(brukerensResept).reit - 1;
            System.out.println("Brukte resept paa " + ReseptListe.hent(brukerensResept).legemiddel1.navn + " "
                    + " Antall gjenvaerende reit:" + ReseptListe.hent(brukerensResept).reit);
        }
    }

    public static void main(String[] args) throws UgyldigListeindeks, UlovligUtskrift {

        Scanner brukerenKommet = new Scanner(System.in);// ta kommando fra brukeren.
        String brukeren;
        LegeSystem legesystem = new LegeSystem();
        legesystem.hentFile("legedata.txt");
        legesystem.hentPasienter();
        legesystem.hentLege();
        legesystem.hentLegemidler();
        legesystem.hentResepter();

        /*
         * do{
         * 
         * System.out.println("Trykk \"q\" for å avslutte programmet ");
         * System.out.print("Trykk \"c\" for å forsette programmet ");
         * 
         * 
         * brukeren=brukerenKommet.next();
         * } while(!brukeren.equals("q"));
         */

    }
}