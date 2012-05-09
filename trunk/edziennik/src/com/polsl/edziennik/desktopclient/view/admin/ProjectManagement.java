package com.polsl.edziennik.desktopclient.view.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.RemoveWorker;
import com.polsl.edziennik.desktopclient.controller.utils.workers.SaveWorker;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.comboBoxes.TeacherComboBoxModel;
import com.polsl.edziennik.desktopclient.model.tables.ProjectTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.admin.panels.ProjectTablePanel;
import com.polsl.edziennik.desktopclient.view.admin.panels.preview.ProjectPreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ProjectButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DefaultSimpleFilterPanel;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;
import com.polsl.edziennik.modelDTO.project.ProjectDTO;

public class ProjectManagement extends JPanel {
	private JSplitPane splitPane;
	private ProjectPreviewPanel preview;
	private ProjectTablePanel tablePanel;
	private ProjectTableModel tableModel;
	private ResourceBundle menu = LangManager.getResource(Properties.Menu);
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private ProjectButtonPanel buttonPanel;
	private DefaultSimpleFilterPanel filter;
	private TeacherComboBoxModel comboModel;

	public ProjectManagement(Integer id) {
		setLayout(new BorderLayout());
		comboModel = new TeacherComboBoxModel();
		tableModel = new ProjectTableModel();
		preview = new ProjectPreviewPanel(bundle.getString("PreviewTitle"), tableModel);

		DataProvider provider = new DataProvider(id);
		provider.execute();
		provider.startProgress();

		filter = new DefaultSimpleFilterPanel(tableModel, bundle.getString("teacherNamesHint"), "teacherName");
		buttonPanel = new ProjectButtonPanel();
		tablePanel = new ProjectTablePanel(tableModel, comboModel, filter, buttonPanel, preview);
		tablePanel.getSelectionModel().addListSelectionListener(new TableSelectionListener());

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(preview);
		add(splitPane);

		add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

		buttonPanel.activate(false);

		buttonPanel.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				preview.setEnabled(true);
				preview.setData(null);
				buttonPanel.activateSave(true);

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
				RemoveWorker w = new RemoveWorker(preview, tablePanel, buttonPanel);
				w.execute();
				w.startProgress();

			}
		});

		setVisible(true);

	}

	private class TableSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selected = tablePanel.getSelected();
			if (selected > -1) {
				new PreviewDataProvider(selected).execute();

			}
		}

	}

	private class DataProvider extends Worker<List<ProjectDTO>> {

		private Integer id;

		public DataProvider(Integer id) {
			super();
			this.id = id;
		}

		@Override
		protected List<ProjectDTO> doInBackground() throws Exception {

			if (id == null) return DelegateFactory.getTeacherDelegate().getTeacherProjects(null);
			return DelegateFactory.getTeacherDelegate().getTeacherProjects(id);

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

			preview.setSelected(selected);
			preview.setData(tableModel.get(selected));
			preview.setEnabled(true);
			buttonPanel.activate(true);
			stopProgress();
		}
	}

}