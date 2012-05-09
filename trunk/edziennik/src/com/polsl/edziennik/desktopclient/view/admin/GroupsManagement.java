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

import com.polsl.edziennik.delegates.AdminDelegate;
import com.polsl.edziennik.delegates.CommonDelegate;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.TeacherDelegate;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.GroupTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.admin.panels.GroupTablePanel;
import com.polsl.edziennik.desktopclient.view.admin.panels.preview.GroupPreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.GroupButtonPanel;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class GroupsManagement extends JPanel {
	private JSplitPane splitPane;
	private GroupPreviewPanel preview;
	private GroupTablePanel tablePanel;
	private GroupTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private GroupButtonPanel buttonPanel;
	private AdminDelegate delegate;

	public GroupsManagement() {
		setLayout(new BorderLayout());

		DataProvider dataProvider = new DataProvider("get");
		dataProvider.execute();
		dataProvider.startProgress();

		tableModel = new GroupTableModel();
		buttonPanel = new GroupButtonPanel();
		preview = new GroupPreviewPanel(bundle.getString("PreviewTitle"), tableModel);
		tablePanel = new GroupTablePanel(tableModel, buttonPanel, preview);
		tablePanel.getSelectionModel().addListSelectionListener(new TableSelectionListener());

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(preview);
		add(splitPane);

		add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

		preview.setEditable(true);

		buttonPanel.activate(false);

		buttonPanel.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				tablePanel.clearSelection();
				preview.setEnabled(true);
				preview.setData(null);
				buttonPanel.activateSave(true);

			}
		});

		buttonPanel.getSaveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SaveWorker worker = new SaveWorker("save");
				worker.execute();
				worker.startProgress();

			}
		});

		buttonPanel.getRemoveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				RemoveWorker worker = new RemoveWorker("delete");
				worker.execute();
			}
		});

		setVisible(true);

	}

	private class TableSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selected = tablePanel.getSelected();
			if (selected > -1) {
				if (tableModel.get(selected) != null) {
					StudentsDataProvider provider = new StudentsDataProvider("get", tableModel.get(selected).getId(),
							selected);
					provider.execute();
					provider.startProgress();
				} else {
					preview.setSelected(selected);
					preview.setData(tableModel.get(selected));
					preview.setEnabled(true);
					buttonPanel.activate(true);
				}

			}
		}

	}

	private class DataProvider extends Worker<List<GroupDTO>> {

		public DataProvider(String operationType) {
			super(operationType);
		}

		@Override
		protected List<GroupDTO> doInBackground() throws Exception {

			TeacherDelegate teacher = DelegateFactory.getTeacherDelegate();
			return teacher.getAllGroups();
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

	private class RemoveWorker extends Worker<Void> {

		public RemoveWorker(String operationType) {
			super(operationType);
		}

		@Override
		protected Void doInBackground() throws Exception {
			if (tableModel.get(tablePanel.getSelected()) != null) try {
				delegate = DelegateFactory.getAdminDelegate();
				if (delegate != null) {
					delegate.deleteGroup((tableModel.get(tablePanel.getSelected())).getId());
				}
			} catch (DelegateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			tableModel.remove(tablePanel.getSelected());

			return null;
		}

		@Override
		public void done() {

			stopProgress();
			preview.clear();
			preview.setEnabled(false);
			buttonPanel.activate(false);
			tablePanel.clearSelection();
		}
	}

	private class SaveWorker extends Worker<Void> {

		public SaveWorker(String operationType) {
			super(operationType);
		}

		@Override
		protected Void doInBackground() throws Exception {
			try {
				preview.saveGroup();
			} catch (DelegateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return null;
		}

		@Override
		public void done() {
			stopProgress();
			preview.setEnabled(false);
			buttonPanel.activate(false);
			tablePanel.clearSelection();
		}
	}

	private class StudentsDataProvider extends Worker<List<StudentDTO>> {

		private Integer groupId;
		private Integer selected;

		public StudentsDataProvider(String operationType, Integer groupId, Integer selected) {
			super(operationType);
			this.groupId = groupId;
			this.selected = selected;
		}

		@Override
		protected List<StudentDTO> doInBackground() throws Exception {
			CommonDelegate delegate = DelegateFactory.getCommonDelegate();
			return delegate.getStudentsFromGroup(groupId);

		}

		@Override
		public void done() {
			stopProgress();
			try {
				preview.setComboModel(get());
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
}
