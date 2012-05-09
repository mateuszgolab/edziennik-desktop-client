package com.polsl.edziennik.desktopclient.view.common;

import java.awt.BorderLayout;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.polsl.edziennik.delegates.CommonDelegate;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.HappyHoursTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.dialogs.ProgressDialog;
import com.polsl.edziennik.desktopclient.view.common.panels.button.DefaultButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.HappyHoursPreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.HappyHoursTablePanel;
import com.polsl.edziennik.modelDTO.happyhours.HappyHoursDTO;

public class HappyHours extends JPanel {
	protected ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected HappyHoursTableModel tableModel;
	protected HappyHoursPreviewPanel preview;
	protected HappyHoursTablePanel tablePanel;
	private JSplitPane splitPane;
	protected DefaultButtonPanel buttonPanel;
	ProgressDialog dialog;

	public HappyHours() {
		setLayout(new BorderLayout());

		DataProvider provider = new DataProvider("get");
		provider.execute();
		provider.startProgress();

		tableModel = new HappyHoursTableModel();

		preview = new HappyHoursPreviewPanel(bundle.getString("PreviewTitle"), tableModel);
		tablePanel = new HappyHoursTablePanel(tableModel, preview);
		tablePanel.getSelectionModel().addListSelectionListener(new TableSelectionListener());

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(preview);
		add(splitPane);

		setVisible(true);
		preview.setEditable(false);

	}

	private class TableSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selected = tablePanel.getSelected();
			if (selected > -1) {
				preview.setSelected(selected);
				preview.setData(tableModel.get(selected));
				preview.setEnabled(true);

				if (buttonPanel != null) {
					buttonPanel.activate(true);
					preview.setEditable(true);
				} else
					preview.setEditable(false);

			}
		}

	}

	private class DataProvider extends Worker<List<HappyHoursDTO>> {

		public DataProvider(String operationType) {
			super(operationType);
		}

		public DataProvider(ProgressDialog dialog) {
			super(dialog);
		}

		@Override
		protected List<HappyHoursDTO> doInBackground() throws Exception {

			CommonDelegate delegate = DelegateFactory.getCommonDelegate();
			return delegate.getHappyHours();
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