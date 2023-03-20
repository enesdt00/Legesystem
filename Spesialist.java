public class Spesialist extends Lege implements Godkjenningsfritak{
    public String Kontrollkode;
        
    public Spesialist(String legensNavn, String Kontrollkode){
        super(legensNavn);
        this.Kontrollkode= Kontrollkode;
    }
@Override 
 public String hentKontrollkode(){
    return Kontrollkode;
 }

    public String toString(){
        return super.toString()+" Kontrollkode: "+ Kontrollkode;
    }
}