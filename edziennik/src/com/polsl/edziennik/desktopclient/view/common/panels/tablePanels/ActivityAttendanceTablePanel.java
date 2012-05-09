package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.controller.utils.filters.ActivityAttendanceFilter;
import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;
import com.polsl.edziennik.desktopclient.model.tables.TableModel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;

public class ActivityAttendanceTablePanel extends ActivityTablePanel {

	public ActivityAttendanceTablePanel(TableModel<?> tableModel, ComboModel<?> comboBoxModel, DateFilterPanel filter,
			ButtonPanel buttonPanel, JPanel preview, String title) {
		super(tableModel, comboBoxModel, filter, buttonPanel, preview, title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runFilter(Long min, Long max, String s) {
		ActivityAttendanceFilter rf = new ActivityAttendanceFilter(min, max, s);
		sorter.setRowFilter(rf);

	}

}
