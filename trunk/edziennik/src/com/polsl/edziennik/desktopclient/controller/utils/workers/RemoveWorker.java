package com.polsl.edziennik.desktopclient.controller.utils.workers;

import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.PreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.SimpleTablePanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.TablePanel;

public class RemoveWorker extends Worker<Void> {
	private PreviewPanel preview;
	private TablePanel tablePanel;
	private SimpleTablePanel simpleTablePanel;
	private ButtonPanel buttonPanel;

	public RemoveWorker(PreviewPanel preview, TablePanel tablePanel, ButtonPanel buttonPanel) {
		super("delete");
		this.preview = preview;
		this.tablePanel = tablePanel;
		this.buttonPanel = buttonPanel;
	}

	public RemoveWorker(PreviewPanel preview, SimpleTablePanel simpleTablePanel, ButtonPanel buttonPanel) {
		super("delete");
		this.preview = preview;
		this.buttonPanel = buttonPanel;
		this.simpleTablePanel = simpleTablePanel;
	}

	@Override
	protected Void doInBackground() throws Exception {

		startProgress();
		if (tablePanel != null) preview.setSelected(tablePanel.getSelected());
		if (simpleTablePanel != null) preview.setSelected(simpleTablePanel.getSelected());
		preview.delete();
		return null;
	}

	@Override
	public void done() {

		preview.clear();
		preview.setEnabled(false);
		buttonPanel.activate(false);
		if (tablePanel != null) tablePanel.clearSelection();
		if (simpleTablePanel != null) simpleTablePanel.clearSelection();
		stopProgress();
	}
}