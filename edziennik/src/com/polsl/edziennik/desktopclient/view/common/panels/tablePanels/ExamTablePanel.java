package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import com.polsl.edziennik.desktopclient.model.tables.TableModel;

public class ExamTablePanel extends SimpleTablePanel {

	public ExamTablePanel(TableModel<?> tableModel) {
		super(tableModel, bundle.getString("examsHint"));
		setColumnWidths();
	}

	@Override
	public void setColumnWidths() {

		if (table.getColumnModel() == null) return;

		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		for (int i = 1; i <= 4; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(100);

		}
		table.getColumnModel().getColumn(2).setPreferredWidth(140);
	}

}
