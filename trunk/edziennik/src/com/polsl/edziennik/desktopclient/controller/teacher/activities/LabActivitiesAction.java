package com.polsl.edziennik.desktopclient.controller.teacher.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.teacher.TeacherAction;
import com.polsl.edziennik.desktopclient.view.teacher.LabActivities;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

/**
 * 
 * Klasa odpowiedzialna za wykonanie akcji uruchomienia modu³u laboratoriów na
 * poziomie prowadz¹cego
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class LabActivitiesAction extends TeacherAction {

	private static String title = menu.getString("labActivities");

	public LabActivitiesAction(TeacherMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO currentTeacher
		LabActivities labActivities = new LabActivities(7);
		parent.addTab(title + "  ", labActivities, new ImageIcon(bundle.getString("labActivitiesIcon")));
		parent.invalidate();
		parent.validate();

	}
}