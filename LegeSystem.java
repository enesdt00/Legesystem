import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class LegeSystem extends Lenkeliste {
    private static final String String = null;
    IndeksertListe<Pasient> pasientListe=new IndeksertListe<Pasient>();
    IndeksertListe<Legemiddel> legemiddelListe=new IndeksertListe<Legemiddel>();
    IndeksertListe<Lege>LegeListe=new IndeksertListe<Lege>();
    IndeksertListe<Resept> ReseptListe=new IndeksertListe<Resept>();

    public void hentFile( String filenavn) throws UgyldigListeindeks, UlovligUtskrift{
        try{
            File file =new File(filenavn);
            Scanner myleser=new Scanner(file);
            String linje=myleser.nextLine();

            while(myleser.hasNextLine()){
                //PASIENTLISTE
                if(linje.contains("# Pasienter")){
                   linje=myleser.nextLine();
                  while(myleser.hasNextLine() && !linje.startsWith("#")){
                  linje=linje.strip();
                  String[] kolonner=linje.split(",");
                  String navn=kolonner[0];
                  String foedselsnummer=kolonner[1];
                  Pasient pasient=new Pasient(navn, foedselsnummer);
                 
                  pasientListe.leggTil(pasient);
                  linje=myleser.nextLine();
                 
                 
                  //LEGEMIDLERS LISTE
                } 
               // System.out.println("sjekke Pasienter");
            }else if(linje.contains("# Legemidler")){
                linje=myleser.nextLine();
                    while(myleser.hasNextLine() && !linje.startsWith("#")){
                        linje=linje.strip();
                        String[] kolonner=linje.split(",");
                        String navn=kolonner[0];
                        String Legemiddeltype=kolonner[1];
                       int legemiddelPris= Integer.parseInt(kolonner[2]);// det bytter variabel fra String til double
                        double legemiddelVirkestofet= Double.parseDouble(kolonner[3]);
                        if(Legemiddeltype.toLowerCase().equals("vanlig")){
                            Legemiddel legemiddel=new Vanlig(navn,legemiddelPris,legemiddelVirkestofet);
                            legemiddelListe.leggTil(legemiddel);
                                };

                        
                        if(Legemiddeltype.toLowerCase().equals("narkotisk")  || Legemiddeltype.toLowerCase().equals("vanedannende") ){
                        int LegemiddelStyrke=Integer.parseInt(kolonner[4]);
                        if(Legemiddeltype.toLowerCase().equals("narkotisk")){
                        Legemiddel legemiddel=new Narkotisk(navn,legemiddelPris,legemiddelVirkestofet,LegemiddelStyrke);
                        legemiddelListe.leggTil(legemiddel);
                      }if(Legemiddeltype.toLowerCase().equals("vannedanne")){
                         Legemiddel legemiddel=new Vanedannende(navn,legemiddelPris,legemiddelVirkestofet,LegemiddelStyrke);
                         legemiddelListe.leggTil(legemiddel);
                        } 
                    }



                    }//System.out.println("sjekke legemidler");
                }//LEGELISTE
                else if(linje.contains("# Leger")){
                    linje=myleser.nextLine();
                    while(myleser.hasNextLine() && !linje.startsWith("#")){
                        linje=linje.strip();
                        String[] kolonner=linje.split(",");
                        String navn=kolonner[0];
                        String legeKontrolnummer=kolonner[1];
                        if(legeKontrolnummer!="0"){//fant spesialister
                            Lege spesialister=new Spesialist(navn, legeKontrolnummer);
                            LegeListe.leggTil(spesialister);
                        }else{
                            Lege vanligLege=new Lege(navn);
                            LegeListe.leggTil(vanligLege);



                        } linje=myleser.nextLine();

                     }//System.out.println("sjekke legeliste");
                    }
                     else if(linje.contains("# Resepter")){
                        linje=myleser.nextLine();
                        while(myleser.hasNextLine() && !linje.startsWith("#")){
                        linje=linje.strip();
                        String[] kolonner=linje.split(",");
                        int legemiddelNummer=Integer.parseInt(kolonner[0]); // skal peke legemiddelListe
                        String LegesNavn=kolonner[1];// ska peke LegeListe
                        int PasientID=Integer.parseInt(kolonner[2]);// ska peke PasientListe
                        String ReseptType=kolonner[3];// ska sette if betinget for alle typene
                        if(ReseptType.toLowerCase().equals("blaa")){
                            int blaaReseptsreit=Integer.parseInt(kolonner[4]);
                            Resept blaaResept;
                            for(int teller=0; teller<LegeListe.storrelsen; teller++){
                              
                                if(LegeListe.hent(teller).legensNavn.equals(LegesNavn)){
                               blaaResept= LegeListe.hent(teller).skrivBlaaResept(legemiddelListe.hent(legemiddelNummer), pasientListe.hent(PasientID), blaaReseptsreit, LegeListe.hent(teller));
                               ReseptListe.leggTil(blaaResept);}}

                        }else if(ReseptType.toLowerCase().equals("hvit")){
                            int hvitReseptsreit=Integer.parseInt(kolonner[4]);
                            Resept hvitResept;
                            for(int teller=0; teller<LegeListe.storrelsen; teller++){
                                if(LegeListe.hent(legemiddelNummer).legensNavn.equals(LegesNavn)){// fant samme Dr.
                             hvitResept=LegeListe.hent(teller).skrivHvitResept(legemiddelListe.hent(legemiddelNummer), pasientListe.hent(PasientID), hvitReseptsreit,LegeListe.hent(teller));
                            ReseptListe.leggTil(hvitResept);}}
                        }else if(ReseptType.toLowerCase().equals("p")){
                            int pReseptsreit=Integer.parseInt(kolonner[4]);
                            Resept pResept;
                            for(int teller=0; teller<LegeListe.storrelsen; teller++){
                                if(LegeListe.hent(legemiddelNummer).legensNavn.equals(LegesNavn)){
                             pResept=LegeListe.hent(teller).skrivPResept(legemiddelListe.hent(legemiddelNummer), pasientListe.hent(PasientID), pReseptsreit, LegeListe.hent(teller));
                            ReseptListe.leggTil(pResept);}}
                        }else if(ReseptType.toLowerCase().equals("militaer")){
                            Resept milResept;
                            for(int teller=0; teller<LegeListe.storrelsen; teller++){
                                if(LegeListe.hent(legemiddelNummer).legensNavn.equals(LegesNavn)){
                            
                            milResept=LegeListe.hent(teller).skrivMilResept(legemiddelListe.hent(legemiddelNummer), pasientListe.hent(PasientID),  LegeListe.hent(teller));
                            ReseptListe.leggTil(milResept);}}
                        }
                        linje=myleser.nextLine();}//System.out.println("sjekke Resepter");
                }
                linje=myleser.nextLine(); }
            myleser.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

          }
          
        
        }/* class VaarIterator implements Iterator{
            Node noden=start;
            @Override
            public boolean hasNext(){
                return noden !=null;
            }
    
            @Override
            public String next(){
                String hentesUt= noden.verdi;
                noden=noden.neste;
                return hentesUt;
            }
            
        }
    
        public Iterator<String>iterator(){
            return new VaarIterator();
        }*/ 
        public void hentPasienter(){
  for(int teller=0; teller<pasientListe.storrelsen; teller++){
    System.out.println(pasientListe.hent(teller));
  }  }  
  public void hentLege(){
    for(int teller=0; teller<LegeListe.storrelsen; teller++){
      System.out.println(LegeListe.hent(teller));
    } }
    public void hentLegemidler(){
        for(int teller=0; teller<legemiddelListe.storrelsen; teller++){
          System.out.println(legemiddelListe.hent(teller));
        }                             
          
        }
        public void hentResepter(){
            for(int teller=0; teller<ReseptListe.storrelsen; teller++){
              System.out.println(ReseptListe.hent(teller));
            }}

    public static void main(String[] args) throws UgyldigListeindeks, UlovligUtskrift {
        
        Scanner brukerenKommet= new Scanner(System.in);// ta kommando fra brukeren.
        String brukeren;
        LegeSystem legesystem=new LegeSystem();
            legesystem.hentFile("legedata.txt");
            legesystem.hentPasienter();
            legesystem.hentLege();
           legesystem.hentLegemidler();
            //legesystem.hentResepter();

      /* do{
      
            System.out.println("Trykk \"q\" for å avslutte programmet ");
            System.out.print("Trykk \"c\" for å forsette programmet ");

           
            brukeren=brukerenKommet.next();
        } while(!brukeren.equals("q"));*/
        

      
    
}
    }
