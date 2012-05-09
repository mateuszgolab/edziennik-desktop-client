package com.polsl.edziennik.desktopclient.view.student.panels.preview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.ActivityAttendanceTableModel;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.ActivityPreviewPanel;
import com.polsl.edziennik.desktopclient.view.student.dialogs.StudentGradeDetailsDialog;
import com.polsl.edziennik.modelDTO.activity.ActivityDTO;
import com.polsl.edziennik.modelDTO.attendance.AttendanceDTO;
import com.polsl.edziennik.modelDTO.grade.RegularGradeDTO;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public class StudentActivityPreviewPanel extends ActivityPreviewPanel {

	private JLabel attendance;
	private JLabel justified;
	private JLabel justified2;
	private JCheckBox attendanceBox;
	private JCheckBox justifiedBox;
	private JButton grades;
	private IButton button;
	private StudentGradeDetailsDialog gradeDetails;
	private ActivityAttendanceTableModel tableModel;

	public StudentActivityPreviewPanel(String title, ActivityAttendanceTableModel model) {
		super(title);
		tableModel = model;
		setEditable(false);
	}

	@Override
	public void create() {
		super.create();
		attendance = label.getLabel("attendance");
		justified = new JLabel("Nieobecnoœæ");
		justified2 = label.getLabel("justified2");

		attendanceBox = new JCheckBox();
		justifiedBox = new JCheckBox();
		button = factory.createTextButton();

		grades = button.getButton("grades", "activityGradesHint");

		grades.addActionListener(new GradesListener());
	}

	@Override
	public void setComponents() {
		super.setComponents();
		add(noteText, cc.xyw(3, 11, 5));
		add(attendance, cc.xyw(1, 13, 5));
		add(attendanceBox, cc.xyw(3, 13, 5));
		add(justified, cc.xyw(1, 17, 5));
		add(justified2, cc.xyw(1, 19, 5));
		add(justifiedBox, cc.xyw(3, 19, 5));
		add(grades, cc.xyw(3, 23, 5));
	}

	public void setData(AttendanceDTO a) {

		if (a == null)
			clear();
		else {
			super.setData((ActivityDTO) a.getActivity());

			if (a.getAttendance() == null)
				attendanceBox.setSelected(false);
			else
				attendanceBox.setSelected(a.getAttendance());
			if (a.getJustified() == null)
				justifiedBox.setSelected(false);
			else
				justifiedBox.setSelected(a.getJustified());
		}

	}

	@Override
	public void clear() {
		super.clear();
		attendanceBox.setSelected(false);
		justifiedBox.setSelected(false);
	}

	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		attendanceBox.setEnabled(b);
		justifiedBox.setEnabled(b);
		grades.setEnabled(b);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	private class GradesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			gradeDetails = new StudentGradeDetailsDialog(tableModel.get(selected).getStudent(), currentActivity.getId());
			if (currentActivity != null && currentActivity.getId() != null) {
				GradesProvider gp = new GradesProvider();
				gp.execute();
				gp.startProgress();

			} else
				gradeDetails.setVisible(true);
		}

	}

	private class GradesProvider extends Worker<List<RegularGradeDTO>> {

		@Override
		protected List<RegularGradeDTO> doInBackground() throws Exception {
			// TODO CurrentStudent
			return DelegateFactory.getCommonDelegate().getStudentGrades(2, currentActivity.getId());
		}

		@Override
		public void done() {
			stopProgress();
			try {
				gradeDetails.setModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gradeDetails.setVisible(true);
		}

	}

	@Override
	public void setEditable(boolean b) {
		super.setEditable(b);
		if (!b) {
			attendanceBox.setEnabled(b);
			justifiedBox.setEnabled(b);
		}
	}

	public void setTeacherModel(List<TeacherDTO> teachers) {
		teacherComboModel.setModel(teachers);
		teacherComboBox.setSelectedItem(null);
		teacherComboBox.updateUI();
	}

	public void setGroupModel(List<GroupDTO> groups) {
		groupComboModel.setModel(groups);
		groupComboBox.setSelectedItem(null);
		groupComboBox.updateUI();
	}

}
