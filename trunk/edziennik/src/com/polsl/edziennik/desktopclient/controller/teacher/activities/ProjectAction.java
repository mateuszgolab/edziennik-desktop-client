package com.polsl.edziennik.desktopclient.controller.teacher.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.teacher.TeacherAction;
import com.polsl.edziennik.desktopclient.view.admin.ProjectManagement;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

/**
 * 
 * Klasa odpowiedzialna za wykonanie akcji uruchomienia modu³u projektów na
 * poziomie prowadz¹cego
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class ProjectAction extends TeacherAction {

	private static String title = menu.getString("projects");

	public ProjectAction(TeacherMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO current teacher
		ProjectManagement projects = new ProjectManagement(7);
		parent.addTab(title + "  ", projects, new ImageIcon(bundle.getString("projectIcon")));
		parent.invalidate();
		parent.validate();

	}

}