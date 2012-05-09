package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.controller.utils.filters.LabActivityAttendanceFilter;
import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;
import com.polsl.edziennik.desktopclient.model.tables.TableModel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;

public class LabActivityAttendanceTablePanel extends LabActivityTablePanel {

	public LabActivityAttendanceTablePanel(TableModel<?> tableModel, ComboModel<?> comboBoxModel,
			DateFilterPanel filter, ButtonPanel buttonPanel, JPanel preview) {
		super(tableModel, comboBoxModel, filter, buttonPanel, preview);
		setColumnWidths();
	}

	@Override
	public void runFilter(Long min, Long max, String s) {
		LabActivityAttendanceFilter rf = new LabActivityAttendanceFilter(min, max, s);
		sorter.setRowFilter(rf);

	}

	@Override
	public void setColumnWidths() {

		if (table.getColumnModel() == null) return;

		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		for (int i = 1; i <= 4; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(130);
		}
		table.getColumnModel().getColumn(2).setPreferredWidth(60);

	}

}
