/**
 * Mule Google Api Commons
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package com.google.gdata.model;

import com.google.gdata.model.Metadata.VirtualValue;

/**
 * An attribute creator allows setting attribute information, which includes the
 * following pieces:
 * <ul>
 * <li>name: The qualified name to use for input and output. Defaults to using
 * the value in the {@link AttributeKey} associated with the metadata. Setting
 * the name here will override the default.</li>
 * <li>required: Validates that the attribute exists in both input and output.
 * Defaults to false.</li>
 * <li>visible: Hides or shows an attribute in the output.
 * Defaults to true.</li>
 * </ul>
 *
 * 
 */
public interface AttributeCreator extends MetadataCreator {

  /**
   * Sets the name of the attribute.  This is used on parsing to decide which
   * field to place an attriubte in, and during generation to choose the display
   * name of the attribute.  This should only be set to override the default
   * name, which is the ID specified in the {@link AttributeKey}.
   *
   * @param name the new name to use for the attribute.
   * @return this attribute creator for chaining.
   */
  AttributeCreator setName(QName name);

  /**
   * Sets the requiredness of this attribute.  If set to true, this attribute
   * must appear in both the input and output or a validation error will occur.
   * If set to false, this attribute is optional.
   *
   * @param required true to set the attribute to required, false to set it
   *     to optional (the default).
   * @return this attribute creator for chaining.
   */
  AttributeCreator setRequired(boolean required);

  /**
   * Sets whether this attribute is visible.  If the attribute is not visible
   * then it will not be included in the output.  This can be used to hide an
   * attribute in particular contexts (such as RSS or JSON output).  It can
   * also be used to explicitly set an attribute to visible that may be hidden
   * by other metadata rules.
   *
   * @param visible true to make the attribute visible (the default), false to
   *     hide it from the output.
   * @return this attribute creator for chaining.
   */
  AttributeCreator setVisible(boolean visible);

  /**
   * Sets the virtual value for the attribute.  This is used as the value
   * of the attribute during parsing and generation.
   */
  AttributeCreator setVirtualValue(VirtualValue virtualValue);
}
