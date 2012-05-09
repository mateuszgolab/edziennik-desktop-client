package com.polsl.edziennik.desktopclient.view.common.panels.preview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.FileChooser;
import com.polsl.edziennik.desktopclient.controller.utils.FileManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IFilter;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ITextArea;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.modelDTO.exercise.ExerciseTopicDTO;
import com.polsl.edziennik.modelDTO.file.FileDTO;

public class ExerciseTopicSimplePreviewPanel extends PreviewPanel {

	public static final int TEXT_SIZE = 30;
	protected JLabel number;
	protected JLabel subject;
	protected JLabel description;
	protected JLabel manual;

	protected JTextField numberText;
	protected JTextField subjectText;
	protected JTextArea descriptionText;
	protected JButton manualButton;

	protected ILabel label;
	protected IButton button;
	protected IFilter filter;
	protected ITextArea area;
	protected CellConstraints cc;
	protected IGuiComponentAbstractFactory factory;
	protected JFileChooser sendFileChooser;
	protected JFileChooser downloadFileChooser;
	protected ExerciseTopicDTO currentExercise;
	protected static FileManager fileManager = new FileManager();

	public ExerciseTopicSimplePreviewPanel(String title) {
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		FormLayout layout = new FormLayout(
				"pref,4dlu, 100dlu, 50dlu,min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu,  pref");

		setLayout(layout);

		factory = GuiComponentFactory.getInstance();
		label = factory.createLabel();
		button = factory.createTextButton();
		filter = factory.createFilter();
		area = factory.createTextArea();

		create();
		setComponents();
		setEnabled(false);
		downloadFileChooser = FileChooser.getDownloadInstance();
		sendFileChooser = FileChooser.getSendInstance();

		manualButton.setEnabled(false);

	}

	public void create() {

		number = label.getLabel("number");
		subject = label.getLabel("exerciseSubject");
		description = label.getLabel("description");
		manual = label.getLabel("manual");

		numberText = new JTextField(TEXT_SIZE);
		subjectText = new JTextField(TEXT_SIZE);
		descriptionText = area.getTextArea(5, 5);
		manualButton = button.getButton("getManual", "getManualHint");
		manualButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FileProvider fp = new FileProvider();
				fp.execute();
				fp.startProgress();
				downloadFileChooser.setCurrentDirectory(null);
				downloadFileChooser.setVisible(true);

			}
		});
	}

	public void setData(ExerciseTopicDTO a) {
		clear();
		if (a != null) {

			numberText.setText(a.getNumber());
			subjectText.setText(a.getSubject());
			descriptionText.setText(a.getDescription());
			if (a.getManualId() != null)
				manualButton.setEnabled(true);
			else
				manualButton.setEnabled(false);

		}
		currentExercise = a;
	}

	@Override
	public void setComponents() {
		cc = new CellConstraints();

		add(number, cc.xy(1, 3));
		add(numberText, cc.xy(3, 3));
		add(subject, cc.xy(1, 5));
		add(subjectText, cc.xy(3, 5));
		add(description, cc.xy(1, 7));
		add(descriptionText, cc.xyw(3, 7, 3));
		add(manual, cc.xy(1, 13));
		add(manualButton, cc.xy(3, 13));
	}

	@Override
	public void clear() {
		numberText.setText("");
		subjectText.setText("");
		descriptionText.setText("");

	}

	@Override
	public void setEnabled(boolean b) {
		numberText.setEnabled(b);
		subjectText.setEnabled(b);
		descriptionText.setEnabled(b);
	}

	public void setEnabledManualButton(boolean b) {
		manualButton.setEnabled(b);
	}

	@Override
	public void disable() {
		clear();
		setEnabled(false);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	private class FileProvider extends Worker<FileDTO> {

		@Override
		protected FileDTO doInBackground() throws Exception {
			return DelegateFactory.getCommonDelegate().getFile(currentExercise.getManualId());
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

	public void setEditable(boolean b) {
		numberText.setEditable(b);
		subjectText.setEditable(b);
		descriptionText.setEditable(b);
	}
}
