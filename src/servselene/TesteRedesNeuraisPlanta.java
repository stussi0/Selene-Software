package servselene;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSink;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * Classe de teste de Redes Neurais
 * @author Luis Alberto Schwind Pedroso Stussi da Silva Pereira
 */
public class TesteRedesNeuraisPlanta {
    public static void main(String arg[]){
        Instances db = null;
        boolean criar = false;
        if(criar){
            ArrayList<Attribute> att = new ArrayList<>();
            att.add(new Attribute("peso"));
            att.add(new Attribute("altura"));
            ArrayList<String> sexo = new ArrayList<>();
            sexo.add("masculino");
            sexo.add("feminino");
            att.add(new Attribute("sexo",sexo));
            ArrayList<String> saude = new ArrayList<>();
            saude.add("desnutrido");
            saude.add("normal");
            saude.add("sobrepeso");
            saude.add("obeso");
            att.add(new Attribute("saude",saude));
            db = new Instances("IMC",att,0);

            for(int i = 0; i < 12; i++){
                double[] dd = new double[4];
                dd[0] = Math.random()*0.6+1.3;
                dd[1] = Math.random()*80.0+25.0;
                if(Math.random()>0.5){
                    dd[2] = db.attribute(2).indexOfValue("masculino");
                } else {
                    dd[2] = db.attribute(2).indexOfValue("feminino");
                }
                int op = Integer.valueOf(JOptionPane.showInputDialog("A pessoa "+i+" do sexo "+db.attribute(2).value((int)dd[2])+" que tem "+dd[0]+"m, "+dd[1]+"kg é considerado:\n1-Desnutrido\n2-Normal\n3-Sobrepeso\n4-Obeso"))-1;
                switch(op){
                    case 0: dd[3] = db.attribute(3).indexOfValue("desnutrido"); break;
                    case 1: dd[3] = db.attribute(3).indexOfValue("normal"); break;
                    case 2: dd[3] = db.attribute(3).indexOfValue("sobrepeso"); break;
                    case 3: dd[3] = db.attribute(3).indexOfValue("obeso"); break;
                }
                db.add(new DenseInstance(1.0,dd));
            }

            try {
                DataSink.write("plantas.arff", db);
            } catch (Exception ex) {
                Logger.getLogger(TesteRedesNeuraisPlanta.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                db = DataSource.read("plantas.arff"); // Trocar o nome da relação
            } catch (Exception ex) {
                Logger.getLogger(TesteRedesNeuraisPlanta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        db.setClassIndex(db.numAttributes()-1);
        
        MultilayerPerceptron cd = new MultilayerPerceptron();
        String[] opc = new String[14];
        opc[0] = "-L";
        opc[1] = "0.3";
        opc[2] = "-M";
        opc[3] = "0.2";
        opc[4] = "-N";
        opc[5] = "500";
        opc[6] = "-V";
        opc[7] = "0";
        opc[8] = "-S";
        opc[9] = "0";
        opc[10] = "-E";
        opc[11] = "20";
        opc[12] = "-H";
        opc[13] = "a";
        try {
            cd.setOptions(opc);
        } catch (Exception ex) {
            Logger.getLogger(TesteRedesNeuraisPlanta.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            cd.buildClassifier(db);
            while(Integer.valueOf(JOptionPane.showInputDialog("0-sair\n1-classificar"))==1){
                double[] dd2 = new double[4];
                dd2[0] = Double.valueOf(JOptionPane.showInputDialog("Qual o comprimento?"));
                dd2[1] = Double.valueOf(JOptionPane.showInputDialog("Qual a largura?"));
                dd2[2] = Double.valueOf(JOptionPane.showInputDialog("Qual a relação largura/comprimento?"));
                Instance teste = db.firstInstance().copy(dd2);
                JOptionPane.showConfirmDialog(null, "A folha de comprimento "+db.attribute(2).value((int)dd2[2])+" que tem "+dd2[0]+"m, "+dd2[1]+"kg é considerado:");
                System.out.println("A pessoa do sexo "+db.attribute(2).value((int)dd2[2])+" que tem "+dd2[0]+"m, "+dd2[1]+"kg é considerado: "+db.attribute(3).value((int)cd.classifyInstance(teste)));
            }
        } catch (Exception ex) {
            Logger.getLogger(TesteRedesNeuraisPlanta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
