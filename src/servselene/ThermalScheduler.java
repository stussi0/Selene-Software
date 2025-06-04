package servselene;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
  Ferramenta que executa um segmento de código repetidas vezes
  em intervalos de tempo definidos.
*/
public class ThermalScheduler extends TimerTask {

	public ThermalScheduler(){
		Process pr;
        	Runtime rt;
        	BufferedReader br;
        	String line;
	}
//    System.out.println("Acquiring Load:");
//    try {
//      rt = Runtime.getRuntime();
      //pr = rt.exec("vcgencmd measure_temp");
//      pr = rt.exec("cat /sys/class/thermal/thermal_zone0/temp");
      //pr = rt.exec("sh script.sh");
      //pr = rt.exec("java ServerCalc");
//      br = new BufferedReader( new InputStreamReader(pr.getInputStream()) );
//      while((line = br.readLine()) != null) System.out.println(line);
//      System.out.println("No Problems!");
//    } catch (IOException e) {
//      System.out.println("Excessão!");
//    }

	@Override
	public void run() {
		int numb = (int)(Math.random()*10);
		switch(numb){
			case 0: System.out.println("Formating  :: " + new Date() + " :: " + Thread.currentThread().getName()); break;
                        case 1: System.out.println("Compiling /dev/ ... "); break;
                        case 2: System.out.println("Cleaning ... "); break;
                        case 3: System.out.println("Rebuilding /dev/ ... "); break;
                        case 4: System.out.println("Restarting ... "); break;
			case 5: System.out.println("Listing endpoints ... "); break;
			case 6: System.out.println("Returning status [OK,200] "); break;
			case 7: System.out.println("Refactorying in "+((int)(Math.random()*3500))+"ms"); break;
			case 8: System.out.println("Feedback test ITL (WAIT) "); break;
			case 9: thermalRead(); break;
			default: System.out.println("Fail status(0) ");
		}
	}

	public static void main(String[] args) {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nStating Test (Payload OFF)");
		Timer timer = new Timer(); // Instantiating a timer object

		ThermalScheduler task1 = new ThermalScheduler(); // Creating a FixedRateSchedulingUsingTimerTask
		timer.scheduleAtFixedRate(task1, 17 * 1000, 17 * 1000); // Scheduling it to be executed with fixed rate at every two seconds

		ThermalScheduler task2 = new ThermalScheduler(); // Creating another FixedRateSchedulingUsingTimerTask
		timer.schedule(task2, 23 * 1000, 23 * 1000); // Scheduling it to be executed with fixed delay at every two seconds

	}
  public static void thermalRead() {
    Process pr= null;
    Runtime rt = null;
    BufferedReader br = null;
    String line;
    System.out.print("Acquiring Load: ");
    try {
      rt = Runtime.getRuntime();
      //pr = rt.exec("vcgencmd measure_temp");
      pr = rt.exec("cat /sys/class/thermal/thermal_zone0/temp");
      //pr = rt.exec("sh script.sh");
      //pr = rt.exec("java ServerCalc");
      br = new BufferedReader( new InputStreamReader(pr.getInputStream()) );
      while((line = br.readLine()) != null) System.out.print(line);
      System.out.println(" --> No Problems!");
    } catch (IOException e) {
      System.out.println("Excessão!");
    }
  }
}
