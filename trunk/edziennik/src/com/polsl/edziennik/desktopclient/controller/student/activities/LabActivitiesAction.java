package com.polsl.edziennik.desktopclient.controller.student.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.student.StudentAction;
import com.polsl.edziennik.desktopclient.view.student.StudentLabActivities;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u laboratoriów na
 * poziomie studenta
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class LabActivitiesAction extends StudentAction {

	private static String title = menu.getString("labActivities");

	public LabActivitiesAction(StudentMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO currentTeacher
		StudentLabActivities labActivities = new StudentLabActivities();
		parent.addTab(title + "  ", labActivities, new ImageIcon(bundle.getString("labActivitiesIcon")));
		parent.invalidate();
		parent.validate();

	}
}