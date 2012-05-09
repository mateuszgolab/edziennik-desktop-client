package com.polsl.edziennik.desktopclient.view.teacher;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.tabbedpanes.ClosableTabbedPane;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;
import com.polsl.edziennik.desktopclient.view.teacher.menu.TeacherIconsPanel;
import com.polsl.edziennik.desktopclient.view.teacher.menu.TeacherMenu;

public class TeacherMainView extends StudentMainView {
	

	public void setMenu() {
		TeacherMenu menu = new TeacherMenu(this);
		setJMenuBar(menu);

	}

	public void setIconsPanel() {
		TeacherIconsPanel teacherIconsPanel = new TeacherIconsPanel(this);
		add(teacherIconsPanel, BorderLayout.NORTH);

	}

}
