package com.polsl.edziennik.desktopclient.view.common.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.jidesoft.swing.JideSplitPane;
import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.model.comboBoxes.GroupComboBoxModel;
import com.polsl.edziennik.desktopclient.model.tables.StudentTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.button.SaveExitButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.VerticalArrowButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DefaultFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DefaultSimpleFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.StudentTightTablePanel;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class StudentDualListDialog extends JDialog {
	private JideSplitPane splitPane;
	private StudentTightTablePanel selectedStudentsPanel;
	private StudentTightTablePanel studentsToChoosePanel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private DefaultFilterPanel filter;
	private DefaultSimpleFilterPanel simpleFilter;
	private StudentTableModel selectedModel;
	private StudentTableModel choosableModel;
	private GroupComboBoxModel comboModel;
	private FrameToolkit frameToolkit = new FrameToolkit();
	private VerticalArrowButtonPanel arrowPanel;
	private JPanel mainPanel;
	private SaveExitButtonPanel buttonPanel;

	public StudentDualListDialog(StudentTableModel model) {
		setTitle(bundle.getString("StudentsTitle"));
		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(preferredSize.getWidth() - 50, preferredSize.getHeight());
		setSize(preferredSize);
		setLocationRelativeTo(null);
		setIconImage(frameToolkit.getTitleIcon("StudentsIcon"));

		selectedModel = model;
		choosableModel = new StudentTableModel();
		comboModel = new GroupComboBoxModel();
		simpleFilter = new DefaultSimpleFilterPanel(selectedModel, bundle.getString("studentNamesHint"), "lastName");
		filter = new DefaultFilterPanel(choosableModel, comboModel, bundle.getString("studentNamesHint"), "lastName",
				bundle.getString("groupHint"), "group");
		selectedStudentsPanel = new StudentTightTablePanel(selectedModel);
		studentsToChoosePanel = new StudentTightTablePanel(choosableModel, comboModel, filter,
				bundle.getString("StudentsTitle"), null, null);

		arrowPanel = new VerticalArrowButtonPanel();
		arrowPanel.getLeft().addActionListener(new LeftArrowListener());
		arrowPanel.getAllLeft().addActionListener(new AllLeftArrowListener());
		arrowPanel.getRight().addActionListener(new RightArrowListener());
		arrowPanel.getAllRight().addActionListener(new AllRightArrorListener());

		splitPane = new JideSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		splitPane.add(selectedStudentsPanel);
		splitPane.add(arrowPanel);
		splitPane.add(studentsToChoosePanel);

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(splitPane);

		add(mainPanel, BorderLayout.CENTER);

		buttonPanel = new SaveExitButtonPanel("saveChangesHint");
		add(buttonPanel, BorderLayout.SOUTH);

	}

	public void setComboModel(List<GroupDTO> list) {
		comboModel.setModel(list);
		comboModel.setSelectedItem(bundle.getString("all"));
	}

	public JButton getSaveButton() {
		return buttonPanel.getSaveButton();
	}

	public JButton getCancelButton() {
		return buttonPanel.getExitButton();
	}

	public void setSelectedModel(List<StudentDTO> list) {
		selectedModel.setModel(list);
	}

	public void setChoosableModel(List<StudentDTO> list) {
		choosableModel.setModel(list);
	}

	private class LeftArrowListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (studentsToChoosePanel.getSelected() > -1) {
				selectedModel.add(choosableModel.get(studentsToChoosePanel.getSelected()));
				choosableModel.remove(studentsToChoosePanel.getSelected());
			}
		}
	}

	private class RightArrowListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (selectedStudentsPanel.getSelected() > -1) {
				choosableModel.add(selectedModel.get(selectedStudentsPanel.getSelected()));
				selectedModel.remove(selectedStudentsPanel.getSelected());
			}
		}
	}

	private class AllLeftArrowListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			selectedModel.add(studentsToChoosePanel.getViewRows());
			choosableModel.remove(studentsToChoosePanel.getViewRows());
		}
	}

	private class AllRightArrorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			choosableModel.add(selectedModel.getData());
			selectedModel.removeAll();
		}
	}

	public List<Integer> getSelectedIds() {
		return selectedModel.getModelIds();
	}

	public void clearSelection() {
		studentsToChoosePanel.clearSelection();
		selectedStudentsPanel.clearSelection();
	}
}
