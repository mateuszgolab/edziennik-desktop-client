package com.polsl.edziennik.desktopclient.view.common;

import java.awt.BorderLayout;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jidesoft.utils.SwingWorker;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.TeacherDelegate;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.model.tables.TeacherTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.TeacherTablePanel;
import com.polsl.edziennik.desktopclient.view.teacher.panels.TeacherPreviewPanel;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public class Teachers extends JPanel {
	private JSplitPane splitPane;
	private TeacherPreviewPanel preview;
	private TeacherTablePanel tablePanel;
	private TeacherTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);

	public Teachers() {
		setLayout(new BorderLayout());

		DataProvider dataProvider = new DataProvider();
		dataProvider.execute();

		tableModel = new TeacherTableModel();

		preview = new TeacherPreviewPanel(bundle.getString("PreviewTitle"), tableModel);
		tablePanel = new TeacherTablePanel(tableModel, null, preview);
		tablePanel.getSelectionModel().addListSelectionListener(new TableSelectionListener());

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(preview);
		add(splitPane);

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

			}
		}

	}

	private class DataProvider extends SwingWorker<List<TeacherDTO>, Integer> {

		@Override
		protected List<TeacherDTO> doInBackground() throws Exception {

			TeacherDelegate teacher = DelegateFactory.getTeacherDelegate();
			return teacher.getAllTeachers();
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

		}

	}
}