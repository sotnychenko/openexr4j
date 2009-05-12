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
 * Chlist.java
 * @since 2009-04-19
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.aqy.openexr4j.header.MalformedHeaderException;
import com.aqy.openexr4j.header.io.EndOfInputException;
import com.aqy.openexr4j.header.io.ReaderUtils.ReadTillNull;


/**
 * Chlist.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-19
 * @version $Id$
 *
 */
public class Chlist implements AttributeReader {

	public List<ImageChannel> get(byte[] byteArray) throws MalformedHeaderException {
		final List<ImageChannel> channels = new ArrayList<ImageChannel>();
		final InputStream stream = new ByteArrayInputStream(byteArray);
		ReadTillNull readTillNull = new ReadTillNull(stream) {
			@Override
			public void onRead(String s) throws MalformedHeaderException, IOException, EndOfInputException {
				channels.add(ImageChannel.build(s, stream));
			}
		};
		readTillNull.startRead();
		return channels;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * */
	@Override
	public boolean accept(int length) {
		return length > 0;
	}

	@Override
	public boolean accept(byte[] byteArray) {
		boolean accepted = true;
		try {
			get(byteArray);
		} catch(MalformedHeaderException mhe) {
			accepted = false;
		}
		return accepted;
	}
}
