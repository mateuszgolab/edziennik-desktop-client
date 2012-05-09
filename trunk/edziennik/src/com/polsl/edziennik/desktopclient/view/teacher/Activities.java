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
import com.polsl.edziennik.delegates.TeacherDelegate;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.RemoveWorker;
import com.polsl.edziennik.desktopclient.controller.utils.workers.SaveWorker;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.comboBoxes.GroupComboBoxModel;
import com.polsl.edziennik.desktopclient.model.tables.ActivityTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.admin.panels.preview.AdminActivityPreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ActivityButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.ActivityTablePanel;
import com.polsl.edziennik.modelDTO.activity.ActivityDTO;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public class Activities extends JPanel {
	private ActivityButtonPanel buttonPanel;
	private JSplitPane splitPane;
	private AdminActivityPreviewPanel preview;
	private ActivityTablePanel tablePanel;
	private GroupComboBoxModel comboModel;
	private ActivityTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private DateFilterPanel filter;
	private TeacherDelegate delegate;
	private Integer id;

	public Activities(Integer id) {
		setLayout(new BorderLayout());
		comboModel = new GroupComboBoxModel();
		tableModel = new ActivityTableModel();
		preview = new AdminActivityPreviewPanel(bundle.getString("PreviewTitle"), tableModel);

		this.id = id;
		DataProvider provider = new DataProvider();
		provider.execute();
		provider.startProgress();

		buttonPanel = new ActivityButtonPanel();
		filter = new DateFilterPanel(tableModel, comboModel, bundle.getString("studentNamesHint"),
				bundle.getString("groupHint"), "group");
		tablePanel = new ActivityTablePanel(tableModel, comboModel, filter, buttonPanel, preview, "ActivityFrameTitle");
		tablePanel.getSelectionModel().addListSelectionListener(new ActivityTableSelectionListener());

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(new JScrollPane(preview));
		add(splitPane);

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

	private class DataProvider extends Worker<List<ActivityDTO>> {
		private List<GroupDTO> groups;

		public DataProvider() {
			super("get");
		}

		@Override
		protected List<ActivityDTO> doInBackground() throws Exception {

			delegate = DelegateFactory.getTeacherDelegate();
			groups = delegate.getAllGroups();
			if (id == null) return delegate.getAllActivities();
			return delegate.getTeacherActivities(id);

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

		public PreviewDataProvider(Integer selected) {
			super("get");
			this.selected = selected;
		}

		@Override
		protected List<TeacherDTO> doInBackground() throws Exception {

			delegate = DelegateFactory.getTeacherDelegate();
			return delegate.getAllTeachers();

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
			buttonPanel.activate(true);
		}
	}

	private class TeachersDataProvider extends Worker<List<TeacherDTO>> {

		public TeachersDataProvider() {
			super("get");
		}

		@Override
		protected List<TeacherDTO> doInBackground() throws Exception {

			delegate = DelegateFactory.getTeacherDelegate();
			return delegate.getAllTeachers();

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
}
