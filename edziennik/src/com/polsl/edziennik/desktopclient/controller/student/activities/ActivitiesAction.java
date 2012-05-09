package com.polsl.edziennik.desktopclient.controller.student.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.student.StudentAction;
import com.polsl.edziennik.desktopclient.view.student.StudentActivities;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu�u �wicze�
 * tablicowych na poziomie studenta
 * 
 * @author Mateusz Go��b
 * 
 */
public class ActivitiesAction extends StudentAction {

	private static String title = menu.getString("activities");

	public ActivitiesAction(StudentMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StudentActivities activities = new StudentActivities();
		parent.addTab(title + "  ", activities, new ImageIcon(bundle.getString("ActivitiesIcon")));
		parent.invalidate();
		parent.validate();

	}
}