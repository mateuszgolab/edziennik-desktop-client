package com.polsl.edziennik.desktopclient.view.admin;

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
import com.polsl.edziennik.desktopclient.controller.utils.workers.RemoveWorker;
import com.polsl.edziennik.desktopclient.controller.utils.workers.SaveWorker;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.ExamTaskTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.admin.panels.preview.AdminExamTaskPreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ExamTaskButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.ExamTaskTablePanel;
import com.polsl.edziennik.modelDTO.exam.ExamDTO;
import com.polsl.edziennik.modelDTO.exam.ExamTaskDTO;
import com.polsl.edziennik.modelDTO.exam.ExamTaskTypeDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public class ExamTaskManagement extends JPanel {
	private JSplitPane splitPane;
	protected AdminExamTaskPreviewPanel preview;
	protected ExamTaskTablePanel tablePanel;
	protected ExamTaskTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected Integer examId;
	private ExamTaskButtonPanel buttonPanel;
	private Integer selected;
	protected ExamDTO exam;

	public ExamTaskManagement(ExamDTO e) {
		this.exam = e;
		if (exam != null) this.examId = exam.getId();
		setLayout(new BorderLayout());
		tableModel = new ExamTaskTableModel();
		preview = new AdminExamTaskPreviewPanel(bundle.getString("PreviewTitle"), tableModel);

		new DataProvider().execute();

		tablePanel = new ExamTaskTablePanel(tableModel);
		tablePanel.getSelectionModel().addListSelectionListener(new TableSelectionListener());

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(new JScrollPane(preview));
		add(splitPane);

		setVisible(true);

		selected = null;

		buttonPanel = new ExamTaskButtonPanel();
		add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
		buttonPanel.activate(false);

		buttonPanel.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				tablePanel.clearSelection();
				selected = null;
				new PreviewDataProvider().execute();

			}
		});

		buttonPanel.getSaveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new SaveWorker(preview, tablePanel, buttonPanel).execute();
				selected = null;
			}
		});

		buttonPanel.getRemoveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new RemoveWorker(preview, tablePanel, buttonPanel).execute();
				selected = null;
			}
		});

		buttonPanel.activate(false);
	}

	private class TableSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			selected = tablePanel.getSelected();
			if (selected > -1) {

				preview.setSelected(selected);
				new PreviewDataProvider().execute();
				buttonPanel.activate(true);

			}
		}

	}

	private class DataProvider extends Worker<List<ExamTaskDTO>> {

		@Override
		protected List<ExamTaskDTO> doInBackground() throws Exception {
			startProgress();
			return DelegateFactory.getCommonDelegate().getExamTasks(examId);

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

	private class PreviewDataProvider extends Worker<List<TeacherDTO>> {

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
			stopProgress();
			new ExamTaskTypesDataProvider().execute();
		}
	}

	private class ExamTaskTypesDataProvider extends Worker<List<ExamTaskTypeDTO>> {

		@Override
		protected List<ExamTaskTypeDTO> doInBackground() throws Exception {
			startProgress();
			return DelegateFactory.getCommonDelegate().getExamTaskTypes();

		}

		@Override
		public void done() {

			try {
				preview.setExamTaskTypesModel(get());

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (selected != null)
				preview.setData(tableModel.get(selected), exam);
			else
				preview.setData(null, exam);
			preview.setEnabled(true);
			buttonPanel.activateSave(true);
			stopProgress();

		}
	}

}
