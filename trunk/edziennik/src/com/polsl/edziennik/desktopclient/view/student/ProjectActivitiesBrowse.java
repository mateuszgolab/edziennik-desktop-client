package com.polsl.edziennik.desktopclient.view.student;

import java.awt.BorderLayout;
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
import com.polsl.edziennik.desktopclient.model.tables.ProjectActivityTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.ActivityTablePanel;
import com.polsl.edziennik.desktopclient.view.student.panels.preview.ProjectActivityStudentPreviewPanel;
import com.polsl.edziennik.modelDTO.activity.ProjectActivityDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;
import com.polsl.edziennik.modelDTO.project.ProjectDTO;

public class ProjectActivitiesBrowse extends JPanel {
	private JSplitPane splitPane;
	private ProjectActivityStudentPreviewPanel preview;
	private ActivityTablePanel tablePanel;
	private GroupComboBoxModel comboModel;
	private ProjectActivityTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private DateFilterPanel filter;
	private ProjectDTO currentProject;

	public ProjectActivitiesBrowse() {
		setLayout(new BorderLayout());
		currentProject = null;

		new StudentProjectProvider().execute();

		comboModel = new GroupComboBoxModel();
		tableModel = new ProjectActivityTableModel();

		filter = new DateFilterPanel(tableModel, comboModel, bundle.getString("studentNamesHint"),
				bundle.getString("groupHint"), "group");
		tablePanel = new ActivityTablePanel(tableModel, comboModel, filter, null, preview, "ProjectActivityFrameTitle");
		tablePanel.getSelectionModel().addListSelectionListener(new ProjectActivityTableSelectionListener());
		preview = new ProjectActivityStudentPreviewPanel(bundle.getString("PreviewTitle"), tableModel);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(new JScrollPane(preview));
		add(splitPane);

	}

	private class ProjectActivityTableSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selected = tablePanel.getSelected();
			if (selected > -1) {
				preview.setSelected(selected);
				preview.setData(tableModel.get(selected));
				preview.setEnabled(true);
			}
		}

	}

	private class StudentProjectProvider extends Worker<ProjectDTO> {

		@Override
		protected ProjectDTO doInBackground() throws Exception {
			startProgress();
			StudentDTO s = DelegateFactory.getTeacherDelegate().getStudent(2);
			if (s != null) s.getProject();
			return null;
		}

		@Override
		public void done() {
			try {
				currentProject = get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stopProgress();
			new DataProvider().execute();

		}

	}

	private class DataProvider extends Worker<List<ProjectActivityDTO>> {

		public DataProvider() {
			super("get");
		}

		@Override
		protected List<ProjectActivityDTO> doInBackground() throws Exception {

			startProgress();
			if (currentProject != null)
				return DelegateFactory.getCommonDelegate().getProjectActivities(currentProject.getId());
			return null;

		}

		@Override
		public void done() {

			try {
				tableModel.setModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			stopProgress();
		}
	}

	// private class PreviewDataProvider extends Worker<List<TeacherDTO>> {
	//
	// private Integer selected;
	//
	// public PreviewDataProvider(Integer selected) {
	// super("get");
	// this.selected = selected;
	// }
	//
	// @Override
	// protected List<TeacherDTO> doInBackground() throws Exception {
	//
	// startProgress();
	// return DelegateFactory.getTeacherDelegate().getAllTeachers();
	//
	// }
	//
	// @Override
	// public void done() {
	// try {
	// preview.setTeacherModel(get());
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ExecutionException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// if (selected != null) {
	// preview.setSelected(selected);
	// preview.setData(tableModel.get(selected));
	// }
	//
	// preview.setProject(currentProject);
	// preview.setEnabled(true);
	// buttonPanel.activate(true);
	// stopProgress();
	//
	// }
	// }

}