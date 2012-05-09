package com.polsl.edziennik.desktopclient.controller.teacher.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.teacher.TeacherAction;
import com.polsl.edziennik.desktopclient.view.teacher.Activities;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

/**
 * 
 * Klasa odpowiedzialna za wykonanie akcji uruchomienia modu³u æwiczeñ
 * tablicowych na poziomie prowadz¹cego
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class ActivitiesAction extends TeacherAction {

	private static String title = menu.getString("activities");

	public ActivitiesAction(TeacherMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Activities activities = new Activities(7);
		parent.addTab(title + "  ", activities, new ImageIcon(bundle.getString("exerciseIcon")));
		parent.invalidate();
		parent.validate();

	}
}