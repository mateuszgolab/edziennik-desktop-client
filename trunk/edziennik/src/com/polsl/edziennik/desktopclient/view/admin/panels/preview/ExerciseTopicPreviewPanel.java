package com.polsl.edziennik.desktopclient.view.admin.panels.preview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.ExerciseTopicTableModel;
import com.polsl.edziennik.desktopclient.view.common.panels.SendFilePanel;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.ExerciseTopicSimplePreviewPanel;
import com.polsl.edziennik.modelDTO.exercise.ExerciseTopicDTO;
import com.polsl.edziennik.modelDTO.file.FileDTO;

public class ExerciseTopicPreviewPanel extends ExerciseTopicSimplePreviewPanel {

	private SendFilePanel sendFilePanel;
	private ExerciseTopicTableModel model;
	private Integer selected;

	public ExerciseTopicPreviewPanel(String title, ExerciseTopicTableModel model) {
		super(title);
		this.model = model;
		selected = null;
		filter = factory.createFilter();

	}

	@Override
	public void setComponents() {
		super.setComponents();
		add(sendFilePanel, cc.xyw(1, 21, 4));
	}

	@Override
	public void create() {
		super.create();
		sendFilePanel = new SendFilePanel(filter.getDocumentFilter());
		sendFilePanel.getChoseButton().addActionListener(sendFilePanel.new DefaultListener());
		sendFilePanel.getSendButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FileDTO file = sendFilePanel.getFile();

				if (file == null || file.getContent() == null) {

					JOptionPane.showMessageDialog(null, bundle.getString("fileTransferError"));
				} else {
					sendFilePanel.getSendButton().setEnabled(false);
				}
			}
		});

	}

	@Override
	public void setSelected(int index) {
		selected = index;
	}

	@Override
	public void save() {
		if (currentExercise == null) currentExercise = new ExerciseTopicDTO();
		setExerciseTopicData();

		SaveFileWorker w = new SaveFileWorker(sendFilePanel.getFile());
		w.execute();
		w.startProgress();

	}

	public void setExerciseTopicData() {
		if (currentExercise == null) currentExercise = new ExerciseTopicDTO();
		currentExercise.setNumber(numberText.getText());
		currentExercise.setSubject(subjectText.getText());
		currentExercise.setDescription(descriptionText.getText());

	}

	@Override
	public void clear() {
		super.clear();
		sendFilePanel.clear();
	}

	@Override
	public void delete() {

		try {
			if (model.get(selected) != null) {
				DelegateFactory.getAdminDelegate().deleteExerciseTopic(model.get(selected).getId());
				model.remove(currentExercise);
			}
		} catch (DelegateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		if (!b)
			sendFilePanel.setEnabled(false);
		else
			sendFilePanel.setChooseEnabled(b);
	}

	public void setManual(boolean b) {
		manualButton.setEnabled(b);
	}

	private class SaveFileWorker extends Worker<FileDTO> {
		private FileDTO f;

		public SaveFileWorker(FileDTO f) {
			super("save");
			this.f = f;
		}

		@Override
		protected FileDTO doInBackground() throws Exception {
			if (f != null) return DelegateFactory.getCommonDelegate().addFile(f);
			return null;

		}

		@Override
		public void done() {
			stopProgress();
			try {

				FileDTO tmp = get();
				if (tmp != null) {
					f.setId(tmp.getId());

					currentExercise.setManualId(tmp.getId());
				}

				try {

					if (currentExercise.getId() == null) {
						currentExercise = DelegateFactory.getAdminDelegate().addExerciseTopic(currentExercise);
						model.add(currentExercise);
					} else {
						DelegateFactory.getAdminDelegate().updateExerciseTopic(currentExercise);
						model.update(currentExercise, selected);
					}

				} catch (DelegateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
