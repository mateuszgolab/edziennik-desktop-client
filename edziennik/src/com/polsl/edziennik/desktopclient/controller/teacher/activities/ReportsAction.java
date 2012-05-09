package com.polsl.edziennik.desktopclient.controller.teacher.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.teacher.TeacherAction;
import com.polsl.edziennik.desktopclient.view.teacher.ReportsForTeacher;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

/**
 * 
 * Klasa odpowiedzialna za wykonanie akcji uruchomienia modu³u sprawozdañ na
 * poziomie prowadz¹cego
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class ReportsAction extends TeacherAction {

	private static String title = menu.getString("reports");

	public ReportsAction(TeacherMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ReportsForTeacher reports = new ReportsForTeacher();
		parent.addTab(title + "  ", reports, new ImageIcon(bundle.getString("reportIcon")));
		parent.invalidate();
		parent.validate();

	}
}