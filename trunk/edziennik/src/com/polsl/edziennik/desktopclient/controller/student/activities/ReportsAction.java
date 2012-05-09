package com.polsl.edziennik.desktopclient.controller.student.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.student.StudentAction;
import com.polsl.edziennik.desktopclient.view.student.ReportsForStudent;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u sprawozdañ na
 * poziomie studenta
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class ReportsAction extends StudentAction {

	private static String title = menu.getString("reports");

	public ReportsAction(StudentMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ReportsForStudent reports = new ReportsForStudent();
		parent.addTab(title + "  ", reports, new ImageIcon(bundle.getString("reportIcon")));
		parent.invalidate();
		parent.validate();

	}
}