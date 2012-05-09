package com.polsl.edziennik.desktopclient.controller.student.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.student.StudentAction;
import com.polsl.edziennik.desktopclient.view.student.ProjectActivitiesBrowse;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u spotkañ
 * projektowych na poziomie studenta
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class ProjectActivitiesBrowseAction extends StudentAction {

	private static String title = menu.getString("projectActivities");

	public ProjectActivitiesBrowseAction(StudentMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ProjectActivitiesBrowse activities = new ProjectActivitiesBrowse();
		parent.addTab(title + "  ", activities, new ImageIcon(bundle.getString("projectIcon")));
		parent.invalidate();
		parent.validate();

	}
}
