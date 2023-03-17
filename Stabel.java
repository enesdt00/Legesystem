public class Stabel<E> extends Lenkeliste<E> {
    @Override
    public void leggTil(E x){
        Node nynoden= new Node(x);
        Node noden;
       

        if(start==null){// i begynnelsen er start lik til node.
            start=nynoden;
            noden=nynoden;
        }else{
            
            
               nynoden.neste=start;
               start=nynoden;
            
           
        }
        storrelsen++;
        


    }
    
}
