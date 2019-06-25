package RoundRobin;

public class Process
{
    private String Name;
    private int arrivalTime;
    private int initialArrival;
	private int burstTime;
    private int initialburstTime;
	private int waitTime;
    private int turnAroundTime;
    private int contextSwitch;
	private int timeQuantum;

	public Process(String name, int arrival, int burst, int quantum)
    {
        Name = name;
        arrivalTime = arrival;
        burstTime = burst; 
        timeQuantum = quantum;
        waitTime = 0;
        turnAroundTime = 0;
        initialArrival = 0;
        initialburstTime = 0;
        contextSwitch = 0;
    }
    
	public String getName()
    {
        return Name;
    }
    
	public void setArrival()
    {
         initialArrival = arrivalTime;
    }
    
	public int getInitialArrival()
    {
        return initialArrival;
    }
    
	public void updateArrival(int cpuRunTime)
	{
	    arrivalTime = arrivalTime - cpuRunTime;
	    if(arrivalTime<0)
	    {
	        arrivalTime = 0;
	    }
	}

	public int getArrivalTime()
    {
        return arrivalTime;
    }
    
	public void setInitialBurst()
    {
        initialburstTime=burstTime;
    }
    
	public int getinitialBurstTime()
    {
        return initialburstTime;
    }
    
	public void setWaitTime(int cpuRunTime)
	{
	        waitTime = waitTime +cpuRunTime;
	}

	public int getTotalWaitTime()
	{
	    return waitTime;
	}

	public void updateRemainingBurstTime()
    {
        burstTime = burstTime - timeQuantum;
        if(burstTime < 0)
        {
            burstTime = 0;
        }
    }
    
	public int getRemainingBurstTime()
    {
        return burstTime;
    }
    
	public void updateContextSwitch()
    {
        contextSwitch++;
    }
    
	public int getContextSwitch()
	{
	    return contextSwitch;
	}

	public void setTurnAround()
	{
	    turnAroundTime = this.getTotalWaitTime() + this.getinitialBurstTime();
	}

	public int getTurnAround()
    {
        return turnAroundTime;
    }
    
	public int checkCompletetion()
	{
        if(getRemainingBurstTime()==0)
        {
            return 1;
        }
        else return 0;
    }
    
	public boolean checkReadyExecute()
    {
        if (getArrivalTime() == 0)
        {
        	return true;
        }
        else return false;
    }

	public void getProcessInfo()
	{
	    System.out.println("*****************************************");
	    System.out.println("Process Name:" + Name);
	    System.out.println("Process ready to enter ready queue:" + checkReadyExecute());
	    System.out.println("Remaining burst time:" + getRemainingBurstTime());
	    System.out.println("Number of times preempted: " + contextSwitch);
	    System.out.println("Process total wait time:" + getTotalWaitTime());
	    System.out.println("Process total turnaound time:" + getTurnAround());
	    System.out.println("*****************************************");
	    System.out.println();
	}
}
