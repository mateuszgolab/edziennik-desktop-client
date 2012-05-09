package com.polsl.edziennik.desktopclient.view.common.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.polsl.edziennik.delegates.CommonDelegate;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.FileChooser;
import com.polsl.edziennik.desktopclient.controller.utils.FileManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.modelDTO.file.FileDTO;

public class DownloadFilePanel extends JPanel {

	protected JButton download;
	protected IGuiComponentAbstractFactory factory;
	protected static FileManager fileManager = new FileManager();
	protected IButton button;
	protected CellConstraints cc;
	protected JFileChooser fileChooser;
	protected FileDTO currentFile;
	protected Integer fileId;

	public DownloadFilePanel(String hint) {

		factory = GuiComponentFactory.getInstance();
		button = factory.createTextButton();
		fileChooser = FileChooser.getDownloadInstance();

		download = button.getButton("downloadButton", hint);
		download.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (fileId != null) {
					FileProvider fp = new FileProvider();
					fp.execute();
					fp.startProgress();
				}
			}
		});

		add(download);

	}

	public JButton getButton() {
		return download;
	}

	public FileDTO getFile() {
		return currentFile;
	}

	public void setFile(FileDTO file) {
		currentFile = file;
		download.setEnabled(true);

	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
		download.setEnabled(true);
	}

	@Override
	public void setEnabled(boolean b) {
		download.setEnabled(b);
	}

	private class FileProvider extends Worker<FileDTO> {

		@Override
		protected FileDTO doInBackground() throws Exception {
			CommonDelegate delegate = DelegateFactory.getCommonDelegate();
			return delegate.getFile(fileId);
		}

		@Override
		public void done() {

			try {

				stopProgress();
				currentFile = get();
				fileChooser.setSelectedFile(new File(currentFile.getName() + currentFile.getExtension()));
				fileChooser.setCurrentDirectory(null);
				fileChooser.setVisible(true);

				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					File f = fileChooser.getSelectedFile();
					fileManager.saveFile(currentFile, f.getAbsolutePath());

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
