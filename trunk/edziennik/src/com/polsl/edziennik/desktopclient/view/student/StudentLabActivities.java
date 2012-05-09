package com.polsl.edziennik.desktopclient.view.student;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.comboBoxes.GroupComboBoxModel;
import com.polsl.edziennik.desktopclient.model.tables.LabActivityAttendanceTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.LabActivityAttendanceTablePanel;
import com.polsl.edziennik.desktopclient.view.student.panels.preview.StudentLabActivityPreviewPanel;
import com.polsl.edziennik.modelDTO.attendance.AttendanceDTO;
import com.polsl.edziennik.modelDTO.exercise.ExerciseTopicDTO;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public class StudentLabActivities extends JPanel {

	private JSplitPane splitPane;
	private StudentLabActivityPreviewPanel preview;
	private LabActivityAttendanceTablePanel tablePanel;
	private GroupComboBoxModel comboModel;
	private LabActivityAttendanceTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private DateFilterPanel filter;

	public StudentLabActivities() {
		setLayout(new BorderLayout());

		comboModel = new GroupComboBoxModel();
		tableModel = new LabActivityAttendanceTableModel();

		DataProvider provider = new DataProvider();
		provider.execute();
		provider.startProgress();

		preview = new StudentLabActivityPreviewPanel(bundle.getString("PreviewTitle"), tableModel);
		filter = new DateFilterPanel(tableModel, comboModel, bundle.getString("studentNamesHint"),
				bundle.getString("groupHint"), "group");
		tablePanel = new LabActivityAttendanceTablePanel(tableModel, comboModel, filter, null, preview);
		tablePanel.getSelectionModel().addListSelectionListener(new ActivityTableSelectionListener());

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(new JScrollPane(preview));
		add(splitPane);

		setVisible(true);

	}

	private class ActivityTableSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selected = tablePanel.getSelected();
			if (selected > -1) {
				PreviewDataProvider provider = new PreviewDataProvider(selected);
				provider.execute();
				provider.startProgress();

			}
		}

	}

	private class DataProvider extends Worker<List<AttendanceDTO>> {

		private List<GroupDTO> groups;

		public DataProvider() {
			super("get");
		}

		@Override
		protected List<AttendanceDTO> doInBackground() throws Exception {

			groups = DelegateFactory.getTeacherDelegate().getAllGroups();
			return DelegateFactory.getStudentDelegate().getLabActivityAttendancesByStudent(2);

		}

		@Override
		public void done() {
			stopProgress();

			try {
				tableModel.setModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			preview.setGroupModel(new ArrayList<GroupDTO>(groups));
			GroupDTO g = new GroupDTO();
			g.setName(bundle.getString("all"));
			g.setId(-1);
			groups.add(g);
			comboModel.setModel(groups);
			comboModel.setSelectedItem(g);
			tablePanel.refresh();

		}
	}

	private class PreviewDataProvider extends Worker<List<TeacherDTO>> {

		private Integer selected;
		private List<ExerciseTopicDTO> ex;

		public PreviewDataProvider(Integer selected) {
			super("get");
			this.selected = selected;
		}

		@Override
		protected List<TeacherDTO> doInBackground() throws Exception {

			ex = DelegateFactory.getCommonDelegate().getExerciseTopics();
			return DelegateFactory.getTeacherDelegate().getAllTeachers();

		}

		@Override
		public void done() {
			stopProgress();

			try {
				preview.setTeacherModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			preview.setExerciseTopicModel(ex);
			preview.setSelected(selected);
			preview.setData(tableModel.get(selected));
			preview.setReportPanelEnabled(true);
			preview.setEditable(false);

		}
	}

}
