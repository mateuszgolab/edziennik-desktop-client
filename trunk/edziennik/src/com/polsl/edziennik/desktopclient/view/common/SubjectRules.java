package com.polsl.edziennik.desktopclient.view.common;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.FileChooser;
import com.polsl.edziennik.desktopclient.controller.utils.FileManager;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IFilter;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.modelDTO.file.FileDTO;
import com.polsl.edziennik.modelDTO.subjectrules.SubjectRulesDTO;

public class SubjectRules extends JPanel {

	protected final ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected SubjectRulesDTO currentSubject;
	protected JLabel academicYear;
	protected JLabel semester;
	protected JLabel description;
	protected JLabel rules;
	protected JLabel name;

	protected JTextField academicYearText;
	protected JTextField semesterText;
	protected JTextArea descriptionText;
	protected JTextField nameText;
	protected JButton rulesButton;

	protected IGuiComponentAbstractFactory factory;
	protected JFileChooser fileChooser;
	protected static FileManager fileManager = new FileManager();
	protected ILabel label;
	protected IButton button;
	protected IFilter filter;
	protected CellConstraints cc;
	public static final int TEXT_SIZE = 30;
	protected JPanel panel;
	protected JFileChooser downloadFileChooser;

	public SubjectRules() {
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(bundle.getString("subjectRules")),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		DataProvider provider = new DataProvider();
		provider.execute();
		provider.startProgress();

		setLayout(new BorderLayout());

		FormLayout layout = new FormLayout(
				"pref,30dlu, 60dlu,20dlu, 50dlu,50dlu, 100dlu, 4dlu, min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref");

		panel = new JPanel(layout);

		factory = GuiComponentFactory.getInstance();
		label = factory.createLabel();
		button = factory.createTextButton();
		filter = factory.createFilter();
		fileChooser = FileChooser.getDownloadInstance();

		create();
		setComponents();
		setEditable(false);

		add(new JScrollPane(panel), BorderLayout.CENTER);

	}

	public void create() {
		downloadFileChooser = FileChooser.getDownloadInstance();
		academicYear = label.getLabel("academicYear");
		semester = label.getLabel("semester");
		description = label.getLabel("description");
		rules = label.getLabel("rules");
		name = label.getLabel("subjectName");

		nameText = new JTextField(TEXT_SIZE);
		academicYearText = new JTextField(TEXT_SIZE);
		semesterText = new JTextField(TEXT_SIZE);
		descriptionText = new JTextArea(5, 5);
		rulesButton = button.getButton("getFile", "getSubjectRulesHint");
		rulesButton.setEnabled(false);
		rulesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (currentSubject != null && currentSubject.getFileId() != null) {
					FileProvider fp = new FileProvider();
					fp.execute();
					fp.startProgress();
				}
			}
		});
	}

	public void setComponents() {

		cc = new CellConstraints();

		panel.add(name, cc.xy(1, 3));
		panel.add(nameText, cc.xyw(3, 3, 3));
		panel.add(academicYear, cc.xy(1, 5));
		panel.add(academicYearText, cc.xy(3, 5));
		panel.add(semester, cc.xy(1, 7));
		panel.add(semesterText, cc.xy(3, 7));
		panel.add(description, cc.xy(1, 9));
		panel.add(descriptionText, cc.xyw(3, 9, 6));
		panel.add(rules, cc.xy(1, 13));
		panel.add(rulesButton, cc.xy(3, 13));
	}

	public void setEditable(boolean b) {
		nameText.setEditable(b);
		academicYearText.setEditable(b);
		semesterText.setEditable(b);
		descriptionText.setEditable(b);
	}

	public void setData(SubjectRulesDTO subject) {

		if (subject != null) {
			nameText.setText(subject.getName());
			academicYearText.setText(subject.getAcademicYear());
			semesterText.setText(subject.getSemester());
			descriptionText.setText(subject.getDescription());
			if (subject.getFileId() != null)
				rulesButton.setEnabled(true);
			else
				rulesButton.setEnabled(false);

		}

		currentSubject = subject;

	}

	private class DataProvider extends Worker<SubjectRulesDTO> {

		@Override
		protected SubjectRulesDTO doInBackground() {

			try {
				return DelegateFactory.getCommonDelegate().getSubjectRules();
			} catch (DelegateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;

		}

		@Override
		public void done() {

			stopProgress();
			try {
				setData(get());

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private class FileProvider extends Worker<FileDTO> {

		@Override
		protected FileDTO doInBackground() throws Exception {
			return DelegateFactory.getCommonDelegate().getFile(currentSubject.getFileId());
		}

		@Override
		public void done() {

			stopProgress();
			try {
				FileDTO result = get();

				if (result != null)
					downloadFileChooser.setSelectedFile(new File(result.getName() + result.getExtension()));
				if (downloadFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					File f = downloadFileChooser.getSelectedFile();
					fileManager.saveFile(result, f.getAbsolutePath());

				}

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}
