package com.polsl.edziennik.desktopclient.view.common.dialogs;

import java.awt.Dimension;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JDialog;

import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.model.tables.StudentTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.StudentTightTablePanel;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class StudentsDialog extends JDialog {

	private StudentTightTablePanel studentsList;
	private FrameToolkit frameToolkit = new FrameToolkit();
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private StudentTableModel studentsModel;

	public StudentsDialog() {

		setTitle(bundle.getString("grades"));
		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(30 + 140 * 3, preferredSize.getHeight());
		setSize(preferredSize);
		setIconImage(frameToolkit.getTitleIcon("GradesIcon"));
		setLocationRelativeTo(null);

		studentsModel = new StudentTableModel();
		studentsList = new StudentTightTablePanel(studentsModel);

		add(studentsList);

	}

	public void setModel(List<StudentDTO> l) {
		studentsModel.setModel(l);
	}
}
