package com.polsl.edziennik.desktopclient.controller.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.polsl.edziennik.modelDTO.file.FileDTO;

public class FileManager {

	public byte[] getBytesFromFile(File f) {
		if (f == null) return null;

		byte[] b = new byte[(int) f.length()];
		try {
			FileInputStream fileInputStream = new FileInputStream(f);
			fileInputStream.read(b);
		} catch (FileNotFoundException ex) {

			ex.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		return b;

	}

	public FileDTO getFileDTOFromFile(File f) {
		if (f == null) return null;

		byte[] b = new byte[(int) f.length()];
		try {
			FileInputStream fileInputStream = new FileInputStream(f);
			fileInputStream.read(b);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		FileDTO file = new FileDTO(b, f.getName().substring(f.getName().lastIndexOf('.'), f.getName().length()), 
				f.getName().substring(0, f.getName().lastIndexOf('.')));

		return file;
	}

	public void saveFile(FileDTO f, String path) {
		if (f == null) return;

		try {
			FileOutputStream out = new FileOutputStream(path);
			out.write(f.getContent());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
