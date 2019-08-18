// SPDX-FileCopyrightText: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import java.util.Locale;
import lombok.ToString;
import org.apache.commons.lang3.ArrayUtils;

@ToString(callSuper = true)
public final class BooleanField extends Field<Boolean> {
  public static final BooleanField FALSE = new BooleanField(false);
  public static final BooleanField TRUE = new BooleanField(true);
  public static final BooleanField NULL = new BooleanField(null);

  private BooleanField(Boolean booleanValue) {
    super(booleanValue);
  }

  // TOTHINK: use ImmutableSet instead
  /*
   * @third party code - BEGIN aboutcode-toolkit
   * SnippetSource: src/attributecode/model.py#BooleanField
   * SnippetCopyrightText: Copyright (c) 2013-2019 nexB Inc. http://www.nexb.com/ - All rights reserved.
   * LicenseInfoInSnippet: Apache-2.0
   */
  private static final String[] TRUE_FLAGS = {"yes", "y", "true", "x"};
  private static final String[] FALSE_FLAGS = {"no", "n", "false"};
  private static final String[] FLAG_VALUES;
  static {
    final int trueFlagsLen = TRUE_FLAGS.length;
    final int falseFlagsLen = FALSE_FLAGS.length;
    FLAG_VALUES = new String[trueFlagsLen + falseFlagsLen];
    System.arraycopy(TRUE_FLAGS, 0, FLAG_VALUES, 0, trueFlagsLen);
    System.arraycopy(FALSE_FLAGS, 0, FLAG_VALUES, trueFlagsLen, falseFlagsLen);
  }
  // @third party code - END aboutcode-toolkit

  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  public static BooleanField of(String stringValue) {
    if (stringValue == null) {
      return NULL;
    }
    stringValue = stringValue.toLowerCase(Locale.ROOT);
    if (ArrayUtils.contains(TRUE_FLAGS, stringValue)) {
      return TRUE;
    } else if (ArrayUtils.contains(FALSE_FLAGS, stringValue)) {
      return FALSE;
    }
    throw new IllegalArgumentException(String.format("Invalid flag value: '%s' is not one of: %s", stringValue, Arrays.toString(FLAG_VALUES)));
  }

  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  public static BooleanField of(Boolean boolValue) {
    return boolValue == null ? NULL : boolValue ? TRUE : FALSE;
  }
}
