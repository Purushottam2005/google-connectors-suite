/**
 * Mule Google Api Commons
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package com.google.gdata.data.calendar;

import com.google.gdata.data.AttributeGenerator;
import com.google.gdata.data.AttributeHelper;
import com.google.gdata.data.ExtensionDescription;
import com.google.gdata.data.ExtensionPoint;
import com.google.gdata.util.ParseException;

/**
 * Indicates whether this is a private copy of the event, changes to which
 * should not be sent to other calendars.
 *
 * 
 */
@ExtensionDescription.Default(
    nsAlias = Namespaces.gCalAlias,
    nsUri = Namespaces.gCal,
    localName = PrivateCopyProperty.XML_NAME)
public class PrivateCopyProperty extends ExtensionPoint {

  /** XML element name */
  static final String XML_NAME = "privateCopy";

  /** XML "value" attribute name */
  private static final String VALUE = "value";

  /** Value */
  private Boolean value = null;

  /**
   * Default mutable constructor.
   */
  public PrivateCopyProperty() {
    super();
  }

  /**
   * Immutable constructor.
   *
   * @param value value.
   */
  public PrivateCopyProperty(Boolean value) {
    super();
    setValue(value);
    setImmutable(true);
  }

  /**
   * Returns the value.
   *
   * @return value
   */
  public Boolean getValue() {
    return value;
  }

  /**
   * Sets the value.
   *
   * @param value value or <code>null</code> to reset
   */
  public void setValue(Boolean value) {
    throwExceptionIfImmutable();
    this.value = value;
  }

  /**
   * Returns whether it has the value.
   *
   * @return whether it has the value
   */
  public boolean hasValue() {
    return getValue() != null;
  }

  @Override
  protected void validate() {
    if (value == null) {
      throwExceptionForMissingAttribute(VALUE);
    }
  }

  /**
   * Returns the extension description, specifying whether it is required, and
   * whether it is repeatable.
   *
   * @param required   whether it is required
   * @param repeatable whether it is repeatable
   * @return extension description
   */
  public static ExtensionDescription getDefaultDescription(boolean required,
      boolean repeatable) {
    ExtensionDescription desc =
        ExtensionDescription.getDefaultDescription(PrivateCopyProperty.class);
    desc.setRequired(required);
    desc.setRepeatable(repeatable);
    return desc;
  }

  @Override
  protected void putAttributes(AttributeGenerator generator) {
    generator.put(VALUE, value);
  }

  @Override
  protected void consumeAttributes(AttributeHelper helper) throws ParseException
      {
    value = helper.consumeBoolean(VALUE, true);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!sameClassAs(obj)) {
      return false;
    }
    PrivateCopyProperty other = (PrivateCopyProperty) obj;
    return eq(value, other.value);
  }

  @Override
  public int hashCode() {
    int result = getClass().hashCode();
    if (value != null) {
      result = 37 * result + value.hashCode();
    }
    return result;
  }

  @Override
  public String toString() {
    return "{PrivateCopyProperty value=" + value + "}";
  }


  public final static PrivateCopyProperty TRUE = new PrivateCopyProperty(true);
  public final static PrivateCopyProperty FALSE =
      new PrivateCopyProperty(false);
}
