package com.polsl.edziennik.desktopclient.view.common.panels.button;

import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.properties.Properties;

public class ProjectActivityPreviewButtonPanel extends JPanel {

	protected JButton attendances;
	protected JButton grades;
	protected ResourceBundle components = LangManager.getResource(Properties.Component);
	protected IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	protected IButton Button;

	public ProjectActivityPreviewButtonPanel() {

		Button = factory.createTextButton();

		attendances = Button.getButton("attendancesButton", "attendancesButton");
		grades = Button.getButton("gradesButton", "gradesButton");

		add(grades);
		add(attendances);

	}

	public void activate(boolean value) {
		attendances.setEnabled(value);
		grades.setEnabled(value);
	}

	public JButton getAttendancesButton() {
		return attendances;
	}

	public JButton getGradesButton() {
		return grades;
	}
}
