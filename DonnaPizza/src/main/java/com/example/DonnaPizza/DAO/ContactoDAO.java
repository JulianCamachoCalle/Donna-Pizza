package com.example.DonnaPizza.DAO;

import com.example.DonnaPizza.Model.ContactoDp;
import java.util.ArrayList;


public class ContactoDAO {
    ArrayList<ContactoDp> listaCont=new ArrayList<>();
    public void registrarCont(ContactoDp objAE){
        listaCont.add(objAE);
    }
    public ArrayList<ContactoDp> getcontacto(){
        return listaCont;
    }
}
