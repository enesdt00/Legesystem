public class Narkotisk extends Legemiddel {// husk å sette ny variabel
    public final int styrke;
    public Narkotisk(String navn,int pris,  double virkestoffet, int styrke){
        super(navn,pris,virkestoffet);
        this.styrke=styrke;
    }
    @Override 
    public String toString(){
       return(super.toString()+ " Styrke:  "+ styrke); 
    }
    
}
