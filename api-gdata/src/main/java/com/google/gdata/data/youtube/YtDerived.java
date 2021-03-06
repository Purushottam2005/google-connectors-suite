/**
 * Mule Google Api Commons
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package com.google.gdata.data.youtube;


import com.google.gdata.data.ExtensionDescription;

/**
 * Object representation for <code>yt:derived</code> which describes
 * how this caption track was derived from other data.
 *
 * 
 */
@ExtensionDescription.Default(
    nsAlias = YouTubeNamespace.PREFIX,
    nsUri = YouTubeNamespace.URI,
    localName = "derived")
public class YtDerived extends AbstractFreeTextExtension {

  /** Value is how the track was derived. */
  public static final class Value {

    /** AutoSync derived caption track. */
    public static final String AUTO_SYNC = "autoSync";

    /** SpeechRecognition derived caption track. */
    public static final String SPEECH_RECOGNITION = "speechRecognition";

    private Value() {}
  }

  /** Creates an empty tag. */
  public YtDerived() {
  }

  /** Creates a tag and initializes its content.
   *
   * @param derived content
   */
  public YtDerived(String derived) {
    super(derived);
  }

}
