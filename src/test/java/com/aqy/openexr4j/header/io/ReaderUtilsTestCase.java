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
 * ReaderUtilsTestCase.java
 * @since 2009-05-09
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import com.aqy.openexr4j.header.MalformedHeaderException;
import com.aqy.openexr4j.header.io.EndOfInputException;
import com.aqy.openexr4j.header.io.ReaderUtils;

/**
 * ReaderUtilsTestCase.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-05-09
 * @version $Id$
 *
 */
public class ReaderUtilsTestCase extends TestCase {

	public static final byte[] data = {
		0x62, 0x6f, 0x78, 0x32, 0x69, 0x0, // "box2i\0"
		0x62,
	};
	
	public static final byte[] no_terminator = {
		0x62, 0x6f, 0x78, 0x32, 0x69, // "box2i"
	};
	
	public static final byte[] empty = {
	};
	
	public static final byte[] zero = {
		0x0
	};
	
	public static final byte[] too_long = {
		0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62,
		0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62,
		0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62,
		0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62,
	};
	
	public void testReadOpenEXRString() throws Exception {
		InputStream stream = new ByteArrayInputStream(data);
		
		String s = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("box2i", s);
		
		boolean thrown = false;
		stream = new ByteArrayInputStream(no_terminator);
		try {
			ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		} catch(MalformedHeaderException mhe) {
			if(mhe.getCause() instanceof EndOfInputException) {
				thrown = true;
			}
		}
		assertTrue(thrown);
		
		thrown = false;
		stream = new ByteArrayInputStream(empty);
		try {
			ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		} catch(MalformedHeaderException mhe) {
			if(mhe.getCause() instanceof EndOfInputException) {
				thrown = true;
			}
		}
		assertTrue(thrown);
		
		thrown = false;
		stream = new ByteArrayInputStream(zero);
		try {
			ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		} catch(MalformedHeaderException mhe) {
			thrown = true;
		}
		assertTrue(thrown);
		
		stream = new ByteArrayInputStream(zero);
		s = ReaderUtils.readOpenEXRStringWithinMaxLenghth(stream);
		assertEquals("", s);
		
		thrown = false;
		stream = new ByteArrayInputStream(too_long);
		try {
			ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		} catch(MalformedHeaderException mhe) {
			thrown = true;
		}
		assertTrue(thrown);
	}
}
