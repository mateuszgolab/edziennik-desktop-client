package com.polsl.edziennik.desktopclient.view.common.panels.preview;

import java.util.Calendar;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.delegates.TeacherDelegate;
import com.polsl.edziennik.desktopclient.controller.utils.DateConverter;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.model.comboBoxes.GroupComboBoxModel;
import com.polsl.edziennik.desktopclient.model.comboBoxes.TeacherComboBoxModel;
import com.polsl.edziennik.modelDTO.activity.ActivityDTO;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public abstract class ActivityPreviewPanel extends PreviewPanel {

	public static final int TEXT_SIZE = 30;
	public static final String[] HOURS = new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
			"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" };
	public static final String[] MINUTES = new String[] { "00", "15", "30", "45" };
	protected JLabel group;
	protected JLabel date;
	protected JLabel hour;
	protected JLabel teacher;
	protected JLabel room;
	protected JLabel note;
	protected JLabel length;

	protected JTextField roomText;
	protected JTextArea noteText;
	protected JXDatePicker datePicker;
	protected JComboBox hours;
	protected JComboBox minutes;
	protected JComboBox lengthHours;
	protected JComboBox lengthMinutes;
	protected JComboBox groupComboBox;

	protected CellConstraints cc;
	protected ActivityDTO currentActivity;
	protected IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();

	protected TeacherComboBoxModel teacherComboModel;
	protected GroupComboBoxModel groupComboModel;
	protected JComboBox teacherComboBox;
	protected TeacherDelegate delegate;
	protected ILabel label;
	private DateConverter dateConverter;

	public ActivityPreviewPanel(String title) {

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		FormLayout layout = new FormLayout(
				"pref,20dlu,28dlu,4dlu,28dlu,4dlu,  28dlu,4dlu,28dlu,4dlu,28dlu",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,4dlu, pref, 8dlu, pref, 1dlu, pref,1dlu, pref, 10dlu, pref");

		setLayout(layout);

		label = factory.createLabel();

		create();
		setComponents();
		setEnabled(false);

	}

	public void create() {
		date = label.getLabel("date");
		hour = label.getLabel("hour");
		teacher = label.getLabel("teacher");
		room = label.getLabel("classRoom");
		note = label.getLabel("note");
		group = label.getLabel("group");
		length = label.getLabel("timeLength");

		hours = new JComboBox(HOURS);
		minutes = new JComboBox(MINUTES);
		lengthHours = new JComboBox(HOURS);
		lengthMinutes = new JComboBox(MINUTES);
		roomText = new JTextField(TEXT_SIZE);
		noteText = new JTextArea(3, 3);

		datePicker = new JXDatePicker(null, new Locale("pl"));

		teacherComboModel = new TeacherComboBoxModel();
		teacherComboBox = new JComboBox(teacherComboModel);

		groupComboModel = new GroupComboBoxModel();
		groupComboBox = new JComboBox(groupComboModel);

		dateConverter = new DateConverter();
	}

	@Override
	public void setComponents() {
		cc = new CellConstraints();

		add(group, cc.xy(1, 1));
		add(groupComboBox, cc.xyw(3, 1, 5));
		add(teacher, cc.xy(1, 3));
		add(teacherComboBox, cc.xyw(3, 3, 5));
		add(date, cc.xy(1, 5));
		add(datePicker, cc.xyw(3, 5, 5));
		add(hours, cc.xy(9, 5));
		add(minutes, cc.xy(11, 5));
		add(length, cc.xy(1, 7));
		add(lengthHours, cc.xy(3, 7));
		add(lengthMinutes, cc.xy(5, 7));
		add(room, cc.xyw(1, 9, 5));
		add(roomText, cc.xyw(3, 9, 5));
		add(note, cc.xyw(1, 11, 5));
		add(noteText, cc.xyw(3, 11, 9));

	}

	public void setData(ActivityDTO a) {

		// nowy Activity
		if (a == null) {
			clear();
		} else {

			// te.set
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(a.getDateFrom());

			Calendar c2 = Calendar.getInstance();
			c2.setTimeInMillis(a.getDateTo());

			datePicker.setDate(dateConverter.LongToDate(a.getDateFrom()));
			hours.setSelectedItem(dateConverter.DateToHours(c.get(Calendar.HOUR_OF_DAY)));
			minutes.setSelectedItem(dateConverter.DateToMinutes(c.get(Calendar.MINUTE)));
			teacherComboBox.setSelectedItem(a.getTeacher());
			groupComboBox.setSelectedItem(a.getGroup());
			roomText.setText(a.getRoom());
			noteText.setText(a.getNote());
			lengthHours.setSelectedItem(dateConverter.DateToHours(c2.get(Calendar.HOUR_OF_DAY)
					- c.get(Calendar.HOUR_OF_DAY) + 1));
			lengthMinutes
					.setSelectedItem(dateConverter.DateToMinutes(c2.get(Calendar.MINUTE) - c.get(Calendar.MINUTE)));
		}

		currentActivity = a;
	}

	@Override
	public void clear() {
		datePicker.setDate(null);
		hours.setSelectedIndex(0);
		minutes.setSelectedIndex(0);
		lengthHours.setSelectedIndex(0);
		lengthMinutes.setSelectedIndex(0);
		teacherComboBox.setSelectedItem(null);
		groupComboBox.setSelectedItem(null);
		roomText.setText("");
		noteText.setText("");
	}

	@Override
	public void setEnabled(boolean b) {
		datePicker.setEnabled(b);
		hours.setEnabled(b);
		minutes.setEnabled(b);
		lengthHours.setEnabled(b);
		lengthMinutes.setEnabled(b);
		teacherComboBox.setEnabled(b);
		groupComboBox.setEnabled(b);
		roomText.setEnabled(b);
		noteText.setEnabled(b);
	}

	public ActivityDTO getCurrentActivity() {
		return currentActivity;

	}

	protected void setActivityData() {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, new Integer(lengthHours.getSelectedItem().toString()));
		c.set(Calendar.MINUTE, new Integer(lengthMinutes.getSelectedItem().toString()));

		currentActivity.setDateFrom(dateConverter.getDate(datePicker.getDate().getTime(),
				(String) hours.getSelectedItem(), (String) minutes.getSelectedItem()));

		currentActivity.setDateTo(dateConverter.getDate(datePicker.getDate().getTime(),
				(String) hours.getSelectedItem(), (String) minutes.getSelectedItem(),
				(String) lengthHours.getSelectedItem(), (String) lengthMinutes.getSelectedItem()));

		// currentActivity.setDateTo(datePicker.getDate().getTime() +
		// c.getTimeInMillis());
		currentActivity.setTeacher((TeacherDTO) teacherComboBox.getSelectedItem());
		currentActivity.setGroup((GroupDTO) groupComboBox.getSelectedItem());
		currentActivity.setRoom(roomText.getText());
		currentActivity.setNote(noteText.getText());
	}

	public void setEditable(boolean b) {
		datePicker.setEditable(b);
		roomText.setEditable(b);
		noteText.setEditable(b);

		if (!b) {

			teacherComboBox.setEnabled(b);
			hours.setEnabled(b);
			minutes.setEnabled(b);
			lengthHours.setEnabled(b);
			lengthMinutes.setEnabled(b);
			groupComboBox.setEnabled(b);
		}
	}

}
