package RoundRobin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

public class Clock 
{
	private int time;
	public Clock()
	{
		time = 0;
	}
	
	public void updateTimeSinceStart(int cpuTime)
	{
		time = time + cpuTime;
	}
	    
	public int getTimeSinceStart()
	{
		return time;
	}
	
	public void updateArrivalTimeToReadyQueue(ArrayList<Process> processList, int cpuRunTime)
	{
		for(int i=0; i<processList.size(); i++)
		{
			Process process = processList.get(i);
	        process.updateArrival(cpuRunTime);
	    }
	}
	
	public void updateWaitReadyQueue(Queue<Process> q, int cpuRunTime, Process currentP)
	{
		Iterator<Process> it = q.iterator();
		while(it.hasNext())
	    {
			Process process = it.next();
			if(process != currentP)
			{
				process.setWaitTime(cpuRunTime);
			}
	    }
	}
}