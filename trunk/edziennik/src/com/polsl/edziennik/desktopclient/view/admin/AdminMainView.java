package com.polsl.edziennik.desktopclient.view.admin;

import java.awt.BorderLayout;

import com.polsl.edziennik.desktopclient.view.admin.menu.AdminIconsPanel;
import com.polsl.edziennik.desktopclient.view.admin.menu.AdminMenu;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

public class AdminMainView extends TeacherMainView {
	public AdminMainView() {
		super();
	}

	public void setMenu() {
		AdminMenu menu = new AdminMenu(this);
		setJMenuBar(menu);

	}

	public void setIconsPanel() {
		AdminIconsPanel adminIconsPanel = new AdminIconsPanel(this);
		add(adminIconsPanel, BorderLayout.NORTH);

	}

}