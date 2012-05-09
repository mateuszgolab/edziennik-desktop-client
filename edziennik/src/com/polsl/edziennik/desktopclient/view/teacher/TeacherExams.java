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
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.ExamTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.ExamTablePanel;
import com.polsl.edziennik.desktopclient.view.teacher.panels.TeacherExamPreviewPanel;
import com.polsl.edziennik.modelDTO.exam.ExamDTO;

public class TeacherExams extends JPanel {
	private JSplitPane splitPane;
	protected TeacherExamPreviewPanel preview;
	protected ExamTablePanel tablePanel;
	protected ExamTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected ExamTableSelectionListener listener;

	public TeacherExams() {
		setLayout(new BorderLayout());
		tableModel = new ExamTableModel();
		preview = new TeacherExamPreviewPanel(bundle.getString("PreviewTitle"));

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

				preview.setSelected(selected);
				preview.setData(tableModel.get(selected));
				preview.setEnabled(true);

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

}