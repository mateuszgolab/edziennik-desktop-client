package com.polsl.edziennik.desktopclient.view.student.panels.preview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IFilter;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.LabActivityAttendanceTableModel;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.LabActivityPreviewPanel;
import com.polsl.edziennik.desktopclient.view.student.SendReportPanel;
import com.polsl.edziennik.desktopclient.view.student.dialogs.StudentGradeDetailsDialog;
import com.polsl.edziennik.modelDTO.activity.LabActivityDTO;
import com.polsl.edziennik.modelDTO.attendance.AttendanceDTO;
import com.polsl.edziennik.modelDTO.exercise.ExerciseTopicDTO;
import com.polsl.edziennik.modelDTO.file.FileDTO;
import com.polsl.edziennik.modelDTO.grade.RegularGradeDTO;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;
import com.polsl.edziennik.modelDTO.resource.ReportDTO;

public class StudentLabActivityPreviewPanel extends LabActivityPreviewPanel {

	private JLabel attendance;
	private JLabel justified;
	private JLabel justified2;
	private JCheckBox attendanceBox;
	private JCheckBox justifiedBox;
	private JButton grades;
	private IButton button;
	private StudentGradeDetailsDialog gradeDetails;
	private LabActivityAttendanceTableModel tableModel;
	private SendReportPanel sendPanel;
	private IFilter filter;
	private ReportDTO report;

	public StudentLabActivityPreviewPanel(String title, LabActivityAttendanceTableModel model) {
		super(title);
		tableModel = model;
		setEditable(false);
	}

	@Override
	public void create() {
		super.create();
		attendance = label.getLabel("attendance");
		justified = new JLabel("done");
		justified2 = label.getLabel("justified2");
		filter = factory.createFilter();
		sendPanel = new SendReportPanel(filter.getDocumentFilter(), "sendReport");
		sendPanel.getChoseButton().addActionListener(sendPanel.new DefaultListener());
		sendPanel.getSendButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileDTO file = sendPanel.getFile();

				if (file == null || file.getContent() == null) {

					JOptionPane.showMessageDialog(null, bundle.getString("fileTransferError"));
				} else {
					SendFileWorker w = new SendFileWorker(file);
					w.execute();
					w.startProgress();

				}
			}
		});
		sendPanel.getStudentsButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StudentsDataProvider p = new StudentsDataProvider();
				p.execute();
				p.startProgress();

			}
		});

		attendanceBox = new JCheckBox();
		justifiedBox = new JCheckBox();
		button = factory.createTextButton();

		grades = button.getButton("grades", "activityGradesHint");

		grades.addActionListener(new GradesListener());
	}

	@Override
	public void setComponents() {
		super.setComponents();

		add(attendance, cc.xyw(1, 15, 5));
		add(attendanceBox, cc.xyw(3, 15, 5));
		add(justified, cc.xyw(1, 19, 5));
		add(justifiedBox, cc.xyw(3, 19, 5));
		add(grades, cc.xyw(3, 21, 5));
		add(sendPanel, cc.xyw(1, 23, 10));
	}

	public void setData(AttendanceDTO at) {

		if (at == null) {
			clear();
		} else {

			super.setData((LabActivityDTO) at.getActivity());

			if (at.getAttendance() == null)
				attendanceBox.setSelected(false);
			else
				attendanceBox.setSelected(at.getAttendance());
			if (at.getJustified() == null)
				justifiedBox.setSelected(false);
			else
				justifiedBox.setSelected(at.getJustified());

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
		sendPanel.setEnabled(b);
	}

	public void setReportPanelEnabled(boolean b) {
		sendPanel.activate();
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

			gradeDetails = new StudentGradeDetailsDialog(tableModel.get(selected).getStudent(),
					currentLabActivity.getId());
			if (currentLabActivity != null && currentLabActivity.getId() != null) {
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
			return DelegateFactory.getCommonDelegate().getStudentGrades(2, currentLabActivity.getId());
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

	public void setExerciseTopicModel(List<ExerciseTopicDTO> ex) {
		exerciseTopicComboModel.setModel(ex);
		exerciseTopicComboBox.setSelectedItem(null);
		exerciseTopicComboBox.updateUI();
	}

	public void setGroupModel(List<GroupDTO> groups) {
		groupComboModel.setModel(groups);
		groupComboBox.setSelectedItem(null);
		groupComboBox.updateUI();
	}

	private class SendFileWorker extends Worker<FileDTO> {

		private FileDTO file;

		public SendFileWorker(FileDTO f) {
			super("set");
			file = f;
		}

		@Override
		protected FileDTO doInBackground() throws Exception {
			return DelegateFactory.getCommonDelegate().addFile(file);
		}

		@Override
		public void done() {
			stopProgress();
			FileDTO result = null;

			try {
				result = get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			SendReportWorker w = new SendReportWorker(result.getId());
			w.execute();
			w.startProgress();

		}
	}

	private class SendReportWorker extends Worker<ReportDTO> {
		private Integer fileId;

		public SendReportWorker(Integer f) {
			super("set");
			fileId = f;
		}

		@Override
		protected ReportDTO doInBackground() throws Exception {
			return DelegateFactory.getStudentDelegate().sendReport(currentLabActivity.getId(), fileId,
					sendPanel.getSelectedStudents());
		}

		@Override
		public void done() {
			stopProgress();
			try {
				report = get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sendPanel.getSendButton().setEnabled(false);
		}

	}

	private class StudentsDataProvider extends Worker<List<StudentDTO>> {

		private List<GroupDTO> groups;

		@Override
		protected List<StudentDTO> doInBackground() throws Exception {
			groups = DelegateFactory.getTeacherDelegate().getAllGroups();
			return DelegateFactory.getTeacherDelegate().getAllStudents();
		}

		@Override
		public void done() {

			stopProgress();
			try {
				sendPanel.setModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GroupDTO g = new GroupDTO();
			g.setId(-1);
			g.setName(bundle.getString("all"));
			groups.add(g);
			sendPanel.setComboModel(groups);
			sendPanel.setStudentsDialog();

		}

	}
}
