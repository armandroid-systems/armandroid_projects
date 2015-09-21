package mx.com.armandroid.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {

    public static void main(String[] args) throws Exception{
        Schema schema = new Schema(2,"recibos");

        addTabla(schema);

        new DaoGenerator().generateAll(schema,"../app/src/main/java-gen");

    }

    public static void addTabla(Schema schema){
        Entity reporte = schema.addEntity("Tareas");
        reporte.addIdProperty();
        reporte.addStringProperty("snr");
        reporte.addStringProperty("latitud");
        reporte.addStringProperty("longitud");
        reporte.addStringProperty("fechahora");
        reporte.addStringProperty("bcRecibo");
        reporte.addStringProperty("cuenta");
        reporte.addStringProperty("servicio");
        reporte.addStringProperty("atendido");

    }
}
