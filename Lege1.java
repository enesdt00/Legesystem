public class Lege1 implements Comparable<Lege1> {
        protected String legensNavn;
        public Lege1(String legensNavn){
            this.legensNavn=legensNavn;
        }
        @Override
        public int compareTo(Lege1 lege){
            return this.legensNavn.compareTo(lege.legensNavn);
        }

        @Override 
        public String toString(){
            return legensNavn;
        }
}