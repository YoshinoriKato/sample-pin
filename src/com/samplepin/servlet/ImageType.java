package com.samplepin.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageType {
	public static enum Format {
		JPEG(ImageType.JPEG), BMP(ImageType.BMP), GIF(ImageType.GIF), PNG(
				ImageType.PNG), TIFF(ImageType.TIFF), PICT(ImageType.PICT), UNKNOWN(
				ImageType.UNKNOWN);
		public final String	name;

		private Format(String s) {
			this.name = s;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}

	public static final String	JPEG	= "jpg", BMP = "bmp", GIF = "gif",
			PNG = "png", TIFF = "tif", PICT = "pic", UNKNOWN = "UNKNOWN";

	// formats
	private static final long	jpeg	= 0xffd8000000000000L,
			png = 0x89504E470D0A1A0AL, gif = 0x4749460000000000L,
			tiff1 = 0x4949000000000000L, tiff2 = 0x4D4D000000000000L,
			bmp = 0x424D000000000000L, pic = 0x5049430000000000L;

	public static Format getFormat(byte[] b) {
		long l;
		if (b.length < 8) {
			return Format.UNKNOWN;
		}
		l = ((long) b[0] << 56) | ((b[1] & 0xffL) << 48)
				| ((b[2] & 0xffL) << 40) | ((b[3] & 0xffL) << 32)
				| ((b[4] & 0xffL) << 24) | ((b[5] & 0xffL) << 16)
				| ((b[6] & 0xffL) << 8) | (b[7] & 0xffL);
		if ((l & jpeg) == jpeg) {
			return Format.JPEG;
		} else if ((l & png) == png) {
			return Format.PNG;
		} else if ((l & gif) == gif) {
			return Format.GIF;
		} else if (((l & tiff1) == tiff1) || ((l & tiff2) == tiff2)) {
			return Format.TIFF;
		} else if ((l & bmp) == bmp) {
			return Format.BMP;
		} else if ((l & pic) == pic) {
			return Format.PICT;
		}
		return Format.UNKNOWN;
	}

	public static Format getFormat(File f) throws IOException {
		FileInputStream i = null;
		try {
			i = new FileInputStream(f);
			return getFormat(i);
		} finally {
			if (i != null) {
				i.close();
			}
		}
	}

	public static Format getFormat(InputStream i) throws IOException {
		i.mark(8);
		byte[] buf = new byte[8];
		int r = i.read(buf, 0, 8);
		// i.reset();
		if (r != 8) {
			return Format.UNKNOWN;
		}
		return getFormat(buf);
	}
}