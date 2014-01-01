package org.shortrip.boozaa.plugins.bootreasure.utils.parsers;

import java.util.LinkedList;
import java.util.Queue;

public class ParserQueue {

	private Queue<Parser> queue = new LinkedList<Parser>();
	
 	synchronized public void enqueue(Parser rew) {    	
 		if( rew != null){
			queue.add(rew);
			notify();
			sendNext();
    	}    	 
    }   

    synchronized public void sendNext() {
        //tant que la queue n'est pas vide
        while(!queue.isEmpty()) {
            try {
                // On envoit le traitement du prochain Parser
                queue.poll().parse(); 
            } catch(Exception ie) {}            
        }        
    }
	
	
}
