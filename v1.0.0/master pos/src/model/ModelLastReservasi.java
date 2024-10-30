/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import util.ModelProfile;
import javax.swing.Icon;
import swing.StatusType;

/**
 *
 * @author usER
 */
public class ModelLastReservasi {

    public ModelLastReservasi(String noReservasi,Icon icon, String name, String tgl, StatusType type) {
        this.noReservasi = noReservasi;
        this.icon = icon;
        this.name = name;
        this.tgl = tgl;
        this.type = type;
    }
    
    private Icon icon;
    private String name;
    private String noReservasi;
    private String tgl;
    private StatusType type;


    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getNoReservasi() {
        return this.noReservasi;
    }
    
    public void setNoReservasi(String noReservasi) {
        this.noReservasi = noReservasi;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }
    
    public StatusType getType() {
        return type;
    }

    public void setType(StatusType type) {
        this.type = type;
    }
    
    public Object[] toRowTable() {
        return new Object[]{noReservasi,new ModelProfile(icon, name), tgl, type};
    }
}
