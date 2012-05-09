package com.polsl.edziennik.desktopclient.controller.admin.students;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.admin.AdminAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.teacher.Summary;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu�u podsumowa�
 * student�w na poziomie administratora
 * 
 * @author Mateusz Go��b
 * 
 */
public class SummaryAction extends AdminAction {

	private static String title = menu.getString("summary");

	public SummaryAction(AdminMainView parent) {
		super(title);
		this.parent = parent;
	}

	/**
	 * metoda uruchamiaj�ca akcj�
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Summary summary = new Summary();
		parent.addTab(title + "  ", summary, new ImageIcon(bundle.getString("StudentsIcon")));
		parent.invalidate();
		parent.validate();

	}
}