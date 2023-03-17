public class hviteResepter extends Resept {
    public hviteResepter(Legemiddel legemiddel1, Pasient pasientID,int reit,Lege legensNavn){
        super(legemiddel1, pasientID, reit,legensNavn);
    }
    @Override
    public int prisAaBetale(){
        return  legemiddel1.hentPris();
       
    }
    @Override
    public String farge(){
        return "Hvit";
    }
    public String toString(){
        return super.toString();
    }
    
}