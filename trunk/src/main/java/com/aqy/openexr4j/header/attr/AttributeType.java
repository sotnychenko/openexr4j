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
 * AttributeType.java
 * @since 2009-04-18
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

/**
 * AttributeType.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-18
 * @version $Id$
 *
 */
public enum AttributeType {
	box2i {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.Box2i;
		}
	},
	box2f {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.Box2f;
		}
	},
	chlist {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.Chlist;
		}
	},
	chromaticities {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.Chromaticities;
		}
	},
	compression {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.Compression;
		}
	},
	double_literal {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.DoubleLiteral;
		}
	},
	envmap {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.Envmap;
		}
	},
	float_literal {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.FloatLiteral;
		}
	},
	int_literal {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.IntegerLiteral;
		}
	},
	keycode {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.Keycode;
		}
	},
	lineOrder {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.LineOrder;
		}
	},
	m33f {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.M33f;
		}
	},
	m44f {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.M44f;
		}
	},
	preview {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.Preview;
		}
	},
	rational {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.Rational;
		}
	},
	string {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.StringLiteral;
		}
	},
	tiledesc {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.TileDesc;
		}
	},
	timecode {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.TimeCode;
		}
	},
	v2i {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.V2i;
		}
	},
	v2f {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.V2f;
		}
	},
	v3i {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.V3i;
		}
	},
	v3f {
		@Override
		public AttributeReader getReader() {
			return AttributeReader.V3f;
		}
	};
	
	/**
	 * The attribute might have java keyword as its value. We might 
	 * need to append some suffix to the value here.
	 * 
	 * @param s string of possible attribute type
	 * @return the actual AttributeType
	 * 
	 * */
	public static AttributeType safeValueOf(String s) {
		StringBuilder sb = new StringBuilder(s);
		if("double".equals(s) || "float".equals(s) || "int".equals(s)) {
			sb.append("_literal");
		}
		return valueOf(sb.toString());
	}

	/**
	 * Gets the attribute reader, which understands the raw data.
	 * 
	 * @return the attribute reader
	 * */
	public abstract AttributeReader getReader();
}
