package com.polsl.edziennik.desktopclient.controller.teacher.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.teacher.TeacherAction;
import com.polsl.edziennik.desktopclient.view.common.HappyHours;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

/**
 * 
 * Klasa odpowiedzialna za wykonanie akcji uruchomienia modu�u przegl�ania
 * godzin dzieka�skich na poziomie prowadz�cego
 * 
 * @author Mateusz Go��b
 * 
 */
public class HappyHoursAction extends TeacherAction {

	private static String title = menu.getString("happyHours");

	public HappyHoursAction(TeacherMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		HappyHours hours = new HappyHours();
		parent.addTab(title + "  ", hours, new ImageIcon(bundle.getString("happyHoursIcon")));
		parent.invalidate();
		parent.validate();

	}
}