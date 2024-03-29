public class Lege implements Comparable<Lege> {
    protected String legensNavn;
    public IndeksertListe<Resept> utskrevneResepter;
    String kontrolKode;

    public Lege(String legensNavn){
        this.legensNavn=legensNavn;
        kontrolKode="0";

        utskrevneResepter=new IndeksertListe<Resept>();
    }

    public String hentNavn(){
        return this.legensNavn;
    }

    public String toString(){
        return "legens navn: "+legensNavn;
    }

    @Override
    public int compareTo(Lege nyLegen) {
       return legensNavn.compareTo(nyLegen.legensNavn);
       
    }

  

    public Resept skrivHvitResept(Legemiddel legemiddel, Pasient pasientID, int reit,Lege legensNavn) throws UlovligUtskrift {
        if(legemiddel instanceof Narkotisk && !(legensNavn instanceof Spesialist) ){
            throw new UlovligUtskrift(legensNavn, legemiddel);
        }
       Resept hvitresept = new hviteResepter(legemiddel,pasientID,reit,legensNavn);
        utskrevneResepter.leggTil(hvitresept);
        pasientID.PasientReseptListe.leggTil(hvitresept);
        return hvitresept;
    }

    public Resept skrivMilResept(Legemiddel legemiddel, Pasient pasientID,Lege legensNavn) throws UlovligUtskrift {
        if(legemiddel instanceof Narkotisk && !(legensNavn instanceof Spesialist) ){
            throw new UlovligUtskrift(legensNavn, legemiddel);
        }
        Resept milResept=new MilResept(legemiddel, pasientID, legensNavn);
        utskrevneResepter.leggTil(milResept);
        pasientID.PasientReseptListe.leggTil(milResept);
        return milResept;
    }


    public Resept skrivPResept(Legemiddel legemiddel, Pasient pasient, int reit,Lege legensNavn) throws UlovligUtskrift {
        if(legemiddel instanceof Narkotisk && !(legensNavn instanceof Spesialist) ){
            throw new UlovligUtskrift(legensNavn, legemiddel);
        }
        Resept pResept = new pResepter(legemiddel,  pasient, reit,legensNavn);
        utskrevneResepter.leggTil(pResept);
        pasient.PasientReseptListe.leggTil(pResept);
        return pResept;
    }

    public Resept skrivBlaaResept(Legemiddel legemiddel, Pasient pasientID,int reit,Lege legensNavn) throws UlovligUtskrift{
        if(legemiddel instanceof Narkotisk && !(legensNavn instanceof Spesialist) ){
            throw new UlovligUtskrift(legensNavn, legemiddel);
        }
        Resept blaaResept=new BlaaResept(legemiddel, pasientID, reit, legensNavn);
        utskrevneResepter.leggTil(blaaResept);
        pasientID.PasientReseptListe.leggTil(blaaResept);
        return blaaResept;
    
    }


   

    public IndeksertListe hentListe(){
       return utskrevneResepter;
 }



   
    
}