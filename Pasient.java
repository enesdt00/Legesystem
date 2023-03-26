

public class Pasient {
    String navn, fodselsnummer;
    public final int PasientID;
    private static int teller=0;
    IndeksertListe<Resept> PasientReseptListe;

    public Pasient(String navn, String fodselsnummer){
        this.navn=navn;
        this.fodselsnummer=fodselsnummer;
        PasientID=teller;
        teller++;
      PasientReseptListe=new IndeksertListe<Resept>();

    }
    public String toString(){
        return  PasientID +" "+navn+" "+fodselsnummer; 
    }
    
}
