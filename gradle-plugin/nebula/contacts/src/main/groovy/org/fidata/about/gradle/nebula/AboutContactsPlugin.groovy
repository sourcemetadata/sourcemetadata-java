// SPDX-FileCopyrightText: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.gradle.nebula

import groovy.transform.CompileStatic
import nebula.plugin.contacts.ContactsExtension
import nebula.plugin.contacts.ContactsPlugin
import org.fidata.about.gradle.extended.ExtendedAboutPlugin
import org.fidata.about.model.extended.ExtendedAbout
import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
class AboutContactsPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    project.plugins.withType(ExtendedAboutPlugin) { ExtendedAboutPlugin aboutPlugin ->
      ExtendedAbout about = aboutPlugin.about
      project.plugins.withType(ContactsPlugin) {
        project.extensions.configure(ContactsExtension) { ContactsExtension extension ->
          extension.addPerson()

        }

    }
  }
}
