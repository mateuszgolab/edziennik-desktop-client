package com.polsl.edziennik.desktopclient.view.student.panels.preview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.TeacherDelegate;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.FileChooser;
import com.polsl.edziennik.desktopclient.controller.utils.FileManager;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IFilter;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.model.tables.ProjectActivityTableModel;
import com.polsl.edziennik.desktopclient.model.tables.RegularGradeTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.modelDTO.activity.ProjectActivityDTO;
import com.polsl.edziennik.modelDTO.file.FileDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;
import com.polsl.edziennik.modelDTO.resource.ProjectResourceDTO;

public class ProjectActivityStudentPreviewPanel extends JPanel {

	public static final int TEXT_SIZE = 30;
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
	private JLabel project;
	private JLabel attendance;
	private JLabel grade;
	private JLabel report;

	private JTextField roomText;
	private JTextArea noteText;
	private JXDatePicker datePicker;
	private JTextField hours;
	private JTextField minutes;
	private JTextField lengthHours;
	private JTextField lengthMinutes;
	private JTextField projectText;
	private JTextField teacherText;
	private JTextField gradeText;
	private JButton sendResource;
	private JButton getResource;
	private JCheckBox attendanceBox;

	private CellConstraints cc;
	private ProjectActivityDTO currentActivity;
	private IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private RegularGradeTableModel regularGradeTableModel;
	private TeacherDelegate delegate;
	private JFileChooser sendFileChooser;
	private JFileChooser downloadFileChooser;
	private IFilter filter;
	private static FileManager fileManager = new FileManager();

	public ProjectActivityStudentPreviewPanel(String title, ProjectActivityTableModel model) {
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		FormLayout layout = new FormLayout(
				"pref,4dlu,30dlu,4dlu,30dlu,4dlu,  28dlu,4dlu,28dlu, 4dlu,28dlu, min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref, 2dlu, pref");

		// layout.setRowGroups(new int[][]{{1, 3, 5, 7}});

		setLayout(layout);

		ILabel Label = factory.createLabel();
		IButton button = factory.createTextButton();
		filter = factory.createFilter();

		date = Label.getLabel("date");
		hour = Label.getLabel("hour");
		teacher = Label.getLabel("teacher");
		room = Label.getLabel("classRoom");
		note = Label.getLabel("note");
		group = Label.getLabel("group");
		length = Label.getLabel("timeLength");
		project = Label.getLabel("project");
		attendance = Label.getLabel("attendance");
		grade = Label.getLabel("grade");
		report = Label.getLabel("report");

		hours = new JTextField(TEXT_SIZE);
		minutes = new JTextField(TEXT_SIZE);
		lengthHours = new JTextField(TEXT_SIZE);
		lengthMinutes = new JTextField(TEXT_SIZE);
		roomText = new JTextField(TEXT_SIZE);
		noteText = new JTextArea(3, 3);
		projectText = new JTextField(TEXT_SIZE);
		teacherText = new JTextField(TEXT_SIZE);
		gradeText = new JTextField(TEXT_SIZE);
		sendResource = button.getButton("sendReport", "sendProjectResourceHint");
		getResource = button.getButton("getReport", "getProjectResourceHint");
		attendanceBox = new JCheckBox();

		sendResource.addActionListener(new SendListener());
		getResource.addActionListener(new GetListener());

		datePicker = new JXDatePicker(null, new Locale("pl"));

		downloadFileChooser = FileChooser.getDownloadInstance();
		sendFileChooser = FileChooser.getSendInstance();

		setComponents();
		setEnabled(false);

		regularGradeTableModel = new RegularGradeTableModel();
		try {
			delegate = DelegateFactory.getTeacherDelegate();
		} catch (DelegateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		datePicker.setEditable(false);
		hours.setEditable(false);
		minutes.setEditable(false);
		lengthHours.setEditable(false);
		lengthMinutes.setEditable(false);
		projectText.setEditable(false);
		teacherText.setEditable(false);
		roomText.setEditable(false);
		noteText.setEditable(false);
		gradeText.setEditable(false);
		// attendanceBox.setE

	}

	public void setComponents() {
		cc = new CellConstraints();

		add(project, cc.xy(1, 1));
		add(projectText, cc.xyw(3, 1, 5));
		add(teacher, cc.xy(1, 3));
		add(teacherText, cc.xyw(3, 3, 5));
		add(date, cc.xy(1, 5));
		add(datePicker, cc.xyw(3, 5, 5));
		// add(hour, cc.xy(1, 7));
		add(hours, cc.xy(9, 5));
		add(minutes, cc.xy(11, 5));
		add(length, cc.xy(1, 7));
		add(lengthHours, cc.xy(3, 7));
		add(lengthMinutes, cc.xy(5, 7));
		add(room, cc.xyw(1, 9, 5));
		add(roomText, cc.xyw(3, 9, 5));
		add(note, cc.xyw(1, 11, 5));
		add(noteText, cc.xyw(3, 11, 5));
		add(attendance, cc.xy(1, 19));
		add(attendanceBox, cc.xy(3, 19));
		add(grade, cc.xy(1, 21));
		add(gradeText, cc.xy(3, 21));
		add(report, cc.xy(1, 23));
		add(sendResource, cc.xyw(3, 23, 5));
		add(getResource, cc.xyw(3, 25, 5));
	}

	public void setData(ProjectActivityDTO a) {
		// nowe spotkanie projektowe
		if (a == null) {
			clear();
		} else {

			// te.set
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(a.getDateFrom());

			Calendar c2 = Calendar.getInstance();
			c2.setTimeInMillis(a.getDateTo());

			hours.setText(new Integer(c.get(Calendar.HOUR)).toString());
			minutes.setText(new Integer(c.get(Calendar.MINUTE)).toString());
			if (a.getTeacher() != null) {
				TeacherDTO t = a.getTeacher();
				teacherText.setText(t.getAcademicTitle() + " " + t.getFirstName() + " " + t.getLastName());
			}
			if (a.getProject() != null) {
				projectText.setText(a.getProject().getName());
			}
			roomText.setText(a.getRoom());
			noteText.setText(a.getNote());
			lengthHours.setText(new Integer(c2.get(Calendar.HOUR) - c.get(Calendar.HOUR)).toString());
			lengthMinutes.setText(new Integer(c2.get(Calendar.MINUTE) - c.get(Calendar.MINUTE)).toString());

		}

		currentActivity = a;
	}

	public void clear() {
		datePicker.setDate(null);
		hours.setText("");
		minutes.setText("");
		lengthHours.setText("");
		lengthMinutes.setText("");
		projectText.setText("");
		teacherText.setText("");
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
		projectText.setEnabled(b);
		teacherText.setEnabled(b);
		roomText.setEnabled(b);
		noteText.setEnabled(b);
		gradeText.setEnabled(b);
		attendanceBox.setEnabled(b);
		sendResource.setEnabled(b);
		getResource.setEnabled(b);
	}

	public void setSelected(int index) {
	}

	protected class SendListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (currentActivity != null) {

				sendFileChooser.resetChoosableFileFilters();
				sendFileChooser.setFileFilter(filter.getDocumentFilter());
				sendFileChooser.setCurrentDirectory(null);
				sendFileChooser.setVisible(true);
				if (sendFileChooser.showOpenDialog(null) == JFileChooser.OPEN_DIALOG) {

					File f = sendFileChooser.getSelectedFile();
					FileDTO file = fileManager.getFileDTOFromFile(f);
					ProjectResourceDTO resource = new ProjectResourceDTO();
					resource.setFile(file);
					// currentActivity.setResource(resource));

				}
			}
		}

	}

	protected class GetListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// if (currentActivity != null && currentActivity.getResource() !=
			// null && currentActivity.getResource().getFile() != null)
			// downloadFileChooser.setSelectedFile(new
			// File(currentActivity.getResource().getFile().getName()
			// + currentActivity.getResource().getFile().getExtension()));
			// downloadFileChooser.setCurrentDirectory(null);
			// downloadFileChooser.setVisible(true);
			//
			// if (downloadFileChooser.showSaveDialog(null) ==
			// JFileChooser.APPROVE_OPTION) {
			// File f = (File) downloadFileChooser.getSelectedFile();
			// fileTool.saveFile(currentActivity.getResource().getFile(),
			// f.getAbsolutePath());
			//
			// }
		}

	}

	public void updateProjectActivity() {
		if (currentActivity != null) {
			// delegate.updateProjectActivity(currentActivity);
		}
	}
}
