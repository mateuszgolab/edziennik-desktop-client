package com.polsl.edziennik.desktopclient.controller.utils.workers;

import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.PreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.SimpleTablePanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.TablePanel;

public class SaveWorker extends Worker<Void> {

	private PreviewPanel preview;
	private TablePanel tablePanel;
	private ButtonPanel buttonPanel;
	private SimpleTablePanel simpleTablePanel;

	public SaveWorker(PreviewPanel preview, TablePanel tablePanel, ButtonPanel buttonPanel) {
		super("save");
		this.preview = preview;
		this.tablePanel = tablePanel;
		this.buttonPanel = buttonPanel;

	}

	public SaveWorker(PreviewPanel preview, SimpleTablePanel simpleTablePanel, ButtonPanel buttonPanel) {
		super("save");
		this.preview = preview;
		this.simpleTablePanel = simpleTablePanel;
		this.buttonPanel = buttonPanel;
	}

	@Override
	protected Void doInBackground() throws Exception {
		startProgress();
		preview.save();
		return null;
	}

	@Override
	public void done() {
		preview.setEnabled(false);
		buttonPanel.activate(false);
		if (tablePanel != null) tablePanel.clearSelection();
		if (simpleTablePanel != null) simpleTablePanel.clearSelection();
		stopProgress();
	}
}