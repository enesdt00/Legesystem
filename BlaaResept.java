public class BlaaResept extends Resept {
    public BlaaResept(Legemiddel legemiddel1, Pasient pasientID,int reit,Lege legensNavn){
        super(legemiddel1, pasientID, reit,legensNavn);
    }
    @Override
    public int prisAaBetale(){
            
            return (int) Math.round(legemiddel1.hentPris()*0.25); }
            @Override
    public String farge(){
                return "Blaa";
            }
            
    public String toString(){
        return super.toString();
    }
    
}
