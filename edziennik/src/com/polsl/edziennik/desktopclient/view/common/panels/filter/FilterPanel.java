package com.polsl.edziennik.desktopclient.view.common.panels.filter;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;

public abstract class FilterPanel extends SimpleFilterPanel {

	protected JComboBox comboBox;
	protected JLabel boxLabel;
	protected JComboBox comboBox2;
	protected JLabel boxLabel2;

	public FilterPanel(TableModel tableModel, ComboModel<?> comboModel, String hint, String comboHint) {
		this.hint = hint;

		comboBox = new JComboBox(comboModel);
		Dimension d = comboBox.getPreferredSize();
		d.width = 150;
		comboBox.setPreferredSize(d);
		comboBox.setToolTipText(comboHint);

		setLayout(new BorderLayout());

		add(comboBox, BorderLayout.NORTH);

		setFilter(15);
		add(filter, BorderLayout.CENTER);

	}

	public FilterPanel(TableModel tableModel, ComboModel<?> comboModel, String hint, String label, String comboHint,
			String comboLabel) {
		this.hint = hint;
		this.labelName = label;
		Label = factory.createLabel();

		boxLabel = Label.getLabel(comboLabel);

		comboBox = new JComboBox(comboModel);
		Dimension d = comboBox.getPreferredSize();
		d.width = 100;
		comboBox.setPreferredSize(d);
		comboBox.setToolTipText(comboHint);

		setLayout(new BorderLayout());

		filter = new JPanel();
		filter.add(boxLabel);
		filter.add(comboBox);

		setFilter(10);
		add(filter, BorderLayout.NORTH);

	}

	public FilterPanel(TableModel tableModel, ComboModel<?> comboModel, String hint, String comboHint, String comboLabel) {
		this.hint = hint;
		Label = factory.createLabel();

		boxLabel = Label.getLabel(comboLabel);

		comboBox = new JComboBox(comboModel);
		Dimension d = comboBox.getPreferredSize();
		d.width = 150;
		comboBox.setPreferredSize(d);
		comboBox.setToolTipText(comboHint);

		setLayout(new BorderLayout());

		JPanel tmp = new JPanel();
		tmp.add(boxLabel);
		tmp.add(comboBox);
		add(tmp, BorderLayout.NORTH);

		setFilter(15);
		if (filter != null) add(filter, BorderLayout.CENTER);

	}

	// 2x combo
	public FilterPanel(TableModel tableModel, ComboModel<?> combo1Model, String combo1Hint, String combo1Label,
			ComboModel<?> combo2Model, String combo2Hint, String combo2Label) {
		Label = factory.createLabel();

		boxLabel = Label.getLabel(combo1Label);
		comboBox = new JComboBox(combo1Model);
		Dimension d = comboBox.getPreferredSize();
		d.width = 150;
		comboBox.setPreferredSize(d);
		comboBox.setToolTipText(combo1Hint);

		boxLabel2 = Label.getLabel(combo2Label);
		comboBox2 = new JComboBox(combo2Model);
		Dimension d2 = comboBox2.getPreferredSize();
		d2.width = 100;
		comboBox2.setPreferredSize(d2);
		comboBox2.setToolTipText(combo1Hint);

		setLayout(new BorderLayout());

		JPanel tmp = new JPanel();
		tmp.add(boxLabel);
		tmp.add(comboBox);
		tmp.add(boxLabel2);
		tmp.add(comboBox2);
		add(tmp, BorderLayout.NORTH);

		setFilter(15);
		if (filter != null) add(filter, BorderLayout.CENTER);

	}

	public FilterPanel(TableModel model) {
		setLayout(new BorderLayout());
		setFilter(15);
		if (filter != null) add(filter, BorderLayout.NORTH);
	}

	public int getComboSelectedIndex() {
		return comboBox.getSelectedIndex();
	}

	public JComboBox getComboBox() {
		return comboBox;
	}

	public JComboBox getComboBox2() {
		return comboBox2;
	}

	@Override
	public abstract void setFilter(int size);

	@Override
	public abstract String getText();

	@Override
	public JButton getIcon() {
		return icon;
	}

	public void refresh() {
		if (comboBox != null) {
			comboBox.updateUI();
		}

		if (comboBox2 != null) {
			comboBox2.updateUI();
		}
	}

	public void reset() {
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