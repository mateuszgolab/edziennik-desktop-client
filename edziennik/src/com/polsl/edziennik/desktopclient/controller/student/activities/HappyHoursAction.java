package com.polsl.edziennik.desktopclient.controller.student.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.student.StudentAction;
import com.polsl.edziennik.desktopclient.view.common.HappyHours;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u godzin
 * dziekañskich na poziomie studenta
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class HappyHoursAction extends StudentAction {

	private static String title = menu.getString("happyHours");

	public HappyHoursAction(StudentMainView parent) {
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