package servselene.tarefas;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import servselene.FirmwareSelene;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
  Ferramenta que executa um segmento de código repetidas vezes
  em intervalos de tempo definidos.
*/
public class VermelhaScheduler extends TimerTask {

	FirmwareSelene firmware;

	public VermelhaScheduler(FirmwareSelene firmware){
		Process pr;
        Runtime rt;
        BufferedReader br;
        String line;
		this.firmware = firmware;
	}

	@Override
	public void run() {
		Instances db = null;
		if (firmware.isEstado()) try {
			db = ConverterUtils.DataSource.read("selene.arff");
			db.setClassIndex(db.numAttributes()-1);
			MultilayerPerceptron cd = (MultilayerPerceptron) SerializationHelper.read("selene.model");
			double[] infoAtual = new double[5];
			infoAtual[0] = (double)firmware.getDistancia(); // Sensor 1
			infoAtual[1] = (double)firmware.getAngulo(); // Sensor 2
			infoAtual[2] = (double)firmware.getIntensidade(); // Sensor 3
			infoAtual[3] = (double)firmware.getForma(); // Sensor 4 ...
			firmware.used();
			Instance classificacao = db.firstInstance().copy(infoAtual);
			System.out.println("Resultado: "+db.attribute(4).value((int)cd.classifyInstance(classificacao)));
		} catch (Exception e) {
			System.err.println("Os arquivos selene.arff ou selene.model estão faltando!");
		}
		
	}

	public static void main(String[] args) {
		System.out.println("\nInicio da procura por pedras vermelhas...\n");
		Timer timer = new Timer(); // Instantiating a timer object
		FirmwareSelene firmware = new FirmwareSelene(null);

		VermelhaScheduler task2 = new VermelhaScheduler(firmware); // Creating another FixedRateSchedulingUsingTimerTask
		timer.schedule(task2, 1 * 1000, 1 * 1000); // Scheduling it to be executed with fixed delay at every two seconds
		//timer.cancel();
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
