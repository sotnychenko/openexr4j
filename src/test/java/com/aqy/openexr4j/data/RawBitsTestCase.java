/**
 * 
 */
package com.aqy.openexr4j.data;

import junit.framework.TestCase;

/**
 * @author Alfred Q Yang
 *
 */
public class RawBitsTestCase extends TestCase {

	public void testZeroToFloatRawBits() throws Exception {
		int zero = 0x0000;
		int zeroRawBits = HalfUtils.toFloatRawBits(zero);
		assertEquals(Float.floatToIntBits(0.f), zeroRawBits);
		assertEquals(HalfUtils.toFP16BitRawBits(zeroRawBits), (short)zero);
		assertEquals(new Half((short)zero).floatValue(), 0.f);
		
		int minusZero = 0x8000;
		int minusZeroRawBits = HalfUtils.toFloatRawBits(minusZero);
		assertEquals(Float.floatToIntBits(-0.f), minusZeroRawBits);
		assertEquals(HalfUtils.toFP16BitRawBits(minusZeroRawBits), (short)minusZero);
		assertEquals(new Half((short)minusZero).floatValue(), -0.f);
	}
	
	public void testDenormalizedToFloatRawBits() throws Exception {
		int denormalized = 0x00BB;
		int denormalizedRawBits = HalfUtils.toFloatRawBits(denormalized);
		assertEquals(0x373B0000, denormalizedRawBits);
		assertEquals(HalfUtils.toFP16BitRawBits(denormalizedRawBits), (short)denormalized);
		Float f = Float.intBitsToFloat(0x373B0000);
		assertEquals(new Half((short)denormalized).floatValue(), f);
	}
	
	public void testInfinityToFloatRawBits() throws Exception {
		int positiveInfinity = Half.halfToRawShortBits(Half.POSITIVE_INFINITY);
		int positiveInfinityRawBits = HalfUtils.toFloatRawBits(positiveInfinity);
		assertEquals(Float.floatToIntBits(Float.POSITIVE_INFINITY), positiveInfinityRawBits);
		assertEquals(HalfUtils.toFP16BitRawBits(positiveInfinityRawBits), (short)positiveInfinity);
		assertEquals(new Half((short)positiveInfinity).floatValue(), Float.POSITIVE_INFINITY);
		
		int negativeInfinity = Half.halfToRawShortBits(Half.NEGATIVE_INFINITY);
		int negativeInfinityRawBits = HalfUtils.toFloatRawBits(negativeInfinity);
		assertEquals(Float.floatToIntBits(Float.NEGATIVE_INFINITY), negativeInfinityRawBits);
		assertEquals(HalfUtils.toFP16BitRawBits(negativeInfinityRawBits), (short)negativeInfinity);
		assertEquals(new Half((short)negativeInfinity).floatValue(), Float.NEGATIVE_INFINITY);
	}
	
	public void testNanToFloatRawBits() throws Exception {
		int NaN = Half.halfToRawShortBits(Half.NaN);
		int NaNRawBits = HalfUtils.toFloatRawBits(NaN);
		assertEquals(Float.floatToIntBits(Float.NaN), NaNRawBits);
		assertEquals(HalfUtils.toFP16BitRawBits(NaNRawBits), (short)NaN);
		assertEquals(new Half((short)NaN).floatValue(), Float.NaN);
	}
	
	public void testConstant() throws Exception {
		assertEquals(Float.intBitsToFloat(0x7f800000), Half.POSITIVE_INFINITY.floatValue());
		assertEquals(Float.intBitsToFloat(0xff800000), Half.NEGATIVE_INFINITY.floatValue());
		assertEquals(Float.intBitsToFloat(0x7fc00000), Half.NaN.floatValue());
	}
}
