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
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.model.comboBoxes.ExerciseTopicComboBoxModel;
import com.polsl.edziennik.desktopclient.model.comboBoxes.GroupComboBoxModel;
import com.polsl.edziennik.desktopclient.model.comboBoxes.TeacherComboBoxModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.modelDTO.activity.LabActivityDTO;
import com.polsl.edziennik.modelDTO.exercise.ExerciseTopicDTO;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public abstract class LabActivityPreviewPanel extends PreviewPanel {

	public static final String[] HOURS = new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
			"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" };
	public static final String[] MINUTES = new String[] { "00", "15", "30", "45" };
	private JLabel group;
	private JLabel date;
	private JLabel hour;
	private JLabel teacher;
	private JLabel room;
	private JLabel note;
	private JLabel length;
	private JLabel exerciseTopic;

	private JTextField roomText;
	protected JTextArea noteText;
	private JXDatePicker datePicker;
	private JComboBox hours;
	private JComboBox minutes;
	private JComboBox lengthHours;
	private JComboBox lengthMinutes;
	protected JComboBox groupComboBox;
	protected JComboBox exerciseTopicComboBox;
	protected JComboBox teacherComboBox;

	protected LabActivityDTO currentLabActivity;
	protected TeacherComboBoxModel teacherComboModel;
	protected GroupComboBoxModel groupComboModel;
	protected ExerciseTopicComboBoxModel exerciseTopicComboModel;

	public ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected ILabel label;
	private DateConverter dateConverter;

	public LabActivityPreviewPanel(String title) {
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
		date = label.getLabel("date");
		hour = label.getLabel("hour");
		teacher = label.getLabel("teacher");
		room = label.getLabel("classRoom");
		note = label.getLabel("note");
		group = label.getLabel("group");
		length = label.getLabel("timeLength");
		exerciseTopic = label.getLabel("exerciseTopic");

		hours = new JComboBox(HOURS);
		minutes = new JComboBox(MINUTES);
		lengthHours = new JComboBox(HOURS);
		lengthMinutes = new JComboBox(MINUTES);
		roomText = new JTextField(TEXT_SIZE);
		noteText = new JTextArea(3, 3);

		datePicker = new JXDatePicker(null, new Locale("pl"));

		dateConverter = new DateConverter();
		teacherComboModel = new TeacherComboBoxModel();
		teacherComboBox = new JComboBox(teacherComboModel);

		groupComboModel = new GroupComboBoxModel();
		groupComboBox = new JComboBox(groupComboModel);

		exerciseTopicComboModel = new ExerciseTopicComboBoxModel();
		exerciseTopicComboBox = new JComboBox(exerciseTopicComboModel);

	}

	@Override
	public void setComponents() {
		cc = new CellConstraints();

		add(exerciseTopic, cc.xy(1, 1));
		add(exerciseTopicComboBox, cc.xyw(3, 1, 5));
		add(group, cc.xy(1, 3));
		add(groupComboBox, cc.xyw(3, 3, 5));
		add(teacher, cc.xy(1, 5));
		add(teacherComboBox, cc.xyw(3, 5, 5));
		add(date, cc.xy(1, 7));
		add(datePicker, cc.xyw(3, 7, 5));
		add(hours, cc.xy(9, 7));
		add(minutes, cc.xy(11, 7));
		add(length, cc.xy(1, 9));
		add(lengthHours, cc.xy(3, 9));
		add(lengthMinutes, cc.xy(5, 9));
		add(room, cc.xyw(1, 11, 5));
		add(roomText, cc.xyw(3, 11, 5));
		add(note, cc.xyw(1, 13, 5));
		add(noteText, cc.xyw(3, 13, 5));

	}

	public void setData(LabActivityDTO a) {
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
			exerciseTopicComboBox.setSelectedItem(a.getExerciseTopic());
			roomText.setText(a.getRoom());
			noteText.setText(a.getNote());
			lengthHours.setSelectedItem(dateConverter.DateToHours(c2.get(Calendar.HOUR_OF_DAY)
					- c.get(Calendar.HOUR_OF_DAY) + 1));
			lengthMinutes
					.setSelectedItem(dateConverter.DateToMinutes(c2.get(Calendar.MINUTE) - c.get(Calendar.MINUTE)));
		}

		currentLabActivity = a;

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
		exerciseTopicComboBox.setSelectedItem(null);
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
		exerciseTopicComboBox.setEnabled(b);
		teacherComboBox.setEnabled(b);
		groupComboBox.setEnabled(b);
		roomText.setEnabled(b);
		noteText.setEnabled(b);
	}

	protected void setLabActivityData() {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, new Integer(lengthHours.getSelectedItem().toString()));
		c.set(Calendar.MINUTE, new Integer(lengthMinutes.getSelectedItem().toString()));

		currentLabActivity.setDateFrom(dateConverter.getDate(datePicker.getDate().getTime(),
				(String) hours.getSelectedItem(), (String) minutes.getSelectedItem()));

		currentLabActivity.setDateTo(dateConverter.getDate(datePicker.getDate().getTime(),
				(String) hours.getSelectedItem(), (String) minutes.getSelectedItem(),
				(String) lengthHours.getSelectedItem(), (String) lengthMinutes.getSelectedItem()));

		// currentActivity.setDateTo(datePicker.getDate().getTime() +
		// c.getTimeInMillis());
		currentLabActivity.setTeacher((TeacherDTO) teacherComboBox.getSelectedItem());
		currentLabActivity.setGroup((GroupDTO) groupComboBox.getSelectedItem());
		currentLabActivity.setExerciseTopic((ExerciseTopicDTO) exerciseTopicComboBox.getSelectedItem());
		currentLabActivity.setRoom(roomText.getText());
		currentLabActivity.setNote(noteText.getText());
	}

	public void setEditable(boolean b) {
		datePicker.setEditable(b);
		roomText.setEditable(b);
		noteText.setEditable(b);
		if (!b) {

			exerciseTopicComboBox.setEnabled(b);
			teacherComboBox.setEnabled(b);
			hours.setEnabled(b);
			minutes.setEnabled(b);
			lengthHours.setEnabled(b);
			lengthMinutes.setEnabled(b);
			groupComboBox.setEnabled(b);
		}

	}

}
