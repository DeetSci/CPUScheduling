package RoundRobin;

public class CPU 
{
	private int timeQuantum;
    private int numberCompleted;
    
	public CPU(int quantum)
    {
       timeQuantum = quantum; 
       numberCompleted = 0;
    }
    
	public int run(Process p)
    {
        if(p.getRemainingBurstTime()>timeQuantum)
        {
            p.updateRemainingBurstTime();
            return timeQuantum;
        }
        else if(p.getRemainingBurstTime()<timeQuantum)
        {
            int run = p.getRemainingBurstTime();
            p.updateRemainingBurstTime();
            numberCompleted++;
            return run;
        } else {
            p.updateRemainingBurstTime();
            numberCompleted++;
            return timeQuantum;
        }
    }
    
	public int getNumberCompleted()
    {
        return numberCompleted;
    }
}