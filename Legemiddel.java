public abstract class Legemiddel{
    public final String navn;
    public final double virkestoffet;
    protected int pris;
   public final int ID;
   private static int teller=0;


    public Legemiddel(String navn,int pris,  double virkestoffet){
        this.navn=navn;
        this.pris=pris;
        this.virkestoffet=virkestoffet;
        this.ID=teller; // skulle være static
        teller++;
        
    }

    public String hentNavn(){
        return navn;
    }

    public int hentPris(){
        return this.pris;
    }
    public void settNyPris(int nyPris){
        this.pris=nyPris;

    }
    @Override
    public String toString(){
        return(navn+" ID: "+ID + " prisen: " + pris + " Virkestoffet:   " + virkestoffet);
    }

   
}