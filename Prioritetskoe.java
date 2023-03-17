public class Prioritetskoe<E extends Comparable<E>> extends Lenkeliste<E> {

    @Override
    public void leggTil(E x) {


        Node nynoden=new Node(x);
        
       
      
       if(start==null){start=nynoden;}
       else{Node noden=start;
        if(noden.verdi.compareTo(nynoden.verdi) >= 0){
            nynoden.neste=start;
            start=nynoden;
        }else{
        while(noden.neste!=null && noden.neste.verdi.compareTo(nynoden.verdi) < 0 ){
            
        

            noden=noden.neste;

            

        } 
    nynoden.neste=noden.neste;
    noden.neste=nynoden;}
    }
       
           
           
        
        storrelsen++;

  
    
      
       
      

   
    }
    @Override
    public E hent()throws UgyldigListeindeks{
     if(start==null){
         throw new UgyldigListeindeks(-1);
     }
     return start.verdi;
    }
    @Override
    public E fjern() throws UgyldigListeindeks{
     if(start==null){
        throw new UgyldigListeindeks(-1);
     }else{
     Node noden=start;
     start=start.neste;
     storrelsen--;
     return noden.verdi;}
 
    }

  
  

    
}

