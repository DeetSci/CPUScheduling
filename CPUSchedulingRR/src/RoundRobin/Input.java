package RoundRobin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Input
{
	public static void start()
	{
        Scanner in = new Scanner(System.in);
        System.out.println("Enter time quantum for processes: ");
        int timeQuantum = in.nextInt();
        
        System.out.println("Enter config file name: ");
        ArrayList<Process> process = new ArrayList<Process>();
        try 
        {
        		String filename = in.next();
        		File inputFile = new File(filename);
        		Scanner fileIn = new Scanner(inputFile);
        		while(fileIn.hasNextLine())
        		{
        			String inputLine = fileIn.nextLine();
        			int i = 0;
        			
        			while (!Character.isDigit(inputLine.charAt(i)))
        			{ 
        				i++;
        			}
        			String pName = inputLine.substring(0,i);
        			String pArrival = inputLine.substring(i,i+2).trim();
        			String pBurst = inputLine.substring(i+2).trim();
        			int pArrivalTime = Integer.parseInt(pArrival);
        			int pBurstTime = Integer.parseInt(pBurst);   
        			process.add(new Process(pName, pArrivalTime, pBurstTime, timeQuantum));
        		}
        	in.close();	
        	fileIn.close();
        }
        
        catch(FileNotFoundException E)
        {
            System.out.println("Restarting Program.");
            Input.start();
        }
        
        Simulator simulation = new Simulator(process, timeQuantum);
        simulation.startSimulation();
	}
}