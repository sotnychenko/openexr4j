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
 * CompressionTestCase.java
 * @since 2009-05-10
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import com.aqy.openexr4j.header.MalformedHeaderException;
import com.aqy.openexr4j.header.attr.Compression.CompressionMethod;
import com.aqy.openexr4j.header.io.ReaderUtils;

/**
 * CompressionTestCase.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-05-10
 * @version $Id$
 *
 */
public class CompressionTestCase extends TestCase {

	public static final byte[] data = {
		0x63, 0x6f, 0x6d, 0x70, 0x72, 0x65, 0x73, 0x73, 0x69, 0x6f, 0x6e, 0x0, // "compression\0"
		0x01, 0x00, 0x00, 0x00, // 1
		0x00, // NONE
	};
	
	public static final byte[] length_too_long = {
		0x63, 0x6f, 0x6d, 0x70, 0x72, 0x65, 0x73, 0x73, 0x69, 0x6f, 0x6e, 0x0, // "compression\0"
		0x02, 0x00, 0x00, 0x00, // 2 -> too long
		0x00, // NONE
	};
	
	public static final byte[] length_too_short = {
		0x63, 0x6f, 0x6d, 0x70, 0x72, 0x65, 0x73, 0x73, 0x69, 0x6f, 0x6e, 0x0, // "compression\0"
		0x00, 0x00, 0x00, 0x00, // 0 -> too short
		0x00, // NONE
	};
	
	public static final byte[] invalid = {
		0x63, 0x6f, 0x6d, 0x70, 0x72, 0x65, 0x73, 0x73, 0x69, 0x6f, 0x6e, 0x0, // "compression\0"
		0x01, 0x00, 0x00, 0x00, // 1
		0x10, // invalid
	};
	
	public void testCompression() throws Exception {
		InputStream stream = new ByteArrayInputStream(data);

		String type = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("compression", type);

		AttributeType at = AttributeType.safeValueOf(type);
		assertEquals(AttributeType.compression, at);

		int length = ReaderUtils.streamReader.readInteger(stream);
		assertEquals(1, length);

		Attribute attribute = AttributeBuilder.build("custom", at, length, stream);
		assertEquals("custom", attribute.getName());
		byte[] byteArray = attribute.getRawData();
		CompressionMethod method = AttributeReader.Compression.get(byteArray);
		assertEquals(CompressionMethod.NO_COMPRESSION, method);
	}
	
	public void testLengthTooLong() throws Exception {
		InputStream stream = new ByteArrayInputStream(length_too_long);

		String type = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("compression", type);

		AttributeType at = AttributeType.safeValueOf(type);
		assertEquals(AttributeType.compression, at);

		int length = ReaderUtils.streamReader.readInteger(stream);
		assertEquals(2, length);

		boolean thrown = false;
		try {
			AttributeBuilder.build("custom", at, length, stream);
		} catch(MalformedHeaderException mhe) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	public void testLengthTooShort() throws Exception {
		InputStream stream = new ByteArrayInputStream(length_too_short);

		String type = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("compression", type);

		AttributeType at = AttributeType.safeValueOf(type);
		assertEquals(AttributeType.compression, at);

		int length = ReaderUtils.streamReader.readInteger(stream);
		assertEquals(0, length);

		boolean thrown = false;
		try {
			AttributeBuilder.build("custom", at, length, stream);
		} catch(MalformedHeaderException mhe) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	public void testInvalid() throws Exception {
		InputStream stream = new ByteArrayInputStream(invalid);

		String type = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("compression", type);

		AttributeType at = AttributeType.safeValueOf(type);
		assertEquals(AttributeType.compression, at);

		int length = ReaderUtils.streamReader.readInteger(stream);
		assertEquals(1, length);

		boolean thrown = false;
		try {
			AttributeBuilder.build("custom", at, length, stream);
		} catch(MalformedHeaderException mhe) {
			thrown = true;
		}
		assertTrue(thrown);
	}
}
