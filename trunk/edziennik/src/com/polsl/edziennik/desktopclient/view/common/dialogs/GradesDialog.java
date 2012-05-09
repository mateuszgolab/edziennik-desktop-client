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
import com.polsl.edziennik.desktopclient.model.tables.StudentGradesTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.button.GradeDetailsButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.StudentGradesTablePanel;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class GradesDialog extends JDialog {
	private StudentGradesTableModel tableModel;
	private FrameToolkit frameToolkit = new FrameToolkit();
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private GradeDetailsButtonPanel buttonPanel;
	private GradeDetailsDialog gradeDetails;
	private StudentGradesTablePanel tablePanel;
	private Integer activityId;

	public GradesDialog(StudentGradesTableModel model) {

		setTitle(bundle.getString("grades"));
		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(30 + 140 * 3 + 10 + 40, preferredSize.getHeight());
		setSize(preferredSize);
		setIconImage(frameToolkit.getTitleIcon("GradesIcon"));
		setLocationRelativeTo(null);

		this.activityId = activityId;
		tableModel = model;
		tablePanel = new StudentGradesTablePanel(model);
		tablePanel.setColumnsWidths();
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
				gradeDetails = new GradeDetailsDialog(tableModel.get(tablePanel.getSelected()),
						GradesDialog.this.activityId);
			}
		});
		add(buttonPanel, BorderLayout.SOUTH);

		buttonPanel.activate(false);

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

	public void setActivity(Integer id) {
		activityId = id;
	}
}