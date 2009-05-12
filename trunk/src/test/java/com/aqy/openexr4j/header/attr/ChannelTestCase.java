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
 * ChannelTestCase.java
 * @since 2009-05-09
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import com.aqy.openexr4j.header.DataType;
import com.aqy.openexr4j.header.MalformedHeaderException;
import com.aqy.openexr4j.header.io.ReaderUtils;

/**
 * ChannelTestCase.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-05-09
 * @version $Id$
 *
 */
public class ChannelTestCase extends TestCase {

	public static byte[] data = {
		0x47, 0x0, // "G\0"
		0x1, 0x0, 0x0, 0x0, // HALF
		0x0, // 0
		0x0, 0x0, 0x0, // {0, 0, 0}
		0x1, 0x0, 0x0, 0x0, // 1
		0x1, 0x0, 0x0, 0x0, // 1
	};
	
	public static byte[] malformed = {
		0x47, 0x0, // "G\0"
		0x1, 0x0, 0x0, 0x0, // HALF
		0x0, // 0
		0x0, 0x0, 0x0, // {0, 0, 0}
		0x1, 0x0, 0x0, // 1
		0x1, 0x0, 0x0, 0x0, // 1
	};
	
	public void testChannel() throws Exception {
		InputStream stream = new ByteArrayInputStream(data);
		
		String name = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("G", name);
		
		ImageChannel channel = ImageChannel.build(name, stream);
		assertEquals(name, channel.getName());
		assertEquals(DataType.HALF, channel.getType());
		assertEquals(0, channel.getPLinear());
		char[] reserved = channel.getReserved();
		assertEquals(3, reserved.length);
		for(int i=0; i<3; ++i) {
			assertEquals(0, reserved[i]);
		}
		assertEquals(1, channel.getXSampling());
		assertEquals(1, channel.getYSampling());
	}
	
	public void testMalformed() throws Exception {
		InputStream stream = new ByteArrayInputStream(malformed);
		
		String name = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("G", name);

		boolean thrown = false;
		try {
			ImageChannel.build(name, stream);
		} catch(MalformedHeaderException mhe) {
			thrown = true;
			System.out.println(mhe.getMessage());
		}
		assertTrue(thrown);
	}
}
