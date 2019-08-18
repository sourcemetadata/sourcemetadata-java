// SPDX-FileCopyrightText: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model.jvm.maven;

import static lombok.Builder.Default;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.fidata.about.model.StringField;

@JsonDeserialize(builder = Developer.DeveloperBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Developer extends Contributor {
  public final StringField getId() {
    return getString("id");
  }

  protected static final class DeveloperBuilderImpl extends DeveloperBuilder<Developer, DeveloperBuilderImpl> {}
}
