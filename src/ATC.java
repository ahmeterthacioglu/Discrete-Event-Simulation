import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ATC {
	public String AtcName;
    public ACC acc;
    Queue<Flight> Ready_Queue = new LinkedList<>();
    PriorityQueue<Flight> Waiting_Queue = new PriorityQueue<>(new Flight.Comparator1());//(flight1,flight2) -> Integer.compare(flight1.operations[flight1.step],flight2.operations[flight2.step]));
    public ATC(String atcName){
        this.AtcName = atcName;
    }

}
