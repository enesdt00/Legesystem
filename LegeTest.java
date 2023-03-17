public class LegeTest {
    public static void main(String[] args) throws UlovligUtskrift {
        Lege lege1=new Lege("Oscar");
        Lege lege2=new Lege("Ada");

        

       
        Legemiddel narkotisk= new Narkotisk("Amfetamin", 1000, 0.002, 7);//id=1
        Legemiddel vanedannende=new Vanedannende("Zopiklon", 1245, 0.05, 6);//id=2
        Legemiddel vanlig=new Vanlig("Ibux", 49, 0.02);//id=3
        Pasient pasient1=new Pasient("Ali", "20158920564");
        Pasient pasient2=new Pasient("veli", "09128920564");
        Pasient pasient3=new Pasient("Ali", "30098920564");
       
        lege1.skrivHvitResept(narkotisk, pasient1, 12, lege1);
        lege1.skrivHvitResept(vanlig, pasient2, 12, lege2);
        lege1.skrivHvitResept(vanedannende, pasient3, 12, lege1);
        System.out.println(lege1.hentListe());


       
       
     
        


    }
    
}
