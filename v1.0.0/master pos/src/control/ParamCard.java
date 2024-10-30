/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author usER
 */
public class ParamCard {

    public ParamCard(List<FieldsCard> fields, InputStream qrcode, InputStream image1, InputStream image2) {
        this.fields = fields;
        this.qrcode = qrcode;
        this.image1 = image1;
        this.image2 = image2;
    }

    public ParamCard() {
    }
    
    private List<FieldsCard> fields;
    private InputStream qrcode;
    private InputStream image1;
    private InputStream image2;

    public List<FieldsCard> getFields() {
        return fields;
    }

    public InputStream getQrcode() {
        return this.qrcode;
    }
    
    public InputStream getImage1() {
        return image1;
    }
    
    public InputStream getImage2() {
        return image2;
    }

}
