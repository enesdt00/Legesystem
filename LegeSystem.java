import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class LegeSystem  {
   
    IndeksertListe<Pasient> pasientListe=new IndeksertListe<Pasient>();
    IndeksertListe<Legemiddel> legemiddelListe=new IndeksertListe<Legemiddel>();
   Prioritetskoe<Lege>LegeListe=new Prioritetskoe<Lege>();
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
                 if(myleser.hasNextLine()) linje=myleser.nextLine();
                 
                 
                  //LEGEMIDLERS LISTE
                } 
               // System.out.println("sjekke Pasienter");
            } if(linje.contains("# Legemidler (navn,type,pris,virkestoff,[styrke])")){
                linje=myleser.nextLine();
                    while(myleser.hasNextLine() && !linje.startsWith("#")){
                        linje=linje.strip();
                        String[] kolonner=linje.split(",");
                        String navn=kolonner[0];
                        String Legemiddeltype=kolonner[1];
                       int legemiddelPris= Integer.parseInt(kolonner[2]);// det bytter variabel fra String til double
                      
                       double legemiddelVirkestofet;                       
                       if(kolonner.length>=4 && kolonner[3]!=null){
                       legemiddelVirkestofet= Double.parseDouble(kolonner[3]);}
                       else{
                      legemiddelVirkestofet=  0.0;
                       }
                       
                        if(Legemiddeltype.toLowerCase().equals("vanlig")){
                           
                            Legemiddel legemiddel=new Vanlig(navn,legemiddelPris,legemiddelVirkestofet);
                            
                            legemiddelListe.leggTil(legemiddel);
                            linje=myleser.nextLine();
                                }
                        else if(Legemiddeltype.toLowerCase().equals("narkotisk")  || Legemiddeltype.toLowerCase().equals("vanedannende") ){
                        int LegemiddelStyrke=Integer.parseInt(kolonner[4]);
                        if(Legemiddeltype.toLowerCase().equals("narkotisk")){
                        Legemiddel legemiddel=new Narkotisk(navn,legemiddelPris,legemiddelVirkestofet,LegemiddelStyrke);
                        legemiddelListe.leggTil(legemiddel);
                        linje=myleser.nextLine();
                      }else if(Legemiddeltype.toLowerCase().equals("vanedannende")){
                         Legemiddel legemiddel=new Vanedannende(navn,legemiddelPris,legemiddelVirkestofet,LegemiddelStyrke);
                         legemiddelListe.leggTil(legemiddel);
                         linje=myleser.nextLine();
                        } 
                    }

                    

                    }
                }//LEGELISTE
                 if(linje.contains("# Leger")){
                    linje=myleser.nextLine();
                    while(myleser.hasNextLine() && !linje.startsWith("#")){
                        linje=linje.strip();
                        String[] kolonner=linje.split(",");
                        String navn=kolonner[0];
                        String legeKontrolnummer=kolonner[1];
                        if(!legeKontrolnummer.equals("0")){//fant spesialister
                            Spesialist spesialister=new Spesialist(navn, legeKontrolnummer);
                            LegeListe.leggTil(spesialister);
                        }else{
                            Lege vanligLege=new Lege(navn);
                            LegeListe.leggTil(vanligLege);



                        } linje=myleser.nextLine();

                     }
                    }
                if(linje.contains("# Resepter")){
                        linje=myleser.nextLine();
                        while((myleser.hasNextLine() || myleser!=null) && !linje.startsWith("#") ){
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
                               ReseptListe.leggTil(blaaResept);
                              // LegeListe.hent(teller).utskrevneResepter.leggTil(blaaResept);
                             //  pasientListe.hent(PasientID).PasientReseptListe.leggTil(blaaResept);
                            }}

                        }else if(ReseptType.toLowerCase().equals("hvit")){
                            int hvitReseptsreit=Integer.parseInt(kolonner[4]);
                            Resept hvitResept;
                            for(int teller=0; teller<LegeListe.storrelsen; teller++){
                                if(LegeListe.hent(teller).legensNavn.equals(LegesNavn)){// fant samme Dr.
                             hvitResept=LegeListe.hent(teller).skrivHvitResept(legemiddelListe.hent(legemiddelNummer), pasientListe.hent(PasientID), hvitReseptsreit,LegeListe.hent(teller));
                            ReseptListe.leggTil(hvitResept);
                           // LegeListe.hent(teller).utskrevneResepter.leggTil(hvitResept);
                           // pasientListe.hent(PasientID).PasientReseptListe.leggTil(hvitResept);
                        }}
                        }else if(ReseptType.toLowerCase().equals("p")){
                            int pReseptsreit=Integer.parseInt(kolonner[4]);
                            Resept pResept;
                            for(int teller=0; teller<LegeListe.storrelsen; teller++){
                                if(LegeListe.hent(teller).legensNavn.equals(LegesNavn)){
                             pResept=LegeListe.hent(teller).skrivPResept(legemiddelListe.hent(legemiddelNummer), pasientListe.hent(PasientID), pReseptsreit, LegeListe.hent(teller));
                            ReseptListe.leggTil(pResept);
                           // LegeListe.hent(teller).utskrevneResepter.leggTil(pResept);
                           // pasientListe.hent(PasientID).PasientReseptListe.leggTil(pResept);
                        }}
                        }else if(ReseptType.toLowerCase().equals("militaer")){
                            Resept milResept;
                            for(int teller=0; teller<LegeListe.storrelsen; teller++){
                                if(LegeListe.hent(teller).legensNavn.equals(LegesNavn)){
                            
                            milResept=LegeListe.hent(teller).skrivMilResept(legemiddelListe.hent(legemiddelNummer), pasientListe.hent(PasientID),  LegeListe.hent(teller));
                            ReseptListe.leggTil(milResept);
                           // LegeListe.hent(teller).utskrevneResepter.leggTil(milResept);
                           // pasientListe.hent(PasientID).PasientReseptListe.leggTil(milResept);
                        }}
                        }
                       if((!myleser.hasNextLine())&& myleser!=null) break;
                        linje=myleser.nextLine();
                       
                    
                    }
                } 
            
                //linje=myleser.nextLine(); }
            }    myleser.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

          }
          
        
        }
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

        public void BrukerResept() throws IOException, UgyldigListeindeks, UlovligUtskrift{
            Scanner brukersInput= new Scanner(System.in);
            System.out.println("Hvilken pasient vil du se resepter for?");
            for(int teller=0; teller<pasientListe.storrelsen; teller++){
            System.out.println(pasientListe.hent(teller));}

            int brukerns=brukersInput.nextInt();

            System.out.print("Valgt pasient: ");
            System.out.println(pasientListe.hent(brukerns));
            System.out.println("Hvilken lege vil du behandle deg?");
            for(int teller=0; teller<LegeListe.storrelsen; teller++){
                System.out.println(teller+" "+LegeListe.hent(teller));}

            int brukernsLege=brukersInput.nextInt();
            System.out.println("Hvilken resept vil du bruke?");
            for(int teller=0; teller< ReseptListe.storrelsen; teller++){
                 System.out.println(teller+" "+ReseptListe.hent(teller).legemiddel1.navn+" "+ReseptListe.hent(teller).reit);
                    } int brukerensResept= brukersInput.nextInt();
                    System.out.println();
                    if(ReseptListe.hent(brukerensResept).reit==0){
                        System.out.println("Kunne ikke bruke resept paa "+ReseptListe.hent(brukerensResept).legemiddel1.navn);
                   }
                   else{
                    ReseptListe.hent(brukerensResept).reit=ReseptListe.hent(brukerensResept).reit-1;}
            System.out.println("Hvilken type Resept trenger du?");
            System.out.println("1. Militaer");
            System.out.println("2.blaaResept");
            System.out.println("3 HvitResept");
            System.out.println("4 PResept");
            int BrukerReseptType=brukersInput.nextInt();
            switch (BrukerReseptType) {
                case 1:
                LegeListe.hent(brukernsLege).skrivMilResept(legemiddelListe.hent(brukerensResept),pasientListe.hent(brukerns),LegeListe.hent(brukernsLege));
                    break;
                case 2:
                LegeListe.hent(brukernsLege).skrivBlaaResept(legemiddelListe.hent(brukerensResept),pasientListe.hent(brukerns),ReseptListe.hent(brukerensResept).reit,LegeListe.hent(brukernsLege));
                break;
                case 3:
                LegeListe.hent(brukernsLege).skrivHvitResept(legemiddelListe.hent(brukerensResept),pasientListe.hent(brukerns),ReseptListe.hent(brukerensResept).reit,LegeListe.hent(brukernsLege));
                break;
                
                default:
                LegeListe.hent(brukernsLege).skrivPResept(legemiddelListe.hent(brukerensResept),pasientListe.hent(brukerns),ReseptListe.hent(brukerensResept).reit,LegeListe.hent(brukernsLege));
                    break;
            }
                     System.out.println("Brukte resept paa "+ReseptListe.hent(brukerensResept).legemiddel1.navn+" "+" Antall gjenvaerende reit:" + ReseptListe.hent(brukerensResept).reit);
                     String file = "nylegedata.txt";
                     FileWriter nyskriver = new FileWriter(file, true);
                     
                     if (new File(file).length() == 0) {
                         nyskriver.write("# PasientsNavn, Legemidler, Forskrivende Legen\n");
                     }
                     
                     nyskriver.write("Pasient: "+ String.valueOf(pasientListe.hent(brukerns).navn) + ", Legemiddel: " + String.valueOf(ReseptListe.hent(brukerensResept).legemiddel1.navn) + ", Lege: " + String.valueOf(LegeListe.hent(brukernsLege).legensNavn) + "\n");
                     
                     nyskriver.close();
                    }

                    



                     /* 
                File file = new File("legedata.txt");
                try{
                    Scanner myleser = new Scanner(file);
                    String linje = myleser.nextLine();
                    FileWriter nyskriver = new FileWriter(file, true);
                    while(myleser.hasNextLine()) {
                        linje = myleser.nextLine();
                        if(linje.startsWith("# Resepter")) {
                             continue;}
                            if(!myleser.hasNextLine() && myleser!=null) {
                                linje=myleser.nextLine();
                                nyskriver.write("\n" + String.valueOf(brukerensResept) + ", " + String.valueOf(legeny.legensNavn) + ", " + String.valueOf(pasientListe.hent(brukerns))/*+","+type+ type+", "+String.valueOf(ReseptListe.hent(brukerensResept).reit));
                            }
                        }
                       
                    
                    myleser.close();
                    nyskriver.flush();
                    nyskriver.close();
                } catch (FileNotFoundException e) {
                    System.out.println("File not found: " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("An error occurred while writing to the file: " + e.getMessage());
                }}*/
            

       public void statistikk(){
        Scanner statikInput = new Scanner(System.in);
        System.out.println("Her er det Menyen som du kan velge:"+"\n"+
        "1 Totalt antall utskrevne resepter på vanedannende legemidler"+"\n"+
        "2 Totalt antall utskrevne resepter på narkotiske legemidler"+"\n"+
        "3. List opp navnene på alle leger (i alfabetisk rekkefølge) som har skrevet ut" +"\n"+
        "minst en resept på narkotiske legemidler, og antallet slike resepter per lege."+"\n"+
        "4.List opp navnene på alle pasienter som har minst en gyldig resept på"+"\n"+
        "narkotiske legemidler og, for disse, skriv ut antallet per pasient" +"\n");
        int input = statikInput.nextInt();
        if (input== 1){
            int antallVanedannende = 0;
            for(int teller=0; teller < LegeListe.storrelsen; teller++){
                for(int teller1=0; teller1<LegeListe.hent(teller).utskrevneResepter.storrelsen; teller1++ ){
                if(LegeListe.hent(teller).utskrevneResepter.hent(teller1).legemiddel1 instanceof Vanedannende)
                antallVanedannende ++;
            }}
            System.out.println("Totalt antall utskrevne resepter på vanedannende legemidler: "+antallVanedannende); 
        }
        if (input== 2){
            int antallNarkotisk = 0;
            for(int teller=0; teller < LegeListe.storrelsen; teller++){
                for(int teller1=0; teller1<LegeListe.hent(teller).utskrevneResepter.storrelsen; teller1++ ){
                    if(LegeListe.hent(teller).utskrevneResepter.hent(teller1).legemiddel1 instanceof Narkotisk)
                    antallNarkotisk ++;
            }}
            System.out.println("Totalt antall utskrevne resepter på narkotiske legemidler: "+antallNarkotisk); 
    }
        if (input ==3){
            int antallResept = 0;
            for(int teller=0; teller < LegeListe.storrelsen; teller++){
                for(int teller1=0; teller1<LegeListe.hent(teller).utskrevneResepter.storrelsen; teller1++ ){
                if(LegeListe.hent(teller).utskrevneResepter.hent(teller1).legemiddel1 instanceof Narkotisk){
                    antallResept++;
                   if(antallResept==1)  System.out.print(LegeListe.hent(teller).legensNavn+": ");
                  
                }
            }if(antallResept>0) System.out.println(antallResept);
            antallResept=0;
      
        }}
        if (input ==4){
            int antallPasient = 0;
            for(int teller=0; teller < pasientListe.storrelsen; teller++){
                for(int teller1=0; teller1<pasientListe.hent(teller).PasientReseptListe.storrelsen; teller1++){
                if(pasientListe.hent(teller).PasientReseptListe.hent(teller1).legemiddel1 instanceof Narkotisk){
                    antallPasient++;
                    if(antallPasient==1) System.out.print(pasientListe.hent(teller).navn+": ");
                    
                } }
                if(antallPasient>0) System.out.println(antallPasient);
                antallPasient=0;
            }
        }
}

           
            

            
           
        

    public static void main(String[] args) throws UgyldigListeindeks, UlovligUtskrift, IOException {
        
        Scanner brukerenKommet= new Scanner(System.in);// ta kommando fra brukeren.
        String brukeren;
        LegeSystem legesystem=new LegeSystem();
            legesystem.hentFile("legedata.txt");
            System.out.println("-------Pasienter------");
          legesystem.hentPasienter();
          System.out.println("-------Leger------");
          legesystem.hentLege();
          System.out.println("-------Legemidler------");
          legesystem.hentLegemidler();
          System.out.println("-------Resepter------");
          legesystem.hentResepter();
        

           

      do{
      
        legesystem.BrukerResept();
        legesystem.statistikk();

      
            System.out.println("Trykk \"q\" for aa avslutte programmet ");
            System.out.print("Trykk \"c\" for aa forsette programmet ");

           
            brukeren=brukerenKommet.next();
        } while(!brukeren.equals("q"));
        

      
    
}
    }
