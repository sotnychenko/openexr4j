/**
 * Copyright (c) 2009, A.Q.Yang.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are 
 * met:
 *
 *    * Redistributions of source code must retain the above 
 *      copyright notice, this list of conditions and the following 
 *      disclaimer.
 *      
 *    * Redistributions in binary form must reproduce the above 
 *      copyright notice, this list of conditions and the following 
 *      disclaimer in the documentation and/or other materials provided 
 *      with the distribution.
 *    
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS 
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 * AttributeReader.java
 * @since 2009-04-24
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

/**
 * AttributeReader.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-24
 * @version $Id$
 *
 */
public interface AttributeReader {
	
	/**
	 * Four <i>int</i>s: xMin, yMin, xMax, yMax
	 * 
	 * */
	public static final Box2i Box2i = new Box2i();
	
	/**
	 * Four <i>float</i>s: xMin, yMin, xMax, yMax
	 * 
	 * */
	public static final Box2f Box2f = new Box2f();
	
	/**
	 * Eight <i>float</i>s: redX, redY, greenX, greenY, blueX, 
	 * blueY, whiteX, whiteY
	 * 
	 * */
	public static final Chromaticities Chromaticities = new Chromaticities();
	
	/**
	 * <i>float</i>
	 * 
	 * */
	public static final FloatLiteral FloatLiteral = new FloatLiteral();
	
	/**
	 * <i>double</i>
	 * 
	 * */
	public static final DoubleLiteral DoubleLiteral = new DoubleLiteral();
	
	/**
	 * <i>unsigned char</i>, possible values are<br>
	 * NO_COMPRESSION = 0<br>
	 * REL_COMPRESSION = 1<br>
	 * ZIPS_COMPRESSION = 2<br>
	 * ZIP_COMPRESSION = 3<br>
	 * PIZ_COMPRESSION = 4<br>
	 * PXR24_COMPRESSION = 5<br>
	 * B44_COMPRESSION = 6<br>
	 * B44A_COMPRESSION = 7
	 * 
	 * */
	public static final Compression Compression = new Compression();
	
	/**
	 * <i>unsigned char</i>, possible values are<br>
	 * ENVMAP_LATLONG = 0<br>
	 * ENVMAP_CUBE = 1
	 * 
	 * */
	public static final Envmap Envmap = new Envmap();
	
	/**
	 * <i>int</i>
	 * 
	 * */
	public static final IntegerLiteral IntegerLiteral = new IntegerLiteral();
	
	/**
	 * Seven <i>int</i>s: filmMfcCode, filmType, prefix, count, perfOffset, 
	 * perfsPerFrame, perfsPerCount
	 * 
	 * */
	public static final Keycode Keycode = new Keycode();
	
	/**
	 * <i>unsigned char</i>, possible values are<br>
	 * INCREASING_Y = 0<br>
	 * DECREASING_Y = 1<br>
	 * RANDOM_Y = 2
	 * 
	 * */
	public static final LineOrder LineOrder = new LineOrder();
	
	/**
	 * 9 <i>float</i>s
	 * 
	 * */
	public static final M33f M33f = new M33f();
	
	/**
	 * 16 <i>float</i>s
	 * 
	 * */
	public static final M44f M44f = new M44f();
	
	/**
	 * Two <i>unsigned int</i>s: timeAndFlags, userData
	 * 
	 * */
	public static final TimeCode TimeCode = new TimeCode();
	
	/**
	 * Two <i>float</i>s.
	 * 
	 * */
	public static final V2f V2f = new V2f();
	
	/**
	 * Two <i>int</i>s.
	 * 
	 * */
	public static final V2i V2i = new V2i();
	
	/**
	 * Three <i>float</i>s.
	 * 
	 * */
	public static final V3f V3f = new V3f();
	
	/**
	 * Three <i>int</i>s.
	 * 
	 * */
	public static final V3i V3i = new V3i();
	
	/**
	 * A sequence of channels followed by a null byte (0x00).<br>
	 * <br>
	 * Channel layout: <br>
	 * name -- zero-terminated string, from 1 to 31 bytes long<br>
	 * pixel type -- <i>int</i>, possible values are UINT = 0, HALF = 1, FLOAT = 2<br>
	 * pLinear -- <i>unsigned char</i>, possible values are 0 and 1<br>
	 * reserved -- three <i>char</i>s, should be zero<br>
	 * xSampling -- <i>int</i>
	 * ySampling -- <i>int</i>
	 * 
	 * */
	public static final Chlist Chlist = new Chlist();
	
	/**
	 * Tow <i>unsigned int</i>s: xSize, ySize, followed by mode, of type <i>
	 * unsigned char</i>, where<br>
	 * mode = levelMode + roundingMode x 16<br>
	 * <br>
	 * Possible values for levelMode:<br>
	 * ONE_LEVEL = 0<br>
	 * MIPMAP_LEVELS = 1<br>
	 * RIPMAP_LEVELS = 2<br>
	 * <br>
	 * Possible values for roundingMode:<br>
	 * ROUND_DOWN = 0<br>
	 * ROUND_UP = 1
	 * 
	 * */
	public static final TileDesc TileDesc = new TileDesc();
	
	/**
	 * An <i>int</i>, followed by an <i>unsigned int</i>.
	 * 
	 * */
	public static final Rational Rational = new Rational();
	
	/**
	 * String length, of type <i>int</i>, followed by a sequence of <i>char</i>s.
	 * 
	 * */
	public static final StringLiteral StringLiteral = new StringLiteral();
	
	/**
	 * Two <i>unsigned int</i>s: width, height, followed by 4xwidthxheight <i>unsigned char
	 * </i>s of pixel data.<br>
	 * <br>
	 * Scan lines are stored top to bottom, within a scan line pixels are stored 
	 * from left to right. A pixel consists of four <i>unsigned char</i>s, R, G, B, A.
	 * 
	 * */
	public static final Preview Preview = new Preview();

	/**
	 * Checks if the length of data is acceptable for the reader.
	 * 
	 * @param length the specified length
	 * @return if the reader accepts the length
	 * 
	 * */
	public boolean accept(int length);
	
	/**
	 * Checks if the raw data is valid for the reader.
	 * 
	 * @param byteArray raw data
	 * @return if the reader accepts the raw data.
	 * 
	 * */
	public boolean accept(byte[] byteArray);
}
