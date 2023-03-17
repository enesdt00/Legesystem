public class Vanedannende extends Legemiddel {
    public final int styrke;
    public Vanedannende(String navn,int pris,  double virkestoffet,int styrke){
        super(navn,pris,virkestoffet);
        this.styrke=styrke;
    }
    @Override
    public String toString(){
        return(super.toString()+ " Styrke:  " + this.styrke);
    }
    
}
