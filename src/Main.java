import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        PrintStream fileOut = null;
        try {
            fileOut = new PrintStream(args[1]);
            //fileOut = new PrintStream("C:\\Users\\ahmet\\eclipse-workspace\\Project3\\case8.out.txt");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        System.setOut(fileOut);

        File file = new File(args[0]);
        //File file = new File("C:\\Users\\ahmet\\eclipse-workspace\\Project3\\cases\\inputs\\case8.in");
        //File file = new File("C:\\Users\\ahmet\\Downloads\\no_zero_time_input.txt");
        ArrayList<String> input = new ArrayList<>();
        Scanner data = new Scanner(file);
        while (data.hasNextLine()) {
            input.add(data.nextLine());
        }
        int A = Integer.parseInt(input.get(0).split(" ")[0]);
        int F = Integer.parseInt(input.get(0).split(" ")[1]);

        //ArrayList<ACC> accs = new ArrayList<>();
        HashMap<String, ACC> ACCs = new HashMap<>();
        HashMap<String,ATC> ATCs = new HashMap<>();

        for (int ACC_index = 1; ACC_index < A + 1; ACC_index++) {
            String[] info = input.get(ACC_index).split(" ");
            String accName = info[0];
            ACC acc = new ACC(accName);
            ACCs.put(accName, acc);
            for (int ATC_index = 1; ATC_index < info.length; ATC_index++) {
                ATC atc = new ATC(info[ATC_index]);
                atc.acc = acc;
                acc.addATC(atc);
                ATCs.put(atc.AtcName, atc);
            }
        }
        for(int index = A+1;index<A+F+1;index++) {
            String[] info = input.get(index).split(" ");
            int admissionTime = Integer.parseInt(info[0]);
            String flightCode = info[1];
            String accName = info[2];
            String departingAtcCode = info[3];
            String landingAtcCode = info[4];
            int[] operations = new int[21];
            for (int i = 5; i < 26; i++) {
                operations[i - 5] = Integer.parseInt(info[i]);
            }
            ACC acc = ACCs.get(accName);
            acc.totalNumberOfFlights++;
           
            Flight newFlight = new Flight(admissionTime, flightCode, acc, ATCs.get(departingAtcCode), ATCs.get(landingAtcCode), operations);

           
            acc.fligthsByAdmissionTime.add(newFlight);




        }

        for(String accName : ACCs.keySet()){
            //ACCs.get(accName).changingReadyQueue();
           
           	ACCs.get(accName).process(ACCs.get(accName));

            System.out.println(ACCs.get(accName).accName+" "+ACCs.get(accName).time);
        }
        //newFlight.acc.process1(acc);
        //System.out.println(acc.time);
    }
}