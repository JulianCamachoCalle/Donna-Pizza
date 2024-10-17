package com.example.DonnaPizza.DAO;
import com.example.DonnaPizza.Model.sugerencias;
import java.util.ArrayList;

public class sugerenciasDAO {
    ArrayList<sugerencias> listaSug=new ArrayList<>();
    public void registrarSug(sugerencias objAE){
        listaSug.add(objAE);
    }
    public ArrayList<sugerencias> getsugerencias(){
        return listaSug;
    }
    
}
