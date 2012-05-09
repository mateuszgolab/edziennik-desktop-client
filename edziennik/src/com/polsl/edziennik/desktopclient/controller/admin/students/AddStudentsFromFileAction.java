package com.polsl.edziennik.desktopclient.controller.admin.students;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.admin.AdminAction;
import com.polsl.edziennik.desktopclient.view.admin.AddStudentsFromFile;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u dodawania
 * studentów z pliku na poziomie administratora
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class AddStudentsFromFileAction extends AdminAction {

	private static String title = menu.getString("addFromFile");

	public AddStudentsFromFileAction(AdminMainView parent) {
		super(title);
		this.parent = parent;
	}

	/**
	 * metoda uruchamiaj¹ca akcjê
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		AddStudentsFromFile studenciPanel = new AddStudentsFromFile();
		parent.addTab(title + "  ", studenciPanel, new ImageIcon(bundle.getString("addStudentsFromFileIcon")));
		parent.invalidate();
		parent.validate();

	}

}