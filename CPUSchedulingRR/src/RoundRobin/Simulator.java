package RoundRobin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Simulator
{
	private ArrayList<Process> processArrayList;
    private int timeQuantum;
    private CPU cpu;
    private Queue<Process> readyQueue;
    private Clock clock;
    private ArrayList<Process> finishedProcesses;
    private int totalNumberOfProcesses;
    private int runTimeOnCPU;
    
	public Simulator(ArrayList<Process> process, int quantum)
    {
        processArrayList = process;
        timeQuantum = quantum;
        cpu = new CPU(timeQuantum);
        readyQueue = new LinkedList<Process>();
        clock = new Clock();
        finishedProcesses = new ArrayList<Process>();
        totalNumberOfProcesses = process.size();
        
        for(int i=0; i<process.size(); i++)
        {
            process.get(i).setArrival();
            process.get(i).setInitialBurst();
        }
    }

    public void startSimulation()
    {
    		ProcessCreation(null);
        RoundRobin();
    }
    
    public void ProcessCreation(Process currentProcess)
    {
        for(int i=0; i<processArrayList.size(); i++)
        {
            Process process = processArrayList.get(i);
            if(process.checkReadyExecute() == true && 
            		process.checkCompletetion() == 0 && (process != currentProcess) && 
            		!readyQueue.contains(process))
            {
                readyQueue.add(process);
                System.out.println("NEW Process " + process.getName() + " added to ready queue");
            }
        }
    }

    public void RoundRobin()
    {
        boolean done = false;
        while(!done)
        {
        		Process currentProcess = readyQueue.peek();
        		if(currentProcess != null)
        		{
        			runTimeOnCPU = cpu.run(currentProcess);
        			clock.updateTimeSinceStart(runTimeOnCPU);
        			clock.updateArrivalTimeToReadyQueue(processArrayList, runTimeOnCPU);
        			clock.updateWaitReadyQueue(readyQueue,runTimeOnCPU, currentProcess);
        			ProcessCreation(currentProcess);
        			
        			if(currentProcess.checkCompletetion() == 1)
        			{
        				System.out.println("Process " + currentProcess.getName() + 
        						" completed execution");
        				processArrayList.remove(currentProcess);
        				System.out.println("Process " + currentProcess.getName() + " removed from ready queue");
        				finishedProcesses.add(readyQueue.remove());
        				if(finishedProcesses.size() == totalNumberOfProcesses)
        				{
        					this.EndingSimulation();
        					done = true;
        				}
        			} else {
        				currentProcess.updateContextSwitch();
        				currentProcess.getProcessInfo();
        				readyQueue.remove();
        				readyQueue.add(currentProcess);
        				ProcessCreation(currentProcess);
        			}
        		} else {
        			clock.updateTimeSinceStart(timeQuantum);
        			clock.updateArrivalTimeToReadyQueue(processArrayList,timeQuantum);
        			ProcessCreation(null);
        		}
        }
    }
    
    public void EndingSimulation()
    {
        int totalWaitTime = 0;
        int totalTurnAroundTime = 0;
        int totalContextSwitches = 0;
        
        for(int i=0; i<finishedProcesses.size(); i++)
        {
        		finishedProcesses.get(i).setTurnAround();
        		totalTurnAroundTime = totalTurnAroundTime + finishedProcesses.get(i).getTurnAround();
        		totalWaitTime = totalWaitTime + finishedProcesses.get(i).getTotalWaitTime();
        		totalContextSwitches = totalContextSwitches + 
            		finishedProcesses.get(i).getContextSwitch();
        }
        
        double averageWaitTime = totalWaitTime / totalNumberOfProcesses;
        double averageTurnAroundTime = totalTurnAroundTime / totalNumberOfProcesses;
        double throughput = ((double)cpu.getNumberCompleted() / clock.getTimeSinceStart()) * 100.0;
        double runningTimeMinusContextSwitch = clock.getTimeSinceStart() - totalContextSwitches;
        double utilization= (runningTimeMinusContextSwitch / clock.getTimeSinceStart()) * 100.0;
        System.out.println();
        System.out.println("Accounting Information****************");
        System.out.println("Average wait time: " + averageWaitTime);
        System.out.println("Average turnaround time: " + averageTurnAroundTime);
        System.out.println("Throughput: " + throughput + "%");
        System.out.println("CPU utilization: " + utilization + "%"); 
        Main.finish();
    }
}