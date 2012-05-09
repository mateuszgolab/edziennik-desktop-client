package com.polsl.edziennik.desktopclient;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.EdziennikDesktop;

public class Start {

	public Start() {
		EdziennikDesktop edziennik = EdziennikDesktop.getInstance();
		LangManager.getResource(Properties.Component);

		edziennik.createAdmin();
		// edziennik.createTeacher();
		// edziennik.createStudent();
	}

	public static void main(String[] args) throws Exception {
		// Set look and feel
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {

//				try {
//
//					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//				} catch (Exception e) {
//
//				}
				
				try {
				    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
			} catch (Exception e) {
			    // If Nimbus is not available, you can set the GUI to another look and feel.
			}
		}});

		new Start();

	}

}
