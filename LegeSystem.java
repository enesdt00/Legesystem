import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public  class LegeSystem {
    Liste<Pasient> pasientListe=new IndeksertListe<Pasient>();
    Liste<Legemiddel>legemiddelListe=new IndeksertListe<Legemiddel>();
    Liste<Lege>LegeListe=new Prioritetskoe<Lege>();

    public void hentFile(){
        try{
            File myObj=new File("legedata.txt");
            Scanner myleser=new Scanner(myObj);
            String linje=myleser.nextLine();

            while(myleser.hasNextLine()){
                if(linje.contains("# Pasienter")){
                  while(!linje.startsWith("#")){
                  linje=linje.strip();
                  String[] kolonner=linje.split(",");
                  String navn=kolonner[0];
                  String foedselsnummer=kolonner[1];
                  Pasient pasient=new Pasient(navn, foedselsnummer);
                  pasientListe.leggTil(pasient);
                }}else if(linje.contains("# # Legemidler")){
                    while(!linje.startsWith("#")){
                        linje=linje.strip();
                        String[] kolonner=linje.split(",");
                        String navn=kolonner[0];
                        String Legemiddeltype=kolonner[1];
                       int legemiddelPris= Integer.parseInt(kolonner[2]);// det bytter variabel fra String til double
                        double legemiddelVirkestofet= Double.parseDouble(kolonner[3]);
                        if(Legemiddeltype.toLowerCase().equals("vanlig")){
                            Legemiddel legemiddel=new Vanlig(navn,legemiddelPris,legemiddelVirkestofet) {
                                };

                        }
                        if(Legemiddeltype.toLowerCase().equals("narkotisk")  || Legemiddeltype.toLowerCase().equals("vanedannende") ){
                        int LegemiddelStyrke=Integer.parseInt(kolonner[4]);
                        if(Legemiddeltype.toLowerCase().equals("narkotisk")){

                        }
                        }



                    }
                }
                linje=myleser.nextLine();
            }
            myleser.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        }

    public static void main(String[] args) {
        Liste<Pasient> pasientListe=new IndeksertListe<Pasient>();
        Liste<Legemiddel>legemiddelListe=new IndeksertListe<Legemiddel>();
        Liste<Lege>LegeListe=new Prioritetskoe<Lege>(); 
        Liste<Resept>reseptListe = new Stabel<Resept>();
       Scanner sc = new Scanner(System.in);
        
       String input = "";

       while(!input.equals("quit")){
                if(input.equals("Skrive ut en fullstendig oversikt over pasienter, leger, legemidler og resepter")){
                    System.out.println(pasientListe);
                    System.out.println(legemiddelListe);
                    System.out.println(LegeListe);
                    System.out.println(reseptListe);

                }

                else if(input.equals("Opprette og legge til nye elementer i systemet")){
                    
                }

       }

    
}



}
