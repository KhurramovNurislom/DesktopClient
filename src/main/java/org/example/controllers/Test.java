package org.example.controllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Test implements Initializable {

    public Pane id_pn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


operPDF("D:\\Tekshirishga\\testDesktop\\test.pdf");



    }

    private void operPDF(String file) {
    try{
        SwingController control = new SwingController();
        SwingViewBuilder factry = new SwingViewBuilder(control);
        JPanel veiwer = factry.buildViewerPanel();
        ComponentKeyBinding.install(control, veiwer);
        control.getDocumentViewController().setAnnotationCallback(
                new org.icepdf.ri.common.MyAnnotationCallback(
                        control.getDocumentViewController()
                )
        );

        control.openDocument(file);


//id_pn.setClip(veiwer);


    }catch(Exception ex){

    }

    }


}
