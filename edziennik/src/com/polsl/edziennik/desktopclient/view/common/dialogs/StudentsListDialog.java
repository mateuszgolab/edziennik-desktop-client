package com.polsl.edziennik.desktopclient.view.common.dialogs;

import java.awt.Dimension;
import java.util.ResourceBundle;

import javax.swing.JDialog;

import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.model.comboBoxes.GroupComboBoxModel;
import com.polsl.edziennik.desktopclient.model.tables.StudentTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DefaultSimpleFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.StudentTightTablePanel;

public class StudentsListDialog extends JDialog {
	private StudentTightTablePanel selectedStudentsPanel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private DefaultSimpleFilterPanel simpleFilter;
	private StudentTableModel selectedModel;
	private GroupComboBoxModel comboModel;
	private FrameToolkit frameToolkit = new FrameToolkit();

	public StudentsListDialog(StudentTableModel model) {
		setTitle(bundle.getString("StudentsTitle"));
		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(preferredSize.getWidth() - 10, preferredSize.getHeight());
		setSize(preferredSize);
		setLocationRelativeTo(null);
		setIconImage(frameToolkit.getTitleIcon("StudentsIcon"));

		selectedModel = model;
		comboModel = new GroupComboBoxModel();
		simpleFilter = new DefaultSimpleFilterPanel(selectedModel, bundle.getString("studentNamesHint"), "lastName");
		selectedStudentsPanel = new StudentTightTablePanel(selectedModel, simpleFilter,
				bundle.getString("selectedStudents"), null, null);

		add(selectedStudentsPanel);

	}

}
