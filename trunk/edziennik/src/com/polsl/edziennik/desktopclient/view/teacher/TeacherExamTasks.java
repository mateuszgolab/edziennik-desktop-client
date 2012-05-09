package com.polsl.edziennik.desktopclient.view.teacher;

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
import com.polsl.edziennik.delegates.login.LoginManager;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.ExamTaskTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.ExamTaskTablePanel;
import com.polsl.edziennik.desktopclient.view.teacher.panels.TeacherExamTaskPreviewPanel;
import com.polsl.edziennik.modelDTO.exam.ExamDTO;
import com.polsl.edziennik.modelDTO.exam.ExamTaskDTO;

public class TeacherExamTasks extends JPanel {
	private JSplitPane splitPane;
	protected TeacherExamTaskPreviewPanel preview;
	protected ExamTaskTablePanel tablePanel;
	protected ExamTaskTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected ExamTableSelectionListener listener;
	protected Integer examId;
	protected ExamDTO exam;

	public TeacherExamTasks(ExamDTO e) {
		this.exam = e;
		if (exam != null) this.examId = exam.getId();
		setLayout(new BorderLayout());
		tableModel = new ExamTaskTableModel();
		preview = new TeacherExamTaskPreviewPanel(bundle.getString("PreviewTitle"), tableModel);

		new DataProvider().execute();

		tablePanel = new ExamTaskTablePanel(tableModel);
		listener = new ExamTableSelectionListener();
		tablePanel.getSelectionModel().addListSelectionListener(listener);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(new JScrollPane(preview));
		add(splitPane);

		setVisible(true);

	}

	private class ExamTableSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selected = tablePanel.getSelected();
			if (selected > -1) {

				preview.setSelected(selected);
				preview.setData(tableModel.get(selected), exam);
				preview.setEnabled(true);
			}
		}
	}

	private class DataProvider extends Worker<List<ExamTaskDTO>> {

		@Override
		protected List<ExamTaskDTO> doInBackground() throws Exception {
			startProgress();
			// TODO : current teacher

			// return
			// DelegateFactory.getTeacherDelegate().getTeacherExamTasks(7,
			// examId);
			return DelegateFactory.getTeacherDelegate().getTeacherExamTasks(7, LoginManager.getUser().getId());

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

}