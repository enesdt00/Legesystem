class MilResept extends hviteResepter {
    public MilResept(Legemiddel legemiddel1, Pasient pasientID,Lege legensNavn){
        super(legemiddel1, pasientID,3, legensNavn);
    }
    @Override
    public int prisAaBetale() {
        return 0;
    }
    @Override
    public String farge(){
        return "Hvit";
    }
    public String toString(){
        return super.toString();
    }
    
    
}