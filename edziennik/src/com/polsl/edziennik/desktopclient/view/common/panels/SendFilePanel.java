package com.polsl.edziennik.desktopclient.view.common.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.desktopclient.controller.utils.FileChooser;
import com.polsl.edziennik.desktopclient.controller.utils.FileManager;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.modelDTO.file.FileDTO;

public class SendFilePanel extends JPanel {

	protected JButton send;
	protected JButton chose;
	protected IGuiComponentAbstractFactory factory;
	protected static FileManager fileManager = new FileManager();
	protected ILabel label;
	protected IButton button;
	protected CellConstraints cc;
	protected ImageIcon icon;
	protected JLabel iconLabel;
	protected JLabel fileChosen;
	protected JFileChooser sendFileChooser;
	protected JFileChooser getFileChooser;
	protected FileDTO currentFile;
	protected final ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected JLabel error;
	protected FileNameExtensionFilter filter;

	public SendFilePanel(FileNameExtensionFilter filter) {

		this.filter = filter;
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		FormLayout layout = new FormLayout("pref,30dlu, 60dlu,20dlu, 300dlu,min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref");

		setLayout(layout);

		factory = GuiComponentFactory.getInstance();
		button = factory.createTextButton();
		getFileChooser = FileChooser.getDownloadInstance();
		sendFileChooser = FileChooser.getSendInstance();
		fileChosen = new JLabel(bundle.getString("noFileSelected"));
		iconLabel = new JLabel();

		create();
		setComponents();
		send.setEnabled(false);

	}

	public SendFilePanel(FileNameExtensionFilter filter, String title) {

		this.filter = filter;
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(bundle.getString(title)),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		FormLayout layout = new FormLayout("pref,30dlu, 60dlu,20dlu, 300dlu,min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref");

		setLayout(layout);

		factory = GuiComponentFactory.getInstance();
		button = factory.createTextButton();
		getFileChooser = FileChooser.getDownloadInstance();
		sendFileChooser = FileChooser.getSendInstance();
		fileChosen = new JLabel(bundle.getString("noFileSelected"));
		iconLabel = new JLabel();

		create();

		setComponents();
		send.setEnabled(false);

	}

	public void setComponents() {
		cc = new CellConstraints();

		add(chose, cc.xy(3, 1));
		add(iconLabel, cc.xy(4, 1));
		add(fileChosen, cc.xy(5, 1));
		add(send, cc.xy(3, 3));
		add(error, cc.xyw(3, 9, 4));
	}

	public void create() {
		chose = button.getButton("choseFile", "choseFileHint");

		send = button.getButton("send", "sendFile");

		error = new JLabel();
	}

	public void setIcon(String ext) {
		if (ext.compareTo(".pdf") == 0)
			icon = new ImageIcon(bundle.getString("pdfIcon"));
		else if (ext.compareTo(".ppt") == 0)
			icon = new ImageIcon(bundle.getString("pptIcon"));
		else if (ext.compareTo(".zip") == 0 || ext.compareTo(".rar") == 0)
			icon = new ImageIcon(bundle.getString("zipIcon"));
		else if (ext.compareTo(".xls") == 0 || ext.compareTo(".xlsx") == 0)
			icon = new ImageIcon(bundle.getString("xlsIcon"));
		else if (ext.compareTo(".doc") == 0 || ext.compareTo(".docx") == 0)
			icon = new ImageIcon(bundle.getString("docIcon"));
		else
			icon = new ImageIcon(bundle.getString("noneIcon"));
		iconLabel.setIcon(icon);

	}

	public JButton getSendButton() {
		return send;
	}

	public JButton getChoseButton() {
		return chose;
	}

	public FileDTO getFile() {
		return currentFile;
	}

	public class DefaultListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sendFileChooser.resetChoosableFileFilters();
			sendFileChooser.setFileFilter(filter);
			sendFileChooser.setCurrentDirectory(null);
			sendFileChooser.setVisible(true);
			if (sendFileChooser.showOpenDialog(null) == JFileChooser.OPEN_DIALOG) {

				File f = sendFileChooser.getSelectedFile();
				currentFile = fileManager.getFileDTOFromFile(f);

			}

			if (currentFile != null) {
				fileChosen.setText(currentFile.getName() + currentFile.getExtension());
				setIcon(currentFile.getExtension());
				repaint();
				send.setEnabled(true);
			}

		}
	}

	public class XlsFileListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			sendFileChooser.resetChoosableFileFilters();
			sendFileChooser.setFileFilter(filter);
			sendFileChooser.setCurrentDirectory(null);
			sendFileChooser.setVisible(true);
			if (sendFileChooser.showOpenDialog(null) == JFileChooser.OPEN_DIALOG) {

				File f = sendFileChooser.getSelectedFile();
				currentFile = fileManager.getFileDTOFromFile(f);

			}

			if (currentFile != null) {
				fileChosen.setText(currentFile.getName() + currentFile.getExtension());
				setIcon(currentFile.getExtension());

				if ((currentFile.getExtension().compareTo(".xls") == 0 || currentFile.getExtension().compareTo(".xlsx") == 0)) {
					send.setEnabled(true);
					error.setText("");
				} else {
					send.setEnabled(false);
					error.setText(bundle.getString("errorExtension"));
				}
				repaint();
			}
		}

	}

	@Override
	public void setEnabled(boolean b) {

		chose.setEnabled(b);
		send.setEnabled(b);
	}

	public void setChooseEnabled(boolean b) {
		chose.setEnabled(b);
	}

	public void clear() {

		iconLabel.setIcon(null);
		fileChosen.setText(bundle.getString("noFileSelected"));
		currentFile = null;
		setEnabled(false);

	}

	public class DefaultSendListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			FileDTO file = getFile();

			if (file == null || file.getContent() == null) {

				JOptionPane.showMessageDialog(null, bundle.getString("fileTransferError"));
			} else {
				send.setEnabled(false);
			}
		}
	}

}
