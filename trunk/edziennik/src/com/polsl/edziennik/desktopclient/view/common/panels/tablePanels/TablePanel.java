package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import com.jidesoft.swing.JideScrollPane;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;
import com.polsl.edziennik.desktopclient.model.tables.TableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.FilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.SimpleFilterPanel;

public abstract class TablePanel extends JPanel {
	private JScrollPane scrollPane;
	protected JTable table;
	protected SimpleFilterPanel simpleFilter;
	protected FilterPanel filter;
	protected ComboModel<?> comboBoxModel;
	protected TableModel<?> tableModel;
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected String hint;
	protected String hint2;
	protected TableRowSorter<TableModel<?>> sorter;
	protected int filterPosition;
	protected int comboFilterPosition;

	public TablePanel(String title, TableModel<?> tableModel, ComboModel<?> comboBoxModel, String hint,
			final FilterPanel filter, final ButtonPanel buttonPanel, final JPanel preview) {
		super(new BorderLayout());
		this.tableModel = tableModel;
		this.comboBoxModel = comboBoxModel;
		this.hint = hint;
		this.filter = filter;
		this.simpleFilter = null;

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		table = new JTable(tableModel);
		sorter = new TableRowSorter<TableModel<?>>(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		table.setRowSorter(sorter);

		scrollPane = new JideScrollPane(table);

		add(scrollPane, BorderLayout.CENTER);

		if (filter != null) {
			add(filter, BorderLayout.NORTH);
			filter.getIcon().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (filter.getComboBox().getSelectedItem() != null
							&& "Wszystkie".compareTo(filter.getComboBox().getSelectedItem().toString()) != 0)
						runFilter(filter.getText(), filter.getComboBox().getSelectedItem().toString());
					else
						runFilter(filter.getText());

					if (buttonPanel != null) {
						buttonPanel.activate(false);
					}

					if (preview != null) {
						preview.setEnabled(false);
						preview.disable();
					}

					clearSelection();
				}
			});
		}

	}

	// dateFilter
	public TablePanel(String title, TableModel<?> tableModel, String hint, final DateFilterPanel dateFilter,
			final ButtonPanel buttonPanel, final JPanel preview) {
		super(new BorderLayout());
		this.tableModel = tableModel;
		this.hint = hint;
		this.filter = dateFilter;

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		table = new JTable(tableModel);
		sorter = new TableRowSorter<TableModel<?>>(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		table.setRowSorter(sorter);

		scrollPane = new JideScrollPane(table);

		add(scrollPane, BorderLayout.CENTER);

		if (dateFilter != null) {
			add(dateFilter, BorderLayout.NORTH);
			dateFilter.getIcon().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					runFilter(dateFilter.getDateFrom(), dateFilter.getDateTo());

					if (buttonPanel != null) {
						buttonPanel.activate(false);
					}

					if (preview != null) {
						preview.setEnabled(false);
						preview.disable();
					}

					clearSelection();
				}
			});

			dateFilter.getClear().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dateFilter.reset();
				}
			});
		}

	}

	// dateFilter + combo
	public TablePanel(String title, TableModel<?> tableModel, ComboModel<?> comboBoxModel, String hint,
			final DateFilterPanel dateFilter, final ButtonPanel buttonPanel, final JPanel preview) {
		super(new BorderLayout());
		this.tableModel = tableModel;
		this.comboBoxModel = comboBoxModel;
		this.hint = hint;
		this.filter = dateFilter;

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		table = new JTable(tableModel);
		sorter = new TableRowSorter<TableModel<?>>(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		table.setRowSorter(sorter);

		scrollPane = new JideScrollPane(table);

		add(scrollPane, BorderLayout.CENTER);

		if (dateFilter != null) {
			add(dateFilter, BorderLayout.NORTH);
			dateFilter.getIcon().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (filter.getComboBox() != null) {
						if (filter.getComboBox().getSelectedItem() != null)
							runFilter(dateFilter.getDateFrom(), dateFilter.getDateTo(), filter.getComboBox()
									.getSelectedItem().toString());
						else
							runFilter(dateFilter.getDateFrom(), dateFilter.getDateTo(), null);

					}

					if (buttonPanel != null) {
						buttonPanel.activate(false);
					}

					if (preview != null) {
						preview.setEnabled(false);
						preview.disable();
					}

					clearSelection();
				}
			});

			dateFilter.getClear().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dateFilter.reset();
					if (buttonPanel != null) {
						buttonPanel.activate(false);
					}

					if (preview != null) {
						preview.setEnabled(false);
						preview.disable();
					}

					clearSelection();
				}
			});
		}

	}

	public TablePanel(String title, TableModel<?> tableModel, String hint, SimpleFilterPanel simpleFilter,
			final ButtonPanel buttonPanel, final JPanel preview) {
		super(new BorderLayout());
		this.tableModel = tableModel;
		this.comboBoxModel = comboBoxModel;
		this.hint = hint;
		this.filter = null;
		this.simpleFilter = simpleFilter;

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		sorter = new TableRowSorter<TableModel<?>>(this.tableModel);
		table = new JTable(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		scrollPane = new JideScrollPane(table);

		add(scrollPane, BorderLayout.CENTER);

		if (simpleFilter != null) {
			if (simpleFilter != null) add(simpleFilter, BorderLayout.NORTH);

			simpleFilter.getIcon().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					runFilter(TablePanel.this.simpleFilter.getText());

					if (buttonPanel != null) {
						buttonPanel.activate(false);
					}

					if (preview != null) {
						preview.setEnabled(false);
						preview.disable();
					}

					clearSelection();
				}
			});
		}

	}

	// date + 2x combo
	public TablePanel(String title, TableModel<?> tableModel, final DateFilterPanel dateFilter,
			final ButtonPanel buttonPanel, final JPanel preview) {
		super(new BorderLayout());
		this.tableModel = tableModel;
		this.filter = dateFilter;
		this.simpleFilter = null;

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		sorter = new TableRowSorter<TableModel<?>>(tableModel);
		table = new JTable(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		scrollPane = new JideScrollPane(table);
		table.setRowSorter(sorter);

		add(scrollPane, BorderLayout.CENTER);

		if (filter != null) {
			add(filter, BorderLayout.NORTH);
			dateFilter.getIcon().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (filter.getComboBox() != null && filter.getComboBox2() != null)
						if (filter.getComboBox().getSelectedItem() != null)
							runFilter(dateFilter.getDateFrom(), dateFilter.getDateTo(), filter.getComboBox()
									.getSelectedItem().toString(), (String) filter.getComboBox2().getSelectedItem());
						else
							runFilter(dateFilter.getDateFrom(), dateFilter.getDateTo(), null, (String) filter
									.getComboBox2().getSelectedItem());

					if (buttonPanel != null) {
						buttonPanel.activate(false);
					}

					if (preview != null) {
						preview.setEnabled(false);
						preview.disable();
					}

					clearSelection();
				}
			});

			dateFilter.getClear().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dateFilter.reset();
				}
			});
		}

	}

	public TablePanel(TableModel tableModel, String title) {
		super(new BorderLayout());
		this.tableModel = tableModel;

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		table = new JTable(tableModel);
		sorter = new TableRowSorter<TableModel<?>>(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		table.setRowSorter(sorter);

		scrollPane = new JideScrollPane(table);

		add(scrollPane, BorderLayout.CENTER);

	}

	public ListSelectionModel getSelectionModel() {
		return table.getSelectionModel();
	}

	public int getSelected() {
		int[] selected = table.getSelectedRows();

		if (selected == null || selected.length == 0)
			return -1;
		else {
			if (sorter != null) return sorter.convertRowIndexToModel(selected[0]);
			return selected[0];
		}

	}

	public abstract int getComboSelectedIndex();

	public void refresh() {
		if (table != null) table.repaint();
		if (filter != null) filter.refresh();
	}

	public void runFilter(String text) {
		RowFilter<TableModel<?>, Object> rf = null;
		try {
			rf = RowFilter.regexFilter(text, filterPosition);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}

	public void runFilter(Long min, Long max) {
	}

	public void runFilter(Long min, Long max, String s, String s2) {
	}

	public void runFilter(Long min, Long max, String s) {
	}

	public void runFilter(String text, String combo) {

		List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
		filters.add(RowFilter.regexFilter(combo, comboFilterPosition));
		filters.add(RowFilter.regexFilter(text, filterPosition));

		RowFilter<Object, Object> rf = RowFilter.andFilter(filters);
		sorter.setRowFilter(rf);
	}

	public void clearSelection() {
		table.getSelectionModel().removeSelectionInterval(table.getSelectionModel().getMinSelectionIndex(),
				table.getSelectionModel().getMaxSelectionIndex());
	}

	public TableColumnModel getColumnModel() {
		return table.getColumnModel();
	}

	public void refreshComboBoxes() {
		if (filter != null) filter.refresh();
	}

}
