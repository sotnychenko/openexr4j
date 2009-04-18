package com.aqy.openexr4j.data;

import java.util.Date;
import java.util.Random;

import junit.framework.TestCase;

public class HalfDataTypeTestCase extends TestCase {

	public void testMinus124dot0625() throws Exception {
		int s = 1;
		int e = 0x15;
		int m = 0x3C1;
		float significand = 1.f;
		for(int i=0; i<10; ++i) {
			int mask = 1 << (9-i);
			significand += Math.pow(2.f, -1-i) * ((m & mask) != 0 ? 1 : 0);
		}
		float h = (float)(Math.pow(-1, s) * Math.pow(2.f, e-15) * significand);
		assertEquals(-124.0625f, h);
	}

	public void testZero() throws Exception {
		Half zero = new Half(0.f);
		assertEquals(0.f, zero.floatValue());
		assertEquals(true, zero.isZero());

		zero = new Half(-0.f);
		assertEquals(-0.f, zero.floatValue());
		assertEquals(true, zero.isZero());
	}

	public void testNan() throws Exception {
		Half nan = new Half(Float.NaN);
		assertEquals(Float.NaN, nan.floatValue());
		assertEquals(true, nan.isNaN());

		nan = new Half(-Float.NaN);
		assertEquals(-Float.NaN, nan.floatValue());
		assertEquals(true, nan.isNaN());
	}

	public void testInfinity() throws Exception {
		Half infinity = new Half(Float.POSITIVE_INFINITY);
		assertEquals(Float.POSITIVE_INFINITY, infinity.floatValue());
		assertEquals(true, infinity.isInfinity());
		assertEquals(false, infinity.isFinite());

		infinity = new Half(Float.NEGATIVE_INFINITY);
		assertEquals(Float.NEGATIVE_INFINITY, infinity.floatValue());
		assertEquals(true, infinity.isInfinity());
		assertEquals(false, infinity.isFinite());
	}

	public void testWhiteBoxNegation() throws Exception {
		Half positiveZero = new Half(0.f);
		Half negativeZero = new Half(-0.f);
		Half random = new Half(-124.0625f);
		Half integer = new Half(1.0f);
		Half positiveInfinity = Half.POSITIVE_INFINITY;
		Half negativeInfinity = Half.NEGATIVE_INFINITY;
		Half NaN = Half.NaN;

		assertTrue(positiveZero.negate().isZero());
		assertTrue(negativeZero.negate().isZero());
		assertEquals(124.0625f, random.negate().floatValue());
		assertEquals(-1.f, integer.negate().floatValue());
		assertTrue(positiveInfinity.negate().isInfinity());
		assertTrue(negativeInfinity.negate().isInfinity());
		assertTrue(NaN.negate().isNaN());
	}

	public void testWhiteBoxMultiplication() throws Exception {
		Half positiveZero = new Half(0.f);
		Half negativeZero = new Half(-0.f);
		Half random = new Half(-124.0625f);
		Half integer = new Half(1.0f);
		Half positiveInfinity = Half.POSITIVE_INFINITY;
		Half negativeInfinity = Half.NEGATIVE_INFINITY;
		Half NaN = Half.NaN;

		assertTrue(positiveZero.mul(positiveZero.floatValue()).isZero());
		assertTrue(positiveZero.mul(negativeZero.floatValue()).isZero());
		assertTrue(positiveZero.mul(random.floatValue()).isZero());
		assertTrue(positiveZero.mul(integer.floatValue()).isZero());
		assertTrue(positiveZero.mul(positiveInfinity.floatValue()).isNaN());
		assertTrue(positiveZero.mul(negativeInfinity.floatValue()).isNaN());
		assertTrue(positiveZero.mul(NaN.floatValue()).isNaN());

		assertTrue(negativeZero.mul(positiveZero.floatValue()).isZero());
		assertTrue(negativeZero.mul(negativeZero.floatValue()).isZero());
		assertTrue(negativeZero.mul(random.floatValue()).isZero());
		assertTrue(negativeZero.mul(integer.floatValue()).isZero());
		assertTrue(negativeZero.mul(positiveInfinity.floatValue()).isNaN());
		assertTrue(negativeZero.mul(negativeInfinity.floatValue()).isNaN());
		assertTrue(negativeZero.mul(NaN.floatValue()).isNaN());

		assertTrue(random.mul(positiveZero.floatValue()).isZero());
		assertTrue(random.mul(negativeZero.floatValue()).isZero());
		assertEquals(15391.50391f, random.mul(random.floatValue()).floatValue(), 0.5f);
		assertEquals(random.floatValue(), random.mul(integer.floatValue()).floatValue());
		assertTrue(random.mul(positiveInfinity.floatValue()).isInfinity());
		assertTrue(random.mul(negativeInfinity.floatValue()).isInfinity());
		assertTrue(random.mul(NaN.floatValue()).isNaN());

		assertTrue(integer.mul(positiveZero.floatValue()).isZero());
		assertTrue(integer.mul(negativeZero.floatValue()).isZero());
		assertEquals(random.floatValue(), integer.mul(random.floatValue()).floatValue());
		assertEquals(integer.floatValue(), integer.mul(integer.floatValue()).floatValue());
		assertTrue(integer.mul(positiveInfinity.floatValue()).isInfinity());
		assertTrue(integer.mul(negativeInfinity.floatValue()).isInfinity());
		assertTrue(integer.mul(NaN.floatValue()).isNaN());

		assertTrue(positiveInfinity.mul(positiveZero.floatValue()).isNaN());
		assertTrue(positiveInfinity.mul(negativeZero.floatValue()).isNaN());
		assertTrue(positiveInfinity.mul(random.floatValue()).isInfinity());
		assertTrue(positiveInfinity.mul(integer.floatValue()).isInfinity());
		assertTrue(positiveInfinity.mul(positiveInfinity.floatValue()).isInfinity());
		assertTrue(positiveInfinity.mul(negativeInfinity.floatValue()).isInfinity());
		assertTrue(positiveInfinity.mul(NaN.floatValue()).isNaN());

		assertTrue(negativeInfinity.mul(positiveZero.floatValue()).isNaN());
		assertTrue(negativeInfinity.mul(negativeZero.floatValue()).isNaN());
		assertTrue(negativeInfinity.mul(random.floatValue()).isInfinity());
		assertTrue(negativeInfinity.mul(integer.floatValue()).isInfinity());
		assertTrue(negativeInfinity.mul(positiveInfinity.floatValue()).isInfinity());
		assertTrue(negativeInfinity.mul(negativeInfinity.floatValue()).isInfinity());
		assertTrue(negativeInfinity.mul(NaN.floatValue()).isNaN());

		assertTrue(NaN.mul(positiveZero.floatValue()).isNaN());
		assertTrue(NaN.mul(negativeZero.floatValue()).isNaN());
		assertTrue(NaN.mul(random.floatValue()).isNaN());
		assertTrue(NaN.mul(integer.floatValue()).isNaN());
		assertTrue(NaN.mul(positiveInfinity.floatValue()).isNaN());
		assertTrue(NaN.mul(negativeInfinity.floatValue()).isNaN());
		assertTrue(NaN.mul(NaN.floatValue()).isNaN());
	}

	public void testWhiteBoxDivision() throws Exception {
		Half positiveZero = new Half(0.f);
		Half negativeZero = new Half(-0.f);
		Half random = new Half(-124.0625f);
		Half integer = new Half(1.0f);
		Half positiveInfinity = Half.POSITIVE_INFINITY;
		Half negativeInfinity = Half.NEGATIVE_INFINITY;
		Half NaN = Half.NaN;

		assertTrue(positiveZero.div(positiveZero.floatValue()).isNaN());
		assertTrue(positiveZero.div(negativeZero.floatValue()).isNaN());
		assertTrue(positiveZero.div(random.floatValue()).isZero());
		assertTrue(positiveZero.div(integer.floatValue()).isZero());
		assertTrue(positiveZero.div(positiveInfinity.floatValue()).isZero());
		assertTrue(positiveZero.div(negativeInfinity.floatValue()).isZero());
		assertTrue(positiveZero.div(NaN.floatValue()).isNaN());

		assertTrue(negativeZero.div(positiveZero.floatValue()).isNaN());
		assertTrue(negativeZero.div(negativeZero.floatValue()).isNaN());
		assertTrue(negativeZero.div(random.floatValue()).isZero());
		assertTrue(negativeZero.div(integer.floatValue()).isZero());
		assertTrue(negativeZero.div(positiveInfinity.floatValue()).isZero());
		assertTrue(negativeZero.div(negativeInfinity.floatValue()).isZero());
		assertTrue(negativeZero.div(NaN.floatValue()).isNaN());

		assertTrue(random.div(positiveZero.floatValue()).isInfinity());
		assertTrue(random.div(negativeZero.floatValue()).isInfinity());
		assertEquals(1.f, random.div(random.floatValue()).floatValue());
		assertEquals(random.floatValue(), random.div(integer.floatValue()).floatValue());
		assertTrue(random.div(positiveInfinity.floatValue()).isZero());
		assertTrue(random.div(negativeInfinity.floatValue()).isZero());
		assertTrue(random.div(NaN.floatValue()).isNaN());

		assertTrue(integer.div(positiveZero.floatValue()).isInfinity());
		assertTrue(integer.div(negativeZero.floatValue()).isInfinity());
		assertEquals(-0.00806045f, integer.div(random.floatValue()).floatValue(), 1e-5);
		assertEquals(1.f, integer.div(integer.floatValue()).floatValue());
		assertTrue(integer.div(positiveInfinity.floatValue()).isZero());
		assertTrue(integer.div(negativeInfinity.floatValue()).isZero());
		assertTrue(integer.div(NaN.floatValue()).isNaN());

		assertTrue(positiveInfinity.div(positiveZero.floatValue()).isInfinity());
		assertTrue(positiveInfinity.div(negativeZero.floatValue()).isInfinity());
		assertTrue(positiveInfinity.div(random.floatValue()).isInfinity());
		assertTrue(positiveInfinity.div(integer.floatValue()).isInfinity());
		assertTrue(positiveInfinity.div(positiveInfinity.floatValue()).isNaN());
		assertTrue(positiveInfinity.div(negativeInfinity.floatValue()).isNaN());
		assertTrue(positiveInfinity.div(NaN.floatValue()).isNaN());

		assertTrue(negativeInfinity.div(positiveZero.floatValue()).isInfinity());
		assertTrue(negativeInfinity.div(negativeZero.floatValue()).isInfinity());
		assertTrue(negativeInfinity.div(random.floatValue()).isInfinity());
		assertTrue(negativeInfinity.div(integer.floatValue()).isInfinity());
		assertTrue(negativeInfinity.div(positiveInfinity.floatValue()).isNaN());
		assertTrue(negativeInfinity.div(negativeInfinity.floatValue()).isNaN());
		assertTrue(negativeInfinity.div(NaN.floatValue()).isNaN());

		assertTrue(NaN.div(positiveZero.floatValue()).isNaN());
		assertTrue(NaN.div(negativeZero.floatValue()).isNaN());
		assertTrue(NaN.div(random.floatValue()).isNaN());
		assertTrue(NaN.div(integer.floatValue()).isNaN());
		assertTrue(NaN.div(positiveInfinity.floatValue()).isNaN());
		assertTrue(NaN.div(negativeInfinity.floatValue()).isNaN());
		assertTrue(NaN.div(NaN.floatValue()).isNaN());
	}

	public void testWhiteBoxAddition() throws Exception {
		Half positiveZero = new Half(0.f);
		Half negativeZero = new Half(-0.f);
		Half random = new Half(-124.0625f);
		Half integer = new Half(1.0f);
		Half positiveInfinity = Half.POSITIVE_INFINITY;
		Half negativeInfinity = Half.NEGATIVE_INFINITY;
		Half NaN = Half.NaN;

		assertTrue(positiveZero.add(positiveZero.floatValue()).isZero());
		assertTrue(positiveZero.add(negativeZero.floatValue()).isZero());
		assertEquals(random.floatValue(), positiveZero.add(random.floatValue()).floatValue());
		assertEquals(integer.floatValue(), positiveZero.add(integer.floatValue()).floatValue());
		assertTrue(positiveZero.add(positiveInfinity.floatValue()).isInfinity());
		assertTrue(positiveZero.add(negativeInfinity.floatValue()).isInfinity());
		assertTrue(positiveZero.add(NaN.floatValue()).isNaN());

		assertTrue(negativeZero.add(positiveZero.floatValue()).isZero());
		assertTrue(negativeZero.add(negativeZero.floatValue()).isZero());
		assertEquals(random.floatValue(), negativeZero.add(random.floatValue()).floatValue());
		assertEquals(integer.floatValue(), negativeZero.add(integer.floatValue()).floatValue());
		assertTrue(negativeZero.add(positiveInfinity.floatValue()).isInfinity());
		assertTrue(negativeZero.add(negativeInfinity.floatValue()).isInfinity());
		assertTrue(negativeZero.add(NaN.floatValue()).isNaN());

		assertEquals(random.floatValue(), random.add(positiveZero.floatValue()).floatValue());
		assertEquals(random.floatValue(), random.add(negativeZero.floatValue()).floatValue());
		assertEquals(random.floatValue() * 2.f, random.add(random.floatValue()).floatValue());
		assertEquals(random.floatValue() + 1.f, random.add(integer.floatValue()).floatValue());
		assertTrue(random.add(positiveInfinity.floatValue()).isInfinity());
		assertTrue(random.add(negativeInfinity.floatValue()).isInfinity());
		assertTrue(random.add(NaN.floatValue()).isNaN());

		assertEquals(integer.floatValue(), integer.add(positiveZero.floatValue()).floatValue());
		assertEquals(integer.floatValue(), integer.add(negativeZero.floatValue()).floatValue());
		assertEquals(random.floatValue() + 1.f, integer.add(random.floatValue()).floatValue());
		assertEquals(integer.floatValue() * 2.f, integer.add(integer.floatValue()).floatValue());
		assertTrue(integer.add(positiveInfinity.floatValue()).isInfinity());
		assertTrue(integer.add(negativeInfinity.floatValue()).isInfinity());
		assertTrue(integer.add(NaN.floatValue()).isNaN());

		assertTrue(positiveInfinity.add(positiveZero.floatValue()).isInfinity());
		assertTrue(positiveInfinity.add(negativeZero.floatValue()).isInfinity());
		assertTrue(positiveInfinity.add(random.floatValue()).isInfinity());
		assertTrue(positiveInfinity.add(integer.floatValue()).isInfinity());
		assertTrue(positiveInfinity.add(positiveInfinity.floatValue()).isInfinity());
		assertTrue(positiveInfinity.add(negativeInfinity.floatValue()).isNaN());
		assertTrue(positiveInfinity.add(NaN.floatValue()).isNaN());

		assertTrue(negativeInfinity.add(positiveZero.floatValue()).isInfinity());
		assertTrue(negativeInfinity.add(negativeZero.floatValue()).isInfinity());
		assertTrue(negativeInfinity.add(random.floatValue()).isInfinity());
		assertTrue(negativeInfinity.add(integer.floatValue()).isInfinity());
		assertTrue(negativeInfinity.add(positiveInfinity.floatValue()).isNaN());
		assertTrue(negativeInfinity.add(negativeInfinity.floatValue()).isInfinity());
		assertTrue(negativeInfinity.add(NaN.floatValue()).isNaN());

		assertTrue(NaN.add(positiveZero.floatValue()).isNaN());
		assertTrue(NaN.add(negativeZero.floatValue()).isNaN());
		assertTrue(NaN.add(random.floatValue()).isNaN());
		assertTrue(NaN.add(integer.floatValue()).isNaN());
		assertTrue(NaN.add(positiveInfinity.floatValue()).isNaN());
		assertTrue(NaN.add(negativeInfinity.floatValue()).isNaN());
		assertTrue(NaN.add(NaN.floatValue()).isNaN());
	}

	public void testWhiteBoxSubtraction() throws Exception {
		Half positiveZero = new Half(0.f);
		Half negativeZero = new Half(-0.f);
		Half random = new Half(-124.0625f);
		Half integer = new Half(1.0f);
		Half positiveInfinity = Half.POSITIVE_INFINITY;
		Half negativeInfinity = Half.NEGATIVE_INFINITY;
		Half NaN = Half.NaN;

		assertTrue(positiveZero.sub(positiveZero.floatValue()).isZero());
		assertTrue(positiveZero.sub(negativeZero.floatValue()).isZero());
		assertEquals(-random.floatValue(), positiveZero.sub(random.floatValue()).floatValue());
		assertEquals(-integer.floatValue(), positiveZero.sub(integer.floatValue()).floatValue());
		assertTrue(positiveZero.sub(positiveInfinity.floatValue()).isInfinity());
		assertTrue(positiveZero.sub(negativeInfinity.floatValue()).isInfinity());
		assertTrue(positiveZero.sub(NaN.floatValue()).isNaN());

		assertTrue(negativeZero.sub(positiveZero.floatValue()).isZero());
		assertTrue(negativeZero.sub(negativeZero.floatValue()).isZero());
		assertEquals(-random.floatValue(), negativeZero.sub(random.floatValue()).floatValue());
		assertEquals(-integer.floatValue(), negativeZero.sub(integer.floatValue()).floatValue());
		assertTrue(negativeZero.sub(positiveInfinity.floatValue()).isInfinity());
		assertTrue(negativeZero.sub(negativeInfinity.floatValue()).isInfinity());
		assertTrue(negativeZero.sub(NaN.floatValue()).isNaN());

		assertEquals(random.floatValue(), random.sub(positiveZero.floatValue()).floatValue());
		assertEquals(random.floatValue(), random.sub(negativeZero.floatValue()).floatValue());
		assertEquals(0.f, random.sub(random.floatValue()).floatValue());
		assertEquals(random.floatValue() - 1.f, random.sub(integer.floatValue()).floatValue());
		assertTrue(random.sub(positiveInfinity.floatValue()).isInfinity());
		assertTrue(random.sub(negativeInfinity.floatValue()).isInfinity());
		assertTrue(random.sub(NaN.floatValue()).isNaN());

		assertEquals(integer.floatValue(), integer.sub(positiveZero.floatValue()).floatValue());
		assertEquals(integer.floatValue(), integer.sub(negativeZero.floatValue()).floatValue());
		assertEquals(1.f - random.floatValue(), integer.sub(random.floatValue()).floatValue());
		assertEquals(0.f, integer.sub(integer.floatValue()).floatValue());
		assertTrue(integer.sub(positiveInfinity.floatValue()).isInfinity());
		assertTrue(integer.sub(negativeInfinity.floatValue()).isInfinity());
		assertTrue(integer.sub(NaN.floatValue()).isNaN());

		assertTrue(positiveInfinity.sub(positiveZero.floatValue()).isInfinity());
		assertTrue(positiveInfinity.sub(negativeZero.floatValue()).isInfinity());
		assertTrue(positiveInfinity.sub(random.floatValue()).isInfinity());
		assertTrue(positiveInfinity.sub(integer.floatValue()).isInfinity());
		assertTrue(positiveInfinity.sub(positiveInfinity.floatValue()).isNaN());
		assertTrue(positiveInfinity.sub(negativeInfinity.floatValue()).isInfinity());
		assertTrue(positiveInfinity.sub(NaN.floatValue()).isNaN());

		assertTrue(negativeInfinity.sub(positiveZero.floatValue()).isInfinity());
		assertTrue(negativeInfinity.sub(negativeZero.floatValue()).isInfinity());
		assertTrue(negativeInfinity.sub(random.floatValue()).isInfinity());
		assertTrue(negativeInfinity.sub(integer.floatValue()).isInfinity());
		assertTrue(negativeInfinity.sub(positiveInfinity.floatValue()).isInfinity());
		assertTrue(negativeInfinity.sub(negativeInfinity.floatValue()).isNaN());
		assertTrue(negativeInfinity.sub(NaN.floatValue()).isNaN());

		assertTrue(NaN.sub(positiveZero.floatValue()).isNaN());
		assertTrue(NaN.sub(negativeZero.floatValue()).isNaN());
		assertTrue(NaN.sub(random.floatValue()).isNaN());
		assertTrue(NaN.sub(integer.floatValue()).isNaN());
		assertTrue(NaN.sub(positiveInfinity.floatValue()).isNaN());
		assertTrue(NaN.sub(negativeInfinity.floatValue()).isNaN());
		assertTrue(NaN.sub(NaN.floatValue()).isNaN());
	}

	public void testWhiteBoxArithmeticsSamples() throws Exception {
		Random random = new Random(new Date().getTime());
		for(int i=0, n=1<<16; i<n; ++i) {
			int j = random.nextInt(n);
			float fi = Float.intBitsToFloat(i);
			float fj = Float.intBitsToFloat(j);
			float f =  fi * fj;
			FP16Bit h = new Half(fi).mul(fj);
			assertEquals(f, h.floatValue());
		}
	}
}
