package com.zkteam.discover.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * IO操作相关的工具类
 */
public class IOUtil {

	/**
	 * 关闭输入流
	 * @param input
	 */
	public static void closeInStream(InputStream input) {

		try {

			if (input != null)
				input.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 关闭输出流
	 * @param output
	 */
	public static void closeOutStream(OutputStream output) {

		try {

			if (output != null)
				output.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 关闭字符输入流
	 * @param reader
	 */
	public static void closeReader(Reader reader) {

		try {

			if (reader != null)
				reader.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 关闭字符输出流
	 * @param writer
	 */
	public static void closeWriter(Writer writer) {

		try {

			if (writer != null)
				writer.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
