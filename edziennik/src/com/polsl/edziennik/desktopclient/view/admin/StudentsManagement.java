package com.polsl.edziennik.desktopclient.view.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.polsl.edziennik.delegates.AdminDelegate;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.TeacherDelegate;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.comboBoxes.GroupComboBoxModel;
import com.polsl.edziennik.desktopclient.model.tables.GroupTableModel;
import com.polsl.edziennik.desktopclient.model.tables.StudentTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.button.StudentButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DefaultFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.StudentPreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.StudentTablePanel;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class StudentsManagement extends JPanel {
	private StudentButtonPanel buttonPanel;
	private JSplitPane splitPane;
	private StudentPreviewPanel preview;
	private StudentTablePanel tablePanel;
	private GroupComboBoxModel comboModel;
	private GroupComboBoxModel comboPreviewModel;
	private StudentTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private DefaultFilterPanel filter;
	private GroupsDataProvider groupsProvider;

	public StudentsManagement() {
		setLayout(new BorderLayout());

		tableModel = new StudentTableModel();
		comboModel = new GroupComboBoxModel();
		comboPreviewModel = new GroupComboBoxModel();

		filter = new DefaultFilterPanel(tableModel, comboModel, bundle.getString("studentNamesHint"), "lastName",
				bundle.getString("groupHint"), "group");

		DataProvider dataProvider = new DataProvider("get");
		groupsProvider = new GroupsDataProvider(comboModel, filter.getComboBox(), comboPreviewModel);
		dataProvider.execute();
		dataProvider.startProgress();
		groupsProvider.execute();
		groupsProvider.startProgress();

		buttonPanel = new StudentButtonPanel();
		preview = new StudentPreviewPanel(bundle.getString("PreviewTitle"), tableModel, comboPreviewModel);
		tablePanel = new StudentTablePanel(tableModel, comboModel, filter, bundle.getString("StudentFrameTitle"),
				buttonPanel, preview);
		tablePanel.getSelectionModel().addListSelectionListener(new StudentTableSelectionListener());

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

				SaveWorker worker = new SaveWorker();
				worker.execute();

			}
		});

		buttonPanel.getRemoveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				RemoveWorker worker = new RemoveWorker();
				worker.execute();

			}
		});

		setVisible(true);

	}

	private class StudentTableSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selected = tablePanel.getSelected();
			if (selected > -1) {
				preview.setSelected(selected);
				preview.setData(tableModel.get(selected));
				preview.setEnabled(true);
				buttonPanel.activate(true);

			}
		}

	}

	private class DataProvider extends Worker<List<StudentDTO>> {

		public DataProvider(String operationType) {
			super(operationType);
		}

		@Override
		protected List<StudentDTO> doInBackground() throws Exception {

			TeacherDelegate teacher = DelegateFactory.getTeacherDelegate();
			return teacher.getAllStudentsWithGroups();
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

	private class GroupsDataProvider extends Worker<List<GroupDTO>> {

		private GroupComboBoxModel comboModel;
		private GroupTableModel tableModel;
		private GroupComboBoxModel comboPreviewModel;
		private JComboBox comboBox;
		private JComboBox comboBoxPreview;

		public GroupsDataProvider(GroupTableModel tableModel) {
			super("get");
			this.tableModel = tableModel;
		}

		public GroupsDataProvider(GroupComboBoxModel comboModel, JComboBox comboBox) {
			super("get");
			this.comboModel = comboModel;
			this.comboBox = comboBox;
		}

		public GroupsDataProvider(GroupComboBoxModel comboModel, JComboBox comboBox,
				GroupComboBoxModel comboPreviewModel) {
			super("get");
			this.comboModel = comboModel;
			this.comboBox = comboBox;
			this.comboPreviewModel = comboPreviewModel;
		}

		public GroupsDataProvider(GroupComboBoxModel comboModel, GroupComboBoxModel comboPreviewModel,
				JComboBox comboBox, JComboBox comboBoxPreview) {
			super("get");
			this.comboModel = comboModel;
			this.comboPreviewModel = comboPreviewModel;
			this.comboBox = comboBox;
			this.comboBoxPreview = comboBoxPreview;
		}

		@Override
		protected List<GroupDTO> doInBackground() {

			startProgress();
			TeacherDelegate teacher;
			try {
				teacher = DelegateFactory.getTeacherDelegate();
				return teacher.getAllGroups();
			} catch (DelegateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		public void done() {

			stopProgress();
			try {
				if (tableModel != null) {
					tableModel.setModel(get());
				} else if (comboModel != null) {
					comboModel.setModel(new ArrayList<GroupDTO>(get()));
					GroupDTO g = new GroupDTO();
					g.setName("Wszystkie");
					g.setId(-1);
					comboModel.add(g);

					comboModel.setSelectedItem(g);
					if (comboBox != null) comboBox.repaint();
				}
				if (comboPreviewModel != null) {
					comboPreviewModel.setModel(get());
					if (comboBoxPreview != null) comboBoxPreview.repaint();
				}

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

		public RemoveWorker() {
			super("delete");
		}

		@Override
		protected Void doInBackground() throws Exception {

			startProgress();

			if (tableModel.get(tablePanel.getSelected()) != null) try {
				AdminDelegate delegate = DelegateFactory.getAdminDelegate();
				if (delegate != null) {
					delegate.removeStudent((tableModel.get(tablePanel.getSelected())).getId());
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
			buttonPanel.activate(false);
			preview.setEnabled(false);
		}
	}

	private class SaveWorker extends Worker<Void> {

		public SaveWorker() {
			super("set");
		}

		@Override
		protected Void doInBackground() {
			try {
				startProgress();
				preview.saveStudent();

			} catch (DelegateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return null;
		}

		@Override
		public void done() {
			stopProgress();
			buttonPanel.activate(false);
			preview.setEnabled(false);
		}
	}
}
