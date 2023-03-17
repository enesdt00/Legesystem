import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LegeSystem {
    Liste<Pasient> pasientListe=new IndeksertListe<Pasient>();
    Liste<Legemiddel>legemiddelListe=new IndeksertListe<Legemiddel>();
    Liste<Lege>LegeListe=new Prioritetskoe<Lege>();
    Stabel<Resept> ReseptListe=new Stabel<Resept>();

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



                    }
                }else if(linje.contains("# Leger")){
                    while(!linje.startsWith("#")){
                        linje=linje.strip();
                        String[] kolonner=linje.split(",");
                        String navn=kolonner[0];
                        String legeKontrolnummer=kolonner[1];
                        if(legeKontrolnummer!="0"){
                            Lege spesialister=new Spesialist(navn, legeKontrolnummer);
                            LegeListe.leggTil(spesialister);
                        }else{
                            Lege vanligLege=new Lege(navn);
                            LegeListe.leggTil(vanligLege);

                        }

                     }}
                     else if(linje.contains("# Resepter") && myleser.hasNextLine()){
                        linje=linje.strip();
                        String[] kolonner=linje.split(",");
                        int legemiddelNummer=Integer.parseInt(kolonner[0]);
                        String LegesNavn=kolonner[1];
                        




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
        
       
    
}}
