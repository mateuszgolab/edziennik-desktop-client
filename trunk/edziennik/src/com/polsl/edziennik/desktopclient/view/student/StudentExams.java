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
import com.polsl.edziennik.delegates.login.LoginManager;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.ExamTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.ExamTablePanel;
import com.polsl.edziennik.desktopclient.view.student.panels.preview.StudentExamPreviewPanel;
import com.polsl.edziennik.modelDTO.exam.ExamDTO;
import com.polsl.edziennik.modelDTO.grade.ExamTaskGradeDTO;

public class StudentExams extends JPanel {
	private JSplitPane splitPane;
	protected StudentExamPreviewPanel preview;
	protected ExamTablePanel tablePanel;
	protected ExamTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected ExamTableSelectionListener listener;

	public StudentExams() {
		setLayout(new BorderLayout());
		tableModel = new ExamTableModel();
		preview = new StudentExamPreviewPanel(bundle.getString("PreviewTitle"), tableModel);

		new DataProvider().execute();

		tablePanel = new ExamTablePanel(tableModel);
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

				if (tableModel.get(selected) != null) new PreviewProvider(selected).execute();

			}
		}

	}

	private class DataProvider extends Worker<List<ExamDTO>> {

		@Override
		protected List<ExamDTO> doInBackground() throws Exception {
			startProgress();
			return DelegateFactory.getCommonDelegate().getExams();

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

	// private class ExamGradeProvider extends Worker<ExamGradeDTO> {
	//
	// @Override
	// protected ExamGradeDTO doInBackground() throws Exception {
	// startProgress();
	// DelegateFactory.getCommonDelegate().getExamFinalGrade();
	//
	// }
	//
	// @Override
	// public void done() {
	// try {
	// preview.setExamGrade(get());
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ExecutionException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// preview.setEnabled(true);
	//
	// }
	//
	// }

	private class PreviewProvider extends Worker<List<ExamTaskGradeDTO>> {

		private Integer selected;

		public PreviewProvider(Integer selected) {
			this.selected = selected;
		}

		@Override
		protected List<ExamTaskGradeDTO> doInBackground() throws Exception {
			startProgress();
			// TODO : currentStudent

			// return
			// DelegateFactory.getCommonDelegate().getExamStudentGrades(tableModel.get(selected).getId(),
			// );
			return DelegateFactory.getCommonDelegate().getExamStudentGrades(tableModel.get(selected).getId(),
					LoginManager.getUser().getId());
		}

		@Override
		public void done() {
			try {
				preview.setExamGrades(get());
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
			stopProgress();
			// new ExamGradeProvider().execute();

		}
	}

}