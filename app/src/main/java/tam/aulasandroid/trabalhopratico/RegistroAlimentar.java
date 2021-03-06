package tam.aulasandroid.trabalhopratico;

import java.io.Serializable;
import java.util.Date;

public class RegistroAlimentar implements Serializable  {


    private String id;
    private String refId;
    private boolean estado;
    private Date data;
    private Date hora;
    private String obs;
    private  String nomeRefeicao;
    private String horaRefeicao;

    public RegistroAlimentar(String id, String refId, boolean estado, Date hora, String obs) {
        this.id = id;
        this.refId = refId;
        this.estado = estado;
        this.hora = hora;
        this.obs = obs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getNomeRefeicao() {
        return nomeRefeicao;
    }

    public void setNomeRefeicao(String nomeRefeicao) {
        this.nomeRefeicao = nomeRefeicao;
    }

    public String getHoraRefeicao() {
        return horaRefeicao;
    }

    public void setHoraRefeicao(String horaRefeicao) {
        this.horaRefeicao = horaRefeicao;
    }

    @Override
    public String toString() {
        return "RegistroAlimentar{" +
                "id='" + id + '\'' +
                ", refId='" + refId + '\'' +
                ", estado=" + estado +
                ", data=" + data +
                ", hora=" + hora +
                ", obs='" + obs + '\'' +
                ", nomeRefeicao='" + nomeRefeicao + '\'' +
                ", horaRefeicao='" + horaRefeicao + '\'' +
                '}';
    }
}
