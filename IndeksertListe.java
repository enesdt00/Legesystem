public class IndeksertListe<E> extends Lenkeliste<E> {

   
    public void leggTil(int pos,E x) throws UgyldigListeindeks {
     Node nynoden=new Node(x);
     
     if(pos<0 || pos>storrelsen){
        System.out.println(storrelsen);
        throw new UgyldigListeindeks(pos);
     }
       if (pos==0){
         if (start==null){
            start=nynoden;
            
         }else{
            nynoden.neste=start;
            start=nynoden;

         }} else if(pos==storrelsen){
             leggTil(x);
             storrelsen--;
            

         }else if(pos>0 && pos<storrelsen){
            Node noden = start;
            
            for(int teller=0; teller<pos-1; teller++){
                noden=noden.neste; 
            }
            Node etter=noden.neste;
            noden.neste=nynoden;
            nynoden.neste=etter;
         }
         storrelsen++;
         
         

        }

        public void sett(int pos, E x) throws UgyldigListeindeks{
         Node nynoden=new Node(x);
       
         if(pos<0 || pos>=storrelsen){
            //System.out.println(storrelsen);
            throw new UgyldigListeindeks(pos);
         } if(pos==0){
            nynoden.neste=start;
            start=nynoden;
            
         }else{
            Node noden=start;
            for(int teller=0; teller<pos-1; teller++){
              noden=noden.neste;

            }
          
        Node peker=noden.neste;
         noden.neste=nynoden;
         nynoden.neste=peker.neste; 

      
         }
         

        }
        
        public E hent(int pos)throws UgyldigListeindeks{
         Node noden=start;
         if(pos<0 || pos>=storrelsen){
            System.out.println(storrelsen);
            throw new UgyldigListeindeks(pos);
         }
         if (pos==0) return start.verdi;
         
         
         else{
           
            for(int teller=0; teller<pos; teller++){
               noden=noden.neste;
            }
         }
         return noden.verdi;
        

        }
        public E fjern(int pos){
         if(pos<0 || pos>=storrelsen){
            //System.out.println(storrelsen);
            throw new UgyldigListeindeks(pos);
         }
         if(pos==0){
            Node noden=start;
            start=start.neste;
            storrelsen--;
            return noden.verdi;
         }else{
            Node noden=start;
            for(int teller=0; teller<pos; teller++){
               noden=noden.neste;
            }
            Node peker=noden;
            noden=peker.neste;
            storrelsen--;
            return peker.verdi;

         }
         
        }

    
     
    }
    
