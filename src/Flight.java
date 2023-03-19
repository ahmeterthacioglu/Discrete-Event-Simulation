import java.util.Comparator;

public class Flight {
    ATC departingAtc;
    ATC landingAtc;
    ACC acc;
    public String flightName;
    int admissionTime;
    int step = 0;
    int numberOfSendingBack = 0;
    boolean contr = true;
    int controller = 10000;

    int[] operations = new int[21];

    public Flight(int admissionTime, String flightName,ACC acc,ATC departingAtc,ATC landingAtc,int[] operations) {
        this.admissionTime = admissionTime;
        this.flightName = flightName;
        this.acc = acc;
        this.departingAtc = departingAtc;
        this.landingAtc = landingAtc;
        this.operations = operations;
    }

    public static class Comparator implements java.util.Comparator<Flight> {
        public int compare(Flight flight1,Flight flight2){
        	
        	if (flight1.operations[flight1.step] != flight2.operations[flight2.step])
            {return flight1.operations[flight1.step]-flight2.operations[flight2.step];}
            else if (flight1.numberOfSendingBack>flight2.numberOfSendingBack ) {
                return 1;
            } else if (flight1.numberOfSendingBack<flight2.numberOfSendingBack) {
                return -1;
            }
            return (flight1.flightName.compareTo(flight2.flightName));
        }

    }
    public static class Comparator1 implements java.util.Comparator<Flight> {
        public int compare(Flight flight1,Flight flight2){
            if (flight1.operations[flight1.step] != flight2.operations[flight2.step])
            {return flight1.operations[flight1.step]-flight2.operations[flight2.step];}
            return (flight1.flightName.compareTo(flight2.flightName));
        }

    }
    public static class Comparator2 implements java.util.Comparator<Flight> {
        public int compare(Flight flight1,Flight flight2){
            if (flight1.admissionTime != flight2.admissionTime)
            {return flight1.admissionTime-flight2.admissionTime;}
            return (flight1.flightName.compareTo(flight2.flightName));
        }

    }






}
