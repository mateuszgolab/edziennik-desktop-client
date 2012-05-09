package com.polsl.edziennik.desktopclient.view.common.panels.preview;

import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.jdesktop.swingx.JXDatePicker;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.delegates.AdminDelegate;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.DateConverter;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.model.tables.HappyHoursTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.modelDTO.happyhours.HappyHoursDTO;

public class HappyHoursPreviewPanel extends JPanel {

	public static final int TEXT_SIZE = 30;
	public static final String[] HOURS = new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
			"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" };
	public static final String[] MINUTES = new String[] { "00", "15", "30", "45" };

	private JLabel dateFrom;
	private JLabel dateTo;
	private JLabel description;

	private JTextArea descriptionText;
	private JXDatePicker dateFromPicker;
	private JXDatePicker dateToPicker;
	private JComboBox hoursFrom;
	private JComboBox minutesFrom;
	private JComboBox hoursTo;
	private JComboBox minutesTo;
	private CellConstraints cc;
	private IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	private HappyHoursTableModel model;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private HappyHoursDTO current;
	private AdminDelegate admin;
	private Integer selected;
	private DateConverter dateConverter;

	public HappyHoursPreviewPanel(String title, HappyHoursTableModel model) {
		this.model = model;

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		FormLayout layout = new FormLayout(
				"pref,4dlu,30dlu,4dlu,30dlu,4dlu,  28dlu,4dlu,28dlu, 4dlu, min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref");

		setLayout(layout);

		ILabel label = factory.createLabel();
		factory.createTextButton();

		dateFrom = label.getLabel("dateStart");
		dateTo = label.getLabel("dateStop");
		description = label.getLabel("note");

		hoursFrom = new JComboBox(HOURS);
		minutesFrom = new JComboBox(MINUTES);
		hoursTo = new JComboBox(HOURS);
		minutesTo = new JComboBox(MINUTES);

		descriptionText = new JTextArea(5, 3);
		descriptionText.setLineWrap(true);
		descriptionText.setWrapStyleWord(true);

		dateFromPicker = new JXDatePicker(null, new Locale("pl"));
		dateToPicker = new JXDatePicker(null, new Locale("pl"));

		dateConverter = new DateConverter();

		setComponents();
		setEnabled(false);

	}

	public void setComponents() {
		cc = new CellConstraints();

		add(dateFrom, cc.xy(1, 1));
		add(dateFromPicker, cc.xyw(3, 1, 5));
		add(hoursFrom, cc.xy(9, 1));
		add(minutesFrom, cc.xy(11, 1));
		add(dateTo, cc.xy(1, 3));
		add(dateToPicker, cc.xyw(3, 3, 5));
		add(hoursTo, cc.xy(9, 3));
		add(minutesTo, cc.xy(11, 3));
		add(description, cc.xyw(1, 5, 5));
		add(descriptionText, cc.xyw(3, 5, 9));
	}

	public void setData(HappyHoursDTO a) {
		// nowe godziny
		if (a == null) {
			clear();
		} else {

			clear();
			int tmp = 0;
			String str = "";

			if (a.getDateFrom() != null) {
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(a.getDateFrom());

				dateFromPicker.setDate(c.getTime());

				tmp = c.get(Calendar.HOUR_OF_DAY);
				if (tmp < 10)
					str = "0" + tmp;
				else
					str = "" + tmp;
				hoursFrom.setSelectedItem(str);

				tmp = c.get(Calendar.MINUTE);
				if (tmp < 10)
					str = "0" + tmp;
				else
					str = "" + tmp;

				minutesFrom.setSelectedItem(str);
			}

			if (a.getDateTo() != null) {
				Calendar c2 = Calendar.getInstance();
				c2.setTimeInMillis(a.getDateTo());

				dateToPicker.setDate(c2.getTime());

				tmp = c2.get(Calendar.HOUR_OF_DAY);
				if (tmp < 10)
					str = "0" + tmp;
				else
					str = "" + tmp;
				hoursTo.setSelectedItem(str);

				tmp = c2.get(Calendar.MINUTE);
				if (tmp < 10)
					str = "0" + tmp;
				else
					str = "" + tmp;

				minutesTo.setSelectedItem(str);
			}
			descriptionText.setText(a.getDescription());

		}

		current = a;
	}

	public void clear() {
		dateFromPicker.setDate(null);
		dateToPicker.setDate(null);
		hoursFrom.setSelectedIndex(0);
		minutesFrom.setSelectedIndex(0);
		hoursTo.setSelectedIndex(0);
		minutesTo.setSelectedIndex(0);
		descriptionText.setText("");
	}

	@Override
	public void setEnabled(boolean b) {
		dateFromPicker.setEnabled(b);
		hoursFrom.setEnabled(b);
		minutesFrom.setEnabled(b);
		dateToPicker.setEnabled(b);
		hoursTo.setEnabled(b);
		minutesTo.setEnabled(b);
		descriptionText.setEnabled(b);

	}

	public void saveHappyHours() throws DelegateException {
		// zapis nowych godziñ dziekañskich

		admin = DelegateFactory.getAdminDelegate();

		if (current == null) {
			current = new HappyHoursDTO();
			setHappyHoursData();
			admin.addHappyHours(current);
			model.add(current);

		} else {
			setHappyHoursData();
			admin.updateHappyHours(current);
			model.update(current, selected);

		}
	}

	private void setHappyHoursData() {

		current.setDateFrom(dateConverter.getDate(dateFromPicker.getDate().getTime(),
				(String) hoursFrom.getSelectedItem(), (String) minutesFrom.getSelectedItem()));
		current.setDateTo(dateConverter.getDate(dateToPicker.getDate().getTime(), (String) hoursTo.getSelectedItem(),
				(String) minutesTo.getSelectedItem()));
		current.setDescription(descriptionText.getText());
	}

	public void setSelected(int index) {
		selected = index;
	}

	@Override
	public void disable() {
		clear();
		setEnabled(false);
	}

	public boolean checkDates() {
		if (dateConverter.getDate(dateFromPicker.getDate().getTime(), (String) hoursFrom.getSelectedItem(),
				(String) minutesFrom.getSelectedItem()) < dateConverter.getDate(dateToPicker.getDate().getTime(),
				(String) hoursTo.getSelectedItem(), (String) minutesTo.getSelectedItem())) return true;
		return false;
	}

	public void setEditable(boolean b) {
		dateFromPicker.setEditable(b);
		hoursFrom.setEnabled(b);
		minutesFrom.setEnabled(b);
		dateToPicker.setEditable(b);
		hoursTo.setEnabled(b);
		minutesTo.setEnabled(b);
		descriptionText.setEditable(b);
	}
}
