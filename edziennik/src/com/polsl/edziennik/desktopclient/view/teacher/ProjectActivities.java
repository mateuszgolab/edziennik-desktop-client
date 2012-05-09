package com.polsl.edziennik.desktopclient.view.teacher;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import com.polsl.edziennik.desktopclient.controller.utils.workers.SaveWorker;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.comboBoxes.GroupComboBoxModel;
import com.polsl.edziennik.desktopclient.model.tables.ProjectActivityTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.admin.panels.preview.AdminProjectActivityPreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ProjectActivityButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.ProjectActivityTablePanel;
import com.polsl.edziennik.modelDTO.activity.ProjectActivityDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;
import com.polsl.edziennik.modelDTO.project.ProjectDTO;

public class ProjectActivities extends JPanel {
	private ProjectActivityButtonPanel buttonPanel;
	private JSplitPane splitPane;
	private AdminProjectActivityPreviewPanel preview;
	private ProjectActivityTablePanel tablePanel;
	private GroupComboBoxModel comboModel;
	private ProjectActivityTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private DateFilterPanel filter;
	private ProjectDTO currentProject;

	public ProjectActivities(ProjectDTO project) {
		currentProject = project;
		setLayout(new BorderLayout());
		comboModel = new GroupComboBoxModel();
		tableModel = new ProjectActivityTableModel();
		filter = new DateFilterPanel(tableModel);
		tablePanel = new ProjectActivityTablePanel(tableModel, comboModel, filter, buttonPanel, preview);
		tablePanel.getSelectionModel().addListSelectionListener(new ProjectActivityTableSelectionListener());
		preview = new AdminProjectActivityPreviewPanel(bundle.getString("PreviewTitle"), tableModel);

		new DataProvider().execute();

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(new JScrollPane(preview));
		add(splitPane);

		buttonPanel = new ProjectActivityButtonPanel();
		add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

		buttonPanel.activate(false);

		buttonPanel.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new PreviewDataProvider(null).execute();

			}
		});

		buttonPanel.getSaveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new SaveWorker(preview, tablePanel, buttonPanel).execute();
			}
		});

		buttonPanel.getRemoveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (tableModel.get(tablePanel.getSelected()) != null)
				// DelegateFactory.getAdminDelegate().deleteProjectActivity(tableModel.get(tablePanel.getSelected()).getId());
					tableModel.remove(tablePanel.getSelected());

			}
		});

		setVisible(true);

		preview.setEnabled(false);
		buttonPanel.activate(false);

	}

	private class ProjectActivityTableSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selected = tablePanel.getSelected();
			if (selected > -1) {

				new PreviewDataProvider(selected).execute();

			}
		}

	}

	private class DataProvider extends Worker<List<ProjectActivityDTO>> {

		public DataProvider() {
			super("get");
		}

		@Override
		protected List<ProjectActivityDTO> doInBackground() throws Exception {

			startProgress();
			return DelegateFactory.getCommonDelegate().getProjectActivities(currentProject.getId());

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

			startProgress();
			return DelegateFactory.getTeacherDelegate().getAllTeachers();

		}

		@Override
		public void done() {
			try {
				preview.setTeacherModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (selected != null) {
				preview.setSelected(selected);
				preview.setData(tableModel.get(selected));
			}

			preview.setProject(currentProject);
			preview.setEnabled(true);
			buttonPanel.activate(true);
			stopProgress();

		}
	}

}
