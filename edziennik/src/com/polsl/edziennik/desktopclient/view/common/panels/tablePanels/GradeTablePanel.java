package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import com.polsl.edziennik.desktopclient.model.tables.TableModel;

public class GradeTablePanel extends TablePanel {

	public GradeTablePanel(TableModel<?> tableModel) {
		super(tableModel, "");
		setColumnsWidth();
	}

	@Override
	public int getComboSelectedIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setColumnsWidth() {

		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);

	}

}
