package com.polsl.edziennik.desktopclient.view.student;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.tabbedpanes.ClosableTabbedPane;
import com.polsl.edziennik.desktopclient.view.student.menu.StudentIconsPanel;
import com.polsl.edziennik.desktopclient.view.student.menu.StudentMenu;

public class StudentMainView extends JFrame {
	public static FrameToolkit frameToolkit = new FrameToolkit();
	protected ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected ClosableTabbedPane tabbedPane;

	public StudentMainView() {
		setTitle(bundle.getString("Title"));
		setSize(frameToolkit.getSize());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(frameToolkit.getTitleIcon("logoIcon"));

		tabbedPane = new ClosableTabbedPane();

		add(tabbedPane, BorderLayout.CENTER);
		setMenu();
		setIconsPanel();

	}

	public void addTab(String name, JPanel panel, ImageIcon icon) {
		tabbedPane.addTab(name, panel);
		tabbedPane.setIconAt(tabbedPane.getTabCount() - 1, icon);
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
	}

	public void setMenu() {
		StudentMenu menu = new StudentMenu(this);
		setJMenuBar(menu);
	}

	public void setIconsPanel() {
		StudentIconsPanel studentIconsPanel = new StudentIconsPanel(this);
		add(studentIconsPanel, BorderLayout.NORTH);

	}

}
