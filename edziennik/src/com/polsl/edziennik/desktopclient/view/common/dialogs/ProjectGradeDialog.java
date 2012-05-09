package com.polsl.edziennik.desktopclient.view.common.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.model.tables.ProjectGradeTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.button.GradeDetailsButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.ProjectStudentTablePanel;
import com.polsl.edziennik.modelDTO.grade.RegularGradeDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;
import com.polsl.edziennik.modelDTO.summary.ProjectSummary;

public class ProjectGradeDialog extends JDialog {
	private ProjectGradeTableModel tableModel;
	private FrameToolkit frameToolkit = new FrameToolkit();
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private GradeDetailsButtonPanel buttonPanel;
	private ProjectStudentTablePanel tablePanel;
	private ProjectGradeDetailsDialog gradeDetails;
	private Integer projectId;

	public ProjectGradeDialog(ProjectGradeTableModel model) {

		setTitle(bundle.getString("grades"));
		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(30 + 140 * 3 + 10, preferredSize.getHeight());
		setSize(preferredSize);
		setIconImage(frameToolkit.getTitleIcon("GradesIcon"));
		setLocationRelativeTo(null);

		tableModel = model;

		tablePanel = new ProjectStudentTablePanel(model);
		tablePanel.setColumnWidths();
		add(tablePanel, BorderLayout.CENTER);

		tablePanel.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selected = tablePanel.getSelected();
				if (selected > -1) {
					buttonPanel.activate(true);

				}
			}
		});

		buttonPanel = new GradeDetailsButtonPanel("detailsButton", "detailsButtonHint");
		buttonPanel.getDetailsButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StudentDTO s = tableModel.get(tablePanel.getSelected());
				if (s != null && s.getId() != null) {
					gradeDetails = new ProjectGradeDetailsDialog(tableModel.get(tablePanel.getSelected()), projectId,
							(ProjectSummary<RegularGradeDTO>) tableModel.getValueAt(tablePanel.getSelected(), 3));
				}
			}
		});
		add(buttonPanel, BorderLayout.SOUTH);

		buttonPanel.activate(false);

	}

	public void setProjectId(Integer id) {
		projectId = id;
	}

	public void setModel(List<StudentDTO> l) {
		tableModel.setModel(l);
	}

	public JButton getExButton() {
		return buttonPanel.getExitButton();
	}

	public JButton getDetailsButton() {
		return buttonPanel.getDetailsButton();
	}

}