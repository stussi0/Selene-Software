package servselene.tools;

import java.util.logging.Level;
import java.util.logging.Logger;

import servselene.TesteRedesNeuraisPlanta;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils;

/**
 * Esta classe está relacionada ao treinador de redes neurais
 * @author Luis Alberto Schwind Pedroso Stussi da Silva Pereira
 */
public class TreinadorRedesNeurais {
    public static void main(String arg[]){
        try {
            Instances db = null;
            db = ConverterUtils.DataSource.read("selene.arff");
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
                SerializationHelper.write("selene.model", cd);
            } catch (Exception ex) {
                Logger.getLogger(TesteRedesNeuraisPlanta.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(TreinadorRedesNeurais.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
