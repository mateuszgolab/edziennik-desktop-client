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
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.TeacherDelegate;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.TeacherTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.button.TeacherButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.TeacherTablePanel;
import com.polsl.edziennik.desktopclient.view.teacher.panels.TeacherPreviewPanel;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public class TeacherManagement extends JPanel {
	private JSplitPane splitPane;
	private TeacherPreviewPanel preview;
	private TeacherTablePanel tablePanel;
	private TeacherTableModel tableModel;
	private ResourceBundle menu = LangManager.getResource(Properties.Menu);
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private TeacherButtonPanel buttonPanel;

	public TeacherManagement() {
		setLayout(new BorderLayout());

		DataProvider dataProvider = new DataProvider();
		dataProvider.execute();
		dataProvider.startProgress();

		tableModel = new TeacherTableModel();

		buttonPanel = new TeacherButtonPanel();
		preview = new TeacherPreviewPanel(bundle.getString("PreviewTitle"), tableModel);
		tablePanel = new TeacherTablePanel(tableModel, buttonPanel, preview);
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
				tablePanel.clearSelection();

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
				worker.startProgress();

			}
		});

		preview.setEditable(true);

		setVisible(true);

	}

	private class TableSelectionListener implements ListSelectionListener {

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

	private class DataProvider extends Worker<List<TeacherDTO>> {

		public DataProvider() {
			super("get");
		}

		@Override
		protected List<TeacherDTO> doInBackground() throws Exception {

			TeacherDelegate teacher = DelegateFactory.getTeacherDelegate();
			return teacher.getAllTeachers();
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
				AdminDelegate delegate = DelegateFactory.getAdminDelegate();
				if (delegate != null) {
					delegate.removeTeacher((tableModel.get(tablePanel.getSelected())).getId());
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
				preview.saveTeacher();

			} catch (DelegateException e1) {
				e1.printStackTrace();
			}

			return null;
		}

		@Override
		public void done() {
			stopProgress();
			buttonPanel.activate(false);
			preview.setEnabled(false);
			tablePanel.clearSelection();
		}
	}
}
