public class pResepter extends hviteResepter{
    public pResepter(Legemiddel legemiddel1, Pasient pasientID,int reit, Lege legensNavn){

        super(legemiddel1, pasientID, reit,legensNavn);
  }
  
  @Override
        public int prisAaBetale(){
            if(legemiddel1.hentPris()-108>=0){
            return legemiddel1.hentPris()-108;
        }else{
            return 0;
        }}
        @Override
        public String farge(){
            return "Hvit";
        }
        @Override
        public String toString(){
            return super.toString();
        }
   
}