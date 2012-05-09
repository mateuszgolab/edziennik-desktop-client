package com.polsl.edziennik.desktopclient.view.common.panels.preview;

import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.desktopclient.controller.utils.DateConverter;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ITextArea;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.modelDTO.exam.ExamDTO;

public abstract class ExamPreviewPanel extends PreviewPanel {

	public static final String[] HOURS = new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
			"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" };
	public static final String[] MINUTES = new String[] { "00", "15", "30", "45" };
	protected JTextArea scopeText;
	private JLabel approach;
	private JLabel room;
	private JLabel date;
	private JLabel duration;
	private JLabel scope;
	protected JTextField approachText;
	protected JTextField roomText;
	protected JComboBox hours;
	protected JComboBox minutes;
	protected JComboBox lengthHours;
	protected JComboBox lengthMinutes;
	protected JXDatePicker datePicker;
	protected ExamDTO currentExam;
	public ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected ILabel label;
	protected ITextArea area;
	protected DateConverter dateConverter;
	protected CellConstraints cc;
	protected IButton button;
	protected Integer examId;

	public ExamPreviewPanel(String title) {
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		FormLayout layout = new FormLayout(
				"pref,20dlu,28dlu,4dlu,28dlu,4dlu,  28dlu,4dlu,28dlu,4dlu,28dlu",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,4dlu, pref, 8dlu, pref, 1dlu, pref,1dlu, pref, 10dlu, pref");

		setLayout(layout);

		create();
		setComponents();
		setEnabled(false);

	}

	public void create() {
		label = factory.createLabel();
		area = factory.createTextArea();
		button = factory.createTextButton();
		approach = label.getLabel("approach");
		date = label.getLabel("date");
		room = label.getLabel("classRoom");
		scope = label.getLabel("scope");
		duration = label.getLabel("timeLength");

		hours = new JComboBox(HOURS);
		minutes = new JComboBox(MINUTES);
		lengthHours = new JComboBox(HOURS);
		lengthMinutes = new JComboBox(MINUTES);
		roomText = new JTextField(TEXT_SIZE);
		approachText = new JTextField(TEXT_SIZE);
		scopeText = area.getTextArea(5, 5);

		datePicker = new JXDatePicker(null, new Locale("pl"));

		dateConverter = new DateConverter();

	}

	@Override
	public void setComponents() {
		cc = new CellConstraints();

		add(approach, cc.xy(1, 1));
		add(approachText, cc.xyw(3, 1, 5));
		add(room, cc.xy(1, 3));
		add(roomText, cc.xyw(3, 3, 5));
		add(date, cc.xy(1, 5));
		add(datePicker, cc.xyw(3, 5, 5));
		add(hours, cc.xy(9, 5));
		add(minutes, cc.xy(11, 5));
		add(duration, cc.xy(1, 7));
		add(lengthHours, cc.xy(3, 7));
		add(lengthMinutes, cc.xy(5, 7));
		add(scope, cc.xy(1, 9));
		add(scopeText, cc.xyw(3, 9, 9));

	}

	@Override
	public void clear() {
		datePicker.setDate(null);
		hours.setSelectedIndex(0);
		minutes.setSelectedIndex(0);
		lengthHours.setSelectedIndex(0);
		lengthMinutes.setSelectedIndex(0);
		roomText.setText("");
		approachText.setText("");
		scopeText.setText("");

	}

	@Override
	public void setEnabled(boolean b) {
		datePicker.setEnabled(b);
		hours.setEnabled(b);
		minutes.setEnabled(b);
		lengthHours.setEnabled(b);
		lengthMinutes.setEnabled(b);
		roomText.setEnabled(b);
		approachText.setEnabled(b);
		scopeText.setEnabled(b);

	}

	public void setData(ExamDTO e) {
		// nowy egzamin
		if (e == null) {
			clear();
		} else {

			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(e.getDate());

			datePicker.setDate(dateConverter.LongToDate(e.getDate()));
			hours.setSelectedItem(dateConverter.DateToHours(c.get(Calendar.HOUR_OF_DAY)));
			minutes.setSelectedItem(dateConverter.DateToMinutes(c.get(Calendar.MINUTE)));
			roomText.setText(e.getPlace());
			approachText.setText(e.getApproach());
			scopeText.setText(e.getScope());

			dateConverter = new DateConverter(e.getDuration());

			lengthHours.setSelectedItem(dateConverter.getHours());
			lengthMinutes.setSelectedItem(dateConverter.getMinutes());

			examId = e.getId();
		}

		currentExam = e;

	}

	public void setEditable(boolean b) {
		datePicker.setEditable(b);
		roomText.setEditable(b);
		scopeText.setEditable(b);
		approachText.setEditable(b);

		if (!b) {

			hours.setEnabled(b);
			minutes.setEnabled(b);
			lengthHours.setEnabled(b);
			lengthMinutes.setEnabled(b);
		}

	}

	public void disable(boolean b) {
		hours.setEnabled(!b);
		minutes.setEnabled(!b);
		lengthHours.setEnabled(!b);
		lengthMinutes.setEnabled(!b);

	}
}
