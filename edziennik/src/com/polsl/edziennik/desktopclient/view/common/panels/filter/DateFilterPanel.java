package com.polsl.edziennik.desktopclient.view.common.panels.filter;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXDatePicker;

import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;

public class DateFilterPanel extends FilterPanel {

	private JXDatePicker dateFrom;
	private JXDatePicker dateTo;
	private JButton clear;
	private IButton button;

	public DateFilterPanel(TableModel tableModel, ComboModel<?> comboModel, String hint, String comboHint,
			String comboLabel) {
		super(tableModel, comboModel, hint, comboHint, comboLabel);
	}

	public DateFilterPanel(TableModel tableModel) {
		super(tableModel);

	}

	public DateFilterPanel(TableModel tableModel, ComboModel<?> comboModel, String comboHint, String comboLabel,
			ComboModel<?> comboModel2, String comboHint2, String comboLabel2) {
		super(tableModel, comboModel, comboHint, comboLabel, comboModel2, comboHint2, comboLabel2);
	}

	@Override
	public void setFilter(int size) {
		filter = new JPanel();
		dateFrom = new JXDatePicker();
		dateFrom.setToolTipText(bundle.getString("dateFromHint"));
		dateTo = new JXDatePicker();
		dateTo.setToolTipText(bundle.getString("dateToHint"));

		ILabel label = factory.createLabel();
		button = factory.createIconButton();

		filter.add(label.getLabel("dateFrom"));
		filter.add(dateFrom);
		filter.add(label.getLabel("dateTo"));
		filter.add(dateTo);

		clear = button.getButton("clearIcon", "clearHint");

		icon = button.getButton("SearchIcon", "filterHint");
		filter.add(clear);
		filter.add(icon);

	}

	@Override
	public String getText() {
		return null;
	}

	public Long getDateFrom() {
		if (dateFrom.getDate() == null) return 0L;
		return dateFrom.getDate().getTime();
	}

	public Long getDateTo() {
		if (dateTo.getDate() == null) return Long.MAX_VALUE;
		return dateTo.getDate().getTime();
	}

	public JButton getClear() {
		return clear;
	}

	@Override
	public void reset() {
		dateFrom.setDate(null);
		dateTo.setDate(null);
		if (comboBox != null) {
			comboBox.setSelectedItem(null);
			comboBox.updateUI();
		}

		if (comboBox2 != null) {
			comboBox2.setSelectedItem(null);
			comboBox2.updateUI();
		}

	}

}
