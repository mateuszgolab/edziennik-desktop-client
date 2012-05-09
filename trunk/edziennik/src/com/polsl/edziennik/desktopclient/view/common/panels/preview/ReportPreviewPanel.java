package com.polsl.edziennik.desktopclient.view.common.panels.preview;

import java.util.Calendar;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.FileChooser;
import com.polsl.edziennik.desktopclient.controller.utils.FileManager;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IFilter;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ITextArea;
import com.polsl.edziennik.desktopclient.model.tables.ReportTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ReportButtonPanel;
import com.polsl.edziennik.modelDTO.resource.ReportDTO;

public class ReportPreviewPanel extends PreviewPanel {

	protected final ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected ReportDTO currentReport;
	protected JLabel exerciseTopic;
	protected JLabel teacher;
	protected JLabel date;
	protected JLabel grade;
	protected JLabel passed;
	protected JLabel teacherComment;

	protected JTextField exerciseTopicText;
	protected JTextField teacherText;
	protected JXDatePicker datePicker;
	protected JTextField gradeText;
	protected JTextArea teacherCommentText;
	protected JCheckBox passedBox;
	protected JFileChooser fileChooser;
	protected static FileManager fileManager = new FileManager();
	protected ILabel label;
	protected IButton button;
	protected IFilter filter;
	protected ITextArea area;
	protected ReportButtonPanel buttonPanel;
	protected Integer selected;
	protected ReportTableModel model;

	public ReportPreviewPanel(ReportTableModel tableModel) {
		this.model = tableModel;
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(bundle.getString("PreviewTitle")),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		FormLayout layout = new FormLayout(
				"pref,30dlu, 100dlu, 4dlu,100dlu, min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref");

		setLayout(layout);

		factory = GuiComponentFactory.getInstance();
		label = factory.createLabel();
		button = factory.createTextButton();
		filter = factory.createFilter();
		area = factory.createTextArea();
		fileChooser = FileChooser.getDownloadInstance();

		create();
		setComponents();
		exerciseTopicText.setEditable(false);
		teacherText.setEditable(false);
		datePicker.setEditable(false);
		passedBox.setEnabled(false);

		setEditable(false);

	}

	public void create() {
		exerciseTopic = label.getLabel("exerciseSubject");
		teacher = label.getLabel("teacher");
		date = label.getLabel("sendDate");
		grade = label.getLabel("grade");
		passed = label.getLabel("passed");
		teacherComment = label.getLabel("teacherComment");

		gradeText = new JTextField(TEXT_SIZE);
		exerciseTopicText = new JTextField(TEXT_SIZE);
		teacherText = new JTextField(TEXT_SIZE);
		teacherCommentText = area.getTextArea(7, 7);
		datePicker = new JXDatePicker();
		passedBox = new JCheckBox();

		buttonPanel = new ReportButtonPanel();

	}

	@Override
	public void setComponents() {

		cc = new CellConstraints();

		add(exerciseTopic, cc.xy(1, 1));
		add(exerciseTopicText, cc.xy(3, 1));
		add(date, cc.xy(1, 3));
		add(datePicker, cc.xy(3, 3));
		add(teacher, cc.xy(1, 5));
		add(teacherText, cc.xy(3, 5));
		add(grade, cc.xy(1, 7));
		add(gradeText, cc.xy(3, 7));
		add(passed, cc.xy(1, 9));
		add(passedBox, cc.xy(3, 9));
		add(teacherComment, cc.xy(1, 11));
		add(teacherCommentText, cc.xy(3, 11));
		add(buttonPanel, cc.xyw(1, 19, 2));
	}

	public void setEditable(boolean b) {
		gradeText.setEditable(b);
		teacherCommentText.setEditable(b);
		passedBox.setEnabled(b);
	}

	public void setData(ReportDTO report) {

		if (report != null) {
			if (report.getLabActivity() != null) {
				if (report.getLabActivity().getTeacher() != null)
					teacherText.setText(report.getLabActivity().getTeacher().toString());
				if (report.getLabActivity().getExerciseTopic() != null)
					exerciseTopicText.setText(report.getLabActivity().getExerciseTopic().getSubject());
			}
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(report.getSentDate());
			datePicker.setDate(c.getTime());
			if (report.getPassed() != null) passedBox.setSelected(report.getPassed());
			teacherCommentText.setText(report.getTeachersComment());
			if (report.getGradeValue() != null) gradeText.setText(report.getGradeValue().toString());
			buttonPanel.setFile(report.getFileId());
			buttonPanel.setReport(report.getId());

		}
		currentReport = report;

	}

	public void setReportData() {
		if (currentReport == null) currentReport = new ReportDTO();
		currentReport.setGradeValue(new Float(gradeText.getText()));
		currentReport.setTeachersComment(teacherCommentText.getText());
		currentReport.setPassed(passedBox.isSelected());
	}

	@Override
	public void setEnabled(boolean b) {
		exerciseTopicText.setEnabled(b);
		teacherText.setEnabled(b);
		datePicker.setEnabled(b);
		gradeText.setEnabled(b);
		buttonPanel.setEnabled(b);
		teacherCommentText.setEnabled(b);
	}

	@Override
	public void clear() {
		gradeText.setText("");
		exerciseTopicText.setText("");
		teacherText.setText("");
		datePicker.setDate(null);
		teacherCommentText.setText("");
		passedBox.setSelected(false);

	}

	@Override
	public void save() {

		try {
			setReportData();
			DelegateFactory.getCommonDelegate().updateReport(currentReport);
			model.update(currentReport, selected);
		} catch (DelegateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}
}