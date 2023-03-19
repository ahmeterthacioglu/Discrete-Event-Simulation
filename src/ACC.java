import java.util.*;
public class ACC {
	public String accName;
    Queue<Flight> Ready_Queue = new LinkedList<>();
    PriorityQueue<Flight> Waiting_Queue = new PriorityQueue<>(new Flight.Comparator1());
    ArrayList<ATC> ATCs_in_ACC = new ArrayList<>();
    PriorityQueue<Flight> minimumContainer = new PriorityQueue<>(new Flight.Comparator());
    PriorityQueue<Flight> fligthsByAdmissionTime = new PriorityQueue<>(new Flight.Comparator2());
    int totalNumberOfFlights;
    int finishedFlights = 0;
    int time = 0;
    boolean checker = true;
    public ACC(String accName){
        this.accName = accName;
    }
    public void addATC(ATC atc){
        ATCs_in_ACC.add(atc);
    }
    public void checkerACCWaiting(ACC acc,int duration) {
    	if(!acc.Waiting_Queue.isEmpty()){
            for(Flight flight : acc.Waiting_Queue){
                flight.operations[flight.step] -= duration;
            }
    	}
    }
    public Flight FindingMinAtcWaiting(ArrayList<ATC> atcs){
        int min = 1000000;
        Flight flight = null;
        for(ATC atc : atcs){
            if(!atc.Waiting_Queue.isEmpty()){
                Flight flightAtc = atc.Waiting_Queue.peek();
                if(flightAtc.operations[flightAtc.step] >= min)
                    continue;
                else{
                    min = flightAtc.operations[flightAtc.step];
                    flight = flightAtc;

                }
            }
            else
                continue;
        }

        return flight;
    }
    
    public Flight FindingMinATCRQWaiting(ArrayList<ATC> atcs){
        int min = 10000000;
        Flight flight = null;
        for(ATC atc: atcs){
            if(!atc.Ready_Queue.isEmpty()){
                Flight flightAtc = atc.Ready_Queue.peek();
                if(flightAtc.operations[flightAtc.step] >= min)
                    continue;
                else {
                    min = flightAtc.operations[flightAtc.step];
                    flight = flightAtc;

                }
            }
        }
        return flight;
    }
    
    public void checkerATCWaiting(ArrayList<ATC> atcs,int duration) {
    	
    	for(ATC atc : atcs) {
    		if(!atc.Waiting_Queue.isEmpty()) {
    			for(Flight flight :atc.Waiting_Queue) {
    				flight.operations[flight.step] -= duration;

    			}
    		}
    	}
    	
    }
    
    public void checkerATCRQ(ArrayList<ATC> atcs,int duration) {
    	
    	for(ATC atc : atcs) {
    		if(!atc.Ready_Queue.isEmpty()) {
    			Flight flight = atc.Ready_Queue.peek();

    			flight.operations[flight.step] -= duration;
    			
    		}
    	}
    }
    

    
    public void process(ACC acc) {
    	boolean checker1 = true;
    	Flight tempFlight = null;

    	while(acc.finishedFlights<acc.totalNumberOfFlights) {
    		if(acc.Ready_Queue.isEmpty()) {
    			int timeDiff = 1;
                
                if(FindingMinATCRQWaiting(ATCs_in_ACC) != null) {

                	minimumContainer.add(FindingMinATCRQWaiting(ATCs_in_ACC));
                }
                if(FindingMinAtcWaiting(ATCs_in_ACC) != null) {

                	minimumContainer.add(FindingMinAtcWaiting(ATCs_in_ACC));
                }
                if(acc.Waiting_Queue.peek() != null) {

                	minimumContainer.add(acc.Waiting_Queue.peek());
                }
                
                
                int remainingTime = 10000;
                Flight closestFlight = null; 

                if (!acc.fligthsByAdmissionTime.isEmpty()) {
                    remainingTime = acc.fligthsByAdmissionTime.peek().admissionTime - acc.time;

                }
                if(acc.fligthsByAdmissionTime.isEmpty()) {
                	remainingTime= 10000;
                }
                if(!minimumContainer.isEmpty()) {
                    closestFlight = minimumContainer.peek(); 
                	timeDiff = closestFlight.operations[closestFlight.step];
                }
                if(minimumContainer.isEmpty()) {
                	timeDiff = remainingTime;
                	acc.Ready_Queue.add(acc.fligthsByAdmissionTime.poll());
                	time += timeDiff;
                	continue;
                }
                if(timeDiff > remainingTime) {
                	timeDiff = remainingTime;
                	checkerACCWaiting(acc,timeDiff);
                	checkerATCRQ(ATCs_in_ACC, timeDiff);
                	checkerATCWaiting(ATCs_in_ACC, timeDiff);
                	acc.Ready_Queue.add(acc.fligthsByAdmissionTime.poll());
                	
                	time += timeDiff;
                	minimumContainer.clear();
                	              
                	continue;	
                }
                
                
                	
                if( closestFlight != null && timeDiff == remainingTime  && closestFlight.flightName.compareTo(acc.fligthsByAdmissionTime.peek().flightName)==1) {
                	checkerACCWaiting(acc,timeDiff);
                   	checkerATCRQ(ATCs_in_ACC, timeDiff);
                   	checkerATCWaiting(ATCs_in_ACC, timeDiff);
                	acc.Ready_Queue.add(acc.fligthsByAdmissionTime.poll());
                   	
                   	time += timeDiff;
                	minimumContainer.clear();

                 
                   	continue;	 	
                }

                if(FindingMinATCRQWaiting(ATCs_in_ACC) != null && closestFlight == FindingMinATCRQWaiting(ATCs_in_ACC)) {
                	checkerACCWaiting(acc,timeDiff);
                	checkerATCRQ(ATCs_in_ACC, timeDiff);
                	checkerATCWaiting(ATCs_in_ACC, timeDiff);
                	for(ATC atc : ATCs_in_ACC) {
                		if(!atc.Ready_Queue.isEmpty())
                			if(atc.Ready_Queue.peek().flightName == closestFlight.flightName) {
                				if(closestFlight.step == 9||closestFlight.step == 19) {

                					if(closestFlight.step == 9)
                						closestFlight.departingAtc.Ready_Queue.remove(closestFlight);
                					else
                						closestFlight.landingAtc.Ready_Queue.remove(closestFlight);
                					closestFlight.acc.Ready_Queue.add(closestFlight);
                				}
                				else {

                					if(closestFlight.step<10) {
                						closestFlight.departingAtc.Ready_Queue.remove(closestFlight);
                						closestFlight.departingAtc.Waiting_Queue.add(closestFlight);
                					}
                					else {
                						closestFlight.landingAtc.Ready_Queue.remove(closestFlight);
                						closestFlight.landingAtc.Waiting_Queue.add(closestFlight);
                					}
                				}
            					closestFlight.step++;                				
                			}
                		
                	}
                	
                	minimumContainer.poll();
                	time += timeDiff;
                	minimumContainer.clear();
                	
                    continue;
                }
                if(FindingMinAtcWaiting(ATCs_in_ACC) != null && closestFlight == FindingMinAtcWaiting(ATCs_in_ACC)) {
    				checkerACCWaiting(acc, timeDiff);
                	checkerATCRQ(ATCs_in_ACC, timeDiff);
                	checkerATCWaiting(ATCs_in_ACC, timeDiff);

                	if(closestFlight.step < 10) {
                		closestFlight.departingAtc.Waiting_Queue.remove(closestFlight);
                		closestFlight.departingAtc.Ready_Queue.add(closestFlight);
                	}
                	else {
                		closestFlight.landingAtc.Waiting_Queue.remove(closestFlight);
                		closestFlight.landingAtc.Ready_Queue.add(closestFlight);
                	}
                	closestFlight.step++;
                	
                	minimumContainer.poll();
                	time += timeDiff;
                	minimumContainer.clear();
                	
                    continue;
                }
                if(acc.Waiting_Queue.peek() != null && closestFlight.flightName == acc.Waiting_Queue.peek().flightName ) {
    				checkerACCWaiting(acc, timeDiff);
                	checkerATCRQ(ATCs_in_ACC, timeDiff);
                	checkerATCWaiting(ATCs_in_ACC, timeDiff);

                	acc.Waiting_Queue.remove(closestFlight);
                	acc.Ready_Queue.add(closestFlight);
                	closestFlight.step++;
                	
                	minimumContainer.poll();
                	time += timeDiff;
                	minimumContainer.clear();
                	
                    continue;
                }
               
    		}
    		else {

    			int timeDiff = 1;
                Flight flightACC = acc.Ready_Queue.peek();
                
                if(FindingMinATCRQWaiting(ATCs_in_ACC) != null) {
                	minimumContainer.add(FindingMinATCRQWaiting(ATCs_in_ACC));
                }
                if(FindingMinAtcWaiting(ATCs_in_ACC) != null) {
                	minimumContainer.add(FindingMinAtcWaiting(ATCs_in_ACC));
                }
                if(acc.Waiting_Queue.peek() != null) {
                	minimumContainer.add(acc.Waiting_Queue.peek());
                }
                if(checker1 && flightACC.operations[flightACC.step] > 30) {

                	flightACC.numberOfSendingBack++;
            		int[] operations = new int[21];
            		for(int i = 0;i<21;i++) {
            			operations[i] = 30;
            		}
            		tempFlight = new Flight(flightACC.admissionTime, flightACC.flightName, flightACC.acc, flightACC.departingAtc, flightACC.landingAtc, operations);
            		checker1 = false;
                }
               
                if(checker1 == false) {
                	
                	minimumContainer.add(tempFlight);
                }
                	
                if(checker1 == true) {
                	
                	minimumContainer.add(flightACC);
                }
                
                int remainingTime = 10000;
                Flight closestFlight = null; 

                if (!acc.fligthsByAdmissionTime.isEmpty()) {
                    remainingTime = acc.fligthsByAdmissionTime.peek().admissionTime - acc.time;

                }
                if(!minimumContainer.isEmpty()) {
                    closestFlight = minimumContainer.peek(); 
                	timeDiff = closestFlight.operations[closestFlight.step];
                }
                if(timeDiff > remainingTime) {
                	timeDiff = remainingTime;
                	if(checker1 == false)
                		tempFlight.operations[tempFlight.step] -= timeDiff;
                	
                	flightACC.operations[flightACC.step] -= timeDiff;
                	checkerACCWaiting(acc,timeDiff);
                	checkerATCRQ(ATCs_in_ACC, timeDiff);
                	checkerATCWaiting(ATCs_in_ACC, timeDiff);
                	acc.Ready_Queue.add(acc.fligthsByAdmissionTime.poll());
                	
                	time += timeDiff;
                  
                	minimumContainer.clear();

                	
                	continue;	
                }
                if(closestFlight != null && timeDiff == remainingTime && closestFlight.numberOfSendingBack != 0) {
                	if(checker1 == false)
                		tempFlight.operations[tempFlight.step] -= timeDiff;
                	
                	flightACC.operations[flightACC.step] -= timeDiff;
                	checkerACCWaiting(acc,timeDiff);
                   	checkerATCRQ(ATCs_in_ACC, timeDiff);
                   	checkerATCWaiting(ATCs_in_ACC, timeDiff);
                	acc.Ready_Queue.add(acc.fligthsByAdmissionTime.poll());
                   	
                   	time += timeDiff;
                	minimumContainer.clear();
                	continue;
                }
                if(closestFlight != null && timeDiff == remainingTime && closestFlight.flightName.compareTo(acc.fligthsByAdmissionTime.peek().flightName)==1) {
                	if(checker1 == false)
                		tempFlight.operations[tempFlight.step] -= timeDiff;
                	
                	flightACC.operations[flightACC.step] -= timeDiff;
                   	checkerACCWaiting(acc,timeDiff);
                   	checkerATCRQ(ATCs_in_ACC, timeDiff);
                   	checkerATCWaiting(ATCs_in_ACC, timeDiff);
                	acc.Ready_Queue.add(acc.fligthsByAdmissionTime.poll());
                	
                	minimumContainer.clear();
                	

                   	time += timeDiff;
                   
                   	continue;	 	
                }
            	
            	if(acc.Waiting_Queue.peek() != null && closestFlight.flightName == acc.Waiting_Queue.peek().flightName ) {
					checkerACCWaiting(acc, timeDiff);
                	checkerATCRQ(ATCs_in_ACC, timeDiff);
                	checkerATCWaiting(ATCs_in_ACC, timeDiff);
                	if(checker1 == false)
                		tempFlight.operations[tempFlight.step] -= timeDiff;
                	
                	flightACC.operations[flightACC.step] -= timeDiff;
                	acc.Waiting_Queue.remove(closestFlight);
                	acc.Ready_Queue.add(closestFlight);
                	closestFlight.step++;
                	
                	minimumContainer.poll();
                	time += timeDiff;
                	minimumContainer.clear();
                	
                	continue;
                }
            	if(FindingMinAtcWaiting(ATCs_in_ACC) != null && closestFlight == FindingMinAtcWaiting(ATCs_in_ACC)) {
					checkerACCWaiting(acc, timeDiff);
                	checkerATCRQ(ATCs_in_ACC, timeDiff);
                	checkerATCWaiting(ATCs_in_ACC, timeDiff);

                	if(closestFlight.step < 10) {
                		closestFlight.departingAtc.Waiting_Queue.remove(closestFlight);
                		closestFlight.departingAtc.Ready_Queue.add(closestFlight);
                	}
                	if(closestFlight.step > 10) {
                		closestFlight.landingAtc.Waiting_Queue.remove(closestFlight);
                		closestFlight.landingAtc.Ready_Queue.add(closestFlight);
                	}
                	if(checker1 == false)
                		tempFlight.operations[tempFlight.step] -= timeDiff;
                	
                	flightACC.operations[flightACC.step] -= timeDiff;
                	closestFlight.step++;
                	
                	minimumContainer.poll();
                	time += timeDiff;
                	minimumContainer.clear();
                	
                	continue;
                }
            	 if(FindingMinATCRQWaiting(ATCs_in_ACC) != null && closestFlight.flightName == FindingMinATCRQWaiting(ATCs_in_ACC).flightName) {
            		 checkerACCWaiting(acc,timeDiff);
                  	checkerATCRQ(ATCs_in_ACC, timeDiff);
                  	checkerATCWaiting(ATCs_in_ACC, timeDiff);
                 	for(ATC atc : ATCs_in_ACC) {
                 		if(!atc.Ready_Queue.isEmpty())
                 			if(atc.Ready_Queue.peek().flightName == closestFlight.flightName) {
                 				if(closestFlight.step == 9||closestFlight.step == 19) {
                 					if(closestFlight.step == 9)
                 						atc.Ready_Queue.remove(closestFlight);
                 					else
                 						atc.Ready_Queue.remove(closestFlight);
                 					closestFlight.acc.Ready_Queue.add(closestFlight);
                 				}
                 				else {

                 					if(closestFlight.step<10) {
                 						atc.Ready_Queue.remove(closestFlight);
                 						atc.Waiting_Queue.add(closestFlight);
                 					}
                 					else {
                 						atc.Ready_Queue.remove(closestFlight);
                 						atc.Waiting_Queue.add(closestFlight);
                 					}
                 				}
             					closestFlight.step++;                				
                 			}
                 		
                 	}
                 	if(checker1 == false)
                 		tempFlight.operations[tempFlight.step] -= timeDiff;
                 	
                 	flightACC.operations[flightACC.step] -= timeDiff;
                 	
                 	minimumContainer.poll();
                 	time += timeDiff;
                	minimumContainer.clear();
                	
              	 	continue;
                 }
            	
                if(checker1 == true && closestFlight.flightName == flightACC.flightName) {
                	flightACC.numberOfSendingBack = 0;
                	checkerACCWaiting(acc, timeDiff);
                	 checkerATCRQ(ATCs_in_ACC, timeDiff);
                	 checkerATCWaiting(ATCs_in_ACC, timeDiff);
                     if (flightACC.step == 20) {
                         acc.Ready_Queue.poll();
                         acc.finishedFlights++;
                         if (finishedFlights == acc.totalNumberOfFlights) {
                             time += timeDiff;
                             break;
                         }
                     }
                     if(flightACC.step == 0 || flightACC.step == 10) {
                         flightACC.step++;

                    	 flightACC.acc.Ready_Queue.remove(flightACC);
                    	 flightACC.acc.Waiting_Queue.add(flightACC); 
                     }
                     if(flightACC.step == 2) {
                         flightACC.step++;

                    	 flightACC.acc.Ready_Queue.remove(flightACC);
                    	 flightACC.departingAtc.Ready_Queue.add(flightACC);
                     }
                     if(flightACC.step == 12) {
                         flightACC.step++;

                    	 flightACC.acc.Ready_Queue.remove(flightACC);
                    	 flightACC.landingAtc.Ready_Queue.add(flightACC);
                     }
                 	 
                 	 minimumContainer.poll();
                 	 time += timeDiff;
                 	 minimumContainer.clear();
                 	 
               	 	 continue;
                }
                
                if(checker1 == false && closestFlight.flightName == tempFlight.flightName) {
                	checkerACCWaiting(acc, timeDiff);
                	checkerATCRQ(ATCs_in_ACC, timeDiff);
                	checkerATCWaiting(ATCs_in_ACC, timeDiff);
                 	flightACC.operations[flightACC.step] -= timeDiff;

                	flightACC.acc.Ready_Queue.remove(flightACC);
                	flightACC.acc.Ready_Queue.add(flightACC);
                	checker1 = true;
                	
               	 	minimumContainer.poll();
               	 	
               	 	
               	 	time += timeDiff;
                 
                	minimumContainer.clear();
                	
               	 	continue;
                }
                
               
    		}
    		
    	}
    }


}
