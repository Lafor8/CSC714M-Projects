package io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;

public class XMLFilterInputStream extends FilterInputStream {

	LinkedList<Integer> inQueue = new LinkedList<Integer>();
	LinkedList<Integer> outQueue = new LinkedList<Integer>();
	final byte[] search, replacement;

	public static void main(String[] args) throws Exception {

		byte[] bytes = "hello xyz world.".getBytes("UTF-8");

		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

		byte[] search = "xyz".getBytes("UTF-8");
		byte[] replacement = "abc".getBytes("UTF-8");

		InputStream ris = new XMLFilterInputStream(bis, search, replacement);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		int b;
		while (-1 != (b = ris.read()))
			bos.write(b);

		System.out.println(new String(bos.toByteArray()));

	}

	protected XMLFilterInputStream(InputStream in, byte[] search, byte[] replacement) {
		super(in);
		this.search = search;
		this.replacement = replacement;
	}

	protected XMLFilterInputStream(InputStream in) throws UnsupportedEncodingException {
		super(in);
		this.search = "&".getBytes("UTF-8");
		this.replacement = "&amp;".getBytes("UTF-8");
	}

	private boolean isMatchFound() {
		Iterator<Integer> inIter = inQueue.iterator();
		for (int i = 0; i < search.length; i++)
			if (!inIter.hasNext() || search[i] != inIter.next())
				return false;
		return true;
	}

	private void readAhead() throws IOException {
		// Work up some look-ahead.
		while (inQueue.size() < search.length) {
			int next = super.read();
			inQueue.offer(next);
			if (next == -1)
				break;
		}
	}

	@Override
	public int read() throws IOException {
		System.out.println("HEY");
		// Next byte already determined.
		if (outQueue.isEmpty()) {

			readAhead();

			if (isMatchFound()) {
				for (int i = 0; i < search.length; i++)
					inQueue.remove();

				for (byte b : replacement)
					outQueue.offer((int) b);
			} else
				outQueue.add(inQueue.remove());
		}

		return outQueue.remove();
	}

	public int read(byte[] b) throws IOException {
		System.out.println("HEY1");
		 //Next byte already determined.
		 if (outQueue.isEmpty()) {
		
		 readAhead();
		
		 if (isMatchFound()) {
		 for (int i = 0; i < search.length; i++)
		 inQueue.remove();
		
		 for (byte br : replacement)
		 outQueue.offer((int) br);
		 } else
		 outQueue.add(inQueue.remove());
		 }
		
		 return outQueue.remove();
		//return super.read(b);
	}

	public int read(byte[] b, int off, int len) throws IOException {
		System.out.println("HEY2");
		// Next byte already determined.
		return super.read(b, off, len);
		// if (outQueue.isEmpty()) {
		//
		// readAhead();
		//
		// if (isMatchFound()) {
		// for (int i = 0; i < search.length; i++)
		// inQueue.remove();
		//
		// for (byte b : replacement)
		// outQueue.offer((int) b);
		// } else
		// outQueue.add(inQueue.remove());
		// }
		//
		// return outQueue.remove();
	}

	// TODO: Override the other read methods.
}