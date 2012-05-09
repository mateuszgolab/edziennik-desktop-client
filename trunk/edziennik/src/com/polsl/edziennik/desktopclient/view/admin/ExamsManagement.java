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
import com.polsl.edziennik.desktopclient.model.tables.ExamTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.admin.panels.preview.AdminExamPreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ExamButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.ExamTablePanel;
import com.polsl.edziennik.modelDTO.exam.ExamDTO;

public class ExamsManagement extends JPanel {
	private JSplitPane splitPane;
	protected AdminExamPreviewPanel preview;
	protected ExamTablePanel tablePanel;
	protected ExamTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private ExamButtonPanel buttonPanel;

	public ExamsManagement() {

		setLayout(new BorderLayout());
		tableModel = new ExamTableModel();
		preview = new AdminExamPreviewPanel(bundle.getString("PreviewTitle"), tableModel);

		new DataProvider().execute();

		tablePanel = new ExamTablePanel(tableModel);
		tablePanel.getSelectionModel().addListSelectionListener(new ExamTableSelectionListener());

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(new JScrollPane(preview));
		add(splitPane);

		setVisible(true);

		buttonPanel = new ExamButtonPanel();

		add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

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

				new SaveWorker(preview, tablePanel, buttonPanel).execute();
			}
		});

		buttonPanel.getRemoveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new RemoveWorker(preview, tablePanel, buttonPanel).execute();

			}
		});

		buttonPanel.activate(false);
	}

	private class ExamTableSelectionListener implements ListSelectionListener {

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
