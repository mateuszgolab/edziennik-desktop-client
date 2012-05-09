package com.polsl.edziennik.desktopclient.controller.teacher.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.teacher.TeacherAction;
import com.polsl.edziennik.desktopclient.view.teacher.ProjectActivities;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;
import com.polsl.edziennik.modelDTO.project.ProjectDTO;

/**
 * 
 * Klasa odpowiedzialna za wykonanie akcji uruchomienia modu³u spotkañ
 * projektowych na poziomie prowadz¹cego
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class ProjectActivityAction extends TeacherAction {

	private static String title = menu.getString("projectActivities");
	private ProjectDTO project;

	public ProjectActivityAction(TeacherMainView parent, ProjectDTO project) {
		super(title);
		this.parent = parent;
		this.project = project;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ProjectActivities projects = new ProjectActivities(project);
		parent.addTab(title + "  ", projects, new ImageIcon(bundle.getString("projectIcon")));
		parent.invalidate();
		parent.validate();

	}

	public void setProjectId(ProjectDTO project) {
		this.project = project;
	}
}