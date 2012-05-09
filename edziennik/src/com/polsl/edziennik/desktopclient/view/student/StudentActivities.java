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
import com.polsl.edziennik.desktopclient.model.tables.ActivityAttendanceTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.ActivityAttendanceTablePanel;
import com.polsl.edziennik.desktopclient.view.student.panels.preview.StudentActivityPreviewPanel;
import com.polsl.edziennik.modelDTO.attendance.AttendanceDTO;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public class StudentActivities extends JPanel {

	private JSplitPane splitPane;
	private StudentActivityPreviewPanel preview;
	private ActivityAttendanceTablePanel tablePanel;
	private GroupComboBoxModel comboModel;
	private ActivityAttendanceTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private DateFilterPanel filter;

	public StudentActivities() {
		setLayout(new BorderLayout());

		comboModel = new GroupComboBoxModel();
		tableModel = new ActivityAttendanceTableModel();

		DataProvider provider = new DataProvider();
		provider.execute();
		provider.startProgress();

		filter = new DateFilterPanel(tableModel, comboModel, bundle.getString("studentNamesHint"),
				bundle.getString("groupHint"), "group");
		tablePanel = new ActivityAttendanceTablePanel(tableModel, comboModel, filter, null, preview,
				"ActivityFrameTitle");
		tablePanel.getSelectionModel().addListSelectionListener(new ActivityTableSelectionListener());
		preview = new StudentActivityPreviewPanel(bundle.getString("PreviewTitle"), tableModel);

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
			return DelegateFactory.getStudentDelegate().getActivityAttendancesByStudent(2);

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

		public PreviewDataProvider(Integer selected) {
			super("get");
			this.selected = selected;
		}

		@Override
		protected List<TeacherDTO> doInBackground() throws Exception {

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

			preview.setSelected(selected);
			preview.setData(tableModel.get(selected));
			preview.setEnabled(true);
			preview.setEditable(false);

		}
	}

}
