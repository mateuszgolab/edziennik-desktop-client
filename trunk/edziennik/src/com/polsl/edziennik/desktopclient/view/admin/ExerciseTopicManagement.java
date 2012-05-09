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

import com.polsl.edziennik.delegates.CommonDelegate;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.RemoveWorker;
import com.polsl.edziennik.desktopclient.controller.utils.workers.SaveWorker;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.ExerciseTopicTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.admin.panels.preview.ExerciseTopicPreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ExerciseTopicButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.ExerciseTopicTablePanel;
import com.polsl.edziennik.modelDTO.exercise.ExerciseTopicDTO;

public class ExerciseTopicManagement extends JPanel {

	private JSplitPane splitPane;
	private ExerciseTopicPreviewPanel preview;
	private ExerciseTopicTablePanel tablePanel;
	private ExerciseTopicTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private ExerciseTopicButtonPanel buttonPanel;

	public ExerciseTopicManagement() {
		setLayout(new BorderLayout());

		tableModel = new ExerciseTopicTableModel();
		DataProvider p = new DataProvider();
		p.execute();
		p.startProgress();

		buttonPanel = new ExerciseTopicButtonPanel();
		preview = new ExerciseTopicPreviewPanel(bundle.getString("PreviewTitle"), tableModel);
		tablePanel = new ExerciseTopicTablePanel(tableModel, buttonPanel, preview);
		tablePanel.getSelectionModel().addListSelectionListener(new ExerciseTopicTableSelectionListener());

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(new JScrollPane(preview));
		add(splitPane);

		add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

		buttonPanel.activate(false);

		buttonPanel.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				preview.setData(null);
				buttonPanel.activateSave(true);
				preview.setEnabled(true);
				tablePanel.clearSelection();
				preview.setManual(false);

			}
		});

		buttonPanel.getSaveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SaveWorker save = new SaveWorker(preview, tablePanel, buttonPanel);
				save.execute();
				save.startProgress();

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

	private class ExerciseTopicTableSelectionListener implements ListSelectionListener {

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

	private class DataProvider extends Worker<List<ExerciseTopicDTO>> {

		@Override
		protected List<ExerciseTopicDTO> doInBackground() throws Exception {

			CommonDelegate factory = DelegateFactory.getCommonDelegate();
			return factory.getExerciseTopics();
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

}
