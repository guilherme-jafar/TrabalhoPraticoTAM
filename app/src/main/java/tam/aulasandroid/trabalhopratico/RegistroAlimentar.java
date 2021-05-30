package tam.aulasandroid.trabalhopratico;

import java.io.Serializable;
import java.util.Date;

public class RegistroAlimentar implements Serializable  {


    private String id;
    private String refId;
    private boolean estado;
    private Date hora;
    private String obs;

    public RegistroAlimentar(String id, String refId, boolean estado, Date hora, String obs) {
        this.id = id;
        this.refId = refId;
        this.estado = estado;
        this.hora = hora;
        this.obs = obs;
    }
}
