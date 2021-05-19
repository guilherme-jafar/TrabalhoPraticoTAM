package tam.aulasandroid.trabalhopratico;

import android.widget.Toast;

import java.io.Serializable;
import java.util.Date;

public class Refeicao implements Serializable, Comparable<Refeicao>{

    private String id;
    private Date hora;
    private String refeicao;
    private String informacao;

    public Refeicao(String id,Date hora, String refeicao, String informacao){
        this.id=id;
        this.hora = hora;
        this.refeicao = refeicao;
        this.informacao = informacao;
    }

    public Refeicao(){
        this("",null, "", "");
    }

    public Date getHora() {
        return hora;
    }

    public String getInformacao() {
        return informacao;
    }

    public String getRefeicao() {
        return refeicao;
    }

//    public boolean setHora(Date hora) {
//
//        if (hora.equalsIgnoreCase("HH:MM")){
//            return false;
//        }else{
//            this.hora = hora;
//            return true;
//        }
//    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public void setHora(Date hora) {

        this.hora = hora;
    }

    public void setInformacao(String informacao) {



        this.informacao = informacao;

    }

    public boolean setRefeicao(String refeicao) {

        if (refeicao.equalsIgnoreCase("")){
            return false;
        }else{
            this.refeicao = refeicao;
            return true;
        }
    }

    public int compareTo(Refeicao o){
        return getHora().compareTo(o.getHora());
    }

    @Override
    public String toString() {
        return "Refeicao{" +
                "hora='" + hora + '\'' +
                ", refeicao='" + refeicao + '\'' +
                ", informacao='" + informacao + '\'' +
                '}';
    }
}
