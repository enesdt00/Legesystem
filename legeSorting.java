


public class legeSorting{
    public static void main(String[] args) {
        Prioritetskoe<Lege1> lege=new Prioritetskoe<>();
        Lege1 lege1= new Lege1("Zeynep");
        Lege1 lege2= new Lege1("Burhan");
        Lege1 lege3= new Lege1("Ali");

        lege.leggTil(lege1);
        lege.leggTil(lege2);
        lege.leggTil(lege3);

       System.out.println( lege.toString());

    }
}
