/*
 * Units of Measurement Reference Implementation
 * Copyright (c) 2005-2016, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tec.units.ri.format;

import java.io.IOException;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;
import javax.measure.format.UnitFormat;

import tec.units.ri.AbstractQuantity;
import tec.units.ri.AbstractUnit;
import tec.units.ri.internal.format.l10n.DecimalFormat;
import tec.units.ri.internal.format.l10n.NumberFormat;
import tec.units.ri.quantity.NumberQuantity;
import tec.units.ri.unit.Units;
import tec.uom.lib.common.function.Parser;

/**
 * <p>
 * This class provides the interface for formatting and parsing {@link Quantity quantities}.
 * </p>
 * 
 * <p>
 * Instances of this class should be able to format quantities stated in {@link CompoundUnit}. See {@link #formatCompound formatCompound(...)}.
 * </p>
 * 
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.8, $Date: 2015-12-30 $
 */
@SuppressWarnings("rawtypes")
public abstract class QuantityFormat implements Parser<CharSequence, Quantity> {

  /**
	 * 
	 */
  // private static final long serialVersionUID = -4628006924354248662L;

  /**
   * Holds the default format instance.
   */
  private static final NumberSpaceUnit DEFAULT = new NumberSpaceUnit(NumberFormat.getInstance(), SimpleUnitFormat.getInstance());

  /**
   * Holds the default format instance.
   */
  private static final NumberSpaceUnit EBNF = new NumberSpaceUnit(NumberFormat.getInstance(), SimpleUnitFormat.getInstance());

  /**
   * Holds the standard format instance.
   */
  private static final Standard STANDARD = new Standard();

  /**
   * Returns the quantity format for the default locale. The default format assumes the quantity is composed of a decimal number and a {@link Unit}
   * separated by whitespace(s).
   * 
   * @return <code>MeasureFormat.getInstance(NumberFormat.getInstance(), UnitFormat.getInstance())</code>
   */
  public static QuantityFormat getInstance() {
    return DEFAULT;
  }

  /**
   * Formats the specified quantity into an <code>Appendable</code>.
   * 
   * @param quantity
   *          the quantity to format.
   * @param dest
   *          the appendable destination.
   * @return the specified <code>Appendable</code>.
   * @throws IOException
   *           if an I/O exception occurs.
   */
  public abstract Appendable format(Quantity<?> quantity, Appendable dest) throws IOException;

  /**
   * Parses a portion of the specified <code>CharSequence</code> from the specified position to produce an object. If parsing succeeds, then the index
   * of the <code>cursor</code> argument is updated to the index after the last character used.
   * 
   * @param csq
   *          the <code>CharSequence</code> to parse.
   * @param index
   *          the current parsing index.
   * @return the object parsed from the specified character sub-sequence.
   * @throws IllegalArgumentException
   *           if any problem occurs while parsing the specified character sequence (e.g. illegal syntax).
   */
  abstract AbstractQuantity<?> parse(CharSequence csq, int index) throws IllegalArgumentException, ParserException;

  /**
   * Parses a portion of the specified <code>CharSequence</code> from the specified position to produce an object. If parsing succeeds, then the index
   * of the <code>cursor</code> argument is updated to the index after the last character used.
   * 
   * @param csq
   *          the <code>CharSequence</code> to parse.
   * @param cursor
   *          the cursor holding the current parsing index.
   * @return the object parsed from the specified character sub-sequence.
   * @throws IllegalArgumentException
   *           if any problem occurs while parsing the specified character sequence (e.g. illegal syntax).
   */
  /*
   * public abstract AbstractQuantity<?> parse(CharSequence csq) throws
   * IllegalArgumentException, ParserException;
   */

  /**
   * Formats the specified value using {@link CompoundUnit} compound units}. The default implementation is locale sensitive and does not use space to
   * separate units. For example:[code] Unit<Length> FOOT_INCH = FOOT.compound(INCH); Measure<Length> height = Measure.valueOf(1.81, METER);
   * System.out.println(height.to(FOOT_INCH));
   * 
   * > 5ft11,26in // French Local
   * 
   * Unit<Angle> DMS = DEGREE_ANGLE.compound(MINUTE_ANGLE).compound(SECOND_ANGLE); Measure<Angle> rotation = Measure.valueOf(35.857497, DEGREE_ANGLE);
   * System.out.println(rotation.to(DMS));
   * 
   * > 35°51'26,989" // French Local [/code]
   * 
   * @param value
   *          the value to format using compound units.
   * @param unit
   *          the compound unit.
   * @param dest
   *          the appendable destination.
   * @return the specified <code>Appendable</code>.
   * @throws IOException
   *           if an I/O exception occurs.
   */
  // @SuppressWarnings("unchecked")
  // protected Appendable formatCompound(double value, CompoundUnit<?> unit,
  // Appendable dest) throws IOException {
  // Unit high = unit.getHigh();
  // Unit low = unit.getLow(); // The unit in which the value is stated.
  // long highValue = (long) low.getConverterTo(high).convert(value);
  // double lowValue = value - high.getConverterTo(low).convert(highValue);
  // if (high instanceof CompoundUnit)
  // formatCompound(highValue, (CompoundUnit) high, dest);
  // else {
  // dest.append(DEFAULT._numberFormat.format(highValue));
  // DEFAULT._unitFormat.format(high, dest);
  // }
  // dest.append(DEFAULT._numberFormat.format(lowValue));
  // return DEFAULT._unitFormat.format(low, dest);
  // }

  /*
   * public final StringBuffer format(Object obj, final StringBuffer
   * toAppendTo, FieldPosition pos) { if (!(obj instanceof
   * AbstractQuantity<?>)) throw new IllegalArgumentException(
   * "obj: Not an instance of Measure"); if ((toAppendTo == null) || (pos ==
   * null)) throw new NullPointerException(); try { return (StringBuffer)
   * format((AbstractQuantity<?>) obj, (Appendable) toAppendTo); } catch
   * (IOException ex) { throw new Error(ex); // Cannot happen. } }
   */

  /*
   * final AbstractQuantity<?> parseObject(String source, ParsePosition pos) {
   * try { return parse(source, pos); } catch (IllegalArgumentException |
   * ParserException e) { return null; // Unfortunately the message why the
   * parsing failed } // is lost; but we have to follow the Format spec.
   * 
   * }
   */

  /**
   * Convenience method equivalent to {@link #format(AbstractQuantity, Appendable)} except it does not raise an IOException.
   * 
   * @param quantity
   *          the quantity to format.
   * @param dest
   *          the appendable destination.
   * @return the specified <code>StringBuilder</code>.
   */
  public final StringBuilder format(Quantity<?> q, StringBuilder dest) {
    try {
      return (StringBuilder) this.format(q, (Appendable) dest);
    } catch (IOException ex) {
      throw new RuntimeException(ex); // Should not happen.
    }
  }

  /**
   * Formats an object to produce a string. This is equivalent to <blockquote> {@link #format(Unit, StringBuilder) format}<code>(unit,
   *         new StringBuilder()).toString();</code> </blockquote>
   *
   * @param obj
   *          The object to format
   * @return Formatted string.
   * @exception IllegalArgumentException
   *              if the Format cannot format the given object
   */
  public final String format(Quantity q) {
    if (q instanceof AbstractQuantity) {
      return format((AbstractQuantity<?>) q, new StringBuilder()).toString();
    } else {
      return (this.format(q, new StringBuilder())).toString();
    }
  }

  static int getFractionDigitsCount(double d) {
    if (d >= 1) { // we only need the fraction digits
      d = d - (long) d;
    }
    if (d == 0) { // nothing to count
      return 0;
    }
    d *= 10; // shifts 1 digit to left
    int count = 1;
    while (d - (long) d != 0) { // keeps shifting until there are no more
      // fractions
      d *= 10;
      count++;
    }
    return count;
  }

  // Holds default implementation.
  private static final class NumberSpaceUnit extends QuantityFormat {
    private final DecimalFormat decimalFormat = new DecimalFormat();

    private final UnitFormat unitFormat;

    private NumberSpaceUnit(NumberFormat numberFormat, UnitFormat unitFormat) {
      // decimalFormat.applyPattern("#,#0.0000##");
      this.unitFormat = unitFormat;
    }

    @Override
    public Appendable format(Quantity<?> quantity, Appendable dest) throws IOException {
      // Unit unit = quantity.getUnit();
      // if (unit instanceof CompoundUnit)
      // return formatCompound(quantity.doubleValue(unit),
      // (CompoundUnit) unit, dest);
      // else {
      // dest.append(numberFormat.format(quantity.getValue()));
      int fract = 0;
      if (quantity != null && quantity.getValue() != null) {
        fract = getFractionDigitsCount(quantity.getValue().doubleValue());
      }
      if (fract > 1) {
        decimalFormat.setMaximumFractionDigits(fract + 1);
      }
      dest.append(decimalFormat.format(quantity.getValue()));
      if (quantity.getUnit().equals(AbstractUnit.ONE))
        return dest;
      dest.append(' ');
      return unitFormat.format(quantity.getUnit(), dest);
      // }
    }

    @SuppressWarnings("unchecked")
    @Override
    AbstractQuantity<?> parse(CharSequence csq, int index) throws IllegalArgumentException, ParserException {
      String str = csq.toString();
      // Number number = parseFormat.parse(str); // TODO combine with
      // NumberFormat
      Number number = null; // FIXME
      if (number == null)
        throw new IllegalArgumentException("Number cannot be parsed");
      Unit unit = unitFormat.parse(csq);
      if (number instanceof Long)
        return NumberQuantity.of(number.longValue(), unit);
      else if (number instanceof Double)
        return NumberQuantity.of(number.doubleValue(), unit);
      else if (number instanceof Integer)
        return NumberQuantity.of(number.intValue(), unit);
      else
        throw new UnsupportedOperationException("Number of type " + number.getClass() + " are not supported");
    }

    public AbstractQuantity<?> parse(CharSequence csq) throws IllegalArgumentException, ParserException {
      return parse(csq, 0);
    }

    // private static final long serialVersionUID = 1L;

  }

  // Holds standard implementation.
  private static final class Standard extends QuantityFormat {

    /**
		 * 
		 */
    // private static final long serialVersionUID = 2758248665095734058L;

    @Override
    public Appendable format(Quantity q, Appendable dest) throws IOException {
      Unit unit = q.getUnit();
      // if (unit instanceof CompoundUnit)
      // return formatCompound(q.doubleValue(unit),
      // (CompoundUnit) unit, dest);
      // else {

      // if (q.isBig()) { // TODO SE only
      // BigDecimal decimal = q.decimalValue(unit,
      // MathContext.UNLIMITED);
      // dest.append(decimal.toString());
      // } else {
      Number number = q.getValue();
      dest.append(number.toString());
      // }
      if (q.getUnit().equals(Units.ONE))
        return dest;
      dest.append(' ');
      return SimpleUnitFormat.getInstance().format(unit, dest);
      // }
    }

    @SuppressWarnings("unchecked")
    @Override
    AbstractQuantity<?> parse(CharSequence csq, int index) throws ParserException {
      int startDecimal = index; // cursor.getIndex();
      while ((startDecimal < csq.length()) && Character.isWhitespace(csq.charAt(startDecimal))) {
        startDecimal++;
      }
      int endDecimal = startDecimal + 1;
      while ((endDecimal < csq.length()) && !Character.isWhitespace(csq.charAt(endDecimal))) {
        endDecimal++;
      }
      Double decimal = new Double(csq.subSequence(startDecimal, endDecimal).toString());
      // cursor.setIndex(endDecimal + 1);
      // Unit unit = EBNFUnitFormat.getInstance().parse(csq, index);
      Unit unit = SimpleUnitFormat.getInstance().parse(csq, index);
      return NumberQuantity.of(decimal.doubleValue(), unit);
    }

    public AbstractQuantity<?> parse(CharSequence csq) throws ParserException {
      return parse(csq, 0);
    }
  }
}
