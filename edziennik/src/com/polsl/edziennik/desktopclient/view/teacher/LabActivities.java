package com.polsl.edziennik.desktopclient.view.teacher;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import com.polsl.edziennik.desktopclient.controller.utils.workers.RemoveWorker;
import com.polsl.edziennik.desktopclient.controller.utils.workers.SaveWorker;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.comboBoxes.GroupComboBoxModel;
import com.polsl.edziennik.desktopclient.model.tables.LabActivityTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.admin.panels.preview.AdminLabActivityPreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ActivityButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.LabActivityTablePanel;
import com.polsl.edziennik.modelDTO.activity.LabActivityDTO;
import com.polsl.edziennik.modelDTO.exercise.ExerciseTopicDTO;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public class LabActivities extends JPanel {
	private ActivityButtonPanel buttonPanel;
	private JSplitPane splitPane;
	private AdminLabActivityPreviewPanel preview;
	private LabActivityTablePanel tablePanel;
	private GroupComboBoxModel comboModel;
	private LabActivityTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private DateFilterPanel filter;
	private Integer id;

	public LabActivities(Integer id) {
		setLayout(new BorderLayout());
		this.id = id;
		comboModel = new GroupComboBoxModel();
		tableModel = new LabActivityTableModel();
		preview = new AdminLabActivityPreviewPanel(bundle.getString("PreviewTitle"), tableModel);

		DataProvider provider = new DataProvider();
		provider.execute();
		provider.startProgress();

		filter = new DateFilterPanel(tableModel, comboModel, bundle.getString("studentNamesHint"),
				bundle.getString("groupHint"), "group");
		tablePanel = new LabActivityTablePanel(tableModel, comboModel, filter, buttonPanel, preview);
		tablePanel.getSelectionModel().addListSelectionListener(new LabActivityTableSelectionListener());

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(new JScrollPane(preview));
		add(splitPane);

		buttonPanel = new ActivityButtonPanel();
		add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

		add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

		buttonPanel.activate(false);

		buttonPanel.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				TeachersDataProvider provider = new TeachersDataProvider();
				provider.execute();
				provider.startProgress();

			}
		});

		buttonPanel.getSaveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SaveWorker worker = new SaveWorker(preview, tablePanel, buttonPanel);
				worker.execute();
				worker.startProgress();
			}
		});

		buttonPanel.getRemoveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				RemoveWorker worker = new RemoveWorker(preview, tablePanel, buttonPanel);
				worker.execute();
				worker.startProgress();

			}
		});

		buttonPanel.activate(false);

		setVisible(true);

	}

	private class LabActivityTableSelectionListener implements ListSelectionListener {

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

	private class DataProvider extends Worker<List<LabActivityDTO>> {
		private List<GroupDTO> groups;

		public DataProvider() {
			super("get");
		}

		@Override
		protected List<LabActivityDTO> doInBackground() throws Exception {

			groups = DelegateFactory.getTeacherDelegate().getAllGroups();
			if (id == null) return DelegateFactory.getTeacherDelegate().getAllLabActivities();
			return DelegateFactory.getTeacherDelegate().getTeacherLabActivities(id);

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
			preview.setGroupComboModel(new ArrayList<GroupDTO>(groups));
			comboModel.setModel(groups);
			comboModel.setSelectedItem(g);
			tablePanel.refresh();
		}
	}

	private class PreviewDataProvider extends Worker<List<TeacherDTO>> {

		private Integer selected;
		private List<ExerciseTopicDTO> exercises;

		public PreviewDataProvider(Integer selected) {
			super("get");
			this.selected = selected;
		}

		@Override
		protected List<TeacherDTO> doInBackground() throws Exception {

			exercises = DelegateFactory.getCommonDelegate().getExerciseTopics();
			return DelegateFactory.getTeacherDelegate().getAllTeachers();

		}

		@Override
		public void done() {
			stopProgress();
			preview.setExerciseTopicModel(exercises);
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
			buttonPanel.activate(true);
		}
	}

	private class TeachersDataProvider extends Worker<List<TeacherDTO>> {

		public TeachersDataProvider() {
			super("get");
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

			tablePanel.clearSelection();
			preview.setEnabled(true);
			preview.setData(null);
			buttonPanel.activateSave(true);
		}
	}

	private class ExerciseTopicsDataProvider extends Worker<List<ExerciseTopicDTO>> {

		@Override
		protected List<ExerciseTopicDTO> doInBackground() throws Exception {

			return DelegateFactory.getCommonDelegate().getExerciseTopics();

		}

		@Override
		public void done() {
			stopProgress();

			try {
				preview.setExerciseTopicModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			tablePanel.clearSelection();
			preview.setEnabled(true);
			preview.setData(null);
			buttonPanel.activateSave(true);
		}
	}
}
