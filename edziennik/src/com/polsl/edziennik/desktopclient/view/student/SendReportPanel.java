package com.polsl.edziennik.desktopclient.view.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.forms.layout.CellConstraints;
import com.polsl.edziennik.desktopclient.model.tables.StudentTableModel;
import com.polsl.edziennik.desktopclient.view.common.dialogs.StudentDualListDialog;
import com.polsl.edziennik.desktopclient.view.common.panels.SendFilePanel;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class SendReportPanel extends SendFilePanel {

	private StudentDualListDialog students;
	private StudentTableModel model;
	private JButton studentsButton;

	public SendReportPanel(FileNameExtensionFilter filter, String title) {
		super(filter, title);
		setEnabled(false);
	}

	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		studentsButton.setEnabled(b);
	}

	public void activate() {
		setChooseEnabled(true);
		studentsButton.setEnabled(true);
	}

	@Override
	public void create() {
		super.create();
		model = new StudentTableModel();
		students = new StudentDualListDialog(model);
		students.getCancelButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				students.dispose();

			}
		});
		studentsButton = button.getButton("studentsButton", "reportStudentsHint");

	}

	public JButton getStudentsButton() {
		return studentsButton;
	}

	@Override
	public void setComponents() {

		cc = new CellConstraints();

		add(studentsButton, cc.xy(3, 1));
		add(chose, cc.xy(3, 3));
		add(iconLabel, cc.xy(4, 3));
		add(fileChosen, cc.xy(5, 3));
		add(send, cc.xy(3, 5));
		add(error, cc.xyw(3, 9, 4));
	}

	public List<Integer> getSelectedStudents() {
		return students.getSelectedIds();
	}

	public void setModel(List<StudentDTO> l) {
		students.setChoosableModel(l);
	}

	public void setComboModel(List<GroupDTO> g) {
		students.setComboModel(g);
	}

	public void setStudentsDialog() {
		students.setVisible(true);
	}
}
